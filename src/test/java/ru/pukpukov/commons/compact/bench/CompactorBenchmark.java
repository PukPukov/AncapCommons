package ru.pukpukov.commons.compact.bench;

import lombok.RequiredArgsConstructor;
import ru.pukpukov.commons.compact.BitwiseCompactor;
import ru.pukpukov.commons.compact.Compactor;
import ru.pukpukov.commons.compact.Morton64Compactor;
import ru.pukpukov.commons.debug.HandTest;

import java.util.Random;

/**
 * Result: <br>
 * <b>Morton64</b> pack: 22 ns, unpack: 15 ns <br>
 * <b>Bitwise</b> pack: 10 ns, unpack: 5 ns <br>
 */
@HandTest
@RequiredArgsConstructor
public class CompactorBenchmark {

    private final int iterations = 100000000;

    private final Random random = new Random();

    private final int[] basesX = new int[this.iterations];
    private final int[] basesY = new int[this.iterations];
    private final long[] packResults = new long[this.iterations];
    private final int[] unpackResultsX = new int[this.iterations];
    private final int[] unpackResultsY = new int[this.iterations];

    private final Compactor compactor;

    public static void main(String[] args) {
        new CompactorBenchmark(new Morton64Compactor()).run();
        new CompactorBenchmark(new BitwiseCompactor()).run();
    }

    private void run() {
        long startPack = System.nanoTime();

        this.fill(this.basesX);
        this.fill(this.basesY);

        for (int i = 0; i < this.iterations; i++) {
            this.packResults[i] = this.compactor.pack(this.basesX[i], this.basesY[i]);
        }

        long endPack = System.nanoTime();

        long startUnpack = System.nanoTime();

        for (int i = 0; i < this.iterations; i++) {
            int[] unpacked = this.compactor.unpack(this.packResults[i]);
            this.unpackResultsX[i] = unpacked[0];
            this.unpackResultsY[i] = unpacked[1];
        }

        long endUnpack = System.nanoTime();
        this.blackHole(this.packResults);
        this.blackHole(this.unpackResultsX);
        this.blackHole(this.unpackResultsY);
        this.releaseBlackHole();
        System.out.println(this.compactor.getClass().getSimpleName()+"'s pack time: " + ((endPack - startPack) / this.iterations ) +" nanoseconds");
        System.out.println(this.compactor.getClass().getSimpleName()+"'s unpack time: " + ((endUnpack - startUnpack) / this.iterations ) +" nanoseconds");
    }
    
    private void releaseBlackHole() {
        System.out.println();
    }

    private void blackHole(int[] unpackResults) {
        System.out.print(this.random.nextInt(unpackResults[unpackResults.length-1]));
    }

    private void blackHole(long[] packResults) {
        System.out.print(this.random.nextLong(packResults[packResults.length-1]));
    }

    private void fill(int[] bases) {
        for (int i = 0; i < bases.length; i++) {
            bases[i] = this.random.nextInt(-5000, 5000000);
        }
    }
    
}