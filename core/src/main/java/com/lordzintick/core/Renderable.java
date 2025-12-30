package com.lordzintick.core;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * A small interface used to provide a simple standard for a render method
 */
public interface Renderable {
    /**
     * Renders the object onto the screen
     * @param batch The {@link Batch} to use for rendering
     * @param deltaTime The dt of the app, used in some cases for movement
     */
    void render(Batch batch, float deltaTime);
}
