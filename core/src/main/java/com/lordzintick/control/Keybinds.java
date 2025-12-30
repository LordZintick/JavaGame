package com.lordzintick.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.lordzintick.core.Logger;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A utility class to hold all the defined {@link Keybind}s and their default states
 */
public final class Keybinds {
    private static final Logger LOGGER = new Logger(Keybinds.class);
    /**
     * A {@link HashMap} containing all the registered {@link Keybind}s
     */
    public static final HashMap<String, Keybind> KEYBINDS = new HashMap<>();

    public static final Keybind UP = register("up", new Keybind(Input.Keys.W, Keybind.Context.GAME, () -> {LOGGER.log("UP pressed");}));
    public static final Keybind DOWN = register("down", new Keybind(Input.Keys.S, Keybind.Context.GAME, () -> {LOGGER.log("DOWN pressed");}));
    public static final Keybind LEFT = register("left", new Keybind(Input.Keys.A, Keybind.Context.GAME, () -> {LOGGER.log("LEFT pressed");}));
    public static final Keybind RIGHT = register("right", new Keybind(Input.Keys.D, Keybind.Context.GAME, () -> {LOGGER.log("RIGHT pressed");}));

    /**
     * Registers a new {@link Keybind} into the {@link Keybinds#KEYBINDS} map for use in the {@link Input} class, and throws an error if the ID already exists
     * @param id The ID to register the keybind under. Unused for now other than preventing two keybinds with the same ID from existing at once
     * @param keybind The actual keybind to register
     * @return The keybind registered
     */
    private static Keybind register(String id, Keybind keybind) {
        if (KEYBINDS.containsKey(id))
            throw new IllegalArgumentException("ID already exists!");

        KEYBINDS.put(id, keybind);
        return keybind;
    }
}
