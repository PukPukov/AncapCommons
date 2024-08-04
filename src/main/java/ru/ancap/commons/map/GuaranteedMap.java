package ru.ancap.commons.map;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Delegate;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString @EqualsAndHashCode
public class GuaranteedMap<K, V> implements Map<K, V> {
    
    private final SafeMap<K, V> base; // two fields are required due to lack of @Delegate support in lombok
    @Delegate
    private final Map<K, V> baseDelegate;
    
    public GuaranteedMap(Map<K, V> base, Supplier<V> guarantor) {
        this(SafeMap.builder(base)
            .guaranteed(guarantor)
            .build());
    }
    
    public GuaranteedMap(Supplier<V> guarantor) {
        this(new HashMap<>(), guarantor);
    }
    
    public GuaranteedMap(SafeMap<K, V> base) {
        this.base = base;
        this.baseDelegate = base;
    }
    
    public @NotNull V getExplicitlyPlaced(Object key) {
        return this.base.getExplicitlyPlaced(key);
    }
    
}