package ru.ancap.commons.exception.uewrapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.function.FailableBiFunction;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class UFunction2<T1, T2, T> implements BiFunction<T1, T2, T> {

    private final FailableBiFunction<T1, T2, T, ?> failableBiFunction;

    @SneakyThrows
    @Override
    public T apply(T1 from1, T2 from2) {
        return this.failableBiFunction.apply(from1, from2);
    }

}