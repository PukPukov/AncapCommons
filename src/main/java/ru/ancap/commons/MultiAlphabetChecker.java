package ru.ancap.commons;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Should be instantiated for every check.
 */
public class MultiAlphabetChecker {
    
    public static boolean isMultiAlphabetic(String string) {
        MultiAlphabetChecker multiAlphabetChecker = new MultiAlphabetChecker();
        
        for (char c : string.toCharArray()) {
            if (
                (c >= 'А' && c <= 'я')
                    || c == 'Ё' || c == 'ё'
                    || c == 'Ґ' || c == 'ґ' || c == 'Є' || c == 'є' || c == 'І' || c == 'і' || c == 'Ї' || c == 'ї'
            ) {
                multiAlphabetChecker.check("slavic");
            } else if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                multiAlphabetChecker.check("latin");
            } else if ((c >= 'Α' && c <= 'Ω') || (c >= 'α' && c <= 'ω')) {
                multiAlphabetChecker.check("greek");
            } else if (Pattern.matches("[\u0590-\u05FF\uFB1D-\uFB4F]", String.valueOf(c))) {
                multiAlphabetChecker.check("hebrew");
            } else if (Pattern.matches("[\u0600-\u06FF\u0750-\u077F]", String.valueOf(c))) {
                multiAlphabetChecker.check("arabic");
            } else if (Pattern.matches("[\u10D0-\u10FF]", String.valueOf(c))) {
                multiAlphabetChecker.check("georgian");
            }
        }
        
        return multiAlphabetChecker.alphabetAmount() > 1;
    }
    
    private final Map<String, Boolean> checker = new HashMap<>();
    private int alphabetAmount = 0;
    
    public void check(String alphabetName) {
        var current = this.checker.getOrDefault(alphabetName, false);
        if (current == false) {
            this.alphabetAmount++;
            this.checker.put(alphabetName, true);
        }
    }
    
    public int alphabetAmount() {
        return this.alphabetAmount;
    }
    
}