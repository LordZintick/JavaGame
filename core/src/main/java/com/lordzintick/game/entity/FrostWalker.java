package com.lordzintick.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.lordzintick.audio.AudioManager;
import com.lordzintick.audio.Sound;
import com.lordzintick.game.AbstractGameObject;
import com.lordzintick.game.entity.effect.SlowEffect;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.game.screen.AbstractGameScreen;

public class FrostWalker extends HostileEntity {
    /**
     * Constructs a new {@link FrostWalker} with the provided spritesheet, which will be split into regions of the provided size
     *
     * @param screen  The {@link AbstractGameScreen} containing this entity
     */
    public FrostWalker(AbstractGameScreen screen, Player player) {
        super(screen, new Texture("textures/game/mobs/frost_walker.png"), 4, 8, player);
        scale = 8f;
    }

    @Override
    public void collide(AbstractGameObject other) {
        super.collide(other);
        if (other instanceof Player && ((Player) other).iframes <= 0) {
            ((Player) other).applyEffect(new SlowEffect(screen.game, 10, 1));
        }
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
        return 200f;
    }

    @Override
    public Sound getHurtSound() {
        return AudioManager.HIT;
    }

    @Override
    public Sound getDeathSound() {
        return AudioManager.KILL;
    }

    @Override
    public int getScore() {
        return 18;
    }

    @Override
    public int getXP() {
        return 2;
    }
}
