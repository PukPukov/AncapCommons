package ru.pukpukov.commons.compact.direct;

import org.junit.jupiter.api.Test;
import ru.pukpukov.commons.compact.BitwiseCompactor;
import ru.pukpukov.commons.compact.Compactor;
import ru.pukpukov.commons.compact.Morton64Compactor;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompactorTest {
    
    private final Compactor morton64 = new Morton64Compactor();
    private final Compactor bitwise = new BitwiseCompactor();
    
    @Test
    public void test() {
        this.testCompact(10, 50);
        this.testCompact(0, 0);
        this.testCompact(0, 501);
        this.testCompact(501, 0);
        this.testCompact(-0, 0);
        this.testCompact(-0, -0);
        this.testCompact(-0, 501);
        this.testCompact(501, -0);
        this.testCompact(501, 405);
        this.testCompact(501, 501);
        this.testCompact(12345, -9875654);
        this.testCompact(-12345, 13514);
        this.testCompact(Compactor.MIN_VALUE, Compactor.MIN_VALUE);
        this.testCompact(Compactor.MAX_VALUE, Compactor.MAX_VALUE);
        this.testCompact(Compactor.MIN_VALUE, Compactor.MAX_VALUE);
        this.testCompact(Compactor.MAX_VALUE, Compactor.MIN_VALUE);
    }

    private void testCompact(int first, int second) {
        this.testCompact(first, second, this.morton64);
        this.testCompact(first, second, this.bitwise);
    }

    private void testCompact(int first, int second, Compactor compactor) {
        long packed = compactor.pack(first, second);
        int[] unpacked = compactor.unpack(packed);
        assertEquals(first, unpacked[0], compactor.getClass().getSimpleName());
        assertEquals(second, unpacked[1], compactor.getClass().getSimpleName());
    }

}