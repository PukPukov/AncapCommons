package ru.ancap.commons.exception.uewrapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.function.FailableBiPredicate;

import java.util.function.BiPredicate;

@RequiredArgsConstructor
public class UPredicate2<T1, T2> implements BiPredicate<T1, T2> {

    private final FailableBiPredicate<T1, T2, ?> failableBiPredicate;

    @SneakyThrows
    @Override
    public boolean test(T1 t1, T2 t2) {
        return this.failableBiPredicate.test(t1, t2);
    }

}
