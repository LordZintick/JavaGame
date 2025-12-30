package com.lordzintick.core;

import com.badlogic.gdx.Gdx;

/**
 * A small wrapper class to provide some small utilities for logging
 */
public class Logger {
    private final String prefix;

    /**
     * Constructs a new {@link Logger} with the prefix set to the provided {@code clazz}'s canonical name
     * @param clazz The class to use for the prefix
     */
    public Logger(Class<?> clazz) {
        this.prefix = clazz.getCanonicalName() == null ? "<anonymous class>" : clazz.getCanonicalName();
    }

    /**
     * Simply logs a message to the output
     * @param msg The message to log
     */
    public void log(String msg) {
        Gdx.app.log(prefix, msg);
    }
}
