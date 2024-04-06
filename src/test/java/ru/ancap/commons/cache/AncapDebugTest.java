package ru.ancap.commons.cache;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ancap.commons.debug.AncapDebug;
import ru.ancap.commons.list.with.ImmutableWithList;

import java.util.List;
import java.util.function.Consumer;

import static org.mockito.Mockito.verify;

public class  AncapDebugTest {
    
    @Test
    public void testArrayNo() {
        Consumer<String> defaultConsumer = AncapDebug.OUTPUT_CONSUMER;
        AncapDebug.OUTPUT_CONSUMER = (Consumer<String>) Mockito.mock(Consumer.class);
        AncapDebug.debugArray(new Object[]{});
        verify(AncapDebug.OUTPUT_CONSUMER).accept("\"[]\"");
        
        AncapDebug.debug(new double[]{50, 11, 40000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000D});
        verify(AncapDebug.OUTPUT_CONSUMER).accept("\"[50.0, 11.0, 4.0E100]\"");
        
        AncapDebug.debug("example", new double[]{50, 11, 40000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000D});
        verify(AncapDebug.OUTPUT_CONSUMER).accept("\"example\" \"[50.0, 11.0, 4.0E100]\"");
        
        AncapDebug.debug("example", new float []{50, 11, 40000000000000000000000000000000000F});
        verify(AncapDebug.OUTPUT_CONSUMER).accept("\"example\" \"[50.0, 11.0, 4.0E34]\"");
        
        AncapDebug.debug("example", new Object[]{new Cache<String>(), new ImmutableWithList<>(List.of("string"))});
        verify(AncapDebug.OUTPUT_CONSUMER).accept("\"example\" \"[Cache(cacheValidity=99999999999, cache=null, lastUpdateTime=0), ImmutableWithList(delegate=[string])]\"");
        
        AncapDebug.debug(new Cache<String>(), new ImmutableWithList<>(List.of("string")));
        verify(AncapDebug.OUTPUT_CONSUMER).accept("\"Cache(cacheValidity=99999999999, cache=null, lastUpdateTime=0)\" \"ImmutableWithList(delegate=[string])\"");
        
        AncapDebug.debug(new Object[]{new Cache<String>(), new ImmutableWithList<>(List.of("string"))}, new ImmutableWithList<>(List.of("string")));
        verify(AncapDebug.OUTPUT_CONSUMER).accept("\"[Cache(cacheValidity=99999999999, cache=null, lastUpdateTime=0), ImmutableWithList(delegate=[string])]\" \"ImmutableWithList(delegate=[string])\"");
        
        AncapDebug.OUTPUT_CONSUMER = defaultConsumer;
    }
    
}