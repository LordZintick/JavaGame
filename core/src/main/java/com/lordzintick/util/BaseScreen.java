package com.lordzintick.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.lordzintick.MainGame;
import com.lordzintick.audio.Sound;
import com.lordzintick.core.Logger;
import com.lordzintick.ui.widget.Widget;

import java.util.ArrayList;

/**
 * An abstract base class from which {@link com.lordzintick.game.screen.AbstractGameScreen} and {@link com.lordzintick.ui.screen.AbstractUIScreen} extend
 */
public abstract class BaseScreen {
    protected final Logger LOGGER = new Logger(this.getClass());
    public final MainGame game;
    public final ArrayList<Widget> widgets = new ArrayList<>();

    /**
     * Constructs a new {@link BaseScreen} with the provided {@link MainGame} and adds the initial widgets to it
     * @param game The {@link MainGame} this screen is for
     */
    protected BaseScreen(MainGame game) {
        this.game = game;
        addWidgets();
    }

    /**
     * Provides the background music to play when this screen is open.<br>
     * Return {@code null} to disable.<br>
     * Also note that this MUST be a music track ({@link Sound#stream} = {@code true}) in order to work correctly
     * @return The {@link Sound} of the background music to play when this screen is open
     */
    public Sound getBackgroundMusic() {
        return null;
    }

    /**
     * Provides the background color of this screen
     * @return The background color of this screen
     */
    public Color getBackgroundColor() {return Color.BLACK;}

    /**
     * A simple utility to get the midpoint X coordinate of the screen
     * @return The midpoint X of the screen
     */
    protected int getMidX() {return Gdx.graphics.getWidth() / 2;}
    /**
     * A simple utility to get the midpoint Y coordinate of the screen
     * @return The midpoint Y of the screen
     */
    protected int getMidY() {return Gdx.graphics.getHeight() / 2;}

    /**
     * Called to add the UI widgets to this screen
     */
    protected abstract void addWidgets();

    /**
     * Controls whether this screen should render the current tilemap.<br>
     * Returns {@code false} by default.
     * @return Whether this screen should enable the {@link com.lordzintick.game.tile.TilemapHandler} and render the current tilemap
     */
    public boolean shouldRenderTilemap() {
        return false;
    }

    public void startMusic() {
        if (getBackgroundMusic() != null && getBackgroundMusic().stream) {
            getBackgroundMusic().play();
        }
    }

    public void pauseMusic() {
        if (getBackgroundMusic() != null && getBackgroundMusic().stream) {
            getBackgroundMusic().pause();
        }
    }

    public void update(float deltaTime) {}
    public void renderGame(float deltaTime) {}
    public abstract void renderUI(float deltaTime);
    public void dispose() {}
}
