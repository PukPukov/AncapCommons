package ru.ancap.commons.withlist;

import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;

import java.util.List;

@AllArgsConstructor
public class MutableWithList<T> implements WithList<T> {

    @Delegate
    private final List<T> delegate;

    public WithList<T> with(T value) {
        this.delegate.add(value);
        return this;
    }

    public WithList<T> without(T value) {
        this.delegate.remove(value);
        return this;
    }

}