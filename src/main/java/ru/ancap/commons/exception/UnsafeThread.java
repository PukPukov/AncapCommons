package ru.ancap.commons.exception;

import lombok.SneakyThrows;
import org.jetbrains.annotations.Blocking;
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
    
    @Blocking
    @SneakyThrows
    public static void sleep(long millis) {
        Thread.sleep(millis);
    }

    public interface Action {
        
        void execute() throws Throwable;
        
    }
    
}
