package ru.pukpukov.commons.parse;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString @EqualsAndHashCode
public class EscapingBuffer {
    
    private boolean currentlyEscaped;
    private boolean escapeNext0; // 0 because of retarded lombok bug
    
    public void step() {
        this.currentlyEscaped = this.escapeNext0;
        this.escapeNext0 = false;
    }
    
    public boolean currentlyEscaped() {
        return this.currentlyEscaped;
    }
    
    public void escapeNext() {
        this.escapeNext0 = true;
    }
    
}