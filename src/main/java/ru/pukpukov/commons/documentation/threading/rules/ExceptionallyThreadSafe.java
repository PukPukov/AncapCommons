package ru.pukpukov.commons.documentation.threading.rules;

/**
 * Инвариант соблюдается, но только потому что при доступе из левого потока гарантированно получишь экспепшен в ебало.
 */
public @interface ExceptionallyThreadSafe {}