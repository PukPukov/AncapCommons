package ru.ancap.commons;

/**
 * Marker for classes that is not working without proper implementation/setup, but nothing really prevents users from using class without implementation.<br><br>
 * <p>
 * Most likely, this is going to be used on classes that expect some implementation to be provided to static field.
 * <p>
 * Common use case — check what classes need to be set up via "go to usages" in any IDE and then set them up.
 */
public @interface ImplementationRequired { }