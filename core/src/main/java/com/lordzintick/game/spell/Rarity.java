package com.lordzintick.game.spell;

import com.badlogic.gdx.graphics.Color;

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
}
