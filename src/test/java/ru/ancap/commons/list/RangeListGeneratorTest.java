package ru.ancap.commons.list;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class RangeListGeneratorTest {

    @Test
    public void test() {
        assertArrayEquals(List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L).toArray(), RangeListGenerator.generate(1, 11).toArray());
    }

}
