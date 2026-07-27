package ru.pukpukov.commons.string;

import lombok.AllArgsConstructor;

import java.util.regex.Pattern;

@AllArgsConstructor
public class PhoneNumberFormatter {
    
    public static String all(String orig) {
        return internationalizeRu(dehumanize(orig));
    }
    
    private static final Pattern dehumanizer = Pattern.compile("[ +-]");
    
    public static String dehumanize(String str) {
        return dehumanizer.matcher(str).replaceAll("");
    };
    
    public static String internationalizeRu(String str) {
        if (!str.startsWith("8")) {
            return str;
        }
        var buffer = new StringBuilder(str);
        buffer.replace(0, 1, "7");
        return buffer.toString();
    }
    
}