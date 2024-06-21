package ru.ancap.commons.instructor.flow;

public interface FlowInstructor<EVENT> {
    
    void subscribe(InterceptingInjector<EVENT> injector);
    
}