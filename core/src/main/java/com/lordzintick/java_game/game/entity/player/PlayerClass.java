package com.lordzintick.java_game.game.entity.player;

import com.lordzintick.java_game.achievement.Achievement;
import com.lordzintick.pixel_krush.core.api.AbstractGame;
import com.lordzintick.pixel_krush.core.util.ListUtil;

import java.util.List;
import java.util.function.Consumer;

public enum PlayerClass {
    MAGE(
        ListUtil.listOf("fireball", // Commons
            "iceball", "acidball", "waterball", "lightningball", "flowerball", "petal",  // Uncommons
            "poison_nova", "lightning_bolt", "plasma_bolt", // Rares
            "wave", "petalstorm", "lightning_strike", // Epics
            "laser_beam"), // Legendaries
        ListUtil.listOf(
            "The classic mage",
            "Has a large assortment of magical abilities at their fingertips",
            "Due to extensive magical studying, gets +10% attack speed, +20 max mana, and +10% mana regeneration rate"
        ),
        "fireball", "play_game", player -> {
            player.maxMana.set(player.maxMana.get() + 20);
            player.setMultiplier("global_cooldown", 0.9f);
            player.setMultiplier("mana_regen", 1.1f);
        }
    ),
    WARRIOR(
        ListUtil.listOf("slash",  // Commons
            "axe_throw", "mega_slash",  // Uncommons
            "rage", // Rares
            "whirlwind"), // Legendaries
        ListUtil.listOf(
            "A powerful berserker with a formidable arsenal of weaponry",
            "Gets a +100% bonus to max health & +10 block power"
        ),
        "slash", "killer_3", player -> {
            player.maxHealth.set(player.maxHealth.get() * 2);
            player.setStat("block_power", 0.1f);
        }
    );
    public final List<String> skillpool;
    public final List<String> description;
    public final String startSkill;
    public final String unlockAchievement;
    public final Consumer<Player> statModifier;

    PlayerClass(List<String> skillpool, List<String> description, String startSkill, String unlockAchievement, Consumer<Player> statModifier) {
        this.skillpool = skillpool;
        this.description = description;
        this.startSkill = startSkill;
        this.unlockAchievement = unlockAchievement;
        this.statModifier = statModifier;
    }

    public boolean checkUnlocked(AbstractGame game) {
        Achievement achievement = game.queryRegistryOrThrow(game.getId("achievements")).getOrNull(game.getId(unlockAchievement));
        if (achievement == null) return false;

        return achievement.achieved;
    }
}
