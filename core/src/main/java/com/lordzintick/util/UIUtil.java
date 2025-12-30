package com.lordzintick.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.lordzintick.MainGame;

import java.util.UUID;

/**
 * A utility class containing various UI-related utility methods
 */
public final class UIUtil {
    /**
     * The magic constant for right-oriented horizontal text alignment
     */
    public static final int RIGHT = 0;
    /**
     * The magic constant for center-oriented horizontal text alignment
     */
    public static final int CENTER = 1;
    /**
     * The magic constant for left-oriented horizontal text alignment
     */
    public static final int LEFT = 2;

    /**
     * Renders a formatted {@link Text} object with all of its special preconfigured formatting included
     * @param game The {@link MainGame} to render the text to
     * @param batch The {@link Batch} to render the text with
     * @param text The formatted {@link Text} object to render with its formatting
     * @param x The X position to render the text at
     * @param y The Y position to render the text at
     * @param width The target width to render the text with
     * @param wrap Whether to wrap the text if it exceeds the target width or let it continue going
     */
    public static void renderText(MainGame game, Batch batch, Text text, float x, float y, int width, boolean wrap) {
        BitmapFont font = text.mega ? game.megaFont : game.font;
        font.setColor(text.color);
        if (text.glitchy) {
            font.draw(batch, generateRandomString(text.text.length()), x, y, width, text.align, wrap);
        } else {
            font.draw(batch, text.text, x, y, width, text.align, wrap);
        }
        font.setColor(Color.WHITE);
    }

    /**
     * Used in the "glitchy" mode text; Generates a random string of A-Z, 0-9 characters of the provided length
     * @param length The length of the random string to generate
     * @return A random string of characters of the provided length
     */
    public static String generateRandomString(int length) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid.substring(0, Math.min(length, uuid.length()));
    }
}
