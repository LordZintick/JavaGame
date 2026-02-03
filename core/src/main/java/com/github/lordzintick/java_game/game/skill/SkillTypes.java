package com.github.lordzintick.java_game.game.skill;

import com.github.lordzintick.pixel_krush.core.api.AbstractGame;
import com.github.lordzintick.pixel_krush.core.api.TiledAtlas;
import com.github.lordzintick.pixel_krush.core.util.Identifier;
import com.github.lordzintick.pixel_krush.core.util.ListUtil;
import com.github.lordzintick.pixel_krush.core.util.Text;
import com.github.lordzintick.pixel_krush.core.util.registry.DeferredRegister;
import com.github.lordzintick.pixel_krush.core.util.registry.ImmutableRegistry;
import com.github.lordzintick.java_game.game.Rarity;
import com.github.lordzintick.java_game.game.entity.effect.RageEffect;
import com.github.lordzintick.java_game.game.entity.player.Player;
import com.github.lordzintick.java_game.ui.widget.SkillSlot;

import java.util.*;

public final class SkillTypes {
    public static DeferredRegister<SkillType> registrar(AbstractGame game) {
        DeferredRegister<SkillType> register = DeferredRegister.create(game, game.getId("skill_types"));
        TiledAtlas icons = game.getCachedAtlas("skills");

        // MAGE
        // Commons
        register.register(game.getId("fireball"), new SkillType(new Text("Fireball"), Rarity.COMMON, ListUtil.listOf(
            new Text("Shoots a small fast fireball"),
            new Text("Can set enemies on fire")
        ), icons.get(1, 0), 5, 0.5f, game.getAudioSample("shoot"),
            (player, multipliers) -> SkillHelper.shootProjectile("textures/game/skills/fireball.png", player, Optional.of("fireball"), Behaviour.FIREBALL)));

        // Uncommons
        register.register(game.getId("iceball"), new SkillType(new Text("Iceball"), Rarity.UNCOMMON, ListUtil.listOf(
            new Text("Shoots a small, slow, and higher damage iceball that pierces once"),
            new Text("Slows enemies")
        ), icons.get(2, 0), 10, 1.5f, game.getAudioSample("shoot"),
            (player, multipliers) -> SkillHelper.shootProjectile("textures/game/skills/iceball.png", player, Optional.of("iceball"), Behaviour.ICEBALL)));

        register.register(game.getId("acidball"), new SkillType(new Text("Acidball"), Rarity.UNCOMMON, ListUtil.listOf(
            new Text("Shoots a slightly slower ball of poison with moderate damage"),
            new Text("Poisons enemies")
        ), icons.get(6, 0), 10, 1.5f, game.getAudioSample("shoot"),
            (player, multipliers) -> SkillHelper.shootProjectile("textures/game/skills/acidball.png", player, Optional.of("acidball"), Behaviour.ACIDBALL)));

        register.register(game.getId("waterball"), new SkillType(new Text("Waterball"), Rarity.UNCOMMON, ListUtil.listOf(
            new Text("Shoots a decently fast ball of water that doesn't do much damage,"),
            new Text("but it pierces four times and slightly pushes enemies back")
        ), icons.get(7, 0), 8, 1.25f, game.getAudioSample("shoot"),
            (player, multipliers) -> SkillHelper.shootProjectile("textures/game/skills/waterball.png", player, Optional.of("waterball"), Behaviour.WATERBALL)));

        register.register(game.getId("lightningball"), new SkillType(new Text("Lightningball"), Rarity.UNCOMMON, ListUtil.listOf(
            new Text("Shoots an extremely fast ball of electrical energy that deals good damage")
        ), icons.get(8, 0), 5, 0.75f, game.getAudioSample("shoot"),
            (player, multipliers) -> SkillHelper.shootProjectile("textures/game/skills/lightningball.png", player, Optional.of("lightningball"), Behaviour.LIGHTNINGBALL)));

        register.register(game.getId("flowerball"), new SkillType(new Text("Flowerball"), Rarity.UNCOMMON, ListUtil.listOf(
            new Text("Shoots a slow, low damage floral ball"),
            new Text("Has a small chance to heal you for 1 health")
        ), icons.get(9, 0), 10, 1.5f, game.getAudioSample("shoot"),
            (player, multipliers) -> SkillHelper.shootProjectile("textures/game/skills/flowerball.png", player, Optional.of("flowerball"), Behaviour.FLOWERBALL)));

        register.register(game.getId("petal"), new SkillType(new Text("Petal"), Rarity.UNCOMMON, ListUtil.listOf(
            new Text("Shoots a fast, small, and low damage petal"),
            new Text("Has a very small chance to heal you for 1 health")
        ), icons.get(1, 2), 1, 0.25f, game.getAudioSample("shoot"),
            (player, multipliers) -> SkillHelper.shootProjectile("textures/game/skills/petal.png", player, Optional.of("petal"), Behaviour.PETAL)));

        // Rares
        register.register(game.getId("poison_nova"), new SkillType(new Text("Poison Nova"), Rarity.RARE, ListUtil.listOf(
            new Text("Emits a poison nova around you that damages enemies"),
            new Text("Poisons enemies")
        ), icons.get(3, 0), 25, 3.5f, game.getAudioSample("kaboom"),
            (player, multipliers) -> SkillHelper.placeStationaryProjectile("textures/game/skills/poison_nova.png", player, false, Optional.of("poison_nova"), Behaviour.POISON_NOVA)));

        register.register(game.getId("lightning_bolt"), new SkillType(new Text("Lightning Bolt"), Rarity.RARE, ListUtil.listOf(
            new Text("Shoots a super fast lightning bolt"),
            new Text("Pierces through three enemies")
        ), icons.get(0, 0), 4, 0.75f, game.getAudioSample("shoot"),
            (player, multipliers) -> SkillHelper.shootProjectile("textures/game/skills/lightning_bolt.png", player, Optional.of("ligntning_bolt"), Behaviour.LIGHTNING_BOLT)));

        register.register(game.getId("plasma_bolt"), new SkillType(new Text("Plasma Bolt"), Rarity.RARE, ListUtil.listOf(
            new Text("Shoots an infinitely piercing but low damage bolt of superheated energy"),
            new Text("Burns enemies")
        ), icons.get(4, 0), 8, 1.25f, game.getAudioSample("shoot"),
            (player, multipliers) -> SkillHelper.shootProjectile("textures/game/skills/plasma_bolt.png", player, Optional.of("plasma_bolt"), Behaviour.PLASMA_BOLT)));

        // Epics
        register.register(game.getId("wave"), new SkillType(new Text("Wave"), Rarity.EPIC, ListUtil.listOf(
            new Text("Summons a large wave to blow your enemies away"),
            new Text("Does moderate damage but knocks back enemies greatly")
        ), icons.get(0, 2), 20, 4f, game.getAudioSample("mega_slash"),
            (player, multipliers) -> SkillHelper.shootProjectile("textures/game/skills/wave.png", player, Optional.of("wave"), Behaviour.WAVE)));

        register.register(game.getId("petalstorm"), new SkillType(new Text("Petalstorm"), Rarity.EPIC, ListUtil.listOf(
            new Text("Shoots a rotating ball of floral destruction"),
            new Text("Pierces through 8 enemies"),
            new Text("Has a chance to heal you on hit"),
            new Text("Fires small petals in four directions every 3/4 second"),
            new Text("Each petal has a small chance to heal you")
        ), icons.get(2, 2), 15, 5f, game.getAudioSample("shoot"),
            (player, multipliers) -> SkillHelper.shootProjectile("textures/game/skills/petalstorm_ball.png", player, Optional.of("petalstorm"), Behaviour.PETALSTORM_BALL)));

        register.register(game.getId("lightning_strike"), new SkillType(new Text("Lightning Strike"), Rarity.EPIC, ListUtil.listOf(
            new Text("Strikes all enemies within a certain area with lightning after a few moments"),
            new Text("Deals extremely high damage")
        ), icons.get(3, 2), 20, 8f, game.getAudioSample("strike"),
            (player, multipliers) -> SkillHelper.wrapWarning(SkillHelper.createStationaryProjectile("textures/game/skills/lightning_strike.png", player, Optional.of("lightning_strike"), Behaviour.LIGHTNING_STRIKE), true, 1f)));

        // Legendaries
        register.register(game.getId("laser_beam"), new SkillType(new Text("Laser Beam"), Rarity.LEGENDARY, ListUtil.listOf(
                new Text("Shoots a concentrated beam of pure evaporating energy towards your cursor for 8 seconds"),
                new Text("Deals constant damage to any nearby mobs and sets them on fire"),
                new Text("Comes with a hefty cooldown and mana cost though")
            ), icons.get(5, 0), 50, 25f, game.getAudioSample("laser"),
                (player, multipliers) -> {
                    game.getAudioSample("kaboom").play();
                    SkillHelper.placeStationaryProjectile(
                        "textures/game/skills/laser_beam.png",
                        player, false,
                        Optional.of("laser_beam"),
                        Behaviour.LASER_BEAM
                    );
                })
        );



        // WARRIOR
        // Commons
        register.register(game.getId("slash"), new SkillType(new Text("Slash"), Rarity.COMMON, ListUtil.listOf(
            new Text("Slashes in front of you, dealing moderate damage"),
            new Text("Has a small chance to apply bleed for a short time to enemies")
        ), icons.get(0, 1), 0, 0.5f, game.getAudioSample("slash"),
            (player, multipliers) -> SkillHelper.triggerMeleeAttack("textures/game/skills/slash.png", player, Optional.of("slash"), Behaviour.SLASH)));

        // Uncommons
        register.register(game.getId("axe_throw"), new SkillType(new Text("Axe Throw"), Rarity.UNCOMMON, ListUtil.listOf(
            new Text("Throws a heavy axe toward your cursor"),
            new Text("Has a chance to apply bleed to enemies")
        ), icons.get(1, 1), 0, 1.5f, game.getAudioSample("kaboom"),
            (player, multipliers) -> SkillHelper.shootProjectile("textures/game/skills/axe.png", player, Optional.of("axe"), Behaviour.AXE)));

        register.register(game.getId("mega_slash"), new SkillType(new Text("Mega Slash"), Rarity.UNCOMMON, ListUtil.listOf(
            new Text("Slashes in a wide arc in front of you,"),
            new Text("dealing high damage to anything unfortunate enough to be caught in the blast."),
            new Text("Applies a powerful bleed to enemeis")
        ), icons.get(3, 1), 0, 6, game.getAudioSample("mega_slash"),
            (player, multipliers) -> SkillHelper.triggerMeleeAttack("textures/game/skills/mega_slash.png", player, Optional.of("mega_slash"), Behaviour.MEGA_SLASH)));

        // Rares
        register.register(game.getId("rage"), new SkillType(new Text("Rage"), Rarity.RARE, ListUtil.listOf(
            new Text("Enrages you, doubling your damage and increasing your speed for a short time")
        ), icons.get(4, 1), 0, 15, game.getAudioSample("roar"),
            (player, multipliers) -> player.applyEffect(new RageEffect(game, 10f, (int) Math.floor(multipliers.damage)))));

        // Legendaries
        register.register(game.getId("whirlwind"), new SkillType(new Text("Whirlwind"), Rarity.LEGENDARY, ListUtil.listOf(
            new Text("Shoots a whirlwind towards your cursor"),
            new Text("Deals low damage but moves super fast and has a chance to cause bleed")
        ), icons.get(2, 1), 0, 0.25f, game.getAudioSample("slash"),
            (player, multipliers) -> SkillHelper.shootProjectile("textures/game/skills/whirlwind.png", player, Optional.of("whirlwind"), Behaviour.WHIRLWIND)));

        return register;
    }

