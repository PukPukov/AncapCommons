package ru.ancap.commons.iterable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IterableToArray {

    /**
     * Not for runtime production — uses reflection and checks type for every entry since not optimized. Made to use in auto tests.
     */
    public static Object[] deepRecursiveReflective(Iterable<?> iterable) {
        Object[] array = shallow(iterable);
        for (int i = 0, arrayLength = array.length; i < arrayLength; i++) {
            Object object = array[i];
            if (object instanceof Iterable) {
                array[i] = deepRecursiveReflective((Iterable<?>) object);
            }
        }
        return array;
    }

    /**
     * Not for runtime production
     */
    public static Object[] shallow(Iterable<?> iterable) {
        int size = 10; if (iterable instanceof Collection) size = ((Collection<?>) iterable).size();
        List<Object> list = new ArrayList<>(size);
        for (Object object : iterable) {
            list.add(object);
        }
        return list.toArray();
    }

}
