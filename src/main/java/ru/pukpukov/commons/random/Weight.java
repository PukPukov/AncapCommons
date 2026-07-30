package ru.pukpukov.commons.random;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@RequiredArgsConstructor @Getter @ToString
public class Weight<T> {
    
    private final T result;
    private final int weight;
    
}