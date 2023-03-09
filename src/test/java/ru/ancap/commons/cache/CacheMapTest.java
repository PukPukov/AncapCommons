package ru.ancap.commons.cache;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CacheMapTest {
    
    @Test
    public void test() {
        CacheMap<String, String> cacheMap = new CacheMap<>();
        assertEquals("example-test", cacheMap.get("1", () -> exampleFunction("example")));
        assertEquals("example-test", cacheMap.get("1"));
        assertThrows(NullPointerException.class, () -> cacheMap.get("2"));
    }
    
    @Test
    @SneakyThrows
    public void testExpired() {
        CacheMap<String, String> cacheMap = new CacheMap<>(10);
        assertEquals("example-test", cacheMap.get("1", () -> exampleFunction("example")));
        Thread.sleep(11);
        assertThrows(NullPointerException.class, () -> cacheMap.get("1"));
    }
    
    private String exampleFunction(String base) {
        return base+"-test";
    }
    
}
