package com.github.lordzintick.java_game.game.entity.player;

import com.github.lordzintick.pixel_krush.core.util.Identifier;
import com.github.lordzintick.pixel_krush.core.util.TriConsumer;

import java.util.List;

public class StatModifierType {
    final TriConsumer<Player, Float, List<Identifier>> modificator;
    final String description;

    public StatModifierType(TriConsumer<Player, Float, List<Identifier>> modificator, String description) {
        this.modificator = modificator;
        this.description = description;
    }

    public StatModifier<StatModifierType> getInstance(float amount) {
        return new StatModifier<>(this, amount);
    }
}
