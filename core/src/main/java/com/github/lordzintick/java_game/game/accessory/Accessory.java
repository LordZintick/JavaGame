package com.github.lordzintick.java_game.game.accessory;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.lordzintick.pixel_krush.core.api.AbstractGame;
import com.github.lordzintick.pixel_krush.core.util.Identifier;
import com.github.lordzintick.pixel_krush.core.util.Text;
import com.github.lordzintick.pixel_krush.core.util.UIUtil;
import com.github.lordzintick.pixel_krush.core.util.registry.ImmutableRegistry;
import com.github.lordzintick.java_game.game.Rarity;
import com.github.lordzintick.java_game.game.entity.player.Player;
import com.github.lordzintick.java_game.game.entity.player.StatModifier;
import com.github.lordzintick.java_game.game.skill.Skill;
import com.github.lordzintick.java_game.game.skill.SkillType;
import com.github.lordzintick.java_game.util.UIUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Accessory<T extends AccessoryType> {
    public static final Identifier ALL = Identifier.of("ALL", "ALL");

    public final T type;
    public final Rarity rarity;
    private final List<Identifier> targets;

    Accessory(T type, Rarity rarity, List<Identifier> targets) {
        this.type = type;
        this.rarity = rarity;
        this.targets = targets;
    }

    public void displayTooltip(Batch batch, AbstractGame game, float x, float y) {
        ArrayList<Text> description = new ArrayList<>(type.description);

        for (StatModifier<?> modifier : type.statModifiers.apply(rarity.ordinal() + 1)) {
            description.add(new Text(modifier.getDescription(targets)));
        }

        UIUtilities.displayTooltip(batch, game, x, y, UIUtil.getFontStringWidth(rarity.name(), game.getFont("normal")), rarity.color, new Text(type.displayName), rarity.name(), description);
    }

    public void modify(Player player) {
        for (StatModifier<?> modifier : type.statModifiers.apply(rarity.ordinal() + 1)) {
            modifier.modify(player, targets);
        }
    }

    public static List<Identifier> getRandomTargets(Player player, Random random) {
        ArrayList<Identifier> retTargets = new ArrayList<>();
        ImmutableRegistry<SkillType> skillTypes = player.screen.game.queryRegistryOrThrow(player.screen.game.getId("skill_types"));

        if (random.nextInt(4) == 0) {
            retTargets.add(ALL);
        } else {
            for (Skill<?> skill : player.equippedSkills.get()) {
                if (skill == null) continue;

                Identifier skillID = skillTypes.getIdOrThrow(skill.type);
                if (!retTargets.contains(skillID) && random.nextBoolean()) {
                    retTargets.add(skillID);
                }
            }

            if (retTargets.isEmpty()) {
                retTargets.add(ALL);
            }
        }

        return retTargets;
    }
}
