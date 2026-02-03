package com.lordzintick.java_game.game.skill;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lordzintick.pixel_krush.core.util.audio.Sound;
import com.lordzintick.pixel_krush.core.util.Text;
import com.lordzintick.java_game.game.Rarity;
import com.lordzintick.java_game.game.entity.player.Player;

import java.util.List;
import java.util.function.BiConsumer;

public class SkillType {
    public final Text displayName;
    public final Rarity rarity;
    public final List<Text> description;
    public final TextureRegion icon;
    public final int manaCost;
    public final float cooldown;
    public final Sound castSound;
    public final BiConsumer<Player, SkillMultiplierSet> action;

    public SkillType(Text displayName, Rarity rarity, List<Text> description, TextureRegion icon, int manaCost, float cooldown, Sound castSound, BiConsumer<Player, SkillMultiplierSet> action) {
        this.displayName = displayName;
        this.rarity = rarity;
        this.description = description;
        this.icon = icon;
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        this.castSound = castSound;
        this.action = action;
    }

    public Skill<SkillType> getInstance() {
        return new Skill<>(this);
    }
}
