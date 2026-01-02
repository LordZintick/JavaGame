package com.lordzintick.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.lordzintick.audio.AudioManager;
import com.lordzintick.audio.Sound;
import com.lordzintick.game.AbstractGameObject;
import com.lordzintick.game.entity.effect.Effect;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.game.screen.AbstractGameScreen;
import com.lordzintick.util.Direction;

import java.util.ArrayList;

public abstract class HostileEntity extends Entity {
    protected Vector2 moveVector = Vector2.Zero;
    public final Player player;
    public float damage = 1;

    /**
     * Constructs a new {@link Entity} with the provided spritesheet, which will be split into regions of the provided size
     *
     * @param screen  The {@link AbstractGameScreen} containing this entity
     * @param texture The spritesheet for this entity to use
     * @param width   The width of the entity's image
     * @param height  The height of the entity's image
     */
    public HostileEntity(AbstractGameScreen screen, Texture texture, int width, int height, Player player) {
        super(screen, texture, width, height);
        this.player = player;
        this.speed = getMoveSpeed();
    }

    @Override
    public void render(Batch batch, float deltaTime) {
        super.render(batch, deltaTime);
        for (int i = 0; i < effects.size(); i++) {
            Effect effect = effects.get(i);
            batch.draw(effect.sprite, x + (float) width / 2 * scale - 8, y + height * scale + 10 + i * 26, 16, 16);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        moveVector = new Vector2(player.x, player.y).sub(x, y).nor();
        x += moveVector.x * deltaTime * speed;
        y += moveVector.y * deltaTime * speed;

        moving = moveVector.len() > 0;

        if (moveVector.y > 0) {
            direction = Direction.UP;
        } else if (moveVector.y < 0) {
            direction = Direction.DOWN;
        }

        if (moveVector.x > 0) {
            direction = Direction.RIGHT;
        } else if (moveVector.x < 0) {
            direction = Direction.LEFT;
        }
    }

    @Override
    public void onDeath() {
        super.onDeath();
        player.xp += getXP() * player.xpMultiplier;
        player.score += getScore();
        while (player.xp >= player.getRequiredXP()) {
            player.xp -= player.getRequiredXP();
            player.levelUp();
        }

        Sound sound = getDeathSound();
        if (sound != null && !sound.stream) {
            sound.play();
        }
    }

    @Override
    public void collide(AbstractGameObject other) {
        if (other instanceof Player && ((Player) other).iframes <= 0) {
            this.shouldRemove = true;
            ((Player) other).iframes = 3f;
            ((Player) other).tryDamage(damage);
        }
    }

    protected abstract float getMoveSpeed();
    public abstract Sound getHurtSound();
    public Sound getDeathSound() {
        return getHurtSound();
    }
    public abstract int getScore();
    public abstract int getXP();
}
