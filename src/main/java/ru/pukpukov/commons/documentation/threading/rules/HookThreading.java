package ru.pukpukov.commons.documentation.threading.rules;

/**
 * Обозначает методы и поля, доступ к которым не является потокобезопасным, но поток, из которого должен
 * происходить доступ в целом очевиден. Частный случай @LogicalThreading.
 */
public @interface HookThreading { }