package ru.ancap.commons.resource;

public interface ResourceSource<T> {

    T getResource(String fileName);

}
