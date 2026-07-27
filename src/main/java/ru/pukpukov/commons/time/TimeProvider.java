package ru.pukpukov.commons.time;

public interface TimeProvider {
    
    TimeProvider SYSTEM_CLOCK = System::currentTimeMillis;
    
    /**
     * @return time in milliseconds
     */
    long currentTime();
    
}