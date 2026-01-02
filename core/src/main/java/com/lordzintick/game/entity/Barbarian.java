package com.lordzintick.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.lordzintick.audio.Sound;
import com.lordzintick.game.entity.effect.RageEffect;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.game.screen.AbstractGameScreen;

public class Barbarian extends HostileEntity {
    private final RageEffect rage;
    /**
     * Constructs a new {@link Entity} with the provided spritesheet, which will be split into regions of the provided size
     *
     * @param screen  The {@link AbstractGameScreen} containing this entity
     * @param player
     */
    public Barbarian(AbstractGameScreen screen, Player player) {
        super(screen, screen.game.assets.get("textures/game/mobs/barbarian.png"), 6, 12, player);
        scale = 8f;
        rage = new RageEffect(screen.game, 10, 1);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (!effects.contains(rage)) {
            effects.add(rage);
        }
    }

    @Override
    protected float getMoveSpeed() {
        return 150f;
    }

    @Override
    public Sound getHurtSound() {
        return screen.game.audio.HIT;
    }

    @Override
    public Sound getDeathSound() {
        return screen.game.audio.KILL;
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