    public static SkillType getWeightedRandomSkillType(Player player, SkillSlot[] slots) {
        int totalWeight = 0;
        AbstractGame game = player.screen.game;
        ImmutableRegistry<SkillType> skillTypes = game.queryRegistryOrThrow(game.getId("skill_types"));

        for (Identifier id : skillTypes.idArray()) {
            if (!player.playerClass.get().skillpool.contains(id.getPath())) continue;
            totalWeight += Rarity.calculateWeight(skillTypes.getOrThrow(id).rarity);
        }

        int value = game.getRandom().nextInt(totalWeight);
        SkillType selectedSkillType = null;

        for (Identifier id : skillTypes.idArray()) {
            if (!player.playerClass.get().skillpool.contains(id.getPath())) continue;
            SkillType skillType = skillTypes.getOrThrow(id);

            boolean alreadyHasSpell = false;
            for (SkillSlot slot : slots) {
                if (slot.slottedSkill == null) continue;

                if (slot.slottedSkill.type == skillType) {
                    alreadyHasSpell = true;
                    break;
                }
            }

            if (alreadyHasSpell) continue;

            value -= Rarity.calculateWeight(skillType.rarity);
            if (value <= 0) {
                selectedSkillType = skillType;
                break;
            }
        }

        return selectedSkillType;
    }
}
