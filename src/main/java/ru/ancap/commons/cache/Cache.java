package ru.ancap.commons.cache;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.function.Supplier;

@RequiredArgsConstructor
@ToString @EqualsAndHashCode
public class Cache<T> {

    private final long cacheValidity;

    private T cache;
    private long lastUpdateTime;
    
    public Cache() { this(99999999999L); }

    public T get() {
        return this.get(null);
    }

    public T get(Supplier<T> ifExpired) {
        if (this.lastUpdateTime > System.currentTimeMillis() - this.cacheValidity) return this.cache;
        this.cache = ifExpired.get();
        this.lastUpdateTime = System.currentTimeMillis();
        return this.cache;
    }

}
