package ru.ancap.commons.exception;

import org.jetbrains.annotations.NonBlocking;
import org.jetbrains.annotations.Nullable;
import ru.ancap.commons.null_.SafeNull;

import java.util.function.Consumer;

public class UnsafeThread {
    
    @NonBlocking
    public static void start(Action action) {
        UnsafeThread.start(action, null);
    }
    
    @NonBlocking
    public static void start(Action action, @Nullable Consumer<Throwable> exceptionConsumer) {
        new Thread(() -> {
            try {
                action.execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                SafeNull.action(exceptionConsumer, notNull -> notNull.accept(throwable));
            }
        }).start();
    }

    public interface Action {
        
        void execute() throws Throwable;
        
    }
}
