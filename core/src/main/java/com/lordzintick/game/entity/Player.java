package com.lordzintick.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.lordzintick.MainGame;
import com.lordzintick.control.Keybinds;
import com.lordzintick.game.screen.AbstractGameScreen;
import com.lordzintick.game.spell.Spell;
import com.lordzintick.util.Direction;

import java.util.ArrayList;

public class Player extends Entity {
    public static final float SPEED = 256;
    public int maxMana = 100;
    public int mana = maxMana;
    public final Spell[] equippedSpells;
    private boolean castingSpell = false;
    private float manaTicks = 0;

    /**
     * Constructs a new {@link Player}
     * @param screen The {@link AbstractGameScreen} containing this player
     */
    public Player(AbstractGameScreen screen) {
        super(screen, new Texture(Gdx.files.internal("textures/player_vertical.png")), 16, 16);
        x = (float) Gdx.graphics.getWidth() / 2;
        y = (float) Gdx.graphics.getWidth() / 2;
        scale = 8f;
        equippedSpells = new Spell[] {null, null, null};
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        manaTicks += deltaTime;
        if (manaTicks >= 0.5f && mana < maxMana) {
            manaTicks = 0;
            mana++;
        }

        if (Keybinds.SPELL_ONE.isPressed && equippedSpells[0] != null) {
            if (!castingSpell) {
                castingSpell = true;
                equippedSpells[0].cast(this);
            }
        }

        if (Keybinds.SPELL_TWO.isPressed && equippedSpells[1] != null) {
            if (!castingSpell) {
                castingSpell = true;
                equippedSpells[1].cast(this);
            }
        }

        if (Keybinds.SPELL_THREE.isPressed && equippedSpells[2] != null) {
            if (!castingSpell) {
                castingSpell = true;
                equippedSpells[2].cast(this);
            }
        }

        if (!Keybinds.SPELL_ONE.isPressed && !Keybinds.SPELL_TWO.isPressed && !Keybinds.SPELL_THREE.isPressed) {
            castingSpell = false;
        }

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        if (Keybinds.UP.isPressed) {
            moving = true;
            direction = Direction.UP;
            if (y + height <= w - w / 16)
                y += SPEED * deltaTime;
        }

        if (Keybinds.DOWN.isPressed) {
            moving = true;
            direction = Direction.DOWN;
            if (y >= w / 32)
                y -= SPEED * deltaTime;
        }

        if (Keybinds.LEFT.isPressed) {
            moving = true;
            direction = Direction.LEFT;
            if (x >= w / 32)
                x -= SPEED * deltaTime;
        }

        if (Keybinds.RIGHT.isPressed) {
            moving = true;
            direction = Direction.RIGHT;
            if (x + width <= w - w / 10)
                x += SPEED * deltaTime;
        }

        if (!Keybinds.UP.isPressed && !Keybinds.DOWN.isPressed && !Keybinds.LEFT.isPressed && !Keybinds.RIGHT.isPressed) {
            moving = false;
        }

        OrthographicCamera cam = screen.game.camera;
        cam.position.set(x - Gdx.graphics.getWidth() / cam.viewportWidth + (float) width / 2 * scale, y - Gdx.graphics.getHeight() / cam.viewportHeight + (float) height / 2 * scale, 0);
    }

    @Override
    protected int getFrameCount() {
        return 2;
    }

    @Override
    protected float getFrameTime() {
        return 0.1f;
    }
}
