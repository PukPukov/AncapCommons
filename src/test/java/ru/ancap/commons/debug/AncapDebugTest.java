package ru.ancap.commons.debug;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.ancap.commons.random.Weight;
import ru.ancap.commons.random.Weights;

import java.util.List;

import static ru.ancap.commons.debug.AncapDebug.*;

@HandTest
public class AncapDebugTest {
    
    @Test // for coverage
    public void test() {
        debug((Object) null);
        debug((Object[]) null);
        debug();
        
        debug(named("first", List.of("a", "b", 3)), new Object[]{"foo", "bar", new int[]{3, 4, 5}});
        debugArray(new Object[]{"foo", "bar", new int[]{3, 4, 5}});
        
        debug(true, false);
        debug(inline(true, false));
        
        debug(name("fizz"), true, false);
        debug(new Weights<>(List.of(new Weight<>(null, 20))));
        
        Assertions.assertTrue(testThrough());
    }
    
    private static boolean testThrough() {
        return debugThrough(true, false);
    }
    
}