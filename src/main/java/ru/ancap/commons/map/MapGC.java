package ru.ancap.commons.map;

import lombok.RequiredArgsConstructor;
import ru.ancap.commons.instructor.Instructor;

import java.util.function.Function;

@RequiredArgsConstructor
public class MapGC<K> {
    
    public static <K> Function<Instructor<K>, MapGC<K>> FROM_INSTRUCTOR() {
        return MapGC::new;
    }
    
    private final Instructor<K> logic;

    public void add(SafeMap<K, ?> map) {
        this.logic.accept(map::remove);
    }
    
}
