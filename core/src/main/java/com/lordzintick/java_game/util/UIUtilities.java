package com.lordzintick.java_game.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.lordzintick.pixel_krush.core.api.AbstractGame;
import com.lordzintick.pixel_krush.core.util.Text;
import com.lordzintick.pixel_krush.core.util.UIUtil;

import java.util.List;

/**
 * A utility class containing various UI-related utility methods
 */
public final class UIUtilities {
    public static void displayTooltip(Batch batch, AbstractGame game, float x, float y, float minWidth, Color color, Text title, String subtitle, List<Text> description) {
        BitmapFont font = game.getFont(title.font);
        float height = (description.size() + 4) * font.getLineHeight();
        float width = Math.max(UIUtil.getFontStringWidth(title + "(0.0s)", font), minWidth);

        for (Text line : description) {
            float lineWidth = UIUtil.getFontStringWidth(line.text, font);
            if (lineWidth > width) {
                width = lineWidth;
            }
        }

        width += width / 8;

        game.getCachedNinePatch("tooltip").draw(batch, x, y, width, height);
        batch.setColor(color);
        font.setColor(color);
        font.draw(batch, title.text, x + width / 16, y + height - height / 8);
        game.getCachedNinePatch("tooltip_overlay").draw(batch, x, y, width, height);
        font.draw(batch, subtitle, x + width / 16, y + height - height / 3f);

        for (int i = 0; i < description.size(); i++) {
            Text line = description.get(i);
            font.setColor(line.color);
            font.draw(batch, line.text, x + width / 16, y + height - (font.getLineHeight() * (4 + i)), width, Align.left, true);
        }
        batch.setColor(Color.WHITE);
        font.setColor(Color.WHITE);
    }
}
