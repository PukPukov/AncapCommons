package ru.ancap.commons.multithreading;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.ancap.commons.exception.UnsafeThread;
import ru.ancap.commons.time.FixedTimeProvider;

public class WaitForTest {
    
    @Test
    public void testTimes() {
        this.testTimesLike(20);
        this.testTimesLike(20);
        this.testTimesLike(20);
        this.testTimesLike(5);
    }

    @SneakyThrows
    private void testTimesLike(int waitTo) {
        var timeProvider = FixedTimeProvider.startFromCurrent();
        WaitFor<String> wait = new WaitFor<>();
        long start = timeProvider.currentTime();
        UnsafeThread.start(() -> {
            timeProvider.emulateWait(waitTo);
            wait.put("test");
        });
        String pulled = wait.get();
        long end = timeProvider.currentTime();
        Assertions.assertEquals("test", pulled);
        long estimated = end - start;
        Assertions.assertEquals(estimated, waitTo, ""+estimated);
    }
    
    @Test
    public void testMultipleGet() {
        var timeProvider = FixedTimeProvider.startFromCurrent();
        WaitFor<String> wait = new WaitFor<>();
        UnsafeThread.start(() -> {
            timeProvider.emulateWait(30);
            wait.put("test");
        });
        Assertions.assertEquals("test", wait.get());
        Assertions.assertEquals("test", wait.get());
        Assertions.assertEquals("test", wait.get());
        Assertions.assertEquals("test", wait.get());
    }

}