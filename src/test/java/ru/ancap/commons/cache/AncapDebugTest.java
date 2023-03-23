package ru.ancap.commons.cache;

import ru.ancap.commons.debug.AncapDebug;
import ru.ancap.commons.debug.HandTest;
import ru.ancap.commons.withlist.ImmutableWithList;

import java.util.List;

@HandTest
public class  AncapDebugTest {
    
    public static void main(String[] args) {
        AncapDebug.debugArray(new Object[]{});
        
        AncapDebug.debug(new double[]{50, 11, 40000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000D});
        AncapDebug.debug("example", new double[]{50, 11, 40000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000D});
        AncapDebug.debug("example", new float []{50, 11, 40000000000000000000000000000000000F});
        AncapDebug.debug("example", new Object[]{new Cache<String>(), new ImmutableWithList<>(List.of("string"))});
        AncapDebug.debug(new Cache<String>(), new ImmutableWithList<>(List.of("string")));
        AncapDebug.debug(new Object[]{new Cache<String>(), new ImmutableWithList<>(List.of("string"))}, new ImmutableWithList<>(List.of("string")));
    }
    
}
