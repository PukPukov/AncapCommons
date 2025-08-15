package ru.ancap.commons;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface BiIterable<A, B> extends Iterable<Pair<A, B>> {
    
    @RequiredArgsConstructor
    class Paired<A, B> implements BiIterable<A, B> {
        
        @Delegate
        private final List<Pair<A, B>> delegate;
        
        public static <A, B> Paired<A, B> of(Map<A, B> map) {
            List<Pair<A, B>> list = new ArrayList<>();
            for (var entry : map.entrySet()) {
                list.add(new Pair<>(entry.getKey(), entry.getValue()));
            }
            return new Paired<>(List.copyOf(list));
        }
        
    }
    
}