package ru.pukpukov.commons.instructor;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.pukpukov.commons.exception.uewrapper.UConsumer;
import ru.pukpukov.commons.exception.uewrapper.URunnable;
import ru.pukpukov.commons.iterable.IterableToArray;
import ru.pukpukov.commons.list.RangeListGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class BatcherTest {

    @Test
    @SneakyThrows
    public void classic() {
        SimpleEventBus<Long> base = new SimpleEventBus<>();
        Batcher<Long> batcher = new Batcher<>(base, 256);
        BlockingQueue<List<Long>> collector = new ArrayBlockingQueue<>(2);
        batcher.subscribe(new UConsumer<>(collector::put));
        AtomicInteger debug_i = new AtomicInteger();

        Thread thread = new Thread(new URunnable(() -> {
            int sleptTimes = 0;
            for (long i = 0; i < 512; i++) {
                debug_i.incrementAndGet();
                if (sleptTimes < 64) { // to not make build too long
                    Thread.sleep(1); sleptTimes++;
                }
                base.dispatch(i);
            }
        }));
        thread.start();
        thread.join();

        assertArrayEquals(
            IterableToArray.deepRecursiveReflective(List.of(List.of(RangeListGenerator.generate(0, 256), RangeListGenerator.generate(256, 512)))),
            IterableToArray.deepRecursiveReflective(List.of(new ArrayList<>(collector)))
        );
    }

}