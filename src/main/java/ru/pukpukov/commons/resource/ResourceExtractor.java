package ru.pukpukov.commons.resource;

import java.io.InputStream;

public interface ResourceExtractor<T> {
    
    T extract(InputStream stream);
    
}
