package ru.ancap.commons;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString @EqualsAndHashCode
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
