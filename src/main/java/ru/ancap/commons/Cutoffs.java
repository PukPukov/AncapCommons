package ru.ancap.commons;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@ToString @EqualsAndHashCode
public class Cutoffs<T> {
    
    private final TreeSet<Cutoff<T>> set = new TreeSet<>();
    private final AtomicInteger index = new AtomicInteger(0);

    public void mark(T t) {
        this.set.add(new Cutoff<>(this.index.incrementAndGet(), System.currentTimeMillis(), t));
    }

    /**
     * Pulls all objects, that was marked from the time in arguments.
     */
    public List<T> from(long time) {
        Set<Cutoff<T>> cutoffSet = this.set.tailSet(new Cutoff<>(-1, time, null), false);
        List<T> pulledValues = new ArrayList<>();
        for (Cutoff<T> cutoff : cutoffSet) pulledValues.add(cutoff.value());
        return pulledValues;
    }
    
    @AllArgsConstructor
    @ToString /* @EqualsAndHashCode уже реализовано */
    public static class Cutoff<T> implements Comparable<Cutoff<?>> {

        private final int  index;
        private final long time;
        private final T    value;

        @Override
        public int compareTo(@NotNull Cutoffs.Cutoff<?> other) {
            int result = Long.compare(this.time, other.time);
            if (result == 0) result = Long.compare(this.index, other.index);
            return result;
        }
        
        public long time()  { return this.time;  }
        public T    value() { return this.value; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cutoff<?> cutoff = (Cutoff<?>) o;
            return this.index == cutoff.index;
        }

        @Override // я не просто так вручную написал hashCode, не менять
        public int hashCode() {
            return Objects.hash(this.index);
        }
        
    }
    
}
