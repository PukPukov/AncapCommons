package ru.ancap.commons.pattern;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MultiPattern implements Pattern {
    
    private final List<Pattern> variants;
    
    @Override
    public boolean match(String string) {
        for (Pattern pattern : this.variants) {
            if (pattern.match(string)) return true;
        }
        return false;
    }
    
}
