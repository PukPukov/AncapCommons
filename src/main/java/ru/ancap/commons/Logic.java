package ru.ancap.commons;

public class Logic {
    
    public static boolean completes(Unstable unstable) {
        try {
            unstable.run();
            return true;
        } catch (Throwable throwable) {
            return false;
        }
    }
    
    public interface Unstable {
        
        void run() throws Throwable;
        
    }
    
}
