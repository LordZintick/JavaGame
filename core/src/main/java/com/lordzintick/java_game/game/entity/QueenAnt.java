package com.lordzintick.java_game.game.entity;

import com.lordzintick.pixel_krush.core.util.audio.Sound;
import com.lordzintick.pixel_krush.core.api.AbstractGameScreen;
import com.lordzintick.java_game.game.entity.player.Player;

public class QueenAnt extends RotatingHostileEntity {
    /**
     * Constructs a new {@link QueenAnt} with the provided spritesheet, which will be split into regions of the provided size
     *
     * @param screen  The {@link AbstractGameScreen} containing this entity
     */
    public QueenAnt(AbstractGameScreen screen, Player player) {
        super(screen, screen.game.getAssetOrThrow("textures/game/mobs/queen_ant.png"), 6, 9, player);
        scale = 10f;
    }

    @Override
    public void damage(float amount, boolean noImmunity) {
        super.damage(amount, noImmunity);
        Ant minion = new Ant(screen, player);
        minion.x = x;
        minion.y = y;
        minion.isSpawned = false;
        screen.objects.add(minion);
    }

    @Override
    protected float getMoveSpeed() {
        return 170f;
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
