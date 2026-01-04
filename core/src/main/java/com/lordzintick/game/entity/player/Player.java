package com.lordzintick.game.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector4;
import com.lordzintick.game.accessory.Accessory;
import com.lordzintick.game.entity.Entity;
import com.lordzintick.game.entity.effect.Effect;
import com.lordzintick.game.screen.AbstractGameScreen;
import com.lordzintick.game.screen.MainGameScreen;
import com.lordzintick.game.skill.Skill;
import com.lordzintick.game.skill.SkillType;
import com.lordzintick.util.Direction;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("UnreachableCode")
public class Player extends Entity {
    public int maxMana = 100;
    public int mana = maxMana;
    public Skill[] equippedSkills;
    private float manaTicks = 0;
    public int score = 0;
    public float xp = 0;
    public int level = 1;
    private float levelupTicks = 0;
    private final TextureRegion[] levelupParticles;
    public Skill equippingSkill = null;
    public int skillPoints = 0;
    public PlayerClass playerClass;
    private boolean updatedClass = false;
    public int maxHealth = 50;
    public final ArrayList<Accessory> accessories = new ArrayList<>();
    private final HashMap<SkillType, Float> skillCooldownMultipliers = new HashMap<>();
    private final HashMap<SkillType, Float> skillDamageMultipliers = new HashMap<>();
    public float globalCooldownMultiplier = 1;
    public float globalDamageMultiplier = 1;
    public float blockPower = 0;
    public float manaRegenMultiplier = 1;
    public float xpMultiplier = 1;
    public float dashTime = 0.5f;
    public float dashSpeed = 1f;
    private float dashTicks = 0;
    private boolean stoppedDashing = true;
    public float dashCooldown = 5f;
    public float remainingDashCooldown = 0;

    /**
     * @param screen The {@link AbstractGameScreen} containing this player
     */
    public Player(AbstractGameScreen screen, PlayerClass playerClass) {
        super(screen, screen.game.playerTextures.get(playerClass)[0][0].getTexture(), 6, 12);
        this.playerClass = playerClass;
        levelupParticles = new TextureRegion[] {screen.game.particlesAtlas[1][0], screen.game.particlesAtlas[1][1]};
        this.speed = 256;
        x = (float) Gdx.graphics.getWidth() / 2;
        y = (float) Gdx.graphics.getWidth() / 2;
        scale = 8f;
        equippedSkills = new Skill[] {screen.game.skillTypes.SKILL_TYPES.get(playerClass.startSkill).getInstance(), null, null};
    }

    @Override
    public void update(float deltaTime) {
        if (playerClass != screen.game.selectedPlayerClass || !updatedClass) {
            playerClass = screen.game.selectedPlayerClass;
            textures = screen.game.playerTextures.get(playerClass);
            if (!updatedClass) {
                updatedClass = true;
                playerClass.statModifier.accept(this);
                health = maxHealth;
                mana = maxMana;
                equippedSkills = new Skill[] {screen.game.skillTypes.SKILL_TYPES.get(playerClass.startSkill).getInstance(), null, null};
                for (int i = 0; i < equippedSkills.length; i++) {
                    ((MainGameScreen) screen).skillSlots[i].slottedSkill = equippedSkills[i];
                }
            }
        }

        super.update(deltaTime);
        this.collisionRect.set(x + (float) width / 4 * scale, y + (float) height / 4 * scale, (float) width / 2 * scale, (float) height / 2 * scale);

        manaTicks += deltaTime;
        if (manaTicks >= 0.1f * manaRegenMultiplier && mana < maxMana) {
            manaTicks = 0;
            mana++;
        }

        if (dashTicks > 0) {
            dashTicks -= deltaTime;
        } else if (!stoppedDashing) {
            stoppedDashing = true;
            speedMultiplier -= dashSpeed;
        }

        if (levelupTicks > 0) {
            levelupTicks -= deltaTime;
            screen.addParticle(
                levelupParticles,
                x + (float) width / 2, y + (float) height / 2,
                new Vector4(screen.game.random.nextFloat(-100, 100), screen.game.random.nextFloat(-100, 100), 0, 0),
                5f, 0.1f, 1f
            );
        }

        remainingDashCooldown -= deltaTime;
        if (screen.game.keybinds.DASH.isPressed && dashTicks <= 0 && remainingDashCooldown <= 0) {
            screen.game.audio.get("dash").play();
            dashTicks = dashTime;
            speedMultiplier += dashSpeed;
            stoppedDashing = false;
            remainingDashCooldown = dashCooldown;
        }

        if ((screen.game.input.mouseButtonsPressed[0] || screen.game.keybinds.ATTACK_1.isPressed) && equippedSkills[0] != null && ticks >= 1f) {
            equippedSkills[0].cast(this);
        }

        if ((screen.game.input.mouseButtonsPressed[2] || screen.game.keybinds.ATTACK_2.isPressed) && equippedSkills[1] != null && ticks >= 1f) {
            equippedSkills[1].cast(this);
        }

        if ((screen.game.input.mouseButtonsPressed[1] || screen.game.keybinds.ATTACK_3.isPressed) && equippedSkills[2] != null && ticks >= 1f) {
            equippedSkills[2].cast(this);
        }

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        if (screen.game.keybinds.UP.isPressed || screen.game.keybinds.ARROW_UP.isPressed || screen.game.keybinds.GAMEPAD_UP.isPressed) {
            moving = true;
            direction = Direction.UP;
            if (y + height <= w - w / 16)
                y += speed * deltaTime * speedMultiplier;
        }

        if (screen.game.keybinds.DOWN.isPressed || screen.game.keybinds.ARROW_DOWN.isPressed || screen.game.keybinds.GAMEPAD_DOWN.isPressed) {
            moving = true;
            direction = Direction.DOWN;
            if (y >= w / 32)
                y -= speed * deltaTime * speedMultiplier;
        }

        if (screen.game.keybinds.LEFT.isPressed || screen.game.keybinds.ARROW_LEFT.isPressed || screen.game.keybinds.GAMEPAD_LEFT.isPressed) {
            moving = true;
            direction = Direction.LEFT;
            if (x >= w / 32)
                x -= speed * deltaTime * speedMultiplier;
        }

        if (screen.game.keybinds.RIGHT.isPressed || screen.game.keybinds.ARROW_RIGHT.isPressed || screen.game.keybinds.GAMEPAD_RIGHT.isPressed) {
            moving = true;
            direction = Direction.RIGHT;
            if (x + width <= w - w / 10)
                x += speed * deltaTime * speedMultiplier;
        }

        if (!screen.game.keybinds.UP.isPressed
            && !screen.game.keybinds.ARROW_UP.isPressed
            && !screen.game.keybinds.GAMEPAD_UP.isPressed
            && !screen.game.keybinds.DOWN.isPressed
            && !screen.game.keybinds.ARROW_DOWN.isPressed
            && !screen.game.keybinds.GAMEPAD_DOWN.isPressed
            && !screen.game.keybinds.LEFT.isPressed
            && !screen.game.keybinds.ARROW_LEFT.isPressed
            && !screen.game.keybinds.GAMEPAD_LEFT.isPressed
            && !screen.game.keybinds.RIGHT.isPressed
            && !screen.game.keybinds.ARROW_RIGHT.isPressed
            && !screen.game.keybinds.GAMEPAD_RIGHT.isPressed) {
            moving = false;
        }

        OrthographicCamera cam = screen.game.camera;
        cam.position.set(x - Gdx.graphics.getWidth() / cam.viewportWidth + (float) width / 2 * scale, y - Gdx.graphics.getHeight() / cam.viewportHeight + (float) height / 2 * scale, 0);
    }

