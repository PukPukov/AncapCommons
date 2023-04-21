package ru.ancap.commons.multithreading;

import lombok.SneakyThrows;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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
        this.objectStore.put(object);
    }
    
}