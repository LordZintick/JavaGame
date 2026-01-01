package com.lordzintick.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;

/**
 * A utility class describing a formatted string of text
 */
public class Text {
    public final String text;
    public Color color = Color.WHITE;
    public boolean glitchy = false;
    public int align = Align.left;
    public boolean mega = false;

    @Override
    public String toString() {
        return text;
    }

    /**
     * Constructs a new {@link Text} around the provided string
     * @param text The raw string to format
     */
    public Text(String text) {
        this.text = text;
    }

    /**
     * Changes the color of the text to the provided one
     * @param color The new color
     * @return The modified {@link Text}
     */
    public Text setColor(Color color) {
        this.color = color;
        return this;
    }

    /**
     * Changes the horizontal alignment of the text to the provided one
     * @param align The new alignment
     * @return The modified {@link Text}
     */
    public Text setAlign(int align) {
        this.align = align;
        return this;
    }

    /**
     * Sets the text to be "glitchy," making it display random characters instead of the provided string
     * @return The modified {@link Text}
     */
    public Text glitchy() {
        this.glitchy = true;
        return this;
    }

    /**
     * Sets the text to be non-glitchy, returning it to its normal state
     * @return The modified {@link Text}
     */
    public Text notGlitchy() {
        this.glitchy = false;
        return this;
    }

    /**
     * Makes the text use the MEGA font instead of the normal one
     * @return The modified {@link Text}
     */
    public Text mega() {
        this.mega = true;
        return this;
    }

    /**
     * Makes the text use the normal font instead of the MEGA one
     * @return The modified {@link Text}
     */
    public Text notMega() {
        this.mega = false;
        return this;
    }
}
