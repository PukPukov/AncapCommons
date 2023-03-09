package ru.ancap.commons.pattern;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MultiPattern implements Pattern {
    
    private final List<Pattern> toComply;
    
    @Override
    public boolean match(String string) {
        for (Pattern pattern : this.toComply) {
            if (!pattern.match(string)) return false;
        }
        return true;
    }
    
}
