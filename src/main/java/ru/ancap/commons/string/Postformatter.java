package ru.ancap.commons.string;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Postformatter {
    
    public static final int INDENT_SIZE = 4;
    
    public static String postformat(String shittyToStringOutput) {
        StringBuilder formatted = new StringBuilder();
        char[] chars = shittyToStringOutput.toCharArray();
        int length = chars.length;
        
        for (int i = 0; i < length; i++) {
            char current = chars[i];
            char prev = i - 1 > 0 ? chars[i - 1] : '\0';
            char next = i + 1 < length ? chars[i + 1] : '\0';
            
            if ((current == '[' && next != ']') || (current == '{'  && next != '}') || (current == '('  && next != ')')) formatted.append(current).append('\n');
            else if ((current == ']' && prev != '[') || (current == '}' && prev != '{') || (current == ')' && prev != '(')) {
                if (next != ',' && next != '\"') formatted.append('\n').append(current).append('\n');
                else {
                    formatted.append('\n').append(current).append(next).append('\n');
                    i++;
                }
            } else formatted.append(current);
        }
        
        String[] lines = formatted.toString().split("\n");
        
        int indentLevel = 0;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (
                line.endsWith("}")|| line.endsWith("}\"") || line.endsWith("},") ||
                    line.endsWith("]")|| line.endsWith("]\"") || line.endsWith("],") ||
                    line.endsWith(")")|| line.endsWith(")\"") || line.endsWith("),")
            ) indentLevel--;
            lines[i] = setIndents(line, indentLevel);
            if (line.endsWith("[") || line.endsWith("{") || line.endsWith("(")) indentLevel++;
        }
        return Arrays.stream(lines)
            .filter(line -> !line.isBlank())
            .collect(Collectors.joining("\n"));
    }
    
    private static String setIndents(String line, int indentLevel) {
        return  " ".repeat(Math.max(0, indentLevel * INDENT_SIZE)) + line.stripIndent();
    }
    
}