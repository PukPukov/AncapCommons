package ru.ancap.commons.multithreading;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.ToString;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * This is not event bus! Should be used to make only one thread wait (amount of producer threads can be unlimited).
 */
@ToString @EqualsAndHashCode
public class WaitFor<T> {
    
    private final BlockingQueue<T> objectStore = new ArrayBlockingQueue<>(1);
    
    private T store;
    
    @SneakyThrows
    public T get() {
        if (this.store == null) this.store = this.objectStore.take();
        return this.store;
    }
    
    @SneakyThrows
    public void put(T object) {
        this.objectStore.offer(object);
    }
    
}