package com.lordzintick.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.lordzintick.audio.Sound;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.game.screen.AbstractGameScreen;

public class QueenAnt extends RotatingHostileEntity {
    /**
     * Constructs a new {@link Entity} with the provided spritesheet, which will be split into regions of the provided size
     *
     * @param screen  The {@link AbstractGameScreen} containing this entity
     */
    public QueenAnt(AbstractGameScreen screen, Player player) {
        super(screen, screen.game.assets.get("textures/game/mobs/queen_ant.png"), 6, 9, player);
        scale = 10f;
    }

    @Override
    public void damage(float amount, boolean noImmunity) {
        super.damage(amount, noImmunity);
        Ant minion = new Ant(screen, player);
        minion.x = x;
        minion.y = y;
        minion.isSpawned = false;
        screen.queueAddObject(minion);
    }

    @Override
    protected float getMoveSpeed() {
        return 170f;
    }

    @Override
    public Sound getHurtSound() {
        return screen.game.audio.get("mega_hit");
    }

    @Override
    public Sound getDeathSound() {
        return screen.game.audio.get("mega_kill");
    }

    @Override
    public int getScore() {
        return 50;
    }

    @Override
    public int getXP() {
        return 10;
    }

    @Override
    protected int getFrameCount() {
        return 2;
    }

    @Override
    protected float getFrameTime() {
        return 0.5f;
    }

    @Override
    public int getMaxHealth() {
        return 35;
    }
}
