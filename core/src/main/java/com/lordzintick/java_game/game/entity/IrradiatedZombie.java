package com.lordzintick.java_game.game.entity;

import com.lordzintick.pixel_krush.core.util.audio.Sound;
import com.lordzintick.pixel_krush.core.api.AbstractGameObject;
import com.lordzintick.pixel_krush.core.api.AbstractGameScreen;
import com.lordzintick.java_game.game.entity.effect.BurnEffect;
import com.lordzintick.java_game.game.entity.player.Player;

public class IrradiatedZombie extends HostileEntity {
    /**
     * Constructs a new {@link IrradiatedZombie} with the provided spritesheet, which will be split into regions of the provided size
     *
     * @param screen  The {@link AbstractGameScreen} containing this entity
     */
    public IrradiatedZombie(AbstractGameScreen screen, Player player) {
        super(screen, screen.game.getAssetOrThrow("textures/game/mobs/irradiated_zombie.png"), 4, 8, player);
        scale = 11.5f;
    }

    @Override
    public void collide(AbstractGameObject other) {
        if (other instanceof Player && ((Player) other).iframes <= 0) {
            ((Player) other).applyEffect(new BurnEffect(screen.game, 5, 1));
        }
        super.collide(other);
    }

    @Override
    protected int getFrameCount() {
        return 2;
    }

    @Override
    protected float getFrameTime() {
        return 0.2f;
    }

    @Override
    public int getMaxHealth() {
        return 50;
    }

    @Override
    protected float getMoveSpeed() {
        return 160f;
    }

    @Override
    public Sound getHurtSound() {
        return screen.game.getAudioSample("mega_hit");
    }

    @Override
    public Sound getDeathSound() {
        return screen.game.getAudioSample("mega_kill");
    }

    @Override
    public int getScore() {
        return 100;
    }

    @Override
    public int getXP() {
        return 25;
    }
}
