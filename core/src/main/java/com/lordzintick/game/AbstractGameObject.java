package com.lordzintick.game;

import com.badlogic.gdx.math.Rectangle;
import com.lordzintick.core.PositionedRenderable;
import com.lordzintick.core.Updateable;
import com.lordzintick.game.entity.Entity;
import com.lordzintick.game.screen.AbstractGameScreen;
import com.lordzintick.util.BaseScreen;

/**
 * An abstract class representing the base for all physical game objects
 */
public abstract class AbstractGameObject extends PositionedRenderable implements Updateable {
    public int width, height;
    public boolean shouldRemove = false;
    public final AbstractGameScreen screen;
    public Rectangle collisionRect;

    /**
     * Constructs a new game object in the provided screen
     * @param screen The {@link AbstractGameScreen} that is the parent/holder of this game object
     */
    public AbstractGameObject(AbstractGameScreen screen) {
        this.screen = screen;
        this.collisionRect = new Rectangle(x, y, width, height);
    }

    public void update(float deltaTime) {
        this.collisionRect.set(x, y, width, height);
    }
    public void dispose() {}
    public void collide(AbstractGameObject other) {}
}
