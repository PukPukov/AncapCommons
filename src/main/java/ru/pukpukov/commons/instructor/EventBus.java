package ru.pukpukov.commons.instructor;

public interface EventBus<T> extends Instructor<T>, EventDispatcher<T> { }