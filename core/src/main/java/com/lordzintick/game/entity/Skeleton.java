package com.lordzintick.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.lordzintick.audio.AudioManager;
import com.lordzintick.audio.Sound;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.game.screen.AbstractGameScreen;

public class Skeleton extends HostileEntity {
    /**
     * Constructs a new {@link Skeleton} with the provided spritesheet, which will be split into regions of the provided size
     *
     * @param screen  The {@link AbstractGameScreen} containing this entity
     */
    public Skeleton(AbstractGameScreen screen, Player player) {
        super(screen, screen.game.assets.get("textures/game/mobs/skeleton.png"), 4, 8, player);
        scale = 8f;
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
        return 10;
    }

    @Override
    protected float getMoveSpeed() {
        return 200f;
    }

    @Override
    public Sound getHurtSound() {
        return screen.game.audio.get("hit");
    }

    @Override
    public Sound getDeathSound() {
        return screen.game.audio.get("kill");
    }

    @Override
    public int getScore() {
        return 15;
    }

    @Override
    public int getXP() {
        return 4;
    }
}
