package com.lordzintick.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.lordzintick.MainGame;
import com.lordzintick.audio.Sound;
import com.lordzintick.core.Logger;
import com.lordzintick.ui.widget.Widget;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract base class from which {@link com.lordzintick.game.screen.AbstractGameScreen} and {@link com.lordzintick.ui.screen.AbstractUIScreen} extend
 */
public abstract class BaseScreen {
    protected final Logger LOGGER = new Logger(this.getClass());
    public final MainGame game;
    public final ArrayList<Widget> widgets = new ArrayList<>();
    protected boolean paused = false;
    private int playingIndex = -1;

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
     * Also note that all of these MUST be music tracks ({@link Sound#stream} = {@code true}) in order to work correctly
     * @return A list of {@link Sound}s of which to pick a random one to play while this screen is open
     */
    public List<Sound> getBackgroundMusic() {
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

    public void startMusic() {
        if (getBackgroundMusic() == null) return;

        if (playingIndex == -1) playingIndex = game.random.nextInt(getBackgroundMusic().size());
        Sound backgroundMusic = getBackgroundMusic().get(playingIndex);

        if (backgroundMusic != null && backgroundMusic.stream) {
            backgroundMusic.play(music -> {
                playingIndex = -1;
                startMusic();
            });
        }
    }

    public void pauseMusic() {
        if (getBackgroundMusic() == null) return;
        Sound backgroundMusic = getBackgroundMusic().get(Math.max(playingIndex, 0));

        if (backgroundMusic != null && backgroundMusic.stream) {
            backgroundMusic.pause();
        }
    }

    public String getPlayingBackgroundMusic() {
        if (getBackgroundMusic() == null) return null;
        if (playingIndex == -1) return getBackgroundMusic().get(0).fileName;
        return getBackgroundMusic().get(playingIndex).fileName;
    }

    public void pause() {
        paused = true;
        pauseMusic();
    }

    public void resume() {
        paused = false;
        startMusic();
    }

    public boolean isPaused() {return paused;}

    public void update(float deltaTime) {}
    public void renderGame(float deltaTime) {}
    public abstract void renderUI(float deltaTime);
    public void dispose() {}
}
