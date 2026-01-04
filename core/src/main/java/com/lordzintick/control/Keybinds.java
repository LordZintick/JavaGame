package com.lordzintick.control;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controllers;
import com.lordzintick.MainGame;
import com.lordzintick.core.Logger;

import java.security.Key;
import java.util.HashMap;

/**
 * A utility class to hold all the defined {@link Keybind}s and their default states
 */
public final class Keybinds {
    private final Logger LOGGER = new Logger(Keybinds.class);
    /**
     * A {@link HashMap} containing all the registered {@link Keybind}s
     */
    public final HashMap<String, Keybind> KEYBINDS = new HashMap<>();

    public final Keybind UP;
    public final Keybind DOWN;
    public final Keybind LEFT;
    public final Keybind RIGHT;
    public final Keybind ARROW_UP;
    public final Keybind ARROW_DOWN;
    public final Keybind ARROW_LEFT;
    public final Keybind ARROW_RIGHT;
    public final Keybind GAMEPAD_UP;
    public final Keybind GAMEPAD_DOWN;
    public final Keybind GAMEPAD_LEFT;
    public final Keybind GAMEPAD_RIGHT;
    public final Keybind ATTACK_1;
    public final Keybind ATTACK_2;
    public final Keybind ATTACK_3;
    public final Keybind PAUSE;
    public final Keybind DASH;

    public Keybinds(MainGame game) {
        Runnable pauseAction = () -> {
            if (game.screen.isPaused()) {
                game.screen.resume();
            } else {
                game.screen.pause();
            }
        };

        UP = register("up", new Keybind(Input.Keys.W, Keybind.Context.GAME, () -> {}));
        DOWN = register("down", new Keybind(Input.Keys.S, Keybind.Context.GAME, () -> {}));
        LEFT = register("left", new Keybind(Input.Keys.A, Keybind.Context.GAME, () -> {}));
        RIGHT = register("right", new Keybind(Input.Keys.D, Keybind.Context.GAME, () -> {}));
        ARROW_UP = register("arrow_up", new Keybind(Input.Keys.UP, Keybind.Context.GAME, () -> {}));
        ARROW_DOWN = register("arrow_down", new Keybind(Input.Keys.DOWN, Keybind.Context.GAME, () -> {}));
        ARROW_LEFT = register("arrow_left", new Keybind(Input.Keys.LEFT, Keybind.Context.GAME, () -> {}));
        ARROW_RIGHT = register("arrow_right", new Keybind(Input.Keys.RIGHT, Keybind.Context.GAME, () -> {}));
        if (Controllers.getCurrent() != null && Controllers.getCurrent().isConnected()) {
            GAMEPAD_UP = register("gamepad_up", new Keybind(Controllers.getCurrent().getMapping().buttonDpadUp, Keybind.Context.GAMEPAD, () -> {
            }));
            GAMEPAD_DOWN = register("gamepad_down", new Keybind(Controllers.getCurrent().getMapping().buttonDpadDown, Keybind.Context.GAMEPAD, () -> {
            }));
            GAMEPAD_LEFT = register("gamepad_left", new Keybind(Controllers.getCurrent().getMapping().buttonDpadLeft, Keybind.Context.GAMEPAD, () -> {
            }));
            GAMEPAD_RIGHT = register("gamepad_right", new Keybind(Controllers.getCurrent().getMapping().buttonDpadRight, Keybind.Context.GAMEPAD, () -> {
            }));
            ATTACK_1 = register("attack_1", new Keybind(Controllers.getCurrent().getMapping().buttonX, Keybind.Context.GAMEPAD, () -> {}));
            ATTACK_2 = register("attack_2", new Keybind(Controllers.getCurrent().getMapping().buttonA, Keybind.Context.GAMEPAD, () -> {}));
            ATTACK_3 = register("attack_3", new Keybind(Controllers.getCurrent().getMapping().buttonB, Keybind.Context.GAMEPAD, () -> {}));
            PAUSE = register("pause", new Keybind(Controllers.getCurrent().getMapping().buttonStart, Keybind.Context.GAMEPAD, pauseAction));
            DASH = register("dash", new Keybind(Controllers.getCurrent().getMapping().buttonR1, Keybind.Context.GAMEPAD, () -> {}));
        } else {
            GAMEPAD_UP = Keybind.unknown();
            GAMEPAD_DOWN = Keybind.unknown();
            GAMEPAD_LEFT = Keybind.unknown();
            GAMEPAD_RIGHT = Keybind.unknown();
            ATTACK_1 = Keybind.unknown();
            ATTACK_2 = Keybind.unknown();
            ATTACK_3 = Keybind.unknown();

            PAUSE = register("pause", new Keybind(Input.Keys.ESCAPE, Keybind.Context.GAME, pauseAction));
            DASH = register("dash", new Keybind(Input.Keys.SPACE, Keybind.Context.GAME, () -> {}));
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
}
