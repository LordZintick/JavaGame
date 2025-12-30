package com.lordzintick.game.spell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector4;
import com.badlogic.gdx.utils.Disposable;
import com.lordzintick.MainGame;
import com.lordzintick.audio.AudioManager;
import com.lordzintick.core.Logger;
import com.lordzintick.game.entity.effect.OnFireEffect;
import com.lordzintick.game.entity.effect.PoisonEffect;
import com.lordzintick.game.entity.effect.SlowEffect;
import com.lordzintick.game.proj.ProjectileHelper;
import com.lordzintick.util.ListUtil;
import com.lordzintick.util.Text;

import java.util.*;

public final class Spells implements Disposable {
    private static final Logger LOGGER = new Logger(Spells.class);
    public final HashMap<String, Spell> SPELLS = new HashMap<>();
    private final ArrayList<Spell> SPELLS_LIST = new ArrayList<>();
    private final ArrayList<Texture> SPELL_SHEETS = new ArrayList<>();
    private final TextureRegion[][] splitSprites;
    public final NinePatch tooltipTexture;
    public final NinePatch tooltipOverlay;
    private final MainGame game;

    public Spells(MainGame game) {
        this.game = game;
        tooltipTexture = new NinePatch(new Texture("textures/tooltip.png"), 2, 2, 2, 2);
        tooltipOverlay = new NinePatch(new Texture("textures/tooltip_overlay.png"), 3, 3, 3, 3);
        splitSprites = TextureRegion.split(new Texture(Gdx.files.internal("textures/spells.png")), 8, 8);
        TextureRegion[][] particles = game.particlesAtlas;

        register("fireball", new Spell("Fireball", Rarity.COMMON, ListUtil.listOf(
            new Text("Shoots a small fast fireball"),
            new Text("Can set enemies on fire")
        ), splitSprites[0][1], 5, 0.5f, AudioManager.SHOOT, (player) -> {
            ProjectileHelper.shootProjectile(registerSheet("textures/fireball.png"), player, ProjectileHelper.ProjConfig.FIREBALL);
        }));

        register("iceball", new Spell("Iceball", Rarity.UNCOMMON, ListUtil.listOf(
            new Text("Shoots a small, slow, and higher damage iceball that pierces once"),
            new Text("Slows enemies")
        ), splitSprites[0][2], 10, 1.5f, AudioManager.SHOOT, (player) -> {
            ProjectileHelper.shootProjectile(registerSheet("textures/iceball.png"), player, ProjectileHelper.ProjConfig.ICEBALL);
        }));

        register("poison_nova", new Spell("Poison Nova", Rarity.UNCOMMON, ListUtil.listOf(
            new Text("Emits a poison nova around you that damages enemies"),
            new Text("Poisons enemies")
        ), splitSprites[0][3], 25, 3.5f, AudioManager.KABOOM, (player) -> {
            ProjectileHelper.placeStationaryProjectile(registerSheet("textures/poison_nova.png"), player, ProjectileHelper.ProjConfig.POISON_NOVA);
        }));

        register("lightning_bolt", new Spell("Lightning Bolt", Rarity.RARE, ListUtil.listOf(
            new Text("Shoots a super fast lightning bolt"),
            new Text("Pierces through three enemies")
        ), splitSprites[0][0], 8, 0.75f, AudioManager.SHOOT, (player) -> {
            ProjectileHelper.shootProjectile(registerSheet("textures/lightning_bolt.png"), player, ProjectileHelper.ProjConfig.LIGHTNING_BOLT);
        }));
    }

    private void register(String id, Spell spell) {
        if (SPELLS.containsKey(id))
            throw new IllegalArgumentException("ID already exists!");

        SPELLS.put(id, spell);
        SPELLS_LIST.add(spell);
    }

    private Texture registerSheet(String filename) {
        Texture tex = new Texture(filename);
        SPELL_SHEETS.add(tex);
        return tex;
    }

    private int calculateWeight(Spell spell) {
        return (Rarity.values().length - spell.rarity.ordinal() + 1) + 1;
    }

    public Spell getRandomSpell() {
        int totalWeight = 0;

        for (Spell spell : SPELLS_LIST) {
            totalWeight += calculateWeight(spell);
        }

        int value = game.random.nextInt(totalWeight);
        Spell selectedSpell = null;

        for (Spell spell : SPELLS_LIST) {
            value -= calculateWeight(spell);
            if (value <= 0) {
                selectedSpell = spell;
                break;
            }
        }

        return selectedSpell;
    }

    public void tickAll(float deltaTime) {
        for (Spell spell : SPELLS.values()) {
            spell.tick(deltaTime);
        }
    }

    public void dispose() {
        SPELL_SHEETS.forEach(Texture::dispose);
        splitSprites[0][0].getTexture().dispose();
        tooltipOverlay.getTexture().dispose();
        tooltipTexture.getTexture().dispose();
    }
}
