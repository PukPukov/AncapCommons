// Original implementation by Andrew Kirilenko (https://github.com/gojuno/go.morton and https://github.com/gojuno/morton-java)
package ru.pukpukov.commons.compact;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;

@EqualsAndHashCode @ToString
public class Morton64 {

    private final long dimensions;
    private final long bits;
    private final long[] masks;
    private final long[] leftShifts;
    private final long[] rightShifts;

    public Morton64(long dimensions, long bits) {
        if (dimensions <= 0 || bits <= 0 || dimensions * bits > 64) throw new RuntimeException(String.format("can't make morton64 with %d dimensions and %d bits", dimensions, bits));

        this.dimensions = dimensions;
        this.bits = bits;

        long mask = (1L << this.bits) - 1;

        long shift = this.dimensions * (this.bits - 1);
        shift |= shift >>> 1;
        shift |= shift >>> 2;
        shift |= shift >>> 4;
        shift |= shift >>> 8;
        shift |= shift >>> 16;
        shift |= shift >>> 32;
        shift -= shift >>> 1;

        ArrayList<Long> masks = new ArrayList<>();
        ArrayList<Long> leftShifts = new ArrayList<>();

        masks.add(mask);
        leftShifts.add(0L);

        while (shift > 0) {
            mask = 0;
            long shifted = 0;

            for (long bit = 0; bit < this.bits; bit++) {
                long distance = (dimensions * bit) - bit;
                shifted |= shift & distance;
                mask |= 1L << bit << ((-shift) & distance);
            }

            if (shifted != 0) {
                masks.add(mask);
                leftShifts.add(shift);
            }

            shift >>>= 1;
        }

        this.masks = new long[masks.size()];
        for (int i = 0; i < masks.size(); i++) this.masks[i] = masks.get(i);

        this.leftShifts = new long[leftShifts.size()];
        for (int i = 0; i < leftShifts.size(); i++) this.leftShifts[i] = leftShifts.get(i);

        this.rightShifts = new long[leftShifts.size()];
        for (int i = 0; i < leftShifts.size() - 1; i++) this.rightShifts[i] = leftShifts.get(i + 1);
        this.rightShifts[this.rightShifts.length - 1] = 0;
    }

    public long pack(long... values) {
        dimensionsCheck(values.length);
        for (long value : values) valueCheck(value);
        long code = 0;
        for (int i = 0; i < values.length; i++) code |= split(values[i]) << i;
        return code;
    }

    public long signedPack(long... values) {
        long[] unsignedValues = new long[values.length];
        for (int i = 0; i < values.length; i++) unsignedValues[i] = shiftSign(values[i]);
        return pack(unsignedValues);
    }

    public long[] unpack(long code) {
        long[] values = new long[(int)this.dimensions];
        return unpack(code, values);
    }

    public long[] unpack(long code, long[] values) {
        for (int i = 0; i < values.length; i++) values[i] = compact(code >> i);
        return values;
    }

    public long[] signedUnpack(long code) {
        long[] values = new long[(int)this.dimensions];
        return signedUnpack(code, values);
    }

    public long[] signedUnpack(long code, long[] values) {
        unpack(code, values);
        for (int i = 0; i < values.length; i++) values[i] = unshiftSign(values[i]);
        return values;
    }

    private void dimensionsCheck(long dimensions) {
        if (this.dimensions != dimensions) throw new RuntimeException(String.format("morton64 with %d dimensions received %d values", this.dimensions, dimensions));
    }

    private void valueCheck(long value) {
        if (value < 0 || value >= (1L << this.bits)) throw new RuntimeException(String.format("morton64 with %d bits per dimension received %d to pack", this.bits, value));
    }

    private long shiftSign(long value) {
        if (value >= (1L << (this.bits - 1)) || value <= -(1L << (this.bits - 1))) throw new RuntimeException(String.format("morton64 with %d bits per dimension received signed %d to pack", this.bits, value));

        if (value < 0) {
            value = -value;
            value |= 1L << (this.bits - 1);
        }
        return value;
    }

    private long unshiftSign(long value) {
        long sign = value & (1L << (this.bits - 1));
        value &= (1L << (this.bits - 1)) - 1;
        if (sign != 0) value = -value;
        return value;
    }

    private long split(long value) {
        for (int o = 0; o < this.masks.length; o ++) value = (value | (value << this.leftShifts[o])) & this.masks[o];
        return value;
    }

    private long compact(long code) {
        for (int o = this.masks.length - 1; o >= 0; o--) code = (code | (code >>> this.rightShifts[o])) & this.masks[o];
        return code;
    }

}