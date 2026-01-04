package com.lordzintick.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.lordzintick.audio.Sound;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.game.screen.AbstractGameScreen;

public class Ant extends RotatingHostileEntity {
    public boolean isSpawned = true;

    /**
     * Constructs a new {@link Entity} with the provided spritesheet, which will be split into regions of the provided size
     *
     * @param screen  The {@link AbstractGameScreen} containing this entity
     */
    public Ant(AbstractGameScreen screen, Player player) {
        super(screen, screen.game.assets.get("textures/game/mobs/ant.png"), 6, 8, player);
        scale = 7f;
    }

    @Override
    protected float getMoveSpeed() {
        return 190f;
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
        return isSpawned ? 10 : 2;
    }

    @Override
    public int getXP() {
        return 1;
    }

    @Override
    protected int getFrameCount() {
        return 2;
    }

    @Override
    protected float getFrameTime() {
        return 0.25f;
    }

    @Override
    public int getMaxHealth() {
        return isSpawned ? 8 : 5;
    }
}
