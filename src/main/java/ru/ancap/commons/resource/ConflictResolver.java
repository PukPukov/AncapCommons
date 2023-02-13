package ru.ancap.commons.resource;

public interface ConflictResolver<T> {
    
    static <T> ConflictResolver<T> NO() {return (software, user) -> user;}

    /**
     * Called every time, when PluginResourceSource loads resource. Should check for conflicts and resolve them if found, and return resource with resolved conflict.
     */
    T resolve(T software, T user);
    
}
