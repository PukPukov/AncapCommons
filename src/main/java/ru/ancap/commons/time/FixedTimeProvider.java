package ru.ancap.commons.time;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Testing classes that rely on time can be nightmare, because time is very unstable thing.
 * The solution is to use fully controlled time provider that does not rely on system clock so 
 * strange OS-caused freezes, gc-caused stop-the-worlds, concurrently running cpu-intensive tests, other things will not affect it.
 */
public class FixedTimeProvider implements TimeProvider {
    
    private final AtomicLong currentTime;
    
    private FixedTimeProvider(long initialTime) {
        this.currentTime = new AtomicLong(initialTime);
    }
    
    public static FixedTimeProvider startFrom(long initial) {
        return new FixedTimeProvider(initial);
    }
    
    public static FixedTimeProvider startFromCurrent() {
        return FixedTimeProvider.startFrom(TimeProvider.SYSTEM_CLOCK.currentTime());
    }
    
    public static FixedTimeProvider startFromZero() {
        return FixedTimeProvider.startFrom(0);
    }
    
    public void emulateWait(long time) {
        this.currentTime.addAndGet(time);
    }
    
    @Override
    public long currentTime() {
        return this.currentTime.get();
    }
    
}