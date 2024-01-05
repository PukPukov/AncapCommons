package ru.ancap.commons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString @EqualsAndHashCode
@Accessors(fluent = true)
public class Pair<K, V> {
    
    private final K key;
    private final V value;

    @Deprecated(forRemoval = false) public K getKey()   { return this.key(); }
    @Deprecated(forRemoval = false) public V getValue() { return this.value(); }
    
}
