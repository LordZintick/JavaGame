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
    private final String projTexture;
    private SkillConfig projConfig;
    private final ArrayList<Projectile> ownedProjectiles = new ArrayList<>();
    /**
     * Constructs a new {@link Entity} with the provided spritesheet, which will be split into regions of the provided size
     *
     * @param screen  The {@link AbstractGameScreen} containing this entity
     */
    public EvilWizard(AbstractGameScreen screen, Player player) {
        super(screen, screen.game.assets.get("textures/game/mobs/evil_wizard.png"), 6, 12, player);
        this.scale = 8f;
        String textureName = "plasma_bolt";
        int val = screen.game.random.nextInt(7);

        switch (val) {
            case 0: {
                textureName = "fireball";
                projConfig = SkillConfig.FIREBALL;
                break;
            }
            case 1: {
                textureName = "iceball";
                projConfig = SkillConfig.ICEBALL;
                break;
            }
            case 2: {
                textureName = "acidball";
                projConfig = SkillConfig.ACIDBALL;
                break;
            }
            case 3: {
                textureName = "waterball";
                projConfig = SkillConfig.WATERBALL;
                break;
            }
            case 4: {
                textureName = "lightningball";
                projConfig = SkillConfig.LIGHTNINGBALL;
                break;
            }
            case 5: {
                textureName = "flowerball";
                projConfig = SkillConfig.FLOWERBALL;
                break;
            }
            case 6: {
                projConfig = SkillConfig.PLASMA_BOLT;
                break;
            }
        }

        this.projTexture = "textures/game/skills/" + textureName + ".png";
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
            ownedProjectiles.add(SkillHelper.shootProjectile(projTexture, this, Optional.empty(), projConfig));
        }
    }

    @Override
    protected float getMoveSpeed() {
        return 100f;
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
        return 25;
    }

    @Override
    public int getXP() {
        return 3;
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
