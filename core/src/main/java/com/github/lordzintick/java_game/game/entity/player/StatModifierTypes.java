package com.github.lordzintick.java_game.game.entity.player;

import com.github.lordzintick.pixel_krush.core.api.AbstractGame;
import com.github.lordzintick.pixel_krush.core.util.Identifier;
import com.github.lordzintick.pixel_krush.core.util.registry.DeferredRegister;
import com.github.lordzintick.java_game.game.accessory.Accessory;

public final class StatModifierTypes {
    public static DeferredRegister<StatModifierType> registrar(AbstractGame game) {
        DeferredRegister<StatModifierType> register = DeferredRegister.create(game, game.getId("stat_modifier_types"));

        register.register(game.getId("increase_damage"), new StatModifierType(
            (player, amount, targets) -> {
                if (targets.contains(Accessory.ALL)) {
                    player.setMultiplier("global_damage", player.getMultiplier("global_damage").get() + amount);
                } else {
                    for (Identifier target : targets) {
                        player.addSkillDamageMultiplier(target.toString(), amount);
                    }
                }
            },
            "+%ah% damage%t"
        ));

        register.register(game.getId("increase_attack_speed"), new StatModifierType(
            (player, amount, targets) -> {
                if (targets.contains(Accessory.ALL)) {
                    player.setMultiplier("global_cooldown", player.getMultiplier("global_cooldown").get() - amount);
                } else {
                    for (Identifier target : targets) {
                        player.addSkillCooldownMultiplier(target.toString(), amount);
                    }
                }
            },
            "+%ah% attack speed%t"
        ));

        register.register(game.getId("increase_max_mana"), new StatModifierType(
            (player, amount, targets) -> player.maxMana.set(player.maxMana.get() + amount),
            "+%a max mana"
        ));

        register.register(game.getId("increase_max_health"), new StatModifierType(
            (player, amount, targets) -> player.maxHealth.set(player.maxHealth.get() + amount),
            "+%a max health"
        ));

        register.register(game.getId("increase_block_power"), new StatModifierType(
            (player, amount, targets) -> player.setStat("block_power", player.getStat("block_power").get() + amount),
            "+%ah block power"
        ));

        register.register(game.getId("increase_mana_regen"), new StatModifierType(
            (player, amount, targets) -> player.setMultiplier("mana_regen", player.getMultiplier("mana_regen").get() + amount),
            "+%ah% mana regeneration rate"
        ));

        register.register(game.getId("increase_xp_gain"), new StatModifierType(
            (player, amount, targets) -> player.setMultiplier("xp", player.getMultiplier("xp").get() + amount),
            "+%ah% xp gain"
        ));

        register.register(game.getId("increase_dash_speed"), new StatModifierType(
            (player, amount, targets) -> player.setStat("dash_speed", player.getStat("dash_speed").get() + amount),
            "+%ah% dash speed"
        ));

        register.register(game.getId("increase_dash_time"), new StatModifierType(
            (player, amount, targets) -> player.setStat("dash_time", player.getStat("dash_time").get() + amount),
            "+%a seconds dash time"
        ));

        register.register(game.getId("decrease_dash_cooldown"), new StatModifierType(
            (player, amount, targets) -> player.setStat("dash_cooldown", player.getStat("dash_cooldown").get() - amount),
            "-%a seconds dash cooldown"
        ));

        return register;
    }
}
