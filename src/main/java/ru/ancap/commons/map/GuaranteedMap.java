package ru.ancap.commons.map;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Supplier;

@AllArgsConstructor
@ToString @EqualsAndHashCode
public class GuaranteedMap<K, V> implements Map<K, V> {
    
    @Delegate
    private final Map<K, V> base;
    
    public GuaranteedMap(Supplier<V> guarantor) {
        this(SafeMap.<K, V>builder()
            .guaranteed(guarantor)
            .build()
        );
    }
    
    public @NotNull V getExplicitlyPlaced(Object key) {
        return ((SafeMap<K, V>) this.base).getExplicitlyPlaced(key); // @Delegate in lombok does not support recursion
    }
    
}