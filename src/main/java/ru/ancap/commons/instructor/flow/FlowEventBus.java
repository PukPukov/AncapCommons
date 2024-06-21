package ru.ancap.commons.instructor.flow;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

@ToString @EqualsAndHashCode
@RequiredArgsConstructor
public class FlowEventBus<EVENT> implements FlowInstructor<EVENT> {
    
    private final Collection<InterceptingInjector<EVENT>> injectors;
    
    public FlowEventBus() { this(new CopyOnWriteArrayList<>()); }
    
    @Override
    public void subscribe(InterceptingInjector<EVENT> injector) {
        this.injectors.add(injector);
    }
    
    public boolean dispatch(EVENT event) {
        for (var injector : this.injectors) {
            boolean intercepted = injector.handle(event);
            if (intercepted) return true;
        }
        return false;
    }
    
}