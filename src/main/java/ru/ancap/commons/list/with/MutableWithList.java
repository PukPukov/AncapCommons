package ru.ancap.commons.list.with;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

import java.util.List;

@AllArgsConstructor
@ToString @EqualsAndHashCode
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