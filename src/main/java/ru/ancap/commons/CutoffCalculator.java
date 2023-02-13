package ru.ancap.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class CutoffCalculator<T> {
    
    private final TreeSet<Cutoff<T>> set = new TreeSet<>();

    public void mark(T t) {
        this.set.add(new Cutoff<>(System.currentTimeMillis(), t));
    }

    /**
     * Pulls all objects, that was marked from the time in arguments.
     */
    public Set<T> from(long time) {
        Set<Cutoff<T>> cutoffSet = this.set.tailSet(new Cutoff<>(time, null), false);
        Set<T> pulledValues = new HashSet<>();
        for (Cutoff<T> cutoff : cutoffSet) {
            pulledValues.add(cutoff.getValue());
        }
        return pulledValues;
    }
    
    @AllArgsConstructor
    @Getter
    public static class Cutoff<T> implements Comparable<Cutoff<?>> {
        
        private final long time;
        private final T value;

        @Override
        public int compareTo(@NotNull CutoffCalculator.Cutoff<?> cutoff) {
            return Long.compare(this.time, cutoff.time);
        }
        
    }
    
}
