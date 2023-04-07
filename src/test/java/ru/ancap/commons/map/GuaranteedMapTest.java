package ru.ancap.commons.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GuaranteedMapTest {
    
    @Test
    public void test() {
        GuaranteedMap<String, List<String>> map1 = new GuaranteedMap<>(ArrayList::new);
        map1.get("example").add("some");
        Assertions.assertEquals("some", map1.get("example").get(0));

        GuaranteedMap<String, List<String>> map2 = new GuaranteedMap<>(ArrayList::new);
        map2.put("example", new ArrayList<>());
        map2.get("example").add("some");
        Assertions.assertEquals("some", map2.get("example").get(0));
    }
    
}
