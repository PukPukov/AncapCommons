package ru.ancap.commons.declarative.flow;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.function.Function;

@AllArgsConstructor
@EqualsAndHashCode @ToString
public class Value<T> {
    
    private final T value;
    
    public static <T> Value<T> of(T value) {
        return new Value<>(value);
    }
    
    public <R> R useAs(Function<T, R> function) {
        return function.apply(this.value);
    }
    
}
