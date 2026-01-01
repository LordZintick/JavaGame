package com.lordzintick.util;

@FunctionalInterface
public interface TriConsumer<T, E, R> {
    void accept(T t, E e, R r);
}
