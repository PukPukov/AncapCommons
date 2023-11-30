package ru.ancap.commons.exception.uewrapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.function.FailableFunction;

import java.util.function.Function;

@RequiredArgsConstructor
public class UFunction<F, T> implements Function<F, T> {

    private final FailableFunction<F, T, ?> failableFunction;

    @SneakyThrows
    @Override
    public T apply(F from) {
        return this.failableFunction.apply(from);
    }

}