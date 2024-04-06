package ru.ancap.commons.instructor;

import java.util.function.Consumer;
import java.util.function.Function;

public interface DynInstructor<TYPE> {
    
    void subscribe(String id, Consumer<TYPE> consumer);
    void unsubscribe(String id);
    
    default <ALTERNATIVE_TYPE> DynInstructor<ALTERNATIVE_TYPE> map(Function<TYPE, ALTERNATIVE_TYPE> mapFunction) {
        return new DynInstructor<>() {
            @Override
            public void subscribe(String id, Consumer<ALTERNATIVE_TYPE> alternativeTypeConsumer) {
                DynInstructor.this.subscribe(id, value -> alternativeTypeConsumer.accept(mapFunction.apply(value)));
            }
            
            @Override
            public void unsubscribe(String id) {
                DynInstructor.this.unsubscribe(id);
            }
        };
    }
    
    default <METHOD_TYPE> METHOD_TYPE as(Function<DynInstructor<TYPE>, METHOD_TYPE> asFunction) {
        return asFunction.apply(this);
    }
    
}