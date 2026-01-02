package com.lordzintick.game.accessory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lordzintick.MainGame;
import com.lordzintick.game.Rarity;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.game.entity.player.StatModifierTypes;
import com.lordzintick.game.skill.SkillType;
import com.lordzintick.ui.widget.AccessorySlot;
import com.lordzintick.util.ListUtil;
import com.lordzintick.util.MathUtil;
import com.lordzintick.util.Text;

import java.util.ArrayList;
import java.util.HashMap;

public final class AccessoryTypes {
    private final HashMap<String, AccessoryType> ACCESSORY_TYPES = new HashMap<>();
    private final MainGame game;

    public AccessoryTypes(MainGame game) {
        this.game = game;
        TextureRegion[][] icons = TextureRegion.split(game.assets.get("textures/game/accessories.png"), 8, 8);

        register("damage",
            new AccessoryType(icons[0][0], "Powerful Strikes", 8, false, mult -> ListUtil.listOf(
                StatModifierTypes.INCREASE_DAMAGE.getInstance(0.05f * mult)
            ), ListUtil.listOf(
                new Text("Your skills do more damage")
            )));

        register("max_mana",
            new AccessoryType(icons[0][1], "Heart of Magic", 3, true, mult -> ListUtil.listOf(
                StatModifierTypes.INCREASE_MAX_MANA.getInstance(5 * mult)
            ), ListUtil.listOf(
                new Text("You have a larger reserve of magical energy")
            )));

        register("max_health",
            new AccessoryType(icons[0][2], "Mega Health", 1, true, mult -> ListUtil.listOf(
                StatModifierTypes.INCREASE_MAX_HEALTH.getInstance(5 * mult)
            ), ListUtil.listOf(
                new Text("You have a larger reserve of health points")
            )));

        register("block_power",
            new AccessoryType(icons[0][3], "Ironskin", 5, true, mult -> ListUtil.listOf(
                StatModifierTypes.INCREASE_BLOCK_POWER.getInstance(0.02f * mult)
            ), ListUtil.listOf(
                new Text("Your chance of blocking attacks is increased")
            )));

        register("attack_speed",
            new AccessoryType(icons[0][4], "Relentless", 6, false, mult -> ListUtil.listOf(
                StatModifierTypes.INCREASE_ATTACK_SPEED.getInstance(0.05f * mult)
            ), ListUtil.listOf(
                new Text("You can use your skills more often")
            )));

        register("mana_regen",
            new AccessoryType(icons[0][5], "Mystic Flow", 4, true, mult -> ListUtil.listOf(
                StatModifierTypes.INCREASE_MANA_REGEN.getInstance(0.1f * mult)
            ), ListUtil.listOf(
                new Text("Your mana reserves regenerate faster")
            )));

        register("xp_gain",
            new AccessoryType(icons[1][0], "Knowledgeable", 5, true, mult -> ListUtil.listOf(
                StatModifierTypes.INCREASE_XP_GAIN.getInstance(0.25f * mult)
            ), ListUtil.listOf(
                new Text("You gain more XP from defeated enemies")
            )));
    }

    private void register(String id, AccessoryType accessoryType) {
        if (ACCESSORY_TYPES.containsKey(id))
            throw new IllegalArgumentException("ID already registered!");

        ACCESSORY_TYPES.put(id, accessoryType);
    }

    public AccessoryType getWeightedRandomAccessoryType(AccessorySlot[] slots) {
        int totalWeight = 0;

        for (String id : ACCESSORY_TYPES.keySet()) {
            totalWeight += ACCESSORY_TYPES.get(id).weight;
        }

        int value = game.random.nextInt(totalWeight);
        AccessoryType selectedAccessoryType = null;

        for (String id : ACCESSORY_TYPES.keySet()) {
            AccessoryType accessoryType = ACCESSORY_TYPES.get(id);

            boolean alreadyHasAccessory = false;
            for (AccessorySlot slot : slots) {
                if (slot.slottedAccessory == null) continue;

                if (slot.slottedAccessory.type == accessoryType) {
                    alreadyHasAccessory = true;
                    break;
                }
            }

            if (alreadyHasAccessory) continue;

            value -= ACCESSORY_TYPES.get(id).weight;
            if (value <= 0) {
                selectedAccessoryType = accessoryType;
                break;
            }
        }

        return selectedAccessoryType;
    }
}
