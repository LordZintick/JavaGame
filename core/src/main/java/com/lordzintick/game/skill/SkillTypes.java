package com.lordzintick.game.skill;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.lordzintick.MainGame;
import com.lordzintick.audio.AudioManager;
import com.lordzintick.core.Logger;
import com.lordzintick.game.Rarity;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.ui.widget.SkillSlot;
import com.lordzintick.util.ListUtil;
import com.lordzintick.util.MathUtil;
import com.lordzintick.util.Text;

import javax.swing.text.html.Option;
import java.util.*;

public final class SkillTypes implements Disposable {
    private static final Logger LOGGER = new Logger(SkillTypes.class);
    public final HashMap<String, SkillType> SKILL_TYPES = new HashMap<>();
    private final ArrayList<SkillType> SKILLS_LIST = new ArrayList<>();
    private final ArrayList<Texture> SKILL_SHEETS = new ArrayList<>();
    private final TextureRegion[][] splitSprites;
    public final NinePatch tooltipTexture;
    public final NinePatch tooltipOverlay;
    private final MainGame game;

    public SkillTypes(MainGame game) {
        this.game = game;
        tooltipTexture = new NinePatch(new Texture("textures/ui/tooltip.png"), 2, 2, 2, 2);
        tooltipOverlay = new NinePatch(new Texture("textures/ui/tooltip_overlay.png"), 3, 3, 3, 3);
        splitSprites = TextureRegion.split(new Texture(Gdx.files.internal("textures/game/skills/skills.png")), 8, 8);

        // MAGE
        register("fireball", new SkillType("Fireball", Rarity.COMMON, ListUtil.listOf(
            new Text("Shoots a small fast fireball"),
            new Text("Can set enemies on fire")
        ), splitSprites[0][1], 5, 0.5f, AudioManager.SHOOT,
            (player, level) -> SkillHelper.shootProjectile(registerSheet("textures/game/skills/fireball.png"), player, Optional.of("fireball"), SkillConfig.FIREBALL)));

        register("iceball", new SkillType("Iceball", Rarity.UNCOMMON, ListUtil.listOf(
            new Text("Shoots a small, slow, and higher damage iceball that pierces once"),
            new Text("Slows enemies")
        ), splitSprites[0][2], 10, 1.5f, AudioManager.SHOOT,
            (player, level) -> SkillHelper.shootProjectile(registerSheet("textures/game/skills/iceball.png"), player, Optional.of("iceball"), SkillConfig.ICEBALL)));

        register("poison_nova", new SkillType("Poison Nova", Rarity.UNCOMMON, ListUtil.listOf(
            new Text("Emits a poison nova around you that damages enemies"),
            new Text("Poisons enemies")
        ), splitSprites[0][3], 25, 3.5f, AudioManager.KABOOM,
            (player, level) -> SkillHelper.placeStationaryProjectile(registerSheet("textures/game/skills/poison_nova.png"), player, Optional.of("poison_nova"), SkillConfig.POISON_NOVA)));

        register("lightning_bolt", new SkillType("Lightning Bolt", Rarity.RARE, ListUtil.listOf(
            new Text("Shoots a super fast lightning bolt"),
            new Text("Pierces through three enemies")
        ), splitSprites[0][0], 8, 0.75f, AudioManager.SHOOT,
            (player, level) -> SkillHelper.shootProjectile(registerSheet("textures/game/skills/lightning_bolt.png"), player, Optional.of("ligntning_bolt"), SkillConfig.LIGHTNING_BOLT)));

        register("plasma_bolt", new SkillType("Plasma Bolt", Rarity.RARE, ListUtil.listOf(
            new Text("Shoots an infinitely piercing but slot and low damage bolt of superheated energy"),
            new Text("Burns enemies")
        ), splitSprites[0][4], 8, 1.25f, AudioManager.SHOOT,
            (player, level) -> SkillHelper.shootProjectile(registerSheet("textures/game/skills/plasma_bolt.png"), player, Optional.of("plasma_bolt"), SkillConfig.PLASMA_BOLT)));



        // WARRIOR
        register("slash", new SkillType("Slash", Rarity.COMMON, ListUtil.listOf(
            new Text("Slashes in front of you, dealing moderate damage"),
            new Text("Has a small chance to apply bleed for a short time to enemies")
        ), splitSprites[1][0], 0, 0.5f, AudioManager.SLASH,
            (player, level) -> SkillHelper.triggerMeleeAttack(registerSheet("textures/game/skills/slash.png"), player, Optional.of("slash"), SkillConfig.SLASH)));

        register("axe_throw", new SkillType("Axe Throw", Rarity.UNCOMMON, ListUtil.listOf(
            new Text("Throws a heavy axe toward your cursor"),
            new Text("Has a chance to apply bleed to enemies")
        ), splitSprites[1][1], 0, 1.5f, AudioManager.KABOOM,
            (player, level) -> SkillHelper.shootProjectile(registerSheet("textures/game/skills/axe.png"), player, Optional.of("axe"), SkillConfig.AXE)));

        register("mega_slash", new SkillType("Mega Slash", Rarity.UNCOMMON, ListUtil.listOf(
            new Text("Slashes in a wide arc in front of you,"),
            new Text("dealing high damage to anything unfortunate enough to be caught in the blast."),
            new Text("Applies a powerful bleed to enemeis")
        ), splitSprites[1][3], 0, 6, AudioManager.MEGA_SLASH,
            (player, level) -> SkillHelper.triggerMeleeAttack(registerSheet("textures/game/skills/mega_slash.png"), player, Optional.of("mega_slash"), SkillConfig.MEGA_SLASH)));

        register("whirlwind", new SkillType("Whirlwind", Rarity.LEGENDARY, ListUtil.listOf(
            new Text("Shoots a whirlwind towards your cursor"),
            new Text("Deals low damage but moves super fast and has a chance to cause bleed")
        ), splitSprites[1][2], 0, 0.25f, AudioManager.SLASH,
            (player, level) -> SkillHelper.shootProjectile(registerSheet("textures/game/skills/whirlwind.png"), player, Optional.of("whirlwind"), SkillConfig.WHIRLWIND)));
    }

    private void register(String id, SkillType skillType) {
        if (SKILL_TYPES.containsKey(id))
            throw new IllegalArgumentException("ID already exists!");

        SKILL_TYPES.put(id, skillType);
        SKILLS_LIST.add(skillType);
    }

    private Texture registerSheet(String filename) {
        Texture tex = new Texture(filename);
        SKILL_SHEETS.add(tex);
        return tex;
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

    public void dispose() {
        SKILL_SHEETS.forEach(Texture::dispose);
        splitSprites[0][0].getTexture().dispose();
        tooltipOverlay.getTexture().dispose();
        tooltipTexture.getTexture().dispose();
    }
}
