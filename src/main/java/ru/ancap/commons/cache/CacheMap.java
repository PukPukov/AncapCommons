package ru.ancap.commons.cache;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@RequiredArgsConstructor
@ToString @EqualsAndHashCode
public class CacheMap<K, T> {

    private final long cacheValidity;

    private final Map<K, Cache<T>> cache = new HashMap<>();

    public CacheMap() { this(99999999999L); }
    
    public T get(K key) {
        return this.get(key, null);
    }

    public T get(K key, Supplier<T> ifExpired) {
        Cache<T> localCache = this.cache.get(key);
        if (localCache == null) {
            localCache = new Cache<>(this.cacheValidity);
            this.cache.put(key, localCache);
        }
        return localCache.get(ifExpired);
    }
    
}
