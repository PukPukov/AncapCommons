package ru.ancap.commons.cache;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.ancap.commons.time.FixedTimeProvider;
import ru.ancap.commons.time.TimeProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CacheTest {

    @Test
    public void test() {
        Cache.timeProvider = FixedTimeProvider.startFromCurrent();
        Cache<String> cache = new Cache<>();
        assertEquals("example-test", cache.get(() -> exampleFunction("example")));
        assertEquals("example-test", cache.get());
        Cache.timeProvider = TimeProvider.SYSTEM_CLOCK;
    }

    @Test
    @SneakyThrows
    public void testExpired() {
        var timeProvider = FixedTimeProvider.startFromCurrent();
        Cache.timeProvider = timeProvider;
        Cache<String> cache = new Cache<>(10);
        assertEquals("example-test", cache.get(() -> exampleFunction("example")));
        timeProvider.emulateWait(11);
        assertThrows(NullPointerException.class, cache::get);
        Cache.timeProvider = TimeProvider.SYSTEM_CLOCK;
    }

    private String exampleFunction(String base) {
        return base+"-test";
    }
    
}