package ru.ancap.commons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.ApiStatus;

@Data
@ToString @EqualsAndHashCode
@Accessors(fluent = true)
public class Pair<K, V> {
    
    private final K key;
    private final V value;
    
    @ApiStatus.Obsolete public K getKey()   { return this.key(); }
    @ApiStatus.Obsolete public V getValue() { return this.value(); }
    
    public K left()  { return this.key;   }
    public V right() { return this.value; }
    
    public K first()  { return this.key;   }
    public V second() { return this.value; }
    
}