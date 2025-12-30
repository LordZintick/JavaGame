package com.lordzintick.game;

import com.lordzintick.core.PositionedRenderable;
import com.lordzintick.game.screen.AbstractGameScreen;
import com.lordzintick.util.BaseScreen;

/**
 * An abstract class representing the base for all physical game objects
 */
public abstract class AbstractGameObject extends PositionedRenderable {
    public int width, height;
    public boolean shouldRemove = false;
    protected final AbstractGameScreen screen;

    /**
     * Constructs a new game object in the provided screen
     * @param screen The {@link AbstractGameScreen} that is the parent/holder of this game object
     */
    public AbstractGameObject(AbstractGameScreen screen) {
        this.screen = screen;
    }

    public void update(float deltaTime) {}
    public void dispose() {}
}
