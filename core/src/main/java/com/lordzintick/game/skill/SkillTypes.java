package com.lordzintick.game.skill;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.lordzintick.MainGame;
import com.lordzintick.core.Logger;
import com.lordzintick.game.Rarity;
import com.lordzintick.game.entity.effect.RageEffect;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.ui.widget.SkillSlot;
import com.lordzintick.util.ListUtil;
import com.lordzintick.util.MathUtil;
import com.lordzintick.util.Text;

import javax.swing.text.html.Option;
import java.util.*;

public final class SkillTypes {
    private static final Logger LOGGER = new Logger(SkillTypes.class);
    public final HashMap<String, SkillType> SKILL_TYPES = new HashMap<>();
    public final NinePatch tooltipTexture;
    public final NinePatch tooltipOverlay;
    private final MainGame game;

    public SkillTypes(MainGame game) {
        this.game = game;
        tooltipTexture = new NinePatch(game.assets.get("textures/ui/tooltip.png", Texture.class), 2, 2, 2, 2);
        tooltipOverlay = new NinePatch(game.assets.get("textures/ui/tooltip_overlay.png", Texture.class), 3, 3, 3, 3);
        TextureRegion[][] splitSprites = TextureRegion.split(game.assets.get("textures/game/skills/skills.png"), 8, 8);

        // MAGE
        // Commons
        register("fireball", new SkillType("Fireball", Rarity.COMMON, ListUtil.listOf(
            new Text("Shoots a small fast fireball"),
            new Text("Can set enemies on fire")
        ), splitSprites[0][1], 5, 0.5f, game.audio.SHOOT,
            (player, damageMult) -> SkillHelper.shootProjectile("textures/game/skills/fireball.png", player, Optional.of("fireball"), SkillConfig.FIREBALL)));

        // Uncommons
        register("iceball", new SkillType("Iceball", Rarity.UNCOMMON, ListUtil.listOf(
            new Text("Shoots a small, slow, and higher damage iceball that pierces once"),
            new Text("Slows enemies")
        ), splitSprites[0][2], 10, 1.5f, game.audio.SHOOT,
            (player, damageMult) -> SkillHelper.shootProjectile("textures/game/skills/iceball.png", player, Optional.of("iceball"), SkillConfig.ICEBALL)));

        register("acidball", new SkillType("Acidball", Rarity.UNCOMMON, ListUtil.listOf(
            new Text("Shoots a slightly slower ball of poison with moderate damage"),
            new Text("Poisons enemies")
        ), splitSprites[0][6], 10, 1.5f, game.audio.SHOOT,
            (player, damageMult) -> SkillHelper.shootProjectile("textures/game/skills/acidball.png", player, Optional.of("acidball"), SkillConfig.ACIDBALL)));

        register("waterball", new SkillType("Waterball", Rarity.UNCOMMON, ListUtil.listOf(
            new Text("Shoots a decently fast ball of water that doesn't do much damage,"),
            new Text("but it pierces four times and slightly pushes enemies back")
        ), splitSprites[0][7], 8, 1.25f, game.audio.SHOOT,
            (player, damageMult) -> SkillHelper.shootProjectile("textures/game/skills/waterball.png", player, Optional.of("waterball"), SkillConfig.WATERBALL)));

        register("lightningball", new SkillType("Lightningball", Rarity.UNCOMMON, ListUtil.listOf(
            new Text("Shoots an extremely fast ball of electrical energy that deals good damage")
        ), splitSprites[0][8], 5, 0.75f, game.audio.SHOOT,
            (player, damageMult) -> SkillHelper.shootProjectile("textures/game/skills/lightningball.png", player, Optional.of("lightningball"), SkillConfig.LIGHTNINGBALL)));

        register("flowerball", new SkillType("Flowerball", Rarity.UNCOMMON, ListUtil.listOf(
            new Text("Shoots a slow, low damage floral ball"),
            new Text("Has a small chance to heal you for 1 health")
        ), splitSprites[0][9], 10, 1.5f, game.audio.SHOOT,
            (player, damageMult) -> SkillHelper.shootProjectile("textures/game/skills/flowerball.png", player, Optional.of("flowerball"), SkillConfig.FLOWERBALL)));

        // Rares
        register("poison_nova", new SkillType("Poison Nova", Rarity.RARE, ListUtil.listOf(
            new Text("Emits a poison nova around you that damages enemies"),
            new Text("Poisons enemies")
        ), splitSprites[0][3], 25, 3.5f, game.audio.KABOOM,
            (player, damageMult) -> SkillHelper.placeStationaryProjectile("textures/game/skills/poison_nova.png", player, Optional.of("poison_nova"), SkillConfig.POISON_NOVA)));

        register("lightning_bolt", new SkillType("Lightning Bolt", Rarity.RARE, ListUtil.listOf(
            new Text("Shoots a super fast lightning bolt"),
            new Text("Pierces through three enemies")
        ), splitSprites[0][0], 4, 0.75f, game.audio.SHOOT,
            (player, damageMult) -> SkillHelper.shootProjectile("textures/game/skills/lightning_bolt.png", player, Optional.of("ligntning_bolt"), SkillConfig.LIGHTNING_BOLT)));

        register("plasma_bolt", new SkillType("Plasma Bolt", Rarity.RARE, ListUtil.listOf(
            new Text("Shoots an infinitely piercing but low damage bolt of superheated energy"),
            new Text("Burns enemies")
        ), splitSprites[0][4], 8, 1.25f, game.audio.SHOOT,
            (player, damageMult) -> SkillHelper.shootProjectile("textures/game/skills/plasma_bolt.png", player, Optional.of("plasma_bolt"), SkillConfig.PLASMA_BOLT)));

        // Epics
        register("wave", new SkillType("Wave", Rarity.EPIC, ListUtil.listOf(
            new Text("Summons a large wave to blow your enemies away"),
            new Text("Does moderate damage but knocks back enemies greatly")
        ), splitSprites[2][0], 20, 4f, game.audio.MEGA_SLASH,
            (player, damageMult) -> SkillHelper.shootProjectile("textures/game/skills/wave.png", player, Optional.of("wave"), SkillConfig.WAVE)));

        // Legendaries
        register("laser_beam", new SkillType("Laser Beam", Rarity.LEGENDARY, ListUtil.listOf(
            new Text("Shoots a concentrated beam of pure evaporating energy towards your cursor for 8 seconds"),
            new Text("Deals constant damage to any nearby mobs and sets them on fire"),
            new Text("Comes with a hefty cooldown and mana cost though")
        ), splitSprites[0][5], 50, 25f, game.audio.LASER,
            (player, damageMult) -> {
                game.audio.KABOOM.play();
                SkillHelper.placeStationaryProjectile(
                    "textures/game/skills/laser_beam.png",
                    player,
                    Optional.of("laser_beam"),
                    SkillConfig.LASER_BEAM
                );
            })
        );



        // WARRIOR
        // Commons
        register("slash", new SkillType("Slash", Rarity.COMMON, ListUtil.listOf(
            new Text("Slashes in front of you, dealing moderate damage"),
            new Text("Has a small chance to apply bleed for a short time to enemies")
        ), splitSprites[1][0], 0, 0.5f, game.audio.SLASH,
            (player, damageMult) -> SkillHelper.triggerMeleeAttack("textures/game/skills/slash.png", player, Optional.of("slash"), SkillConfig.SLASH)));

        // Uncommons
        register("axe_throw", new SkillType("Axe Throw", Rarity.UNCOMMON, ListUtil.listOf(
            new Text("Throws a heavy axe toward your cursor"),
            new Text("Has a chance to apply bleed to enemies")
        ), splitSprites[1][1], 0, 1.5f, game.audio.KABOOM,
            (player, damageMult) -> SkillHelper.shootProjectile("textures/game/skills/axe.png", player, Optional.of("axe"), SkillConfig.AXE)));

        register("mega_slash", new SkillType("Mega Slash", Rarity.UNCOMMON, ListUtil.listOf(
            new Text("Slashes in a wide arc in front of you,"),
            new Text("dealing high damage to anything unfortunate enough to be caught in the blast."),
            new Text("Applies a powerful bleed to enemeis")
        ), splitSprites[1][3], 0, 6, game.audio.MEGA_SLASH,
            (player, damageMult) -> SkillHelper.triggerMeleeAttack("textures/game/skills/mega_slash.png", player, Optional.of("mega_slash"), SkillConfig.MEGA_SLASH)));

        // Rares
        register("rage", new SkillType("Rage", Rarity.RARE, ListUtil.listOf(
            new Text("Enrages you, doubling your damage and increasing your speed for a short time")
        ), splitSprites[1][4], 0, 15, game.audio.ROAR,
            (player, damageMult) -> player.applyEffect(new RageEffect(game, 10f, (int) Math.floor(damageMult)))));

        // Legendaries
        register("whirlwind", new SkillType("Whirlwind", Rarity.LEGENDARY, ListUtil.listOf(
            new Text("Shoots a whirlwind towards your cursor"),
            new Text("Deals low damage but moves super fast and has a chance to cause bleed")
        ), splitSprites[1][2], 0, 0.25f, game.audio.SLASH,
            (player, damageMult) -> SkillHelper.shootProjectile("textures/game/skills/whirlwind.png", player, Optional.of("whirlwind"), SkillConfig.WHIRLWIND)));
    }

