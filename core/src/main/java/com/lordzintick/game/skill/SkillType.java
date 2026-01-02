package com.lordzintick.game.skill;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lordzintick.audio.Sound;
import com.lordzintick.game.Rarity;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.util.Text;

import java.util.List;
import java.util.function.BiConsumer;

public class SkillType {
    public final String displayName;
    public final Rarity rarity;
    public final List<Text> description;
    public final TextureRegion icon;
    public final int manaCost;
    public final float cooldown;
    public final Sound castSound;
    public final BiConsumer<Player, Float> action;

    public SkillType(String displayName, Rarity rarity, List<Text> description, TextureRegion icon, int manaCost, float cooldown, Sound castSound, BiConsumer<Player, Float> action) {
        this.displayName = displayName;
        this.rarity = rarity;
        this.description = description;
        this.icon = icon;
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        this.castSound = castSound;
        this.action = action;
    }

    public Skill getInstance() {
        return new Skill(this);
    }
}
