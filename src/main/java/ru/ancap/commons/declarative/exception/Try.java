package ru.ancap.commons.declarative.exception;

public class Try {
    
    public static void perform(Action action) {
        try { action.run(); }
        catch (Throwable ignored) { }
    }
    
    public interface Action {
        
        void run() throws Throwable;
        
    }
    
}
