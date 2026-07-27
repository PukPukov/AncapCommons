package ru.pukpukov.commons.axis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberAxisTest {


    @Test
    public void test() {
        assertEquals(2, new CyclicNumberAxis( 10)    .offset(-3,   5 ));
        assertEquals(7, new CyclicNumberAxis( 10)    .offset( 5,   12));
        assertEquals(2, new CyclicNumberAxis( 10)    .offset( 5,  -3 ));
        assertEquals(1, new CyclicNumberAxis( 6 )    .offset( 4,  -3 ));
        assertEquals(5, new CyclicNumberAxis( 10)    .offset( 5,   0 ));

        assertEquals(8, new CyclicNumberAxis( 1, 11) .offset( 5,   12));
        assertEquals(5, new CyclicNumberAxis( 3, 13) .offset( 5,  -3 ));
        assertEquals(3, new CyclicNumberAxis( 2, 8 ) .offset( 4,  -3 ));
        assertEquals(0, new CyclicNumberAxis(-5, 5 ) .offset( 5,   0 ));

        assertEquals(3, new CyclicNumberAxis( 12)    .offset( 12,  3 ));
        assertEquals(0, new CyclicNumberAxis( 1 )    .offset( 5,   10));
        assertEquals(8, new CyclicNumberAxis( 12)    .offset(-31,  3 ));
    }

}
