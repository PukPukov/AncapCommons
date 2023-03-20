package ru.ancap.commons.withlist;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@ToString @EqualsAndHashCode
public class ImmutableWithList<T> implements WithList<T> {

    @Delegate
    private final List<T> delegate;

    public WithList<T> with(T value) {
        List<T> mutate = new ArrayList<>(this.delegate);
        mutate.add(value);
        return new ImmutableWithList<>(List.copyOf(mutate));
    }

    public WithList<T> without(T value) {
        List<T> mutate = new ArrayList<>(this.delegate);
        mutate.remove(value);
        return new ImmutableWithList<>(List.copyOf(mutate));
    }

}