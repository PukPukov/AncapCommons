package ru.ancap.commons;

import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

/**
 * <b>Deprecated</b>, use {@link ru.ancap.commons.cache.Cache}
 */
@Deprecated(forRemoval = true)
@RequiredArgsConstructor
public class Cache<T> {
    
    private final long cacheValidity;
    
    private T cache;
    private long lastUpdateTime;
    
    public T get(Supplier<T> ifExpired) {
        if (this.lastUpdateTime > System.currentTimeMillis() - this.cacheValidity) return this.cache;
        this.cache = ifExpired.get();
        this.lastUpdateTime = System.currentTimeMillis();
        return this.cache;
    }
    
}
