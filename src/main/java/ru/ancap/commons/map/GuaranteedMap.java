package ru.ancap.commons.map;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Delegate;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString @EqualsAndHashCode
public class GuaranteedMap<K, V> implements Map<K, V> {
    
    @Delegate
    private final Map<K, V> base;
    
    public GuaranteedMap(Map<K, V> base, Supplier<V> guarantor) {
        this(SafeMap.builder(base)
            .guaranteed(guarantor)
            .build());
    }
    
    public GuaranteedMap(Supplier<V> guarantor) {
        this(new HashMap<>(), guarantor);
    }
    
    public @NotNull V getExplicitlyPlaced(Object key) {
        return ((SafeMap<K, V>) this.base).getExplicitlyPlaced(key); // @Delegate in lombok does not support recursion
    }
    
}