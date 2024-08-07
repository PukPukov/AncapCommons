package ru.ancap.commons;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Representation of pattern that commonly implemented in Java 21 and later via sealed interfaces and pattern matching on them.
 * Implementation on older javas requires more boilerplate but still possible.
 */
@RequiredArgsConstructor
@Accessors(fluent = true) @Getter
public class DynData<E extends Enum<E>, T> {
    
    private final E type;
    private final T value;
    
}