package com.lordzintick.game.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.lordzintick.audio.AudioManager;
import com.lordzintick.core.Logger;
import com.lordzintick.game.AbstractGameObject;
import com.lordzintick.game.entity.effect.Effect;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.game.screen.AbstractGameScreen;
import com.lordzintick.util.Direction;

import java.util.ArrayList;

/**
 * A base abstract class from which all moving and living entities should extend from
 */
public abstract class Entity extends AbstractGameObject {
    protected final Logger LOGGER = new Logger(this.getClass());

    public float speed = 1;
    protected TextureRegion[][] textures;
    protected final ArrayList<Effect> effects = new ArrayList<>();
    private final ArrayList<Effect> effectsForRemoval = new ArrayList<>();
    public Direction direction = Direction.DOWN;
    public boolean moving = false;
    protected double animTicks = 0;
    protected double ticks = 0;
    private int frame = 0;
    public float scale = 1;
    public float health = 999;
    public float iframes = 0;
    public Color colorModifier = Color.WHITE;

    /**
     * Constructs a new {@link Entity} with the provided spritesheet, which will be split into regions of the provided size
     * @param screen The {@link AbstractGameScreen} containing this entity
     * @param texture The spritesheet for this entity to use
     * @param width The width of the entity's damage
     * @param height The height of the entity's image
     */
    public Entity(AbstractGameScreen screen, Texture texture, int width, int height) {
        super(screen);
        this.width = width;
        this.height = height;
        this.textures = TextureRegion.split(texture, width, height);
        health = getMaxHealth();
    }

    /**
     * Gets the amount of frames in the walking animation for this entity
     * @return The amount of frames in the walking animation of this entity
     */
    protected abstract int getFrameCount();

    /**
     * Defines the time in seconds that it takes for the walking animation frame to advance by 1
     * @return The time in seconds that it takes for the frame to advance
     */
    protected abstract float getFrameTime();

    public abstract int getMaxHealth();
    public void onDeath() {
        shouldRemove = true;
        textures[0][0].getTexture().dispose();
    }

    public void applyEffect(Effect effect) {
        effect.apply(this);
        effects.add(effect);
    }

    public void damage(float amount) {
        this.health -= amount;
        if (this instanceof HostileEntity) {
            if (((HostileEntity) this).getHurtSound() != null) {
                ((HostileEntity) this).getHurtSound().play();
            }
        } else if (this instanceof Player) {
            AudioManager.PLAYER_HIT.play();
        }
    }

    @Override
    public void update(float deltaTime) {
        this.collisionRect.set(x, y, width * scale, height * scale);
        ticks += deltaTime;

        if (iframes > 0) iframes -= deltaTime;

        if (health <= 0 && ticks >= 1f) {
            onDeath();
        }

        for (Effect effect : effects) {
            effect.tick(this, deltaTime);
            effect.timeLeft -= deltaTime;

            if (effect.timeLeft <= 0) {
                effect.end(this);
                effectsForRemoval.add(effect);
            }
        }

        for (Effect effect : effectsForRemoval) {
            effects.remove(effect);
        }

        effectsForRemoval.clear();
    }

    @Override
    public void render(Batch batch, float deltaTime) {
        TextureRegion texture = textures[0][0];

        animTicks += deltaTime;
        if (!moving) {
            switch (direction) {
                case DOWN:
                    break;
                case LEFT:
                    texture = textures[1][0];
                    break;
                case RIGHT:
                    texture = textures[2][0];
                    break;
                case UP:
                    texture = textures[3][0];
                    break;
            }
        } else {
            if (animTicks >= getFrameTime()) {
                animTicks = 0;
                if (frame == getFrameCount() - 1)
                    frame = 0;
                else
                    frame++;
            }

            switch (direction) {
                case DOWN:
                    texture = textures[4 + frame][0];
                    break;
                case LEFT:
                    texture = textures[4 + getFrameCount() + frame][0];
                    break;
                case RIGHT:
                    texture = textures[4 + 2 * getFrameCount() + frame][0];
                    break;
                case UP:
                    texture = textures[4 + 3 * getFrameCount() + frame][0];
                    break;
            }
        }

        batch.draw(texture, x, y, width * scale, height * scale);

        batch.setColor(colorModifier.cpy().sub(0, 0, 0, 0.5f));
        if (iframes > 0) batch.setColor(Color.RED);
        batch.draw(texture, x, y, width * scale, height * scale);
        batch.setColor(Color.WHITE);
    }
}
