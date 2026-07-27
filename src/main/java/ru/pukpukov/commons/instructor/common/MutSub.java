package ru.pukpukov.commons.instructor.common;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.pukpukov.commons.instructor.SimpleEventBus;

/**
 * Subscribe to field mutation.
 */
@NoArgsConstructor @AllArgsConstructor
public class MutSub<T> {

    public final SimpleEventBus<T> onChange = new SimpleEventBus<>();

    private T value;

    public void set(T new_) {
        this.value = new_;
        this.onChange.dispatch(new_);
    }

    public T get() {
        return this.value;
    }

}