package ru.ancap.commons.map;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

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
    
}
