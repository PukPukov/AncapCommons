package ru.ancap.commons.null_;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface NnulMap<K, V> extends Map<K, V> {
    
    @NotNull V get(Object key);
    
}