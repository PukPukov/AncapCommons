package ru.ancap.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CutoffsTest {
    
    @Test
    public void on() throws Exception {
        Cutoffs<String> cutoffs = new Cutoffs<>();
        cutoffs.mark("1");
        cutoffs.mark("2");
        cutoffs.mark("3");
        Thread.sleep(50);
        cutoffs.mark("4");
        cutoffs.mark("5");
        cutoffs.mark("6");
        assertEquals(3,   cutoffs.from(System.currentTimeMillis() - 25).size());
        assertEquals("5", cutoffs.from(System.currentTimeMillis() - 5).get(1));
    }
    
}
