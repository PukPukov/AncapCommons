package ru.ancap.commons.instructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Super-simple event-bus. Written only to test the SafeMap.
 */
public class SimpleEventBus<EVENT> implements Instructor<EVENT> {
    
    private final List<Consumer<EVENT>> listeners = new ArrayList<>();

    @Override
    public void accept(Consumer<EVENT> consumer) {
        this.listeners.add(consumer);
    }
    
    public void dispatch(EVENT event) {
        for (Consumer<EVENT> listener : this.listeners) {
            listener.accept(event);
        }
    }
    
}
