package ru.ancap.commons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.ApiStatus;

@Data
@ToString @EqualsAndHashCode
@Accessors(fluent = true)
public class Pair<A, B> {
    
    private final A a;
    private final B b;
    
    @ApiStatus.Obsolete public A getKey()   { return this.a(); }
    @ApiStatus.Obsolete public B getValue() { return this.b(); }
    
    public A left()  { return this.a; }
    public B right() { return this.b; }
    
    public A first()  { return this.a; }
    public B second() { return this.b; }
    
    public A a() { return this.a; }
    public B b() { return this.b; }
    
}