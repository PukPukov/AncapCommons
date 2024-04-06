package ru.ancap.commons.time;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FixedTimeProviderTest {
    
    @Test
    public void testFromZero() {
        testTimeProvider(FixedTimeProvider.startFromZero());
    }
    
    @Test
    public void testCurrent() {
        testTimeProvider(FixedTimeProvider.startFromCurrent());
    }
    
    private void testTimeProvider(FixedTimeProvider timeProvider) {
        long startTime = timeProvider.currentTime();
        long waitedTime = 1005;
        timeProvider.emulateWait(waitedTime);
        assertEquals(startTime+waitedTime, timeProvider.currentTime());
    }
    
}