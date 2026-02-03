package com.github.lordzintick.java_game.game.accessory;

import com.github.lordzintick.pixel_krush.core.api.AbstractGame;
import com.github.lordzintick.pixel_krush.core.api.TiledAtlas;
import com.github.lordzintick.pixel_krush.core.util.Identifier;
import com.github.lordzintick.pixel_krush.core.util.ListUtil;
import com.github.lordzintick.pixel_krush.core.util.Text;
import com.github.lordzintick.pixel_krush.core.util.registry.DeferredRegister;
import com.github.lordzintick.pixel_krush.core.util.registry.ImmutableRegistry;
import com.github.lordzintick.java_game.game.entity.player.StatModifierType;
import com.github.lordzintick.java_game.ui.widget.AccessorySlot;

public final class AccessoryTypes {
    public static DeferredRegister<AccessoryType> registrar(AbstractGame game) {
        DeferredRegister<AccessoryType> register = DeferredRegister.create(game, game.getId("accessories"));
        ImmutableRegistry<StatModifierType> statModifierTypes = game.queryRegistryOrThrow(game.getId("stat_modifier_types"));
        TiledAtlas icons = game.getCachedAtlas("accessories");

        register.register(game.getId("damage"),
            new AccessoryType(icons.get(0, 0), "Powerful Strikes", 8, false, mult -> ListUtil.listOf(
                statModifierTypes.getOrThrow(game.getId("increase_damage")).getInstance(0.05f * mult)
            ), ListUtil.listOf(
                new Text("Your skills do more damage")
            )));

        register.register(game.getId("max_mana"),
            new AccessoryType(icons.get(1, 0), "Heart of Magic", 3, true, mult -> ListUtil.listOf(
                statModifierTypes.getOrThrow(game.getId("increase_max_mana")).getInstance(5 * mult)
            ), ListUtil.listOf(
                new Text("You have a larger reserve of magical energy")
            )));

        register.register(game.getId("max_health"),
            new AccessoryType(icons.get(2, 0), "Mega Health", 1, true, mult -> ListUtil.listOf(
                statModifierTypes.getOrThrow(game.getId("increase_max_health")).getInstance(5 * mult)
            ), ListUtil.listOf(
                new Text("You have a larger reserve of health points")
            )));

        register.register(game.getId("block_power"),
            new AccessoryType(icons.get(3, 0), "Ironskin", 5, true, mult -> ListUtil.listOf(
                statModifierTypes.getOrThrow(game.getId("increase_block_power")).getInstance(0.02f * mult)
            ), ListUtil.listOf(
                new Text("Your chance of blocking attacks is increased")
            )));

        register.register(game.getId("attack_speed"),
            new AccessoryType(icons.get(4, 0), "Relentless", 6, false, mult -> ListUtil.listOf(
                statModifierTypes.getOrThrow(game.getId("increase_attack_speed")).getInstance(0.05f * mult)
            ), ListUtil.listOf(
                new Text("You can use your skills more often")
            )));

        register.register(game.getId("mana_regen"),
            new AccessoryType(icons.get(5, 0), "Mystic Flow", 4, true, mult -> ListUtil.listOf(
                statModifierTypes.getOrThrow(game.getId("increase_mana_regen")).getInstance(0.1f * mult)
            ), ListUtil.listOf(
                new Text("Your mana reserves regenerate faster")
            )));

        register.register(game.getId("xp_gain"),
            new AccessoryType(icons.get(0, 1), "Knowledgeable", 5, true, mult -> ListUtil.listOf(
                statModifierTypes.getOrThrow(game.getId("increase_xp_gain")).getInstance(0.25f * mult)
            ), ListUtil.listOf(
                new Text("You gain more XP from defeated enemies")
            )));

        register.register(game.getId("dash_speed"),
            new AccessoryType(icons.get(1, 1), "Speedster", 3, true, mult -> ListUtil.listOf(
                statModifierTypes.getOrThrow(game.getId("increase_dash_speed")).getInstance(0.2f * mult)
            ), ListUtil.listOf(
                new Text("Your speed increases more when dashing")
            )));

        register.register(game.getId("dash_time"),
            new AccessoryType(icons.get(2, 1), "Endurance", 2, true, mult -> ListUtil.listOf(
                statModifierTypes.getOrThrow(game.getId("increase_dash_time")).getInstance(0.1f * mult)
            ), ListUtil.listOf(
                new Text("You dash for longer")
            )));

        register.register(game.getId("dash_cooldown"),
            new AccessoryType(icons.get(3, 1), "Speedy Recovery", 3, true, mult -> ListUtil.listOf(
                statModifierTypes.getOrThrow(game.getId("decrease_dash_cooldown")).getInstance(0.1f * mult)
            ), ListUtil.listOf(
                new Text("You can dash more frequently")
            )));

        return register;
    }

    public static AccessoryType getWeightedRandomAccessoryType(AbstractGame game, AccessorySlot[] slots) {
        int totalWeight = 0;
        ImmutableRegistry<AccessoryType> accessoryTypes = game.queryRegistryOrThrow(game.getId("accessories"));

        for (Identifier id : accessoryTypes.idArray()) {
            totalWeight += accessoryTypes.getOrThrow(id).weight;
        }

        int value = game.getRandom().nextInt(totalWeight);
        AccessoryType selectedAccessoryType = null;

        for (Identifier id : accessoryTypes.idArray()) {
            AccessoryType accessoryType = accessoryTypes.getOrThrow(id);

            boolean alreadyHasAccessory = false;
            for (AccessorySlot slot : slots) {
                if (slot.slottedAccessory == null) continue;

                if (slot.slottedAccessory.type == accessoryType) {
                    alreadyHasAccessory = true;
                    break;
                }
            }

            if (alreadyHasAccessory) continue;

            value -= accessoryTypes.getOrThrow(id).weight;
            if (value <= 0) {
                selectedAccessoryType = accessoryType;
                break;
            }
        }

        return selectedAccessoryType;
    }
}
