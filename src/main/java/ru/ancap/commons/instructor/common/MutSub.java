package ru.ancap.commons.instructor.common;

import ru.ancap.commons.instructor.EventBus;
import ru.ancap.commons.instructor.SimpleEventBus;

/**
 * Subscribe to field mutation.
 */
public class MutSub<T> {

    public final EventBus<T> onChange = new SimpleEventBus<>();

    private T value;

    public void set(T new_) {
        this.value = new_;
        this.onChange.dispatch(new_);
    }

    public T get() {
        return this.value;
    }

}