    @Override
    public void render(Batch batch, float deltaTime) {
        super.render(batch, deltaTime);
        for (int i = 0; i < effects.size(); i++) {
            Effect effect = effects.get(i);
            batch.draw(effect.sprite, x + (float) width / 2 * scale - 16, y + height * scale + 10 + i * 42, 32, 32);
        }
    }

    @Override
    public void heal(float amount) {
        super.heal(amount);
        screen.game.gameData.totalHealthHealed += amount;
    }

    @Override
    public void damage(float amount) {
        tryDamage(amount);
    }

    @Override
    public void onDeath() {
        screen.game.gameData.totalDeaths++;
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
        return maxHealth;
    }

    public int getRequiredXP() {
        return level * 10;
    }

    public float getMinimumCooldown() {
        return Math.min(equippedSkills[0] == null ? 0.25f : (equippedSkills[0].type.cooldown * getSkillCooldownMultiplier(equippedSkills[0].type)),
            Math.min(equippedSkills[1] == null ? 0.25f : (equippedSkills[1].type.cooldown * getSkillCooldownMultiplier(equippedSkills[1].type)),
                equippedSkills[2] == null ? 0.25f : (equippedSkills[2].type.cooldown * getSkillCooldownMultiplier(equippedSkills[2].type)))
        );
    }

    public void tryDamage(float amount) {
        iframes = 3f;
        float val = screen.game.random.nextFloat();
        if (val <= blockPower && blockPower > 0) {
            screen.game.audio.get("block").play();
            screen.game.gameData.totalDamageBlocked += amount;
        } else {
            health -= amount;
            screen.game.audio.get("player_hit").play();
            screen.game.gameData.totalDamageTaken += amount;
        }
    }

    public void addSkillCooldownMultiplier(String id, float amount) {
        SkillType type = screen.game.skillTypes.SKILL_TYPES.get(id);
        if (!skillCooldownMultipliers.containsKey(type)) {
            skillCooldownMultipliers.put(type, amount);
        } else {
            skillCooldownMultipliers.put(type, skillCooldownMultipliers.get(type) - amount);
        }
    }

    public void addSkillDamageMultiplier(String id, float amount) {
        SkillType type = screen.game.skillTypes.SKILL_TYPES.get(id);
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
            return Math.max(0.1f, skillCooldownMultipliers.get(type) * globalCooldownMultiplier);
        }
    }

    public float getSkillDamageMultiplier(SkillType type) {
        if (!skillDamageMultipliers.containsKey(type)) {
            skillDamageMultipliers.put(type, 1f);
            return 1f;
        } else {
            return Math.min(10f, skillDamageMultipliers.get(type) * globalDamageMultiplier);
        }
    }

    public void levelUp() {
        level++;
        screen.game.audio.get("levelup").play();
        levelupTicks += 2;
        screen.pause();
        skillPoints++;
    }
}
