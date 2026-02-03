package com.lordzintick.java_game.game.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector4;
import com.lordzintick.java_game.GameData;
import com.lordzintick.pixel_krush.core.api.AbstractGameScreen;
import com.lordzintick.pixel_krush.core.api.TiledAtlas;
import com.lordzintick.pixel_krush.core.api.ecs.LivingEntity;
import com.lordzintick.pixel_krush.core.api.ecs.comp.*;
import com.lordzintick.pixel_krush.core.api.ecs.sys.anim.AbstractAnimationSystem;
import com.lordzintick.pixel_krush.core.api.ecs.sys.anim.DirectionalAnimationSystem;
import com.lordzintick.pixel_krush.core.util.Direction;
import com.lordzintick.pixel_krush.core.util.ListUtil;
import com.lordzintick.java_game.game.accessory.Accessory;
import com.lordzintick.java_game.game.screen.MainGameScreen;
import com.lordzintick.java_game.game.skill.Skill;
import com.lordzintick.java_game.game.skill.SkillMultiplierSet;
import com.lordzintick.java_game.game.skill.SkillType;

import java.util.Locale;

@SuppressWarnings("UnreachableCode")
public class Player extends LivingEntity {
    public final ValueComponent<Float> maxHealth;
    public final ValueComponent<Float> maxMana;
    public final RangedFloatComponent mana;
    public final ValueComponent<Integer> score;
    public final BiValueComponent<Integer, Integer> level;
    public final ValueComponent<Integer> skillPoints;
    public final ValueComponent<PlayerClass> playerClass;
    public final ListComponent<Skill<?>> equippedSkills;
    public final ListComponent<Accessory<?>> equippedAccessories;
    public final MapComponent<SkillType, Float> skillCooldownMultipliers;
    public final MapComponent<SkillType, Float> skillDamageMultipliers;
    public final ComponentCollection multipliers;
    public final ComponentCollection stats;

    private float manaTicks = 0;
    private float levelupTicks = 0;
    public Skill equippingSkill = null;
    private boolean updatedClass = false;
    private float dashTicks = 0;
    private boolean stoppedDashing = true;
    public float remainingDashCooldown = 0;

    /**
     * @param screen The {@link AbstractGameScreen} containing this player
     */
    public Player(AbstractGameScreen screen, PlayerClass playerClass) {
        super(screen, screen.game.getAssetOrThrow("textures/game/player/player_" + playerClass.name().toLowerCase(Locale.ROOT) + ".png"), 6, 12);

        maxHealth = components.register(getId("max_health"), new ValueComponent<>(this, 50f));
        maxMana = components.register(getId("max_mana"), new ValueComponent<>(this, 100f));
        mana = components.register(getId("mana"), new RangedFloatComponent(this, 100, 0, 100));
        score = components.register(getId("score"), new ValueComponent<>(this, 0));
        level = components.register(getId("level"), new BiValueComponent<>(this, 0, 1));
        skillPoints = components.register(getId("skill_points"), new ValueComponent<>(this, 0));
        this.playerClass = components.register(getId("player_class"), new ValueComponent<>(this, PlayerClass.MAGE));
        equippedSkills = components.register(getId("equipped_skills"), new ListComponent<>(this, ListUtil.listOf(
            ((SkillType) screen.game.queryRegistryOrThrow(screen.game.getId("skill_types")).getOrThrow(screen.game.getId(playerClass.startSkill))).getInstance(), null, null
        )));
        equippedAccessories = components.register(getId("equipped_accessories"), new ListComponent<>(this, ListUtil.listOf()));
        skillCooldownMultipliers = components.register(getId("skill_cooldown_multipliers"), new MapComponent<>(this));
        skillDamageMultipliers = components.register(getId("skill_damage_multipliers"), new MapComponent<>(this));
        multipliers = components.register(getId("multipliers"), ComponentCollection.builder(this)
                .put(getId("multipliers/global_cooldown"), new ValueComponent<>(this, 1f))
                .put(getId("multipliers/global_damage"), new ValueComponent<>(this, 1f))
                .put(getId("multipliers/mana_regen"), new ValueComponent<>(this, 1f))
                .put(getId("multipliers/xp"), new ValueComponent<>(this, 1f))
            .build());
        stats = components.register(getId("stats"), ComponentCollection.builder(this)
                .put(getId("stats/block_power"), new ValueComponent<>(this, 0f))
                .put(getId("stats/dash_time"), new ValueComponent<>(this, 0.5f))
                .put(getId("stats/dash_speed"), new ValueComponent<>(this, 1f))
                .put(getId("stats/dash_cooldown"), new ValueComponent<>(this, 5f))
            .build());

        speed.set(256f);
        x = 160 * MainGameScreen.MAP_SCALE;
        y = 160 * MainGameScreen.MAP_SCALE;
        scale = 8f;
    }

