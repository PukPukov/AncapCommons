package ru.pukpukov.commons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.ApiStatus;

@Data @ToString @EqualsAndHashCode
public class Pair<A, B> {
    
    private final A a;
    private final B b;
    
    @ApiStatus.Obsolete public A getKey()   { return this.a; }
    @ApiStatus.Obsolete public B getValue() { return this.b; }
    
    @ApiStatus.Obsolete public A getA() { return this.a; }
    @ApiStatus.Obsolete public B getB() { return this.b; }
    
    public A key() { return this.a; }
    public B value() { return this.b; }
    
    public A left()  { return this.a; }
    public B right() { return this.b; }
    
    public A first()  { return this.a; }
    public B second() { return this.b; }
    
}