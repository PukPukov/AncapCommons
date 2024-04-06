package ru.ancap.commons;

import lombok.*;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import ru.ancap.commons.time.TimeProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

@ToString @EqualsAndHashCode
@RequiredArgsConstructor
public class Cutoffs<T> {
    
    public static TimeProvider timeProvider = TimeProvider.SYSTEM_CLOCK;
    
    private final TreeSet<Cutoff<T>> set = new TreeSet<>();
    private final AtomicInteger index = new AtomicInteger(0);
    private final int size;

    public void mark(T t) {
        if (this.set.size() >= this.size) this.set.pollFirst();
        this.set.add(new Cutoff<>(this.index.incrementAndGet(), timeProvider.currentTime(), t));
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
    @ToString @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    @Accessors(fluent = true) @Getter
    public static class Cutoff<T> implements Comparable<Cutoff<?>> {

        @EqualsAndHashCode.Include
        private final int  index;
        private final long time;
        private final T    value;

        @Override
        public int compareTo(@NotNull Cutoffs.Cutoff<?> other) {
            int result = Long.compare(this.time, other.time);
            if (result == 0) result = Long.compare(this.index, other.index);
            return result;
        }
        
    }
    
}