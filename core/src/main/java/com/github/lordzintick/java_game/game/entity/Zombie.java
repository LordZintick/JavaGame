package com.github.lordzintick.java_game.game.entity;

import com.github.lordzintick.pixel_krush.core.util.audio.Sound;
import com.github.lordzintick.pixel_krush.core.api.AbstractGameScreen;
import com.github.lordzintick.java_game.game.entity.player.Player;

public class Zombie extends HostileEntity {
    /**
     * Constructs a new {@link Zombie} with the provided spritesheet, which will be split into regions of the provided size
     *
     * @param screen  The {@link AbstractGameScreen} containing this entity
     */
    public Zombie(AbstractGameScreen screen, Player player) {
        super(screen, screen.game.getAssetOrThrow("textures/game/mobs/zombie.png"), 4, 8, player);
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
        return 5;
    }

    @Override
    protected float getMoveSpeed() {
        return 180f;
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
        return 10;
    }

    @Override
    public int getXP() {
        return 2;
    }
}
