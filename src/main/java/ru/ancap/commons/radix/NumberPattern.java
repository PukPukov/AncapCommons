package ru.ancap.commons.radix;

import ru.ancap.commons.Logic;
import ru.ancap.commons.cache.CacheMap;
import ru.ancap.commons.pattern.Pattern;

public class NumberPattern implements Pattern, RadixProvider {

    @Override
    public boolean match(String string) {
        return this.radix(string) != null;
    }
    
    private final CacheMap<String, Integer> radixCache = new CacheMap<>();

    @Override
    public Integer radix(String string) {
        return this.radixCache.get(string, () -> {
            if      (string.startsWith("0b") && Logic.completes(() -> Long.parseLong(string.substring(2), 2)))  return 2;
            else if (string.startsWith("0x") && Logic.completes(() -> Long.parseLong(string.substring(2), 16))) return 16;
            else if (string.startsWith("0")  && Logic.completes(() -> Long.parseLong(string.substring(1), 8)))  return 8;
            else if (                           Logic.completes(() -> Long.parseLong(string, 10)))              return 10;
            return null;
        });
    }
    
}
