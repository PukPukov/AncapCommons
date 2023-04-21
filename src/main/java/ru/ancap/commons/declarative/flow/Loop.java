package ru.ancap.commons.declarative.flow;

import ru.ancap.commons.exception.UnsafeThread;

public class Loop {
    
    public static void run(Runnable runnable, long delay) {
        UnsafeThread.start(() -> {
            runnable.run();
            Thread.sleep(delay);
        });
    }
    
}
