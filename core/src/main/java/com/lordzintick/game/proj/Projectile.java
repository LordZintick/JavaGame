package com.lordzintick.game.proj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.lordzintick.game.AbstractGameObject;
import com.lordzintick.game.screen.AbstractGameScreen;

public class Projectile extends AbstractGameObject {
    public Vector2 movementVector;
    public final Texture sheet;
    public final float frameTime;
    protected float animTicks = 0;
    private int frame = 0;
    private float angle;
    private final TextureRegion[][] splitFrames;

    /**
     * Constructs a new game object in the provided screen
     *
     * @param screen The {@link AbstractGameScreen} that is the parent/holder of this game object
     */
    public Projectile(AbstractGameScreen screen, float angle, int width, int height, Texture sheet, float frameTime) {
        super(screen);
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.movementVector = new Vector2((float) Math.cos(angle), (float) Math.sin(angle)).nor();
        this.sheet = sheet;
        this.splitFrames = TextureRegion.split(sheet, width, height);
        this.frameTime = frameTime;
    }

    @Override
    public void render(Batch batch, float deltaTime) {
        x += movementVector.x;
        y += movementVector.y;

        if (Math.abs(x) >= Gdx.graphics.getWidth() * 4 || Math.abs(y) >= Gdx.graphics.getWidth() * 4) {
            shouldRemove = true;
        }

        animTicks += deltaTime;
        if (animTicks >= frameTime) {
            animTicks = 0;
            if (frame >= splitFrames.length) {
                frame = 0;
            } else {
                frame++;
            }
        }

        batch.draw(splitFrames[frame][0], x, y, 0, 0, width, height, 1, 1, angle);
    }
}
