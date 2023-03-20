package ru.ancap.commons.pattern;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@AllArgsConstructor
@ToString @EqualsAndHashCode
public class ConstantPattern implements Pattern {
    
    private final Set<String> constants;
    
    @Override
    public boolean match(String string) {
        return this.constants.contains(string);
    }
    
}
