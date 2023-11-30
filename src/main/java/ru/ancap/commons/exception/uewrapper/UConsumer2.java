package ru.ancap.commons.exception.uewrapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.function.FailableBiConsumer;

import java.util.function.BiConsumer;

@RequiredArgsConstructor
public class UConsumer2<T1, T2> implements BiConsumer<T1, T2> {

    private final FailableBiConsumer<T1, T2, ?> failableBiConsumer;

    @SneakyThrows
    @Override
    public void accept(T1 t1, T2 t2) {
        this.failableBiConsumer.accept(t1, t2);
    }

}
