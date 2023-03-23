package ru.ancap.commons.null_;

import java.util.function.Consumer;
import java.util.function.Function;

public class SafeNull {
    
    public static <T, O> O function(T nullable, Function<T, O> function) {
        if (nullable != null) return function.apply(nullable);
        else return null;
    }
    
    public static <T> void action(T nullable, Consumer<T> action) {
        if (nullable != null) action.accept(nullable);
    }
    
}
