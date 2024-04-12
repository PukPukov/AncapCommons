package ru.ancap.commons;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Duration;
import java.util.function.Consumer;

@AllArgsConstructor
@ToString @EqualsAndHashCode
public class MeteredTask implements Runnable {
    
    private final Runnable onStart;
    private final Runnable task;
    private final Consumer<Duration> onEnd;

    @Override
    public void run() {
        this.onStart.run();
        long start = System.nanoTime();
        this.task.run();
        long end = System.nanoTime();
        this.onEnd.accept(Duration.ofNanos(end-start));
    }
    
}
