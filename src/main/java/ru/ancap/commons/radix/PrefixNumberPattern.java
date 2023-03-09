package ru.ancap.commons.radix;

import ru.ancap.commons.Logic;
import ru.ancap.commons.cache.CacheMap;
import ru.ancap.commons.pattern.Pattern;

public class PrefixNumberPattern implements Pattern, RadixProvider {

    @Override
    public boolean match(String string) {
        return this.radix(string) != null;
    }
    
    private final CacheMap<String, Integer> radixCache = new CacheMap<>();

    @Override
    public Integer radix(String string) {
        return this.radixCache.get(string, () -> {
            if (string.startsWith("0b")) return 2;
            else if (string.startsWith("0x")) return 16;
            else if (string.startsWith("0")) return 8;
            else if (Logic.completes(() -> Integer.parseInt(string, 10))) return 10;
            return null;
        });
    }
    
}
