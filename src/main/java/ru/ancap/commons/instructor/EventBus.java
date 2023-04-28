package ru.ancap.commons.instructor;

public interface EventBus<T> extends Instructor<T> {
    
    void dispatch(T event);
    
}
