package com.github.lordzintick.java_game.game.entity;

import com.github.lordzintick.pixel_krush.core.util.audio.Sound;
import com.github.lordzintick.pixel_krush.core.api.AbstractGameObject;
import com.github.lordzintick.pixel_krush.core.api.AbstractGameScreen;
import com.github.lordzintick.java_game.game.entity.effect.SlowEffect;
import com.github.lordzintick.java_game.game.entity.player.Player;

public class FrostWalker extends HostileEntity {
    /**
     * Constructs a new {@link FrostWalker} with the provided spritesheet, which will be split into regions of the provided size
     *
     * @param screen  The {@link AbstractGameScreen} containing this entity
     */
    public FrostWalker(AbstractGameScreen screen, Player player) {
        super(screen, screen.game.getAssetOrThrow("textures/game/mobs/frost_walker.png"), 4, 8, player);
        scale = 8f;
    }

    @Override
    public void collide(AbstractGameObject other) {
        if (other instanceof Player && ((Player) other).iframes <= 0) {
            ((Player) other).applyEffect(new SlowEffect(screen.game, 10, 1));
        }
        super.collide(other);
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
        return 12;
    }

    @Override
    protected float getMoveSpeed() {
        return 170f;
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
        return 18;
    }

    @Override
    public int getXP() {
        return 4;
    }
}
