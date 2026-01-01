package com.lordzintick.game.proj;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.lordzintick.game.entity.Entity;
import com.lordzintick.game.screen.AbstractGameScreen;

import java.util.function.BiConsumer;

public class MeleeAttack extends StationaryProjectile {
    public final float angle;

    /**
     * Constructs a new projectile in the provided screen
     *
     * @param screen    The {@link AbstractGameScreen} that is the parent/holder of this projectile
     */
    public MeleeAttack(AbstractGameScreen screen, Entity owner, float angle, int width, int height, Texture sheet, int damage, float frameTime, int pierce, float lifeTime, BiConsumer<Projectile, Float> tick, BiConsumer<Projectile, Entity> hit, boolean friendly) {
        super(screen, owner, width, height, sheet, damage, frameTime, pierce, lifeTime, tick, hit, friendly);
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

    @Override
    public void render(Batch batch, float deltaTime) {
        // Used for debug purposes
        //batch.draw(screen.game.debugTexture, collisionRect.x, collisionRect.y, collisionRect.width, collisionRect.height);

        animTicks += deltaTime;
        if (animTicks >= frameTime) {
            animTicks = 0;
            if (frame >= splitFrames.length - 1) {
                frame = 0;
            } else {
                frame++;
            }
        }

        batch.draw(splitFrames[frame][0], x, y, 0, 0, width * 8, height * 8, 1, 1, angle);
    }
}
