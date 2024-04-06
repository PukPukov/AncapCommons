package ru.ancap.commons;

import org.junit.jupiter.api.Test;
import ru.ancap.commons.time.FixedTimeProvider;
import ru.ancap.commons.time.TimeProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CutoffsTest {
    
    @Test
    public void test() {
        var timeProvider = FixedTimeProvider.startFromCurrent();
        Cutoffs.timeProvider = timeProvider;
        Cutoffs<String> cutoffs = new Cutoffs<>(100);
        cutoffs.mark("1");
        cutoffs.mark("2");
        cutoffs.mark("3");
        timeProvider.emulateWait(50);
        cutoffs.mark("4");
        cutoffs.mark("5");
        cutoffs.mark("6");
        assertEquals(3,   cutoffs.from(timeProvider.currentTime() - 25) .size());
        assertEquals(6,   cutoffs.from(timeProvider.currentTime() - 100).size());
        assertEquals("5", cutoffs.from(timeProvider.currentTime() - 5).get(1));
        Cutoffs.timeProvider = TimeProvider.SYSTEM_CLOCK;
    }
    
    @Test
    public void testLimit() {
        var timeProvider = FixedTimeProvider.startFromCurrent();
        Cutoffs.timeProvider = timeProvider;
        Cutoffs<String> cutoffs = new Cutoffs<>(10);
        cutoffs.mark("1");
        cutoffs.mark("2");
        cutoffs.mark("3");
        timeProvider.emulateWait(50);
        cutoffs.mark("4");
        cutoffs.mark("5");
        cutoffs.mark("6");
        cutoffs.mark("7");
        cutoffs.mark("8");
        cutoffs.mark("9");
        cutoffs.mark("10");
        cutoffs.mark("11");
        assertEquals(8,   cutoffs.from(timeProvider.currentTime() - 25) .size());
        assertEquals(10,  cutoffs.from(timeProvider.currentTime() - 100).size());
        assertEquals("5", cutoffs.from(timeProvider.currentTime() - 5).get(1));
        Cutoffs.timeProvider = TimeProvider.SYSTEM_CLOCK;
    }
    
}