package ru.ancap.commons.instructor;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Super-simple event-bus.
 */
@ToString @EqualsAndHashCode
@RequiredArgsConstructor
public class SimpleEventBus<EVENT> implements Instructor<EVENT>, EventDispatcher<EVENT>, EventBus<EVENT> {
    
    private final Collection<Consumer<EVENT>> listeners;

    public SimpleEventBus() { this(new CopyOnWriteArrayList<>()); }

    @Override
    public void subscribe(Consumer<EVENT> consumer) {
        this.listeners.add(consumer);
    }
    
    @Override
    public void dispatch(EVENT event) {
        for (Consumer<EVENT> listener : this.listeners) listener.accept(event);
    }
    
}