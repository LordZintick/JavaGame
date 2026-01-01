package com.lordzintick.game.accessory;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.lordzintick.MainGame;
import com.lordzintick.game.Rarity;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.game.entity.player.StatModifier;
import com.lordzintick.game.skill.Skill;
import com.lordzintick.game.skill.SkillType;
import com.lordzintick.util.ListUtil;
import com.lordzintick.util.Text;
import com.lordzintick.util.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Accessory {
    public final AccessoryType type;
    public final Rarity rarity;
    private final List<String> targets;

    Accessory(AccessoryType type, Rarity rarity, List<String> targets) {
        this.type = type;
        this.rarity = rarity;
        this.targets = targets;
    }

    public void displayTooltip(Batch batch, MainGame game, float x, float y) {
        ArrayList<Text> description = new ArrayList<>(type.description);

        for (StatModifier modifier : type.statModifiers.apply(rarity.ordinal() + 1)) {
            description.add(new Text(modifier.getDescription(targets)));
        }

        UIUtil.displayTooltip(batch, game, x, y, UIUtil.getFontStringWidth(rarity.name(), game.font), rarity.color, type.displayName, rarity.name(), description);
    }

    public void modify(Player player) {
        for (StatModifier modifier : type.statModifiers.apply(rarity.ordinal() + 1)) {
            modifier.modify(player, targets);
        }
    }

    public static List<String> getRandomTargets(Player player, Random random) {
        ArrayList<String> retTargets = new ArrayList<>();

        if (random.nextInt(4) == 0) {
            retTargets.add("ALL");
        } else {
            for (Skill skill : player.equippedSkills) {
                if (skill == null) continue;

                String skillID = ListUtil.getKey(player.screen.game.skillTypes.SKILL_TYPES, skill.type);
                if (!retTargets.contains(skillID) && random.nextBoolean()) {
                    retTargets.add(skillID);
                }
            }

            if (retTargets.isEmpty()) {
                retTargets.add("ALL");
            }
        }

        return retTargets;
    }
}
