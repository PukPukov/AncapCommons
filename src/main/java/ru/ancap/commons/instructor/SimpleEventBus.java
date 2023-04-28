package ru.ancap.commons.instructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Super-simple event-bus.
 */
public class SimpleEventBus<EVENT> implements EventBus<EVENT> {
    
    private final List<Consumer<EVENT>> listeners = new ArrayList<>();

    @Override
    public void accept(Consumer<EVENT> consumer) {
        this.listeners.add(consumer);
    }
    
    @Override
    public void dispatch(EVENT event) {
        for (Consumer<EVENT> listener : this.listeners) {
            listener.accept(event);
        }
    }
    
}
