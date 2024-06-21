package ru.ancap.commons.iterable;

import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IterateSupplierTest {
    
    @Test
    public void testIterateSupplierAndStream() {
        var rangeSupplier = new RangeSupplier(0, 10);
        assertEquals(55, IterateSupplier.stream(rangeSupplier).reduce(Integer::sum).orElseThrow());
    }
    
    private static class RangeSupplier implements Supplier<@Nullable Integer> {
        private int from;
        private int to;
        
        public RangeSupplier(int from, int to) {
            this.from = from;
            this.to = to;
        }
        
        private int current = this.from;
        
        @Override
        public @Nullable Integer get() {
            int toReturn = this.current++;
            if (toReturn > this.to) return null;
            return toReturn;
        }
    }
    
}