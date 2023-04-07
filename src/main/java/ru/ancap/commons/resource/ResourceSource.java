package ru.ancap.commons.resource;

import org.jetbrains.annotations.Nullable;

public interface ResourceSource<T> {

    /**
     * Null if not found
     */
    @Nullable T getResource(String fileName);

}
