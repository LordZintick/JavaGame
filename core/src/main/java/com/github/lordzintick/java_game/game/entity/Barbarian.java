package com.github.lordzintick.java_game.game.entity;

import com.github.lordzintick.pixel_krush.core.util.audio.Sound;
import com.github.lordzintick.pixel_krush.core.api.AbstractGameScreen;
import com.github.lordzintick.java_game.game.entity.effect.RageEffect;
import com.github.lordzintick.java_game.game.entity.player.Player;

public class Barbarian extends HostileEntity {
    private final RageEffect rage;
    /**
     * Constructs a new {@link Barbarian} with the provided spritesheet, which will be split into regions of the provided size
     *
     * @param screen  The {@link AbstractGameScreen} containing this entity
     * @param player
     */
    public Barbarian(AbstractGameScreen screen, Player player) {
        super(screen, screen.game.getAssetOrThrow("textures/game/mobs/barbarian.png"), 6, 12, player);
        scale = 8f;
        rage = new RageEffect(screen.game, 999, 1);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (!hasEffect(rage)) {
            hasEffect(rage);
        }
    }

    @Override
    protected float getMoveSpeed() {
        return 240f;
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
        return 15;
    }

    @Override
    public int getXP() {
        return 4;
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
        return 15;
    }
}
