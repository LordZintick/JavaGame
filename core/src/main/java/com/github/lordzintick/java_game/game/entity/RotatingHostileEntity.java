package com.github.lordzintick.java_game.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.github.lordzintick.pixel_krush.core.api.AbstractGameScreen;
import com.github.lordzintick.pixel_krush.core.api.ecs.sys.anim.AbstractAnimationSystem;
import com.github.lordzintick.pixel_krush.core.api.ecs.sys.anim.SimpleAnimationSystem;
import com.github.lordzintick.java_game.game.entity.player.Player;

public abstract class RotatingHostileEntity extends HostileEntity {
    /**
     * Constructs a new {@link RotatingHostileEntity} with the provided spritesheet, which will be split into regions of the provided size
     *
     * @param screen  The {@link AbstractGameScreen} containing this entity
     * @param texture The spritesheet for this entity to use
     * @param width   The width of the entity's image
     * @param height  The height of the entity's image
     */
    public RotatingHostileEntity(AbstractGameScreen screen, Texture texture, int width, int height, Player player) {
        super(screen, texture, width, height, player);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        angle.set(moveVector.angleDeg() + 90);
    }

    @Override
    protected AbstractAnimationSystem getAnimationSystem() {
        return new SimpleAnimationSystem(this);
    }
}
