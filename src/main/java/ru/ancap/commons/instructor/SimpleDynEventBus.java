package ru.ancap.commons.instructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class SimpleDynEventBus<EVENT> implements DynInstructor<EVENT>, EventDispatcher<EVENT> {
    
    private final Map<String, Consumer<EVENT>> consumers = new ConcurrentHashMap<>();
    
    @Override
    public void subscribe(String id, Consumer<EVENT> consumer) {
        this.consumers.put(id, consumer);
    }
    
    @Override
    public void unsubscribe(String id) {
        this.consumers.remove(id);
    }
    
    @Override
    public void dispatch(EVENT event) {
        for (var entry : this.consumers.entrySet()) {
            entry.getValue().accept(event);
        }
    }
    
}