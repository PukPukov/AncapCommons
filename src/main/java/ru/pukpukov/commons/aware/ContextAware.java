package ru.pukpukov.commons.aware;


public @interface ContextAware {

    Aware[] awareOf();
    InsecureContextHandle handle();
    
}
