package ru.ancap.commons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString @EqualsAndHashCode
public class Pair<K, V> {
    
    private final K key;
    private final V value;
    
}
