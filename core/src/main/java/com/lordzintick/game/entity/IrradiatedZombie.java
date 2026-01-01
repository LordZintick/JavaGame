package com.lordzintick.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.lordzintick.audio.AudioManager;
import com.lordzintick.audio.Sound;
import com.lordzintick.game.AbstractGameObject;
import com.lordzintick.game.entity.effect.BurnEffect;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.game.screen.AbstractGameScreen;

public class IrradiatedZombie extends HostileEntity {
    /**
     * Constructs a new {@link IrradiatedZombie} with the provided spritesheet, which will be split into regions of the provided size
     *
     * @param screen  The {@link AbstractGameScreen} containing this entity
     */
    public IrradiatedZombie(AbstractGameScreen screen, Player player) {
        super(screen, new Texture("textures/game/mobs/irradiated_zombie.png"), 4, 8, player);
        scale = 11.5f;
    }

    @Override
    public void collide(AbstractGameObject other) {
        super.collide(other);
        if (other instanceof Player && ((Player) other).iframes <= 0) {
            ((Player) other).applyEffect(new BurnEffect(screen.game, 5, 1));
        }
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
        return AudioManager.MEGA_HIT;
    }

    @Override
    public Sound getDeathSound() {
        return AudioManager.MEGA_KILL;
    }

    @Override
    public int getScore() {
        return 100;
    }

    @Override
    public int getXP() {
        return 10;
    }
}
