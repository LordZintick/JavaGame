package com.lordzintick.game.entity.player;

import com.lordzintick.game.skill.SkillType;
import com.lordzintick.game.skill.SkillTypes;
import com.lordzintick.util.TriConsumer;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class StatModifierType {
    final TriConsumer<Player, Float, List<String>> modificator;
    final String description;

    public StatModifierType(TriConsumer<Player, Float, List<String>> modificator, String description) {
        this.modificator = modificator;
        this.description = description;
    }

    public StatModifier getInstance(float amount) {
        return new StatModifier(this, amount);
    }
}
