package ru.ancap.commons;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class BiList<A, B> implements Iterable<Pair<A, B>> {
    
    @Delegate
    private final Iterable<Pair<A, B>> delegate;
    
}