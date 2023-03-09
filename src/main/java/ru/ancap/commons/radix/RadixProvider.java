package ru.ancap.commons.radix;

import org.jetbrains.annotations.Nullable;

public interface RadixProvider {
    
    @Nullable
    Integer radix(String string);
    
}
