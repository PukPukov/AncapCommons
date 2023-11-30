package ru.ancap.commons.exception.uewrapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.function.FailableConsumer;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class UConsumer<T> implements Consumer<T> {

    private final FailableConsumer<T, ?> failableConsumer;

    @SneakyThrows
    @Override
    public void accept(T t) {
        this.failableConsumer.accept(t);
    }

}
