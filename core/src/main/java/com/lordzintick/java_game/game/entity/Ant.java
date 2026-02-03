package com.lordzintick.java_game.game.entity;

import com.lordzintick.pixel_krush.core.util.audio.Sound;
import com.lordzintick.pixel_krush.core.api.AbstractGameScreen;
import com.lordzintick.java_game.game.entity.player.Player;

public class Ant extends RotatingHostileEntity {
    public boolean isSpawned = true;

    /**
     * Constructs a new {@link Ant} with the provided spritesheet, which will be split into regions of the provided size
     *
     * @param screen  The {@link AbstractGameScreen} containing this entity
     */
    public Ant(AbstractGameScreen screen, Player player) {
        super(screen, screen.game.getAssetOrThrow("textures/game/mobs/ant.png"), 6, 8, player);
        scale = 7f;
    }

    @Override
    protected float getMoveSpeed() {
        return 190f;
    }

    @Override
    public Sound getHurtSound() {
        return screen.game.getAudioSample("hit");
    }

    @Override
    public Sound getDeathSound() {
        return screen.game.getAudioSample("kill");
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