    @Override
    public void update(float deltaTime) {
        if (playerClass != screen.game.getMetadata("selectedPlayerClass") || !updatedClass) {
            playerClass.set(screen.game.getMetadata("selectedPlayerClass"));
            textures.set(screen.game.getCachedAtlas("player_" + playerClass.get().name().toLowerCase(Locale.ROOT)).list());
            if (!updatedClass) {
                updatedClass = true;
                playerClass.get().statModifier.accept(this);
                health.set(maxHealth.get());
                mana.set(maxMana.get());
                equippedSkills.clear();
                equippedSkills.addAll(ListUtil.listOf(
                    ((SkillType) screen.game.queryRegistryOrThrow(screen.game.getId("skill_types")).getOrThrow(screen.game.getId(playerClass.get().startSkill))).getInstance(), null, null
                ));
            }
        }

        super.update(deltaTime);
        this.collisionRect.set(x + (float) width / 4 * scale, y + (float) height / 4 * scale, (float) width / 2 * scale, (float) height / 2 * scale);

        health.setLimits(0, getMaxHealth());
        mana.setLimits(0, maxMana.get());
        float manaRegen = getMultiplier("mana_regen").get();
        manaTicks += deltaTime;
        if (manaTicks >= 0.1f / manaRegen && mana.get() < maxMana.get()) {
            manaTicks = 0;
            mana.set(mana.get() + 1);
        }

        ValueComponent<Float> dashSpeed = getStat("dash_speed");
        ValueComponent<Float> dashTime = getStat("dash_time");
        ValueComponent<Float> dashCooldown = getStat("dash_cooldown");
        if (dashTicks > 0) {
            dashTicks -= deltaTime;
        } else if (!stoppedDashing) {
            stoppedDashing = true;
            speed.set2(speed.get2() - dashSpeed.get());
        }

        if (levelupTicks > 0) {
            levelupTicks -= deltaTime;
            TiledAtlas particles = screen.game.getCachedAtlas("particles");
            screen.addParticle(
                new TextureRegion[] {particles.get(0, 1), particles.get(1, 1)},
                x + (float) width / 2, y + (float) height / 2,
                new Vector4(screen.game.getRandom().nextFloat(-100, 100), screen.game.getRandom().nextFloat(-100, 100), 0, 0),
                5f, 0.1f, 1f
            );
        }

        remainingDashCooldown -= deltaTime;
        if (screen.game.getKeybind("dash").isPressed && dashTicks <= 0 && remainingDashCooldown <= 0) {
            screen.game.getAudioSample("dash").play();
            dashTicks = dashTime.get();
            speed.set2(speed.get2() + dashSpeed.get());
            stoppedDashing = false;
            remainingDashCooldown = dashCooldown.get();
        }

        if ((screen.game.getKeybind("attack_1").isPressed) && equippedSkills.get(0) != null && ticks >= 1f) {
            equippedSkills.get(0).cast(this);
        }

        if ((screen.game.getKeybind("attack_2").isPressed) && equippedSkills.get(1) != null && ticks >= 1f) {
            equippedSkills.get(1).cast(this);
        }

        if ((screen.game.getKeybind("attack_3").isPressed) && equippedSkills.get(2) != null && ticks >= 1f) {
            equippedSkills.get(2).cast(this);
        }

        float s = MainGameScreen.MAP_SCALE;
        float w = 320 * s;

        if (screen.game.getKeybind("up").isPressed) {
            moving.set(true);
            direction.set(Direction.UP);
            if (y + height * scale <= w - w / 20)
                y += speed.get() * deltaTime * speed.get2();
        }

        if (screen.game.getKeybind("down").isPressed) {
            moving.set(true);
            direction.set(Direction.DOWN);
            if (y >= w / 20)
                y -= speed.get() * deltaTime * speed.get2();
        }

        if (screen.game.getKeybind("left").isPressed) {
            moving.set(true);
            direction.set(Direction.LEFT);
            if (x >= w / 20)
                x -= speed.get() * deltaTime * speed.get2();
        }

        if (screen.game.getKeybind("right").isPressed) {
            moving.set(true);
            direction.set(Direction.RIGHT);
            if (x + width * scale <= w - w / 20)
                x += speed.get() * deltaTime * speed.get2();
        }

        if (!screen.game.getKeybind("up").isPressed
            && !screen.game.getKeybind("down").isPressed
            && !screen.game.getKeybind("left").isPressed
            && !screen.game.getKeybind("right").isPressed) {
            moving.set(false);
        }

        OrthographicCamera cam = screen.game.getCamera();
        cam.position.set(x - Gdx.graphics.getWidth() / cam.viewportWidth + (float) width / 2 * scale, y - Gdx.graphics.getHeight() / cam.viewportHeight + (float) height / 2 * scale, 0);
    }

