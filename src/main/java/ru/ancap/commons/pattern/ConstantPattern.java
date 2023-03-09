package ru.ancap.commons.pattern;

import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class ConstantPattern implements Pattern {
    
    private final Set<String> constants;
    
    @Override
    public boolean match(String string) {
        return this.constants.contains(string);
    }
    
}
