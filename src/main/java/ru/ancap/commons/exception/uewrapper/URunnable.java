package ru.ancap.commons.exception.uewrapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.function.FailableRunnable;

@RequiredArgsConstructor
public class URunnable implements Runnable {

    private final FailableRunnable<?> failableRunnable;

    @SneakyThrows
    @Override
    public void run() {
        this.failableRunnable.run();
    }

}
