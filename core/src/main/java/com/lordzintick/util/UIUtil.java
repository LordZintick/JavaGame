package com.lordzintick.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.utils.UIUtils;
import com.badlogic.gdx.utils.Align;
import com.lordzintick.MainGame;

import java.util.List;
import java.util.UUID;

/**
 * A utility class containing various UI-related utility methods
 */
public final class UIUtil {

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

    public static void renderOutlinedText(MainGame game, Batch batch, Text text, float x, float y, int width, boolean wrap) {
        BitmapFont font = text.mega ? game.megaFont : game.outlinedFont;
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

    public static float getFontStringWidth(String string, BitmapFont font) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, string);
        return layout.width;
    }

    public static void displayTooltip(Batch batch, MainGame game, float x, float y, float minWidth, Color color, String title, String subtitle, List<Text> description) {
        float height = (description.size() + 4) * game.font.getLineHeight();
        float width = Math.max(UIUtil.getFontStringWidth(title + "(0.0s)", game.font), minWidth);

        for (Text line : description) {
            float lineWidth = UIUtil.getFontStringWidth(line.text, game.font);
            if (lineWidth > width) {
                width = lineWidth;
            }
        }

        width += width / 8;

        game.skillTypes.tooltipTexture.draw(batch, x, y, width, height);
        batch.setColor(color);
        game.font.setColor(color);
        game.font.draw(batch, title, x + width / 16, y + height - height / 8);
        game.skillTypes.tooltipOverlay.draw(batch, x, y, width, height);
        game.font.draw(batch, subtitle, x + width / 16, y + height - height / 3f);

        for (int i = 0; i < description.size(); i++) {
            Text line = description.get(i);
            game.font.setColor(line.color);
            game.font.draw(batch, line.text, x + width / 16, y + height - (game.font.getLineHeight() * (4 + i)), width, Align.left, true);
        }
        batch.setColor(Color.WHITE);
        game.font.setColor(Color.WHITE);
    }
}
