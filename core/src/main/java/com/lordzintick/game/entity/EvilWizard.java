package com.lordzintick.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.lordzintick.audio.AudioManager;
import com.lordzintick.audio.Sound;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.game.proj.Projectile;
import com.lordzintick.game.skill.SkillConfig;
import com.lordzintick.game.skill.SkillHelper;
import com.lordzintick.game.screen.AbstractGameScreen;

import java.util.ArrayList;
import java.util.Optional;

public class EvilWizard extends HostileEntity {
    private float cooldown = 3f;
    private final Texture projTexture;
    private final ArrayList<Projectile> ownedProjectiles = new ArrayList<>();
    /**
     * Constructs a new {@link Entity} with the provided spritesheet, which will be split into regions of the provided size
     *
     * @param screen  The {@link AbstractGameScreen} containing this entity
     * @param player
     */
    public EvilWizard(AbstractGameScreen screen, Player player) {
        super(screen, new Texture("textures/game/mobs/evil_wizard.png"), 6, 12, player);
        this.scale = 8f;
        this.projTexture = createTexture("textures/game/skills/plasma_bolt.png");
    }

    @Override
    public void onDeath() {
        super.onDeath();
        for (Projectile projectile : ownedProjectiles) {
            projectile.shouldRemove = true;
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (cooldown > 0) cooldown -= deltaTime;

        if (cooldown <= 0) {
            cooldown = 3f;
            ownedProjectiles.add(SkillHelper.shootProjectile(projTexture, this, Optional.empty(), SkillConfig.PLASMA_BOLT));
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
    public Sound getDeathSound() {
        return AudioManager.KILL;
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
