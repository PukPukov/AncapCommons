package ru.ancap.commons.exception.uewrapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.function.FailableBiFunction;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class UFunction2<F1, F2, T> implements BiFunction<F1, F2, T> {

    private final FailableBiFunction<F1, F2, T, ?> failableBiFunction;

    @SneakyThrows
    @Override
    public T apply(F1 from1, F2 from2) {
        return this.failableBiFunction.apply(from1, from2);
    }

}