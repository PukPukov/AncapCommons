package ru.pukpukov.commons.string;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OnConstantSplitterTest {
    
    @Test
    public void test() {
        OnConstantSplitter splitter = new OnConstantSplitter();
        this.testSplitter(splitter, List.of("ab", "cd", "ef"),   "abcdef",   Set.of("ab", "cd", "ef"));
        this.testSplitter(splitter, List.of("abc", "def"),       "abcdef",   Set.of("ab", "def", "cd", "abc", "ef"));
        this.testSplitter(splitter, List.of("abc", "def", "y"),  "abcdefy",  Set.of("y", "ab", "def", "cd", "abc", "ef"));
        this.testSplitter(splitter, null,                        "abcdefy1", Set.of("y", "ab", "def", "cd", "abc", "ef"));
        this.testSplitter(splitter, List.of("abcd", "ef"),       "abcdef",   Set.of("ab", "cd", "ef", "cde", "f", "abcd"));
        this.testSplitter(splitter, List.of("abcd", "ef", "y"),  "abcdefy",  Set.of("ab", "cd", "ef", "cde", "f", "abcd", "y"));
        this.testSplitter(splitter, List.of("abcd", "efy"),      "abcdefy",  Set.of("cd", "efy", "cde", "ab", "f", "abcd", "y"));
        this.testSplitter(splitter, List.of("abcd", "z", "efy"), "abcdzefy", Set.of("efy", "cd", "ef", "cde", "ab", "q", "f", "abcd", "y", "z"));
        
        this.testSplitter(splitter, 
            List.of(
                "abcdefy", "zzzz", "zzzz", "zzzz", "zzzz", "zzzz", "zzzz", "zzzz", "zzzz", "zzzz", "zzzz", "zzzz", 
                "zzzz", "zzzz", "zzzz", "zzzz", "zzzz", "zzzz", "zzzz", "zzzz", "zzzz", "zzzz", "zzzz", "zzzz", "zzzz", 
                "zzzz", "zzzz", "zzzz", "abcdy", "abcdefy"
            ),
            "abcdefyzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzabcdyabcdefy",
            Set.of(
                "cd", "z", "ef", "cde", "ab", "q", "f", "abcd", "y", "zu", "umbro", "umbro2", "umbor", "1", "cda", "efa", 
                "cdea", "aba", "qa", "fa", "abcda", "ya", "zzzz", "zzz", "zua", "fdg", "gdfgfd", "ghjghj", "a1",
                "ebf", "cdbe", "bq", "abcdefy", "efy", "zbu", "gfg", "gh", "b1", "cdb", "efb", "cdeb", "abb", "qb", 
                "fb", "abcdb", "by", "bz", "bzu", "ochbghjko", "ghj", "1b", "abcdy"
            )
        );

        this.testFuzzySplit(splitter, "zzzzz", 2, Set.of("zz", "zzz"));
        this.testFuzzySplit(splitter, "zzzzzzzzzz", 4, Set.of("zz", "zzz"));
        this.testFuzzySplit(splitter, "zzzzazzzzzz", 5, Set.of("zz", "a", "zzz"));
    }

    private void testFuzzySplit(OnConstantSplitter splitter, String input, int outputLength, Set<String> constants) {
        List<String> compositions = splitter.findMinimalComposition(input, constants);
        assert compositions != null;
        StringBuilder rebuild = new StringBuilder(); for (String part : compositions) rebuild.append(part);
        assertEquals(rebuild.toString(), input);
        assertEquals(outputLength, compositions.size());
    }

    private void testSplitter(OnConstantSplitter splitter, List<String> output, String input, Set<String> constants) {
        assertEquals(output, splitter.findMinimalComposition(input, constants));
        
        int maximumOperations = input.length() * constants.size();
        assertTrue(maximumOperations >= splitter.lastCallIterations, maximumOperations+" < "+splitter.lastCallIterations); // Test that complexity is O(n * m)
    }

}
