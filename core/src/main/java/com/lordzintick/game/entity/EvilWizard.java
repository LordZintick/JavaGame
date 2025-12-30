package com.lordzintick.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector4;
import com.lordzintick.audio.AudioManager;
import com.lordzintick.audio.Sound;
import com.lordzintick.game.proj.ProjectileHelper;
import com.lordzintick.game.screen.AbstractGameScreen;

import java.util.Random;

public class EvilWizard extends HostileEntity {
    private float cooldown = 3f;
    private final Texture projTexture;
    /**
     * Constructs a new {@link Entity} with the provided spritesheet, which will be split into regions of the provided size
     *
     * @param screen  The {@link AbstractGameScreen} containing this entity
     * @param player
     */
    public EvilWizard(AbstractGameScreen screen, Player player) {
        super(screen, new Texture("textures/evil_wizard.png"), 6, 16, player);
        this.scale = 8f;
        this.projTexture = createTexture("textures/plasma_bolt.png");
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (cooldown > 0) cooldown -= deltaTime;

        if (cooldown <= 0) {
            cooldown = 3f;
            ProjectileHelper.shootProjectile(projTexture, this, ProjectileHelper.ProjConfig.PLASMA_BOLT);
        }
    }

    @Override
    protected float getMoveSpeed() {
        return 100f;
    }

    @Override
    public Sound getHurtSound() {
        return AudioManager.HIT;
    }

    @Override
    public int getScore() {
        return 25;
    }

    @Override
    public int getXP() {
        return 2;
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
        return 8;
    }
}
