package ru.ancap.commons.random;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

@RequiredArgsConstructor
@Accessors(fluent = true) @Getter
public class Weights<T> {
    
    private final List<Weight<T>> weights;
    private final long totalWeight;
    private final long[] prefixSums;
    
    public Weights(List<Weight<T>> weights) {
        if (weights.isEmpty()) throw new IllegalArgumentException("There should be something in weights.");
        this.weights = weights;
        long total = 0;
        this.prefixSums = new long[weights.size()];
        for (int i = 0; i < weights.size(); i++) {
            int weight = weights.get(i).weight();
            if (weight < 0) throw new IllegalArgumentException("Weight should be positive");
            total += weight;
            this.prefixSums[i] = total;
        }
        this.totalWeight = total;
    }
    
    public T roll() {
        return this.roll(ThreadLocalRandom.current());
    }
    
    public T roll(RandomGenerator random) {
        double randomValue = random.nextLong(1, this.totalWeight+1);
        
        int index = Arrays.binarySearch(this.prefixSums, (long) randomValue);
        if (index < 0) {
            index = -index - 1;
        }
        return this.weights.get(index).result();
    }
    
}