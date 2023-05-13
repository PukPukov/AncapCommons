package ru.ancap.commons.list.merge;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.List;

@ToString @EqualsAndHashCode
public class MergeList<T> implements List<T> {

    @Delegate
    private final List<T> delegate;
    
    public MergeList(List<T> one, List<T> other) {
        this(one, other, false);
    }
    
    public MergeList(List<T> one, List<T> other, boolean immutable) {
        List<T> newList = new ArrayList<>();
        newList.addAll(one);
        newList.addAll(other);
        if (immutable) newList = List.copyOf(newList); 
        this.delegate = newList;
    }
    
}
