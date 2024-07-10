package ru.ancap.commons.multithreading;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.ToString;

import java.util.concurrent.CountDownLatch;

@ToString @EqualsAndHashCode
public class WaitFor<T> {
    
    private final CountDownLatch latch = new CountDownLatch(1);
    
    // no volatile because of memory effect of count down latch
    private T store;
    
    @SneakyThrows
    public T get() {
        this.latch.await();
        return this.store;
    }
    
    @SneakyThrows
    public void put(T object) {
        this.store = object;
        this.latch.countDown();
    }
    
}