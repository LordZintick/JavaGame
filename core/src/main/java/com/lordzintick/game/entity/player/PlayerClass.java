package com.lordzintick.game.entity.player;

import com.lordzintick.util.ListUtil;
import com.lordzintick.util.Text;

import java.util.List;
import java.util.function.Consumer;

public enum PlayerClass {
    MAGE(
        ListUtil.listOf("fireball", // Commons
            "iceball", "acidball", "waterball", "lightningball", "flowerball", "petal",  // Uncommons
            "poison_nova", "lightning_bolt", "plasma_bolt", // Rares
            "wave", "petalstorm", // Epics
            "laser_beam"), // Legendaries
        ListUtil.listOf(
            "The classic mage",
            "Has a large assortment of magical abilities at their fingertips",
            "Due to extensive magical studying, gets +10% attack speed, +20 max mana, and +10% mana regeneration rate"
        ),
        "fireball", "", player -> {
            player.maxMana += 20;
            player.globalCooldownMultiplier = 0.9f;
            player.manaRegenMultiplier = 1.1f;
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
        "slash", "Kill a bunch of enemies to unlock", player -> {
            player.maxHealth *= 2;
            player.blockPower += 0.1f;
        }
    );
    public final List<String> skillpool;
    public final List<String> description;
    public final String startSkill;
    public final String hint;
    public final Consumer<Player> statModifier;

    PlayerClass(List<String> skillpool, List<String> description, String startSkill, String hint, Consumer<Player> statModifier) {
        this.skillpool = skillpool;
        this.description = description;
        this.startSkill = startSkill;
        this.hint = hint;
        this.statModifier = statModifier;
    }
}
