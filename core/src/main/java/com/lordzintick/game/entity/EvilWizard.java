package com.lordzintick.game.entity;

import com.lordzintick.audio.Sound;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.game.proj.Projectile;
import com.lordzintick.game.skill.Behaviour;
import com.lordzintick.game.skill.SkillHelper;
import com.lordzintick.game.screen.AbstractGameScreen;

import java.util.ArrayList;
import java.util.Optional;

public class EvilWizard extends HostileEntity {
    private float cooldown = 3f;
    private final String projTexture;
    private Behaviour projConfig;
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
                projConfig = Behaviour.FIREBALL;
                break;
            }
            case 1: {
                textureName = "iceball";
                projConfig = Behaviour.ICEBALL;
                break;
            }
            case 2: {
                textureName = "acidball";
                projConfig = Behaviour.ACIDBALL;
                break;
            }
            case 3: {
                textureName = "waterball";
                projConfig = Behaviour.WATERBALL;
                break;
            }
            case 4: {
                textureName = "lightningball";
                projConfig = Behaviour.LIGHTNINGBALL;
                break;
            }
            case 5: {
                textureName = "flowerball";
                projConfig = Behaviour.FLOWERBALL;
                break;
            }
            case 6: {
                projConfig = Behaviour.PLASMA_BOLT;
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
            screen.game.audio.get("shoot");
            ownedProjectiles.add(SkillHelper.shootProjectile(projTexture, this, Optional.empty(), projConfig));
        }
    }

    @Override
    protected float getMoveSpeed() {
        return 100f;
    }

    @Override
    public Sound getHurtSound() {
        return screen.game.audio.get("hit");
    }

    @Override
    public Sound getDeathSound() {
        return screen.game.audio.get("kill");
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
