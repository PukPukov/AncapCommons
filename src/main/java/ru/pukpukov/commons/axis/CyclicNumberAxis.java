package ru.pukpukov.commons.axis;

public class CyclicNumberAxis implements NumberAxis {

    public static CyclicNumberAxis HEXAGONAL = new CyclicNumberAxis(6);
    public static CyclicNumberAxis CIRCLE    = new CyclicNumberAxis(Math.PI * 2);

    private final double boundOffset;
    private final double period;

    public CyclicNumberAxis(double period) {
        this(0, period, null);
    }

    public CyclicNumberAxis(double start, double end) {
        this(start, end - start, null);
    }

    private CyclicNumberAxis(double boundOffset, double period, Object hack_to_add_other_method_with_the_same_signature) {
        if (period <= 0) throw new IllegalArgumentException("Period can not be smaller or equal to 0");
        this.boundOffset = boundOffset;
        this.period = period;
    }

    @Override
    public double offset(double base, double steps) {
        double baseMod = base % this.period;

        double offset = steps % this.period;

        double newBase = baseMod + offset;
        if (newBase >= this.period) newBase -= this.period;
        else if (newBase < 0) newBase += this.period;

        return newBase + this.boundOffset;
    }

}
