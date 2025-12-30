package com.lordzintick.ui.widget;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.lordzintick.core.PositionedRenderable;
import com.lordzintick.ui.screen.AbstractUIScreen;
import com.lordzintick.util.BaseScreen;

/**
 * An abstract base class from which all UI elements extend
 */
public abstract class Widget extends PositionedRenderable {
    protected final BaseScreen screen;
    public int width, height = 0;
    protected boolean hovering = false;
    protected double ticks = 0;
    public boolean visible = true;

    /**
     * Constructs a simple {@link Widget} with no defined width or height
     * @param screen The {@link BaseScreen} this {@link Widget} is for
     */
    protected Widget(BaseScreen screen) {
        this.screen = screen;
    }

    /**
     * Constructs a {@link Widget} and defines its initial {@code width} and {@code height}
     * @param screen The {@link BaseScreen} this {@link Widget} is for
     * @param width The initial width of the {@link Widget}
     * @param height The initial height of the {@link Widget}
     */
    protected Widget(BaseScreen screen, int width, int height) {
        this.screen = screen;
        this.width = width;
        this.height = height;
    }

    /**
     * Triggers when the user clicks this {@link Widget}
     * @param button The mouse button used to click the {@link Widget}
     */
    public void click(int button) {}

    /**
     * Triggers when the user releases their mouse over this {@link Widget}
     * @param button The mouse button released over this {@link Widget}
     */
    public void release(int button) {}

    /**
     * Triggers when the user hovers their mouse over this {@link Widget}
     */
    public void hover() {
        hovering = true;
    }

    /**
     * Triggers when the user moves their mouse away from over this {@link Widget}
     */
    public void unHover() {
        hovering = false;
    }

    @Override
    public void render(Batch batch, float deltaTime) {
        ticks += deltaTime;
    }
}
