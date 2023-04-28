package ru.ancap.commons.instructor;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Instructor<TYPE> extends Consumer<Consumer<TYPE>> {
    
    default <ALTERNATIVE_TYPE> Instructor<ALTERNATIVE_TYPE> map(Function<TYPE, ALTERNATIVE_TYPE> mapFunction) {
        return alternativeTypeConsumer -> Instructor.this.accept(value -> alternativeTypeConsumer.accept(mapFunction.apply(value)));
    }
    
    default <METHOD_TYPE> METHOD_TYPE as(Function<Instructor<TYPE>, METHOD_TYPE> asFunction) {
        return asFunction.apply(this);
    }
    
}