    @Override
    public void heal(float amount) {
        super.heal(amount);
        ((GameData) screen.game.getDataSerializerOrThrow("java_game_data")).totalHealthHealed += amount;
    }

    @Override
    public void damage(float amount) {
        tryDamage(amount);
    }

    @Override
    public void onDeath() {
        ((GameData) screen.game.getDataSerializerOrThrow("java_game_data")).totalDeaths++;
        LOGGER.log("Oh noes! You died!");
        Gdx.app.exit();
    }

    @Override
    protected int getFrameCount() {
        return 2;
    }

    @Override
    protected float getFrameTime() {
        return 0.1f;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth == null ? 50 : maxHealth.get().intValue();
    }

    @Override
    protected AbstractAnimationSystem getAnimationSystem() {
        return new DirectionalAnimationSystem(this);
    }

    public int getRequiredXP() {
        return level.get2() * 10;
    }

    public float getMinimumCooldown() {
        return Math.min(equippedSkills.get(0) == null ? 0.25f : (equippedSkills.get(0).type.cooldown * getSkillCooldownMultiplier(equippedSkills.get(0).type)),
            Math.min(equippedSkills.get(1) == null ? 0.25f : (equippedSkills.get(1).type.cooldown * getSkillCooldownMultiplier(equippedSkills.get(1).type)),
                equippedSkills.get(2) == null ? 0.25f : (equippedSkills.get(2).type.cooldown * getSkillCooldownMultiplier(equippedSkills.get(2).type)))
        );
    }

    public void tryDamage(float amount) {
        iframes = 3f;
        float val = screen.game.getRandom().nextFloat();
        ValueComponent<Float> blockPower = getStat("block_power");
        GameData gameData = screen.game.getDataSerializerOrThrow("java_game_data");
        if (val <= blockPower.get() && blockPower.get() > 0) {
            screen.game.getAudioSample("block").play();
            gameData.totalDamageBlocked += amount;
        } else {
            health.set(health.get() - amount);
            screen.game.getAudioSample("player_hit").play();
            gameData.totalDamageTaken += amount;
        }
    }

    public void addSkillCooldownMultiplier(String id, float amount) {
        SkillType type = screen.game.queryRegistryOrThrow(getId("skill_types")).getOrThrow(getId(id));
        if (!skillCooldownMultipliers.containsKey(type)) {
            skillCooldownMultipliers.put(type, amount);
        } else {
            skillCooldownMultipliers.put(type, skillCooldownMultipliers.get(type) - amount);
        }
    }

    public void addSkillDamageMultiplier(String id, float amount) {
        SkillType type = screen.game.queryRegistryOrThrow(getId("skill_types")).getOrThrow(getId(id));
        if (!skillDamageMultipliers.containsKey(type)) {
            skillDamageMultipliers.put(type, amount);
        } else {
            skillDamageMultipliers.put(type, skillDamageMultipliers.get(type) + amount);
        }
    }

    public float getSkillCooldownMultiplier(SkillType type) {
        if (!skillCooldownMultipliers.containsKey(type)) {
            skillCooldownMultipliers.put(type, 1f);
            return 1f;
        } else {
            return Math.max(0.01f, skillCooldownMultipliers.get(type) * getMultiplier("global_cooldown").get());
        }
    }

    public float getSkillDamageMultiplier(SkillType type) {
        if (!skillDamageMultipliers.containsKey(type)) {
            skillDamageMultipliers.put(type, 1f);
            return 1f;
        } else {
            return Math.min(100f, skillDamageMultipliers.get(type) * getMultiplier("global_damage").get());
        }
    }

    public SkillMultiplierSet getSkillMultiplierSet(SkillType type) {
        return new SkillMultiplierSet(getSkillDamageMultiplier(type), getSkillCooldownMultiplier(type));
    }

    public void levelUp() {
        level.set2(level.get2() + 1);
        screen.game.getAudioSample("levelup").play();
        levelupTicks += 2;
        screen.pause();
        skillPoints.set(skillPoints.get() + 1);
    }

    public ValueComponent<Float> getMultiplier(String name) {
        return multipliers.get(getId("multipliers/" + name));
    }
    public void setMultiplier(String name, float value) {
        ((ValueComponent<Float>) multipliers.get(getId("multipliers/" + name))).set(value);
    }
    public ValueComponent<Float> getStat(String name) {
        return stats.get(getId("stats/" + name));
    }
    public void setStat(String name, float value) {
        ((ValueComponent<Float>) stats.get(getId("stats/" + name))).set(value);
    }
}
