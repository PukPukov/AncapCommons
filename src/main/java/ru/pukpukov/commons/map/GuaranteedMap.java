package ru.pukpukov.commons.map;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Delegate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.pukpukov.commons.null_.NnulMap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString @EqualsAndHashCode
public class GuaranteedMap<K, V> implements NnulMap<K, V> {
    
    private final SafeMap<K, V> base; // two fields are required due to lack of nested @Delegate support in lombok
    @Delegate
    private final Map<K, V> baseDelegate;
    
    private final Map<K, V> base2;
    
    public GuaranteedMap(Map<K, V> base, Supplier<V> guarantor) {
        this(SafeMap.builder(base)
            .guaranteed(guarantor)
            .build());
    }
    
    public GuaranteedMap(Supplier<V> guarantor) {
        this(new HashMap<>(), guarantor);
    }
    
    public GuaranteedMap(SafeMap<K, V> base) {
        this.base = base;
        this.baseDelegate = base;
        this.base2 = base.getBase();
    }
    
    public @NotNull V getExplicitlyPlaced(Object key) {
        return this.base.getExplicitlyPlaced(key);
    }
    
    // Мне кажется в один момент надо будет уже рефакторинг всех этих сейфмапов делать.
    public @Nullable V getExplicitlyPlacedNullable(K key) {
        return this.base2.get(key);
    } 
    
}