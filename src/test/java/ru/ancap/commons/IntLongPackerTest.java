package ru.ancap.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntLongPackerTest {
    
    @Test
    public void testPackAndUnpack() {
        testPackUnpack(123456789, 987654321);
        testPackUnpack(-123456789, -987654321);
        testPackUnpack(0, 0);
        testPackUnpack(Integer.MAX_VALUE, Integer.MAX_VALUE);
        testPackUnpack(Integer.MIN_VALUE, Integer.MAX_VALUE);
        testPackUnpack(Integer.MIN_VALUE, Integer.MIN_VALUE);
        testPackUnpack(Integer.MAX_VALUE, Integer.MIN_VALUE);
    }
    
    private void testPackUnpack(int high, int low) {
        long packed = IntLongPacker.pack(high, low);
        assertEquals(high, IntLongPacker.unpackHigh(packed));
        assertEquals(low, IntLongPacker.unpackLow(packed));
    }
    
}