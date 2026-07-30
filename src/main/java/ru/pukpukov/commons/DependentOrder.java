// нейрослоп, не редактировать вручную
package ru.pukpukov.commons;

import net.jcip.annotations.NotThreadSafe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.pukpukov.commons.documentation.AISlop;
import ru.pukpukov.commons.documentation.threading.Mutable;

import java.util.*;

@Mutable @NotThreadSafe
@AISlop @SuppressWarnings("all")
public class DependentOrder<K, V> {
    
    // Результат (Snapshot), безопасный для чтения из других потоков
    public volatile List<V> atomicItems = Collections.emptyList();
    
    // Хранилище значений (только существующие элементы)
    private final Map<K, V> values = new HashMap<>();
    
    // Хранилище ограничений: Ключ -> Набор ID, которые должны быть ДО ключа (Key зависит от Value)
    private final Map<K, Set<K>> mustBeAfterConstraints = new HashMap<>();
    
    // Хранилище ограничений: Ключ -> Набор ID, которые должны быть ПОСЛЕ ключа (Key является зависимостью для Value)
    private final Map<K, Set<K>> mustBeBeforeConstraints = new HashMap<>();
    
    /**
     * Добавляет элемент. Зависимости могут указывать на еще не существующие элементы.
     */
    public void add(K id, @NotNull V value, @Nullable Set<K> mustBeAfter, @Nullable Set<K> mustBeBefore) {
        if (this.values.containsKey(id)) {
            throw new IllegalArgumentException("Node with ID " + id + " already exists.");
        }
        
        // 1. Сохраняем значение
        this.values.put(id, value);
        
        // 2. Сохраняем ограничения (копируем для безопасности)
        if (mustBeAfter != null && !mustBeAfter.isEmpty()) {
            this.mustBeAfterConstraints.put(id, new HashSet<>(mustBeAfter));
        }
        if (mustBeBefore != null && !mustBeBefore.isEmpty()) {
            this.mustBeBeforeConstraints.put(id, new HashSet<>(mustBeBefore));
        }
        
        // 3. Пересчитываем порядок
        rebuild();
    }
    
    /**
     * Удаляет элемент по ID и обновляет порядок оставшихся.
     */
    public void remove(K id) {
        if (!this.values.containsKey(id)) {
            return; // Или можно бросить исключение, если требуется строгая логика
        }
        
        // 1. Удаляем значение
        this.values.remove(id);
        
        // 2. Удаляем ограничения, связанные с этим ID (как "владельцем" ограничений)
        // Примечание: Мы НЕ удаляем id из списков зависимостей других нод, 
        // так как если этот id добавят снова, связь должна восстановиться.
        this.mustBeAfterConstraints.remove(id);
        this.mustBeBeforeConstraints.remove(id);
        
        // 3. Пересчитываем порядок
        rebuild();
    }
    
    /**
     * Строит эффективный граф на основе СУЩЕСТВУЮЩИХ элементов и их ограничений,
     * сортирует его и обновляет atomicItems.
     */
    private void rebuild() {
        // Строим граф только для ключей, которые есть в values
        Map<K, Set<K>> effectiveAdjacency = new HashMap<>();
        
        // Инициализируем граф всеми существующими узлами
        for (K id : values.keySet()) {
            effectiveAdjacency.put(id, new HashSet<>());
        }
        
        // Заполняем ребра на основе сохраненных ограничений
        for (K currentId : values.keySet()) {
            // Обработка mustBeAfter (Target -> Current)
            // Current должен быть ПОСЛЕ Target. Значит ребро от Target к Current.
            Set<K> afterDependencies = mustBeAfterConstraints.get(currentId);
            if (afterDependencies != null) {
                for (K dependencyId : afterDependencies) {
                    // Создаем ребро только если зависимость фактически существует сейчас
                    if (values.containsKey(dependencyId)) {
                        effectiveAdjacency.get(dependencyId).add(currentId);
                    }
                }
            }
            
            // Обработка mustBeBefore (Current -> Target)
            // Current должен быть ПЕРЕД Target. Значит ребро от Current к Target.
            Set<K> beforeDependencies = mustBeBeforeConstraints.get(currentId);
            if (beforeDependencies != null) {
                for (K targetId : beforeDependencies) {
                    // Создаем ребро только если целевой узел фактически существует сейчас
                    if (values.containsKey(targetId)) {
                        effectiveAdjacency.get(currentId).add(targetId);
                    }
                }
            }
        }
        
        // Сортируем
        List<K> sortedIds = performTopologicalSort(effectiveAdjacency);
        
        // Собираем итоговые значения
        List<V> sortedValues = new ArrayList<>(sortedIds.size());
        for (K sortedId : sortedIds) {
            sortedValues.add(this.values.get(sortedId));
        }
        
        // Атомарно подменяем список
        this.atomicItems = List.copyOf(sortedValues);
    }
    
    private List<K> performTopologicalSort(Map<K, Set<K>> adjacencyList) {
        Map<K, Integer> inDegree = new HashMap<>();
        
        // Инициализация inDegree нулями для всех узлов графа
        for (K node : adjacencyList.keySet()) {
            inDegree.put(node, 0);
        }
        
        // Подсчет входящих ребер
        for (Map.Entry<K, Set<K>> entry : adjacencyList.entrySet()) {
            for (K neighbor : entry.getValue()) {
                // ВАЖНО: adjacencyList мы строим только из существующих ключей,
                // поэтому neighbor гарантированно есть в map, но проверка на null полезна для отладки
                inDegree.merge(neighbor, 1, Integer::sum);
            }
        }
        
        Queue<K> queue = new LinkedList<>();
        for (Map.Entry<K, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) queue.add(entry.getKey());
        }
        
        List<K> result = new ArrayList<>();
        
        while (!queue.isEmpty()) {
            K current = queue.poll();
            result.add(current);
            
            if (adjacencyList.containsKey(current)) {
                for (K neighbor : adjacencyList.get(current)) {
                    int updatedDegree = inDegree.get(neighbor) - 1;
                    inDegree.put(neighbor, updatedDegree);
                    
                    if (updatedDegree == 0) {
                        queue.add(neighbor);
                    }
                }
            }
        }
        
        if (result.size() != adjacencyList.size()) {
            // Чтобы не ломать состояние при цикле (например, добавили элемент, замкнувший круг),
            // мы не сохраняем результат в atomicItems, а бросаем ошибку.
            // Однако, так как add/remove уже изменили values/constraints, класс останется в
            // некорректном состоянии (с циклом), пока его не починят удалением элемента.
            throw new IllegalStateException("Impossible constraints: Cycle detected in dependencies.");
        }
        
        return result;
    }
}