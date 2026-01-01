package com.lordzintick.game.entity.player;

import com.lordzintick.util.ListUtil;
import com.lordzintick.util.Text;

import java.util.List;
import java.util.function.Consumer;

public enum PlayerClass {
    MAGE(
        ListUtil.listOf("fireball", "iceball", "poison_nova", "lightning_bolt", "plasma_bolt"),
        ListUtil.listOf(
            "The classic mage",
            "Has an assortment of powerful magical abilities and +20 max mana",
            "Also gets +10% attack speed"
        ),
        "fireball", player -> {
            player.maxMana += 20;
            player.globalCooldownMultiplier = 0.9f;
        }
    ),
    WARRIOR(
        ListUtil.listOf("slash", "axe_throw", "mega_slash", "whirlwind"),
        ListUtil.listOf(
            "A powerful berserker with a formidable arsenal of weaponry",
            "Gets a +100% bonus to max health & +10 block power"
        ),
        "slash", player -> {
            player.maxHealth *= 2;
            player.blockPower += 0.1f;
        }
    );
    public final List<String> skillpool;
    public final List<String> description;
    public final String startSkill;
    public final Consumer<Player> statModifier;

    PlayerClass(List<String> skillpool, List<String> description, String startSkill, Consumer<Player> statModifier) {
        this.skillpool = skillpool;
        this.description = description;
        this.startSkill = startSkill;
        this.statModifier = statModifier;
    }
}
