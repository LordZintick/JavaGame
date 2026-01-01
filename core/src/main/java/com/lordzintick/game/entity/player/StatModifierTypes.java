package com.lordzintick.game.entity.player;

import com.lordzintick.game.accessory.AccessoryType;

import java.util.HashMap;

public final class StatModifierTypes {
    private static final HashMap<String, StatModifierType> STAT_MODIFIER_TYPES = new HashMap<>();

    public static final StatModifierType INCREASE_DAMAGE = register("increase_damage", new StatModifierType(
        (player, amount, targets) -> {
            if (targets.contains("ALL")) {
                player.globalDamageMultiplier += amount;
            } else {
                for (String target : targets) {
                    player.addSkillDamageMultiplier(target, amount);
                }
            }
        },
        "+%ah% damage%t"
    ));

    public static final StatModifierType INCREASE_ATTACK_SPEED = register("increase_atk_speed", new StatModifierType(
        (player, amount, targets) -> {
            if (targets.contains("ALL")) {
                player.globalCooldownMultiplier -= amount;
            } else {
                for (String target : targets) {
                    player.addSkillCooldownMultiplier(target, amount);
                }
            }
        },
        "+%ah% attack speed%t"
    ));

    public static final StatModifierType INCREASE_MAX_MANA = register("increase_max_mana", new StatModifierType(
        (player, amount, targets) -> player.maxMana += amount,
        "+%a max mana"
    ));

    public static final StatModifierType INCREASE_MAX_HEALTH = register("increase_max_health", new StatModifierType(
        (player, amount, targets) -> player.maxHealth += amount,
        "+%a max health"
    ));

    public static final StatModifierType INCREASE_BLOCK_POWER = register("increase_block_power", new StatModifierType(
        (player, amount, targets) -> player.blockPower += amount,
        "+%ah block power"
    ));

    public static final StatModifierType INCREASE_MANA_REGEN = register("increase_mana_regen", new StatModifierType(
        (player, amount, targets) -> player.manaRegenMultiplier -= amount,
        "+%ah% mana regeneration rate"
    ));

    public static final StatModifierType INCREASE_XP_GAIN = register("increase_xp_gain", new StatModifierType(
        (player, amount, targets) -> player.xpMultiplier += amount,
        "+%ah% xp gain"
    ));

    private static <T extends StatModifierType> T register(String id, T t) {
        if (STAT_MODIFIER_TYPES.containsKey(id))
            throw new IllegalArgumentException("ID already registered!");

        STAT_MODIFIER_TYPES.put(id, t);
        return t;
    }
}
