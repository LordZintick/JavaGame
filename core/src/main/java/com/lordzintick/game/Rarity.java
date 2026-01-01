package com.lordzintick.game;

import com.badlogic.gdx.graphics.Color;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.game.skill.SkillType;
import com.lordzintick.ui.widget.SkillSlot;
import com.lordzintick.util.MathUtil;

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
            totalWeight += MathUtil.calculateWeight(rarity);
        }

        int value = random.nextInt(totalWeight);
        Rarity selectedRarity = null;

        for (Rarity rarity : values()) {
            value -= MathUtil.calculateWeight(rarity);
            if (value <= 0) {
                selectedRarity = rarity;
                break;
            }
        }

        return selectedRarity;
    }
}
