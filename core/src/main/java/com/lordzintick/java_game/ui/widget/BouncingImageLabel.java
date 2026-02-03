package com.lordzintick.java_game.ui.widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.lordzintick.pixel_krush.core.api.BaseScreen;
import com.lordzintick.pixel_krush.core.util.Direction;
import com.lordzintick.pixel_krush.core.ui.impl.ImageLabel;

/**
 * An extension of the {@link ImageLabel} used solely for the bouncing java logos on the title screen
 */
public class BouncingImageLabel extends ImageLabel {
    /**
     * The speed at which all {@link BouncingImageLabel}s will move, as a multiplier
     */
    private static final float SPEED = 1;
    /**
     * The current moving direction of the {@link BouncingImageLabel}.
     * Should only be diagonal.
     */
    private Direction direction;

    /**
     * Constructs a new {@link BouncingImageLabel} with the provided initial parameters
     * @param screen The {@link BaseScreen} this {@link BouncingImageLabel} is for
     * @param img The image to display on this {@link BouncingImageLabel}
     * @param x The initial X position of this {@link BouncingImageLabel}
     * @param y The initial Y position of this {@link BouncingImageLabel}
     * @param width The width to display the image at (also used for collisions)
     * @param height The height to display the image at (also used for collisions)
     * @param startDir The initial movement direction this {@link BouncingImageLabel} will be moving in
     */
    public BouncingImageLabel(BaseScreen screen, Texture img, int x, int y, int width, int height, Direction startDir) {
        super(screen, img, x, y, width, height);
        this.direction = startDir;
    }

    @Override
    public void render(Batch batch, float deltaTime) {
        // Handle horizontal collisions
        if (this.x <= 0 || this.x + this.width >= Gdx.graphics.getWidth()) {
            direction = direction.flip(false);
        }

        // Handle vertical collisions
        if (this.y <= 0 || this.y + this.height >= Gdx.graphics.getHeight()) {
            direction = direction.flip(true);
        }

        // Move the object and render the image
        this.x += direction.vector.x * SPEED;
        this.y += direction.vector.y * SPEED;
        super.render(batch, deltaTime);
    }
}
