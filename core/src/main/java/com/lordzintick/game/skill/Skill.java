package com.lordzintick.game.skill;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.lordzintick.MainGame;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.util.Text;
import com.lordzintick.util.UIUtil;

import java.util.ArrayList;

public class Skill {
    public final SkillType type;
    public int level = 1;
    private float remainingCooldown;

    Skill(SkillType type) {
        this.type = type;
    }

    public void displayTooltip(Batch batch, Player player, MainGame game, float x, float y) {
        String title = type.displayName;
        if (isOnCooldown()) {
            title = type.displayName + "(" + Math.floor(remainingCooldown * 10) / 10 + "s)";
        }

        ArrayList<Text> description = new ArrayList<>(type.description);

        float dmgMult = player.getSkillDamageMultiplier(type);
        float coolMult = player.getSkillCooldownMultiplier(type);

        if (dmgMult > 1) {
            description.add(new Text("+" + (dmgMult - 1) * 100 + "% damage"));
        }

        if (coolMult < 1) {
            description.add(new Text("+" + (1 - coolMult) * 100 + "% attack speed"));
        }

        UIUtil.displayTooltip(batch, game, x, y, UIUtil.getFontStringWidth(type.rarity.name(), game.font), type.rarity.color, title, type.rarity.name(), description);
    }

    public void cast(Player player) {
        if (player.mana >= type.manaCost && remainingCooldown <= 0) {
            remainingCooldown = type.cooldown * player.getSkillCooldownMultiplier(type);
            player.mana -= type.manaCost;
            if (!type.castSound.stream) {
                type.castSound.play();
            }
            type.action.accept(player, level);
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
