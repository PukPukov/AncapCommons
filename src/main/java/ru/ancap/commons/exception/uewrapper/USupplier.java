package ru.ancap.commons.exception.uewrapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.function.FailableSupplier;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class USupplier<T> implements Supplier<T> {

    private final FailableSupplier<T, ?> failableSupplier;

    @SneakyThrows
    @Override
    public T get() {
        return this.failableSupplier.get();
    }

}
