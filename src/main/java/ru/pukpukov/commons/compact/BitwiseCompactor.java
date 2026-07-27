package ru.pukpukov.commons.compact;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode @ToString
public class BitwiseCompactor implements Compactor {

    @Override
    public int[] unpack(long code) {
        int first = (int) (code >> 32);
        int second = (int) code;
        return new int[]{first, second};
    }

    @Override
    public long pack(int first, int second) {
        return (((long) first) << 32) | (second & 0xffffffffL);
    }
    
}