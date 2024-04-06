package ru.ancap.commons.instructor;

public interface EventDispatcher<T> {
    
    void dispatch(T event);
    
}