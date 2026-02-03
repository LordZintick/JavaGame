package com.github.lordzintick.java_game.game;

import com.badlogic.gdx.graphics.Color;

import java.util.Random;

public enum Rarity {
    COMMON(Color.GRAY),
    UNCOMMON(Color.LIME),
    RARE(Color.BLUE),
    EPIC(Color.PURPLE),
    LEGENDARY(Color.GOLD);
    public final Color color;

    Rarity(Color color) {
        this.color = color;
    }

    public static Rarity getWeightedRandomRarity(Random random) {
        int totalWeight = 0;

        for (Rarity rarity : values()) {
            totalWeight += calculateWeight(rarity);
        }

        int value = random.nextInt(totalWeight);
        Rarity selectedRarity = null;

        for (Rarity rarity : values()) {
            value -= calculateWeight(rarity);
            if (value <= 0) {
                selectedRarity = rarity;
                break;
            }
        }

        return selectedRarity;
    }

    public static int calculateWeight(Rarity rarity) {
        return (int) Math.pow(((Rarity.values().length - rarity.ordinal() + 1) + 1), 2);
    }
}
