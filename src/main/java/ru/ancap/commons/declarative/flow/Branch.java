package ru.ancap.commons.declarative.flow;

public class Branch {

    public static <T> T of(boolean test, T ifTrue, T ifFalse) {
        if (test) return ifTrue;
        return ifFalse;
    }
    
}
