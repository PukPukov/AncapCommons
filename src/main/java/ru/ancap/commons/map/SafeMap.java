package ru.ancap.commons.map;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Delegate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.ancap.commons.declarative.flow.Branch;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString @EqualsAndHashCode
public class SafeMap<K, V> implements Map<K, V> {
    
    public static <K, V> Builder<K, V> builder() { return new Builder<>(new ConcurrentHashMap<>()); }
    public static <K, V> Builder<K, V> builder(Map<K, V> base) { return new Builder<>(base); } 

    @RequiredArgsConstructor
    public static class Builder<K, V> {
        
        private final Map<K, V> base;
        private @Nullable Function<K, V> guarantor;
        private @Nullable MapGC<K> gc;
        
        public Builder<K, V> guaranteed(Supplier<V> guarantor) { this.guarantor = (__) -> guarantor.get(); return this; }
        public Builder<K, V> guaranteedF(Function<K, V> guarantor) { this.guarantor = guarantor; return this; }
        public Builder<K, V> collectGarbage(MapGC<K> gc) { this.gc = gc; return this; }
        
        public SafeMap<K, V> build() {
            SafeMap<K, V> map = new SafeMap<>(this.base, Branch.of(
                this.guarantor != null,
                key -> {
                    V value = this.base.get(key);
                    if (value == null) {
                        value = this.guarantor.apply(key);
                        this.base.put(key, value);
                    }
                    return value;
                },
                this.base::get
            ));
            if (this.gc != null) this.gc.add(map);
            return map;
        }
        
    }
    
    @Delegate(excludes = LombokExclude.class)
    private final Map<K, V> base;
    private final Function<K, V> getStrategy;

    @Override
    public @NotNull V get(Object key) {
        return this.getStrategy.apply((K) key);
    }
    
    @Override
    public boolean containsKey(Object key) {
        return this.base.containsKey(key);
    }
    
    public @NotNull V getExplicitlyPlaced(Object key) {
        var value = this.base.get(key);
        if (value == null) throw new NoSuchElementException();
        return value;
    }

    private interface LombokExclude<T> {
        T get(Object key);
        boolean containsKey(Object key);
    }
    
}