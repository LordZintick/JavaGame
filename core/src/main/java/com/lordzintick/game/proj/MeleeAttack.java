package com.lordzintick.game.proj;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.lordzintick.game.entity.Entity;
import com.lordzintick.game.screen.AbstractGameScreen;

import java.util.function.BiConsumer;

public class MeleeAttack extends StationaryProjectile {
    /**
     * Constructs a new projectile in the provided screen
     *
     * @param screen    The {@link AbstractGameScreen} that is the parent/holder of this projectile
     */
    public MeleeAttack(AbstractGameScreen screen, Entity owner, float angle, int width, int height, Texture sheet, int damage, float frameTime, float lifeTime, boolean followCursor, BiConsumer<Projectile, Float> tick, BiConsumer<Projectile, Entity> hit, boolean friendly) {
        super(screen, owner, width, height, sheet, damage, frameTime, lifeTime, followCursor, tick, hit, friendly);
        this.angle = angle;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float offsetAngle = angle + 45;
        this.collisionRect.set(
            (float) (x - width * 4 + Math.cos(Math.toRadians(offsetAngle)) * 50),
            (float) (y - height * 4 + Math.sin(Math.toRadians(offsetAngle)) * 50),
            width * 8,
            height * 8
        );
        this.x = owner.x + (float) owner.width / 2 * owner.scale;
        this.y = owner.y + (float) owner.height / 2 * owner.scale;
    }
}
