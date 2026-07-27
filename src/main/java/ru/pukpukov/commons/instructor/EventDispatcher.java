package ru.pukpukov.commons.instructor;

public interface EventDispatcher<T> {
    
    void dispatch(T event);
    
}