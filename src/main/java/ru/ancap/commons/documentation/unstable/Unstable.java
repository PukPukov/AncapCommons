package ru.ancap.commons.documentation.unstable;

import java.lang.annotation.Repeatable;

/**
 * Marks entries that will explicitly not preserve backwards compatibility.
 */
@Unstable(finalizationExpectations = Expectations.PREVIEW)
@Repeatable(DifferentiatedlyUnstable.class)
public @interface Unstable {
    
    String finalizationExpectations(); // You can use presets from Expectations class or write your own expectations (e.g. "1.0")
    String what() default "Everything"; // What part is unstable?
    
}