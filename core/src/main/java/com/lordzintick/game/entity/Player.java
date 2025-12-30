package com.lordzintick.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector4;
import com.lordzintick.audio.AudioManager;
import com.lordzintick.control.Keybinds;
import com.lordzintick.game.proj.Projectile;
import com.lordzintick.game.proj.StationaryProjectile;
import com.lordzintick.game.screen.AbstractGameScreen;
import com.lordzintick.game.spell.Spell;
import com.lordzintick.game.spell.Spells;
import com.lordzintick.util.Direction;
import com.lordzintick.util.MathUtil;

import java.util.function.BiConsumer;

public class Player extends Entity {
    public int maxMana = 100;
    public int mana = maxMana;
    public final Spell[] equippedSpells;
    private float manaTicks = 0;
    public int score = 0;
    public int xp = 0;
    public int level = 1;
    private float levelupTicks = 0;
    private final TextureRegion[] levelupParticles;
    public Spell equippingSpell = null;
    public int skillPoints = 0;

    /**
     * Constructs a new {@link Player}
     * @param screen The {@link AbstractGameScreen} containing this player
     */
    public Player(AbstractGameScreen screen) {
        super(screen, new Texture(Gdx.files.internal("textures/player.png")), 6, 16);
        TextureRegion[] splitParticles = TextureRegion.split(new Texture("textures/particles.png"), 2, 2)[1];
        levelupParticles = new TextureRegion[] {splitParticles[0], splitParticles[1]};
        this.speed = 256;
        x = (float) Gdx.graphics.getWidth() / 2;
        y = (float) Gdx.graphics.getWidth() / 2;
        scale = 8f;
        equippedSpells = new Spell[] {screen.game.spells.SPELLS.get("fireball"), null, null};
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        manaTicks += deltaTime;
        if (manaTicks >= 0.1f && mana < maxMana) {
            manaTicks = 0;
            mana++;
        }

        if (levelupTicks > 0) {
            levelupTicks -= deltaTime;
            screen.addParticle(
                levelupParticles,
                x + (float) width / 2, y + (float) height / 2,
                new Vector4(screen.game.random.nextFloat(-100, 100), screen.game.random.nextFloat(-100, 100), 0, 0),
                5f, 0.1f, 3f
            );
        }

        if (screen.game.input.mouseButtonsPressed[0] && equippedSpells[0] != null && ticks >= 1f) {
            equippedSpells[0].cast(this);
        }

        if (screen.game.input.mouseButtonsPressed[2] && equippedSpells[1] != null && ticks >= 1f) {
            equippedSpells[1].cast(this);
        }

        if (screen.game.input.mouseButtonsPressed[1] && equippedSpells[2] != null && ticks >= 1f) {
            equippedSpells[2].cast(this);
        }

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        if (Keybinds.UP.isPressed) {
            moving = true;
            direction = Direction.UP;
            if (y + height <= w - w / 16)
                y += speed * deltaTime;
        }

        if (Keybinds.DOWN.isPressed) {
            moving = true;
            direction = Direction.DOWN;
            if (y >= w / 32)
                y -= speed * deltaTime;
        }

        if (Keybinds.LEFT.isPressed) {
            moving = true;
            direction = Direction.LEFT;
            if (x >= w / 32)
                x -= speed * deltaTime;
        }

        if (Keybinds.RIGHT.isPressed) {
            moving = true;
            direction = Direction.RIGHT;
            if (x + width <= w - w / 10)
                x += speed * deltaTime;
        }

        if (!Keybinds.UP.isPressed && !Keybinds.DOWN.isPressed && !Keybinds.LEFT.isPressed && !Keybinds.RIGHT.isPressed) {
            moving = false;
        }

        OrthographicCamera cam = screen.game.camera;
        cam.position.set(x - Gdx.graphics.getWidth() / cam.viewportWidth + (float) width / 2 * scale, y - Gdx.graphics.getHeight() / cam.viewportHeight + (float) height / 2 * scale, 0);
    }

    @Override
    public void onDeath() {
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
        return 20;
    }

    public int getRequiredXP() {
        return level * 10;
    }

    public void levelUp() {
        level++;
        AudioManager.LEVELUP.play();
        levelupTicks += 2;
        screen.pause();
        skillPoints++;

    }
}
