package ru.ancap.commons.radix;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NumberPatternTest {
    
    @Test
    public void test() {
        RadixProvider radixProvider = new NumberPattern();
        assertEquals(16, radixProvider.radix("0xCAFEBABE"));
        assertEquals(16, radixProvider.radix("0x123456789"));
        assertEquals(16, radixProvider.radix("0xabcdef"));

        assertEquals(8, radixProvider.radix("01234567"));
        assertEquals(8, radixProvider.radix("05647466"));
        assertEquals(8, radixProvider.radix("03456236"));

        assertEquals(2, radixProvider.radix("0b0001011100"));
        assertEquals(2, radixProvider.radix("0b1010011110010"));
        assertEquals(2, radixProvider.radix("0b1111111111111"));

        assertEquals(10, radixProvider.radix("123456789"));
        assertEquals(10, radixProvider.radix("987654321"));
        assertEquals(10, radixProvider.radix("234538684"));

        assertNull(radixProvider.radix("not-a-number"));
        assertNull(radixProvider.radix(""));
        assertNull(radixProvider.radix("0b"));
        assertNull(radixProvider.radix("0b011100112"));
        assertNull(radixProvider.radix("6985697856978560795687956785609758697568705678"));
        assertNull(radixProvider.radix("0xZZZ"));
    }
    
}
