package ru.ancap.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PathTest {
    
    @Test
    public void test() {
        assertEquals("foo.bar.baz.1", Path.dot("foo", "bar", "baz", "1"));
    }
    
}