package com.lordzintick.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lordzintick.core.Logger;
import com.lordzintick.game.AbstractGameObject;
import com.lordzintick.game.screen.AbstractGameScreen;
import com.lordzintick.util.Direction;

/**
 * A base abstract class from which all moving and living entities should extend from
 */
public abstract class Entity extends AbstractGameObject {
    protected final Logger LOGGER = new Logger(this.getClass());

    protected final TextureRegion[][] textures;
    public Direction direction = Direction.DOWN;
    public boolean moving = false;
    protected double animTicks = 0;
    protected double ticks = 0;
    private int frame = 0;
    protected float scale = 1;

    /**
     * Constructs a new {@link Entity} with the provided spritesheet, which will be split into regions of the provided size
     * @param screen The {@link AbstractGameScreen} containing this entity
     * @param texture The spritesheet for this entity to use
     * @param width The width of the entity's image
     * @param height The height of the entity's image
     */
    public Entity(AbstractGameScreen screen, Texture texture, int width, int height) {
        super(screen);
        this.width = width;
        this.height = height;
        this.textures = TextureRegion.split(texture, width, height);
    }

    /**
     * Gets the amount of frames in the walking animation for this entity
     * @return The amount of frames in the walking animation of this entity
     */
    protected abstract int getFrameCount();

    /**
     * Defines the time in seconds that it takes for the walking animation frame to advance by 1
     * @return The time in seconds that it takes for the frame to advance
     */
    protected abstract float getFrameTime();

    @Override
    public void update(float deltaTime) {
        ticks += deltaTime;
    }

    @Override
    public void render(Batch batch, float deltaTime) {
        animTicks += deltaTime;
        if (!moving) {
            switch (direction) {
                case DOWN: batch.draw(textures[0][0], x, y, width * scale, height * scale); break;
                case LEFT: batch.draw(textures[1][0], x, y, width * scale, height * scale); break;
                case RIGHT: batch.draw(textures[2][0], x, y, width * scale, height * scale); break;
                case UP: batch.draw(textures[3][0], x, y, width * scale, height * scale); break;
            }
        } else {
            if (animTicks >= getFrameTime()) {
                animTicks = 0;
                if (frame == getFrameCount() - 1)
                    frame = 0;
                else
                    frame++;
            }

            switch (direction) {
                case DOWN: batch.draw(textures[4 + frame][0], x, y, width * scale, height * scale); break;
                case LEFT: batch.draw(textures[4 + getFrameCount() + frame][0], x, y, width * scale, height * scale); break;
                case RIGHT: batch.draw(textures[4 + 2 * getFrameCount() + frame][0], x, y, width * scale, height * scale); break;
                case UP: batch.draw(textures[4 + 3 * getFrameCount() + frame][0], x, y, width * scale, height * scale); break;
            }
        }
    }
}
