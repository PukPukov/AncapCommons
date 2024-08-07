package ru.ancap.commons.debug;


import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

import static ru.ancap.commons.debug.AncapDebug.*;

@HandTest
public class AncapDebugTest {
    
    public static void main(String[] args) {
        debug((Object) null);
        debug((Object[]) null);
        debug();
        
        debug(named("first", List.of("a", "b", 3)), new Object[]{"foo", "bar", new int[]{3, 4, 5}});
        debugArray(new Object[]{"foo", "bar", new int[]{3, 4, 5}});
        
        debug(true, false);
        debug(inline(true, false));
        
        debug(name("fizz"), true, false);
        
        testThrough();
    }
    
    private static boolean testThrough() {
        return debugThrough(true, false);
    }
    
    @RequiredArgsConstructor
    @ToString
    public static class PartialErasure<E extends Number> {
        
        private final E number;
        
    }
    
}