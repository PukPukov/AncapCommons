package ru.ancap.commons.cache;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CacheTest {

    @Test
    public void test() {
        Cache<String> cache = new Cache<>();
        assertEquals("example-test", cache.get(() -> exampleFunction("example")));
        assertEquals("example-test", cache.get());
    }

    @Test
    @SneakyThrows
    public void testExpired() {
        Cache<String> cache = new Cache<>(10);
        assertEquals("example-test", cache.get(() -> exampleFunction("example")));
        Thread.sleep(11);
        assertThrows(NullPointerException.class, cache::get);
    }

    private String exampleFunction(String base) {
        return base+"-test";
    }
    
}
