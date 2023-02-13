package ru.ancap.util.resource;

import java.io.InputStream;

public interface ResourceSource<T> {

    T getResource(String fileName, ResourceExtractor<T> resourceExtractor, ConflictResolver<T> conflictResolver);

}
