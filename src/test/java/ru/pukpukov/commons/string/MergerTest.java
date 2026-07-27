package ru.pukpukov.commons.string;

import org.junit.jupiter.api.Test;
import ru.pukpukov.commons.pattern.ConstantPattern;
import ru.pukpukov.commons.pattern.MultiPattern;
import ru.pukpukov.commons.pattern.Pattern;
import ru.pukpukov.commons.radix.NumberPattern;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MergerTest {
    
    private final LiteralMerger literalMerger = new LiteralMerger();
    
    private final Set<String> constants = Set.of(
        "example",
        "exam",
        "CONSTANT",
        "something else"
    );
    
    @Test
    public void testConstantPattern() {
        Pattern pattern = new ConstantPattern(this.constants);
        assertEquals(List.of("Some", "merged", "text", "for", "example"),      this.literalMerger.merge(pattern, List.of("Some", "merged", "text", "for", "example")));
        assertEquals(List.of("Some", "merged", "text", "for", "example"),      this.literalMerger.merge(pattern, List.of("Some", "merged", "text", "for", "exa", "mple")));
        assertEquals(List.of("Some", "merged", "text", "for", "example", "1"), this.literalMerger.merge(pattern, List.of("Some", "merged", "text", "for", "exa", "mple", "1")));
        assertEquals(List.of("Some", "merged", "text", "for", "example"),      this.literalMerger.merge(pattern, List.of("Some", "merged", "text", "for", "e", "xample")));
        for (int i = 0; i < 100; i++) assertEquals(List.of("e", "example"),    this.literalMerger.merge(pattern, List.of("e", "e", "xam", "ple")));
    }
    
    @Test
    public void testNumberPrefixPattern() {
        Pattern pattern = new NumberPattern();
        assertEquals(List.of("Some", "0xCAFEBABE", "for", "example"),          this.literalMerger.merge(pattern, List.of("Some", "0", "xCAFEBABE", "for", "example")));
        assertEquals(List.of("Some", "0xCAFEBABE", "for", "example"),          this.literalMerger.merge(pattern, List.of("Some", "0", "xCAFE", "BABE", "for", "example")));
    }
    
    @Test
    public void testMultiPattern() {
        Pattern pattern = new MultiPattern(List.of(
            new ConstantPattern(this.constants),
            new NumberPattern()
        ));
        
        assertEquals(List.of("Some", "0xCAFEBABE", "for", "example"),          this.literalMerger.merge(pattern, List.of("Some", "0", "xCAF", "EB", "A", "BE", "for", "e", "xam", "ple")));
    }
    
}
