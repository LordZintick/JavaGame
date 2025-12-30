package com.lordzintick.control;

import com.lordzintick.game.screen.AbstractGameScreen;
import com.lordzintick.ui.screen.AbstractUIScreen;
import com.lordzintick.util.BaseScreen;

/**
 * An input class defining a key and an action to run when that key is pressed
 */
public class Keybind {
    public final int defaultKey;
    public final Context context;
    public final Runnable action;
    public boolean isPressed = false;

    /**
     * Construct a new Keybind with the provided key and action
     * @param defaultKey The default key to bind the provided action to
     * @param context The {@link Context} for the keybind to take effect in
     * @param action The action to run when the aforementioned key is pressed
     */
    public Keybind(int defaultKey, Context context, Runnable action) {
        this.defaultKey = defaultKey;
        this.context = context;
        this.action = action;
    }

    public boolean checkContext(BaseScreen screen) {
        return (screen instanceof AbstractUIScreen && context == Context.UI) || (screen instanceof AbstractGameScreen && context == Context.GAME);
    }

    public enum Context {
        UI,
        GAME
    }
}
