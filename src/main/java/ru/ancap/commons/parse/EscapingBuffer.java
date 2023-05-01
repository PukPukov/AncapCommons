package ru.ancap.commons.parse;

public class EscapingBuffer {
    
    private boolean currentlyEscaped;
    private boolean escapeNext;
    
    public void step() {
        this.currentlyEscaped = this.escapeNext;
        this.escapeNext = false;
    }
    
    public boolean currentlyEscaped() {
        return this.currentlyEscaped;
    }
    
    public void escapeNext() {
        this.escapeNext = true;
    }
    
}
