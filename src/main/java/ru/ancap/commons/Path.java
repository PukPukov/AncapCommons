package ru.ancap.commons;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Path {
    
    public static String dot(String... nodes) {
        return String.join(".", nodes);
    }
    
}