package ru.ancap.commons.null_;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SafeNullTest {
    
    @Test
    public void test() {
        Assertions.assertDoesNotThrow(() -> SafeNull.function(null,         notNull -> notNull.getClass().getSimpleName()));
        Assertions.assertEquals("Object",   SafeNull.function(new Object(), notNull -> notNull.getClass().getSimpleName()));

        Assertions.assertDoesNotThrow(() -> SafeNull.action(null, notNull -> System.out.println(notNull.getClass().getSimpleName())));
        final String[] simpleName = new String[1]; 
        SafeNull.action(new Object(), object -> simpleName[0] = object.getClass().getSimpleName());
        Assertions.assertEquals(simpleName[0], "Object");
    }
    
}
