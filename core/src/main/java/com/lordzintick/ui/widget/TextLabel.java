package com.lordzintick.ui.widget;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lordzintick.ui.screen.AbstractUIScreen;
import com.lordzintick.util.BaseScreen;
import com.lordzintick.util.Text;
import com.lordzintick.util.UIUtil;

/**
 * An extension of {@link Widget} representing a simple string of text to be displayed on the screen.<br>
 * Also see {@link TextButton}
 */
public class TextLabel extends Widget {
    public Text text;

    /**
     * Constructs a new {@link TextLabel} displaying the provided {@link Text} at the provided coordinates
     * @param screen The {@link BaseScreen} this {@link TextLabel} is for
     * @param text The {@link Text} to display in the label
     * @param x The initial X position of the label
     * @param y The initial Y position of the label
     */
    public TextLabel(BaseScreen screen, Text text, int x, int y) {
        super(screen);
        this.x = x;
        this.y = y;
        this.text = text;
        this.height = (int) screen.game.font.getLineHeight();
    }

    @Override
    public void render(Batch batch, float deltaTime) {
        // Use UIUtil to render the Text with all of its configured formatting
        UIUtil.renderText(screen.game, batch, text, x, y, width, false);
    }
}
