package ru.ancap.commons;

public interface TriFunction<V1, V2, V3, R> {
    
    R apply(V1 v1, V2 v2, V3 v3);
    
}
