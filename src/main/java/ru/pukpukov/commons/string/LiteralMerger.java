package ru.pukpukov.commons.string;

import ru.pukpukov.commons.pattern.Pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Will merge literals in input list, if the merged literal will match pattern
 */

public class LiteralMerger {

    public List<String> merge(Pattern pattern, List<String> input) {
        List<String> holder = input;
        while (true) {
            List<String> next = this.mergeStep(pattern, holder);
            if (next.equals(holder)) break;
            holder = next;
        }
        return holder;
    }

    public List<String> mergeStep(Pattern pattern, List<String> input) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            String current = input.get(i);
            list.add(current);

            if (i < input.size() - 1) {
                String next = input.get(i+1);
                String combined = current + next;
                if (pattern.match(combined)) {
                    list.remove(list.size() - 1);
                    list.add(combined);
                    i++;
                } else {
                    for (int j = i + 2; j < input.size(); j++) {
                        next = input.get(j);
                        combined += next;
                        if (pattern.match(combined)) {
                            list.remove(list.size() - 1);
                            list.add(combined);
                            i = j;
                            break;
                        }
                    }
                }
            }
        }
        
        return list;
    }

}