package ru.ancap.commons.map;

import org.junit.jupiter.api.Test;
import ru.ancap.commons.instructor.SimpleEventBus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SafeMapTest {
    
    @Test
    public void test() {
        SimpleEventBus<Integer> deletions = new SimpleEventBus<>();
        SafeMap<String, List<String>> map = SafeMap.<String, List<String>>builder()
            .guaranteed(ArrayList::new)
            .collectGarbage( deletions
                .map(Object::toString)
                .as(MapGC::new)
            )
            .build();
        map.put("1", new ArrayList<>());
        map.put("2", new LinkedList<>());
        map.put("3", new Vector<>());
        map.put("4", new ArrayList<>());
        map.put("5", new ArrayList<>());
        deletions.dispatch(4);
        deletions.dispatch(5);
        assertEquals(3, map.size());
        assertDoesNotThrow(() -> map.get("6").add("some string"));
        assertEquals("some string", map.get("6").get(0));
    }
    
}