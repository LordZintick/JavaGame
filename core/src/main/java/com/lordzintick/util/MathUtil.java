package com.lordzintick.util;

import com.badlogic.gdx.Gdx;
import com.lordzintick.game.Rarity;
import com.lordzintick.game.skill.SkillType;

/**
 * A utility class with various math-related utility methods
 */
public final class MathUtil {
    /**
     * Checks whether a certain point, provided in screen coordinates, lies within a certain area, provided in game coordinates
     * @param px The point x, in screen coordinates
     * @param py The point y, in screen coordinates
     * @param ax The area x, in game coordinates
     * @param ay The area y, in game coordinates
     * @param aw The area width, in game coordinates
     * @param ah The area height, in game coordinates
     * @return Whether the provided point lies inside the provided area or not
     */
    public static boolean isPointInArea(int px, int py, float ax, float ay, int aw, int ah) {
        return (px >= ax && px <= ax + aw && py <= -ay + Gdx.graphics.getHeight() && py >= -ay - ah + Gdx.graphics.getHeight());
    }

    public static int calculateWeight(Rarity rarity) {
        return (int) Math.pow(((Rarity.values().length - rarity.ordinal() + 1) + 1), 2);
    }
}
