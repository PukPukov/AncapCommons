package ru.ancap.commons.resource;

import java.io.InputStream;

public interface ResourceExtractor<T> {
    
    T extract(InputStream stream);

    /**
     * Called every time when resource loaded. Should check for conflicts and resolve them if found,
     * and return resource with resolved conflict. <br> <br>
     *
     * Not called, if definitely no conflict - if user didn't have this resource before and wasn't able to change it.
     */
    InputStream resolveConflict(InputStream software, InputStream user);
    
}
