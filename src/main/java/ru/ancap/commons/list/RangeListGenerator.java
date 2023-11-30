package ru.ancap.commons.list;

import java.util.ArrayList;
import java.util.List;

public class RangeListGenerator {

    public static List<Long> generate(long fromInclusive, long toExclusive) {
        int length = Math.toIntExact(toExclusive-fromInclusive);
        List<Long> list = new ArrayList<>(length);
        for (long i = fromInclusive; i < toExclusive; i++) {
            list.add(i);
        }
        return list;
    }

}
