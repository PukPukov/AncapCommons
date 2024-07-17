package ru.ancap.commons;

public class IntLongPacker {
    
    public static long pack(int high, int low) {
        return (((long) high) << 32) | (low & 0xFFFFFFFFL);
    }
    
    public static int unpackHigh(long packed) {
        return (int) (packed >> 32);
    }
    
    public static int unpackLow(long packed) {
        return (int) packed;
    }
    
}