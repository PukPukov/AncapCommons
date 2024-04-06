package ru.ancap.commons.cache;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.ancap.commons.time.TimeProvider;

import java.util.function.Supplier;

@RequiredArgsConstructor
@ToString @EqualsAndHashCode
public class Cache<T> {
    
    public static TimeProvider timeProvider = TimeProvider.SYSTEM_CLOCK;

    private final long cacheValidity;

    private T cache;
    private long lastUpdateTime;
    
    public Cache() { this(99999999999L); }

    public T get() {
        return this.get(null);
    }

    public T get(Supplier<T> ifExpired) {
        if (this.lastUpdateTime > timeProvider.currentTime() - this.cacheValidity) return this.cache;
        this.cache = ifExpired.get();
        this.lastUpdateTime = timeProvider.currentTime();
        return this.cache;
    }

}