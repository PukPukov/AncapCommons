package ru.ancap.commons.documentation.unstable;

/**
 * Expectations presets.
 */
public class Expectations {
    
    public static final String NEVER = "No backwards compatibility attempts will be made.";
    
    public static final String NONE = "It may be finalized in the future, or be downgraded to NEVER, and its impossible to speculate at the moment.";
    
    public static final String PREVIEW = "Something in between @Unstable(finalizationExpectations = Expectations.NONE) and @ApiStatus.Stable.";
    
}