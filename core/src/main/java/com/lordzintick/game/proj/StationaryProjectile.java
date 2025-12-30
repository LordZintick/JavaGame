package com.lordzintick.game.proj;

import com.badlogic.gdx.graphics.Texture;
import com.lordzintick.game.entity.Entity;
import com.lordzintick.game.entity.HostileEntity;
import com.lordzintick.game.entity.Player;
import com.lordzintick.game.screen.AbstractGameScreen;

import java.util.function.BiConsumer;

public class StationaryProjectile extends Projectile {
    private float lifeTime;
    /**
     * Constructs a new projectile in the provided screen
     *
     * @param screen    The {@link AbstractGameScreen} that is the parent/holder of this projectile
     */
    public StationaryProjectile(AbstractGameScreen screen, Entity owner, float moveX, float moveY, int width, int height, Texture sheet, int damage, float frameTime, int pierce, float lifeTime, BiConsumer<Projectile, Float> tick, BiConsumer<Projectile, Entity> hit, boolean friendly) {
        super(screen, owner, moveX, moveY, width, height, sheet, damage, frameTime, 0, pierce, tick, hit, friendly);
        this.lifeTime = lifeTime;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        lifeTime -= deltaTime;
        if (lifeTime <= 0) {
            shouldRemove = true;
        }
    }
}
