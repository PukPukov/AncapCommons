package ru.pukpukov.commons.compact;

/**
 * Supports packing range from Integer.MIN_VALUE + 1 to Integer.MAX_VALUE by specification
 */
public interface Compactor {
    
    int MIN_VALUE = -2147483647;
    int MAX_VALUE =  2147483647;
    
    int[] unpack(long code);
    
    long pack(int first, int second);
    
    default long pack(long first, long second) {
        return this.pack((int) first, (int) second);
    }
    
}