package ru.pukpukov.commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import ru.pukpukov.commons.documentation.AISlop;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@AISlop
class DependentOrderTest {
    
    // Тестируемый экземпляр. K=String, V=String
    private DependentOrder<String, String> order;
    
    @BeforeEach
    void setUp() {
        order = new DependentOrder<>();
    }
    
    @Nested
    @DisplayName("Базовый функционал добавления (add)")
    class AddTests {
        
        @Test
        @DisplayName("Добавление элементов без зависимостей")
        void addWithoutDependencies() {
            order.add("1", "A", null, null);
            order.add("2", "B", null, null);
            
            assertThat(order.atomicItems)
                .hasSize(2)
                .contains("A", "B"); // Порядок не гарантирован без зависимостей, но элементы должны быть
        }
        
        @Test
        @DisplayName("Ошибка при дублировании ID")
        void addDuplicateIdThrowsException() {
            order.add("1", "A", null, null);
            
            assertThatThrownBy(() -> order.add("1", "New A", null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already exists"); // Опционально, если есть сообщение
        }
        
        @Test
        @DisplayName("Поле atomicItems не должно быть null изначально")
        void atomicItemsIsNotNullInitially() {
            assertThat(order.atomicItems).isNotNull().isEmpty();
        }
        
        @Test
        @DisplayName("Возвращаемый список неизменяем (Immutable)")
        void atomicItemsIsImmutable() {
            order.add("1", "A", null, null);
            List<String> items = order.atomicItems;
            
            assertThatThrownBy(() -> items.add("B"))
                .isInstanceOf(UnsupportedOperationException.class);
        }
    }
    
    @Nested
    @DisplayName("Топологическая сортировка и зависимости")
    class TopologicalSortTests {
        
        @Test
        @DisplayName("Простая зависимость 'mustBeAfter' (B после A)")
        void simpleMustBeAfter() {
            // "B" зависит от "A" (должен быть после "A")
            order.add("A", "valA", null, null);
            order.add("B", "valB", Set.of("A"), null);
            
            assertThat(order.atomicItems).containsExactly("valA", "valB");
        }
        
        @Test
        @DisplayName("Простая зависимость 'mustBeBefore' (A до B)")
        void simpleMustBeBefore() {
            // "A" должен быть перед "B"
            order.add("A", "valA", null, Set.of("B"));
            order.add("B", "valB", null, null);
            
            assertThat(order.atomicItems).containsExactly("valA", "valB");
        }
        
        @Test
        @DisplayName("Цепочка зависимостей A -> B -> C")
        void dependencyChain() {
            // Добавляем в хаотичном порядке, но с зависимостями
            // C зависит от B, B зависит от A
            order.add("C", "valC", Set.of("B"), null);
            order.add("A", "valA", null, null);
            order.add("B", "valB", Set.of("A"), null);
            
            assertThat(order.atomicItems).containsExactly("valA", "valB", "valC");
        }
        
        @Test
        @DisplayName("Смешанные зависимости (Before и After)")
        void mixedDependencies() {
            // A после B
            // C перед B
            // Ожидаемый порядок: C -> B -> A
            order.add("A", "valA", Set.of("B"), null);
            order.add("B", "valB", null, null);
            order.add("C", "valC", null, Set.of("B"));
            
            assertThat(order.atomicItems).containsExactly("valC", "valB", "valA");
        }
    }
    
    @Nested
    @DisplayName("Lazy Dependency Resolution (Ленивое разрешение)")
    class LazyResolutionTests {
        
        @Test
        @DisplayName("Зависимость от несуществующего ключа игнорируется, пока ключ не добавлен")
        void dependsOnMissingKey() {
            // A зависит от B, но B еще нет
            order.add("A", "valA", Set.of("B"), null);
            
            // Пока B нет, A просто добавляется
            assertThat(order.atomicItems).contains("valA");
            
            // Добавляем B. Теперь правило "A после B" должно заработать
            order.add("B", "valB", null, null);
            
            assertThat(order.atomicItems).containsExactly("valB", "valA");
        }
        
        @Test
        @DisplayName("Круговая ленивая загрузка")
        void circularLazyLoadingSequence() {
            // 1. Добавляем C (хочет быть после B). B нет -> [C]
            order.add("C", "valC", Set.of("B"), null);
            assertThat(order.atomicItems).containsExactly("valC");
            
            // 2. Добавляем B (хочет быть после A). A нет -> B игнорирует A, но C видит B.
            // Порядок: B, C
            order.add("B", "valB", Set.of("A"), null);
            assertThat(order.atomicItems).containsExactly("valB", "valC");
            
            // 3. Добавляем A. Теперь B видит A.
            // Порядок: A, B, C
            order.add("A", "valA", null, null);
            assertThat(order.atomicItems).containsExactly("valA", "valB", "valC");
        }
    }
    
    @Nested
    @DisplayName("Удаление элементов (remove)")
    class RemoveTests {
        
        @Test
        @DisplayName("Удаление существующего элемента")
        void removeExisting() {
            order.add("1", "A", null, null);
            order.add("2", "B", null, null);
            
            order.remove("1");
            
            assertThat(order.atomicItems)
                .hasSize(1)
                .containsExactly("B");
        }
        
        @Test
        @DisplayName("Удаление несуществующего элемента (no-op)")
        void removeNonExisting() {
            order.add("1", "A", null, null);
            
            order.remove("999"); // Не должно падать
            
            assertThat(order.atomicItems).containsExactly("A");
        }
        
        @Test
        @DisplayName("При удалении элемента зависимость от него становится 'спящей'")
        void removeMakesDependencyInactive() {
            // A -> B (A зависит от B)
            order.add("B", "valB", null, null);
            order.add("A", "valA", Set.of("B"), null);
            
            assertThat(order.atomicItems).containsExactly("valB", "valA");
            
            // Удаляем B. Теперь A больше не ограничивается B (так как B нет)
            order.remove("B");
            assertThat(order.atomicItems).containsExactly("valA");
            
            // Возвращаем B. Зависимость A от B должна снова активироваться
            order.add("B", "valB", null, null);
            assertThat(order.atomicItems).containsExactly("valB", "valA");
        }
    }
    
    @Nested
    @DisplayName("Обработка циклических зависимостей")
    class CycleTests {
        
        @Test
        @DisplayName("Прямой цикл A <-> B вызывает IllegalStateException")
        void directCycle() {
            order.add("A", "valA", Set.of("B"), null); // A после B
            
            // Пытаемся добавить B, который должен быть после A
            // A -> B -> A
            assertThatThrownBy(() -> order.add("B", "valB", Set.of("A"), null))
                .isInstanceOf(IllegalStateException.class);
        }
        
        @Test
        @DisplayName("Цикл через mustBeBefore")
        void cycleViaBefore() {
            order.add("A", "valA", null, Set.of("B")); // A до B
            
            // Добавляем B, который хочет быть до A
            // B -> A -> B
            assertThatThrownBy(() -> order.add("B", "valB", null, Set.of("A")))
                .isInstanceOf(IllegalStateException.class);
        }
        
        @Test
        @DisplayName("Цикл возникающий при разрешении ленивой зависимости")
        void lazyCycle() {
            // A зависит от B (B еще нет)
            order.add("A", "valA", Set.of("B"), null);
            
            // B зависит от A
            // В момент добавления B система видит: A зависит от B, и B зависит от A
            assertThatThrownBy(() -> order.add("B", "valB", Set.of("A"), null))
                .isInstanceOf(IllegalStateException.class);
        }
    }
}