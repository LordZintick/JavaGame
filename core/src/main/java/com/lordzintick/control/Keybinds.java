package com.lordzintick.control;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controllers;
import com.lordzintick.MainGame;
import com.lordzintick.core.Logger;

import java.security.Key;
import java.util.Collection;
import java.util.HashMap;

/**
 * A utility class to hold all the defined {@link Keybind}s and their default states
 */
public final class Keybinds {
    private final Logger LOGGER = new Logger(Keybinds.class);
    /**
     * A {@link HashMap} containing all the registered {@link Keybind}s
     */
    private final HashMap<String, Keybind> KEYBINDS = new HashMap<>();

    public Keybinds(MainGame game) {
        Runnable pauseAction = () -> {
            if (game.screen.isPaused()) {
                game.screen.resume();
            } else {
                game.screen.pause();
            }
        };

        if (Controllers.getCurrent() != null && Controllers.getCurrent().isConnected()) {
            register("up", new Keybind(Controllers.getCurrent().getMapping().buttonDpadUp, Keybind.Context.GAMEPAD, () -> {
            }));
            register("down", new Keybind(Controllers.getCurrent().getMapping().buttonDpadDown, Keybind.Context.GAMEPAD, () -> {
            }));
            register("left", new Keybind(Controllers.getCurrent().getMapping().buttonDpadLeft, Keybind.Context.GAMEPAD, () -> {
            }));
            register("right", new Keybind(Controllers.getCurrent().getMapping().buttonDpadRight, Keybind.Context.GAMEPAD, () -> {
            }));
            register("scroll_up", new Keybind(Controllers.getCurrent().getMapping().buttonDpadUp, Keybind.Context.GAMEPAD_UI, () -> {
            }));
            register("scroll_down", new Keybind(Controllers.getCurrent().getMapping().buttonDpadDown, Keybind.Context.GAMEPAD_UI, () -> {
            }));
            register("attack_1", new Keybind(Controllers.getCurrent().getMapping().buttonX, Keybind.Context.GAMEPAD, () -> {}));
            register("attack_2", new Keybind(Controllers.getCurrent().getMapping().buttonA, Keybind.Context.GAMEPAD, () -> {}));
            register("attack_3", new Keybind(Controllers.getCurrent().getMapping().buttonB, Keybind.Context.GAMEPAD, () -> {}));
            register("pause", new Keybind(Controllers.getCurrent().getMapping().buttonStart, Keybind.Context.GAMEPAD, pauseAction));
            register("dash", new Keybind(Controllers.getCurrent().getMapping().buttonR1, Keybind.Context.GAMEPAD, () -> {}));
        } else {
            register("up", new Keybind(Input.Keys.W, Keybind.Context.GAME, () -> {}));
            register("down", new Keybind(Input.Keys.S, Keybind.Context.GAME, () -> {}));
            register("left", new Keybind(Input.Keys.A, Keybind.Context.GAME, () -> {}));
            register("right", new Keybind(Input.Keys.D, Keybind.Context.GAME, () -> {}));
            register("arrow_up", new Keybind(Input.Keys.UP, Keybind.Context.GAME, () -> {}));
            register("arrow_down", new Keybind(Input.Keys.DOWN, Keybind.Context.GAME, () -> {}));
            register("arrow_left", new Keybind(Input.Keys.LEFT, Keybind.Context.GAME, () -> {}));
            register("arrow_right", new Keybind(Input.Keys.RIGHT, Keybind.Context.GAME, () -> {}));

            register("pause", new Keybind(Input.Keys.ESCAPE, Keybind.Context.GAME, pauseAction));
            register("dash", new Keybind(Input.Keys.SPACE, Keybind.Context.GAME, () -> {}));
        }
    }

    /**
     * Registers a new {@link Keybind} into the {@link Keybinds#KEYBINDS} map for use in the {@link Input} class, and throws an error if the ID already exists
     * @param id The ID to register the keybind under. Unused for now other than preventing two keybinds with the same ID from existing at once
     * @param keybind The actual keybind to register
     * @return The keybind registered
     */
    private Keybind register(String id, Keybind keybind) {
        if (KEYBINDS.containsKey(id))
            throw new IllegalArgumentException("ID already exists!");

        KEYBINDS.put(id, keybind);
        return keybind;
    }

    public Keybind get(String id) {
        if (!KEYBINDS.containsKey(id)) return Keybind.unknown();
        return KEYBINDS.get(id);
    }

    public Collection<Keybind> getKeybinds() {
        return KEYBINDS.values();
    }
}
