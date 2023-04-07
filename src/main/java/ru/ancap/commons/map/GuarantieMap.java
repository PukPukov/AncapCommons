package ru.ancap.commons.map;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@AllArgsConstructor
@ToString @EqualsAndHashCode
public class GuarantieMap<K, V> implements Map<K, V> {
    
    @Delegate(excludes = LombokGetExclude.class)
    private final Map<K, V> base;
    private final Supplier<V> guarantor;
    
    public GuarantieMap(Supplier<V> guarantor) {
        this(new HashMap<>(), guarantor);
    }

    @Override
    public @NotNull V get(Object key) {
        V value = this.base.get(key);
        if (value == null) {
            value = this.guarantor.get();
            this.base.put((K) key, value);
        }
        return value;
    }

    interface LombokGetExclude<T> { T get(Object key); }
    
}
