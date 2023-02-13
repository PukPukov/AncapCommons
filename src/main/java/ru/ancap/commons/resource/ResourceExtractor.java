package ru.ancap.util.resource;

import java.io.InputStream;

public interface ResourceExtractor<T> {
    
    T extract(InputStream stream);
    
}
