package com.github.lordzintick.java_game.game.skill;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.lordzintick.pixel_krush.core.api.AbstractGame;
import com.github.lordzintick.pixel_krush.core.util.Text;
import com.github.lordzintick.pixel_krush.core.util.UIUtil;
import com.github.lordzintick.java_game.game.entity.player.Player;
import com.github.lordzintick.java_game.util.UIUtilities;

import java.util.ArrayList;

public class Skill<T extends SkillType> {
    public final T type;
    private float remainingCooldown;

    Skill(T type) {
        this.type = type;
    }

    public void displayTooltip(Batch batch, Player player, AbstractGame game, float x, float y) {
        Text title = type.displayName;
        if (type.manaCost > 0) {
            title.concat(" (" + type.manaCost + " mana)");
        }
        if (isOnCooldown()) {
            title.concat(" (" + Math.floor(remainingCooldown * 10) / 10 + "s)");
        }

        ArrayList<Text> description = new ArrayList<>(type.description);

        float dmgMult = player.getSkillDamageMultiplier(type);
        float coolMult = player.getSkillCooldownMultiplier(type);

        if (dmgMult > 1) {
            description.add(new Text("+" + (int) ((dmgMult - 1) * 100) + "% damage"));
        }

        if (coolMult < 1) {
            description.add(new Text("+" + (int) ((1 - coolMult) * 100) + "% attack speed"));
        }

        UIUtilities.displayTooltip(batch, game, x, y, UIUtil.getFontStringWidth(type.rarity.name(), game.getFont(title.font)), type.rarity.color, title, type.rarity.name(), description);
    }

    public void cast(Player player) {
        if (player.mana.get() >= type.manaCost && remainingCooldown <= 0) {
            remainingCooldown = type.cooldown * player.getSkillCooldownMultiplier(type);
            player.mana.set(player.mana.get() - type.manaCost);
            if (!type.castSound.stream) {
                type.castSound.play();
            }
            type.action.accept(player, player.getSkillMultiplierSet(type));
        }
    }

    public void tick(float deltaTime) {
        if (remainingCooldown > 0) {
            remainingCooldown -= deltaTime;
        }
    }

    public boolean isOnCooldown() {
        return remainingCooldown > 0;
    }

    public float getRemainingCooldown() {
        return remainingCooldown;
    }
}
