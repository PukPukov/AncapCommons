package ru.pukpukov.commons.compact;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode @ToString
@AllArgsConstructor
public class Morton64Compactor implements Compactor {
    
    private final Morton64 morton64 = new Morton64(2, 32);

    @Override
    public int[] unpack(long code) {
        long[] unpackedInLong = this.morton64.signedUnpack(code);
        int[] unpacked = new int[2]; for (int i = 0; i < unpackedInLong.length; i++) unpacked[i] = (int) unpackedInLong[i];
        return unpacked;
    }

    @Override
    public long pack(int first, int second) {
        return this.morton64.signedPack(first, second);
    }
}