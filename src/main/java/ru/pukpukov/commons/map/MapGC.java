package ru.pukpukov.commons.map;

import lombok.RequiredArgsConstructor;
import ru.pukpukov.commons.instructor.Instructor;

import java.util.Map;

@RequiredArgsConstructor
public class MapGC<K> {
    
    private final Instructor<K> logic;
    
    public void add(Map<K, ?> map) {
        this.logic.subscribe(map::remove);
    }
    
}