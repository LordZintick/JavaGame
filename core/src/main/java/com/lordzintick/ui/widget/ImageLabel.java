package com.lordzintick.ui.widget;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lordzintick.ui.screen.AbstractUIScreen;
import com.lordzintick.util.BaseScreen;

import java.awt.geom.AffineTransform;

/**
 * An extension of {@link Widget} describing a simple image displayer
 */
public class ImageLabel extends Widget {
    public Texture img;

    /**
     * Constructs a new {@link ImageLabel} displaying the provided image at the provided coordinates with the provided {@code width} and {@code height}
     * @param screen The {@link BaseScreen} this {@link ImageLabel} is for
     * @param img The image for this {@link ImageLabel} to display
     * @param x The initial X position of the image label
     * @param y The initial Y position of the image label
     * @param width The width to display the image at
     * @param height The height to display the image at
     */
    public ImageLabel(BaseScreen screen, Texture img, int x, int y, int width, int height) {
        super(screen);
        this.img = img;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(Batch batch, float deltaTime) {
        super.render(batch,deltaTime);
        // Simply draw the image (no rotation support yet)
        batch.draw(img, x, y, width, height);
    }
}
