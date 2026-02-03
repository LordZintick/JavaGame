package com.github.lordzintick.java_game.game.proj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.github.lordzintick.pixel_krush.core.api.AbstractGameScreen;
import com.github.lordzintick.pixel_krush.core.api.ecs.LivingEntity;

import java.util.function.BiConsumer;

public class StationaryProjectile extends Projectile {
    private float lifeTime;
    private Vector2 direction = Vector2.Zero;

    /**
     * Constructs a new projectile in the provided screen
     *
     * @param screen    The {@link AbstractGameScreen} that is the parent/holder of this projectile
     */
    public StationaryProjectile(AbstractGameScreen screen, LivingEntity owner, int width, int height, Texture sheet, int damage, float frameTime, float lifeTime, boolean followMouse, BiConsumer<Projectile, Float> tick, BiConsumer<Projectile, LivingEntity> hit, boolean friendly) {
        super(screen, owner, 0, 0, width, height, sheet, damage, frameTime, 0, 999, followMouse, tick, hit, friendly);
        this.lifeTime = lifeTime;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (aimAtCursor) {
            float collX = x - width * 4 + MathUtils.cosDeg(direction.angleDeg()) * width * 4;
            float collY = y - width * 4 + MathUtils.sinDeg(direction.angleDeg()) * width * 4;
            this.collisionRect.set(
                collX, collY,
                width * 8,
                width * 8
            );
        }

        lifeTime -= deltaTime;
        if (lifeTime <= 0) {
            remove();
        }
    }

    @Override
    public void render(Batch batch, float deltaTime) {
        animTicks += deltaTime;
        if (animTicks >= frameTime) {
            animTicks = 0;
            if (frame >= splitFrames.length - 1) {
                frame = 0;
            } else {
                frame++;
            }
        }

        screen.game.getCamera().update();
        Vector2 camPos = new Vector2(screen.game.getCamera().position.x, screen.game.getCamera().position.y);
        Vector2 pos = new Vector2(owner.x + (float) owner.width / 2 * owner.scale, owner.y + (float) owner.height / 2 * owner.scale);
        Vector2 targetPos = new Vector2(Gdx.input.getX(), -Gdx.input.getY() + Gdx.graphics.getHeight()).add(camPos.x, camPos.y);
        direction = new Vector2(targetPos.sub(pos));
        Vector2 normalDir = new Vector2(direction.cpy().nor());
        float bigDim = Math.max(owner.width, owner.height) * owner.scale;

        if (aimAtCursor) {
            x = owner.x + (float) owner.width / 2 * owner.scale + normalDir.x * bigDim;
            y = owner.y + (float) owner.height / 4 * owner.scale + normalDir.y * bigDim;
        }

        batch.draw(splitFrames[frame][0], x, y, 0, aimAtCursor ? height * 4 : 0, width * 8, height * 8, 1, 1, aimAtCursor ? direction.angleDeg() : 0);
    }
}
