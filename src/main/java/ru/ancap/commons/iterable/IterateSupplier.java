package ru.ancap.commons.iterable;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Has overhead of one additional withdraw from supplier due to principle of Iterator.
 */
public class IterateSupplier {
    
    public static <T> Stream<@NonNull T> stream(Supplier<@Nullable T> sequenceSupplier) {
        return StreamIterator.wrap(IterateSupplier.wrap(sequenceSupplier));
    }
    
    /**
     * Only @NonNull if hasNext() properly called.
     */
    public static <T> Iterator<@NonNull T> wrap(Supplier<@Nullable T> sequenceSupplier) {
        final T first = sequenceSupplier.get();
        return new Iterator<>() {
            
            private T next = first;
            
            @Override
            public boolean hasNext() {
                return next != null;
            }
            
            @Override
            public T next() {
                T returned = next;
                next = sequenceSupplier.get();
                return returned;
            }
            
        };
    }
    
}