package ru.ancap.commons.instructor;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class Batcher<TYPE> implements Instructor<List<TYPE>> {

    private final Instructor<TYPE> original;
    private final int batchSize;

    @Override
    public void accept(Consumer<List<TYPE>> consumer) {
        List<TYPE> batchAccumulator = new ArrayList<>(this.batchSize);
        this.original.accept(value -> {
            batchAccumulator.add(value);
            if (batchAccumulator.size() == this.batchSize) {
                consumer.accept(List.copyOf(batchAccumulator));
                batchAccumulator.clear();
            }
        });
    }

}
