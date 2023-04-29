package ru.ancap.commons.list.with;

import java.util.List;

public interface WithList<T> extends List<T> {
    
    WithList<T> with(T value);
    WithList<T> without(T value);
    
}
