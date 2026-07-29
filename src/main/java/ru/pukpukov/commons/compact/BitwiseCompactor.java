package ru.pukpukov.commons.compact;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode @ToString
public class BitwiseCompactor implements Compactor {

    @Override
    public int[] unpack(long code) {
        int first = unpackHigh(code);
        int second = unpackLow(code);
        return new int[]{first, second};
    }

    @Override
    public long pack(int first, int second) {
        return pack0(first, second);
    }
    
    public static long pack0(int first, int second) {
        return (((long) first) << 32) | (second & 0xffffffffL);
    }
    
    public static int unpackHigh(long packed) {
        return (int) (packed >> 32);
    }
    
    public static int unpackLow(long packed) {
        return (int) packed;
    }
    
}