package ru.ancap.commons.random;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Accessors(fluent = true) @Getter
public class Weight<T> {
    
    private final T result;
    private final int weight;
    
}