package ru.ancap.commons.cache;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.ancap.commons.time.FixedTimeProvider;
import ru.ancap.commons.time.TimeProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CacheMapTest {
    
    @Test
    public void test() {
        Cache.timeProvider = FixedTimeProvider.startFromCurrent();
        CacheMap<String, String> cacheMap = new CacheMap<>();
        assertEquals("example-test", cacheMap.get("1", () -> exampleFunction("example")));
        assertEquals("example-test", cacheMap.get("1"));
        assertThrows(NullPointerException.class, () -> cacheMap.get("2"));
        Cache.timeProvider = TimeProvider.SYSTEM_CLOCK;
    }
    
    @Test
    @SneakyThrows
    public void testExpired() {
        var timeProvider = FixedTimeProvider.startFromCurrent();
        Cache.timeProvider = timeProvider;
        CacheMap<String, String> cacheMap = new CacheMap<>(10);
        assertEquals("example-test", cacheMap.get("1", () -> exampleFunction("example")));
        timeProvider.emulateWait(11);
        assertThrows(NullPointerException.class, () -> cacheMap.get("1"));
        Cache.timeProvider = TimeProvider.SYSTEM_CLOCK;
    }
    
    private String exampleFunction(String base) {
        return base+"-test";
    }
    
}