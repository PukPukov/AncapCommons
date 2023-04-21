package ru.ancap.commons.multithreading;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.ancap.commons.exception.UnsafeThread;

public class WaitForTest {
    
    @Test
    public void testTimes() {
        this.testTimesLike(20, 20);
        this.testTimesLike(20, 20);
        this.testTimesLike(20, 20);
        this.testTimesLike(5, 20);
    }

    @SneakyThrows
    private void testTimesLike(int waitTo, int deviation) {
        WaitFor<String> wait = new WaitFor<>();
        long start = System.currentTimeMillis();
        UnsafeThread.start(() -> {
            Thread.sleep(waitTo);
            wait.put("test");
        });
        String pulled = wait.get();
        long end = System.currentTimeMillis();
        Assertions.assertEquals("test", pulled);
        long estimated = end - start;
        Assertions.assertTrue(estimated >= waitTo && estimated <= waitTo + deviation, ""+estimated);
    }
    
    @Test
    public void testMultipleGet() {
        WaitFor<String> wait = new WaitFor<>();
        UnsafeThread.start(() -> {
            Thread.sleep(30);
            wait.put("test");
        });
        Assertions.assertEquals("test", wait.get());
        Assertions.assertEquals("test", wait.get());
        Assertions.assertEquals("test", wait.get());
        Assertions.assertEquals("test", wait.get());
    }

}