    private void register(String id, SkillType skillType) {
        if (SKILL_TYPES.containsKey(id))
            throw new IllegalArgumentException("ID already exists!");

        SKILL_TYPES.put(id, skillType);
    }

    public SkillType getWeightedRandomSkillType(Player player, SkillSlot[] slots) {
        int totalWeight = 0;

        for (String id : SKILL_TYPES.keySet()) {
            if (!player.playerClass.skillpool.contains(id)) continue;
            totalWeight += MathUtil.calculateWeight(SKILL_TYPES.get(id).rarity);
        }

        int value = game.random.nextInt(totalWeight);
        SkillType selectedSkillType = null;

        for (String id : SKILL_TYPES.keySet()) {
            if (!player.playerClass.skillpool.contains(id)) continue;
            SkillType skillType = SKILL_TYPES.get(id);

            boolean alreadyHasSpell = false;
            for (SkillSlot slot : slots) {
                if (slot.slottedSkill == null) continue;

                if (slot.slottedSkill.type == skillType) {
                    alreadyHasSpell = true;
                    break;
                }
            }

            if (alreadyHasSpell) continue;

            value -= MathUtil.calculateWeight(SKILL_TYPES.get(id).rarity);
            if (value <= 0) {
                selectedSkillType = skillType;
                break;
            }
        }

        return selectedSkillType;
    }
}
