package ru.ancap.commons.aware;


public @interface ContextAware {

    Aware[] awareOf();
    InsecureContextHandle handle();
    
}
