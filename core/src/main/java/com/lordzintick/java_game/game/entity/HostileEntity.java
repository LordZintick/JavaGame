package com.lordzintick.java_game.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.lordzintick.java_game.GameData;
import com.lordzintick.pixel_krush.core.api.TiledAtlas;
import com.lordzintick.pixel_krush.core.util.audio.Sound;
import com.lordzintick.pixel_krush.core.api.AbstractGameObject;
import com.lordzintick.pixel_krush.core.api.AbstractGameScreen;
import com.lordzintick.pixel_krush.core.api.ecs.Entity;
import com.lordzintick.pixel_krush.core.api.ecs.LivingEntity;
import com.lordzintick.pixel_krush.core.api.ecs.sys.anim.AbstractAnimationSystem;
import com.lordzintick.pixel_krush.core.api.ecs.sys.anim.DirectionalAnimationSystem;
import com.lordzintick.pixel_krush.core.util.Direction;
import com.lordzintick.java_game.game.entity.player.Player;

public abstract class HostileEntity extends LivingEntity {
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
        this.speed.set(getMoveSpeed());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        moveVector = new Vector2(player.x, player.y).sub(x, y).nor();
        x += moveVector.x * deltaTime * speed.get() * speed.get2();
        y += moveVector.y * deltaTime * speed.get() * speed.get2();

        moving.set(moveVector.len() > 0);

        if (moveVector.y > 0) {
            direction.set(Direction.UP);
        } else if (moveVector.y < 0) {
            direction.set(Direction.DOWN);
        }

        if (moveVector.x > 0) {
            direction.set(Direction.RIGHT);
        } else if (moveVector.x < 0) {
            direction.set(Direction.LEFT);
        }
    }

    @Override
    public void onDeath() {
        super.onDeath();
        player.level.set((int) (player.level.get() + getXP() * player.getMultiplier("xp").get()));
        player.score.set(player.score.get() + getScore());
        while (player.level.get() >= player.getRequiredXP()) {
            player.level.set(player.level.get() - player.getRequiredXP());
            player.levelUp();
        }

        Sound sound = getDeathSound();
        if (sound != null && !sound.stream) {
            sound.play();
        }
        ((GameData) screen.game.getDataSerializerOrThrow("java_game_data")).totalEnemiesKilled++;
    }

    @Override
    public void damage(float amount) {
        this.damage(amount, false);
    }

    @Override
    public void damage(float amount, boolean noImmunity) {
        super.damage(amount, noImmunity);
        if (!noImmunity)
            iframes = Math.min(0.25f, player.getMinimumCooldown());

        if (getHurtSound() != null) {
            getHurtSound().play();
        }
        ((GameData) screen.game.getDataSerializerOrThrow("java_game_data")).totalDamageDone += amount;
    }

    @Override
    public void collide(AbstractGameObject other) {
        if (other instanceof Player && ((LivingEntity) other).iframes <= 0) {
            remove();
            ((LivingEntity) other).damage(damage);
        }
    }

    @Override
    protected AbstractAnimationSystem getAnimationSystem() {
        return new DirectionalAnimationSystem(this);
    }

    protected abstract float getMoveSpeed();
    public abstract Sound getHurtSound();
    public Sound getDeathSound() {
        return getHurtSound();
    }
    public abstract int getScore();
    public abstract int getXP();
}
