package com.lordzintick.game.proj;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector4;
import com.lordzintick.core.Logger;
import com.lordzintick.core.Renderable;
import com.lordzintick.core.Updateable;
import com.lordzintick.game.AbstractGameObject;
import com.lordzintick.game.screen.AbstractGameScreen;

public class Particle extends AbstractGameObject {
    private static final Logger LOGGER = new Logger(Particle.class);

    public Vector4 velocity;
    public final TextureRegion[] frames;
    public float scale;
    private float animTicks = 0;
    private float ticks = 0;
    private int frame = 0;
    public final float frameTime;
    private final float lifeTime;
    private float angle = 0;

    public Particle(AbstractGameScreen screen, TextureRegion[] frames, float scale, float frameTime, float lifeTime) {
        super(screen);
        this.frames = frames;
        this.scale = scale;
        this.frameTime = frameTime;
        this.lifeTime = lifeTime;
    }

    @Override
    public void render(Batch batch, float deltaTime) {
        animTicks += deltaTime;
        if (animTicks >= frameTime) {
            animTicks = 0;
            if (frame >= frames.length - 1) {
                frame = 0;
            } else {
                frame++;
            }
        }

        TextureRegion frameToDraw = frames[frame];
        batch.draw(frameToDraw, x, y, 0, 0, frameToDraw.getRegionWidth(), frameToDraw.getRegionHeight(), scale, scale, angle);
    }

    @Override
    public void update(float deltaTime) {
        x += velocity.x * deltaTime;
        y += velocity.y * deltaTime;
        scale = Math.max(0, scale + velocity.z * deltaTime);
        angle += velocity.w * deltaTime;

        ticks += deltaTime;
        if (ticks >= lifeTime || scale <= 0) {
            shouldRemove = true;
        }
    }
}
