package ru.ancap.commons.exception.uewrapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.function.FailablePredicate;

import java.util.function.Predicate;

@RequiredArgsConstructor
public class UPredicate<T> implements Predicate<T> {

    private final FailablePredicate<T, ?> failablePredicate;

    @SneakyThrows
    @Override
    public boolean test(T t) {
        return this.failablePredicate.test(t);
    }

}
