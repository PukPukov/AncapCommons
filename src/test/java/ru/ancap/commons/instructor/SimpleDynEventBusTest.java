package ru.ancap.commons.instructor;

import org.junit.jupiter.api.Test;
import ru.ancap.commons.iterable.IterableToArray;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class SimpleDynEventBusTest {
    
    @Test
    public void test() {
        String listenID = UUID.randomUUID().toString();
        SimpleDynEventBus<Integer> eventBus = new SimpleDynEventBus<>();
        List<Integer> collector = new ArrayList<>();
        
        eventBus.subscribe(listenID, collector::add);
        eventBus.dispatch(100);
        eventBus.dispatch(200);
        eventBus.dispatch(300);
        
        eventBus.unsubscribe(listenID);
        eventBus.dispatch(400);
        eventBus.dispatch(500);
        
        assertArrayEquals(
            IterableToArray.deepRecursiveReflective(List.of(100, 200, 300)),
            IterableToArray.deepRecursiveReflective(collector)
        );
    }
    
}