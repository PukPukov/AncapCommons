package ru.ancap.commons.resource;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;

public interface ConflictResolver {

    ConflictResolver NO = (software, user) -> user;

    /**
     * Called every time, when PluginResourceSource loads resource. Should check for conflicts and resolve them if found,
     * and return resource with resolved conflict. <br> <br>
     * 
     * Not called, if definitely no conflict - if user don't have this resource before and wasn't able to change it.
     */
    InputStream resolve(@NotNull InputStream software, @NotNull InputStream user);
    
}
