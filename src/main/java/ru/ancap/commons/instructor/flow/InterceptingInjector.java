package ru.ancap.commons.instructor.flow;

public interface InterceptingInjector<EVENT> {
    
    /**
     * @return intercept attempt result (true if intercepted and flow should be stopped, false if not)
     */
    boolean handle(EVENT event);
    
}