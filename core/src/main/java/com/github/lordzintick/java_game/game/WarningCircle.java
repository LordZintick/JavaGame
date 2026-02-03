package com.github.lordzintick.java_game.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.lordzintick.pixel_krush.core.api.AbstractGameObject;
import com.github.lordzintick.pixel_krush.core.api.AbstractGameScreen;

public class WarningCircle extends AbstractGameObject {
    public final AbstractGameObject objectToSpawn;
    public final float timeToLive;
    private float lifeTime = 0;

    /**
     * Constructs a new game object in the provided screen
     *
     * @param screen The {@link AbstractGameScreen} that is the parent/holder of this game object
     */
    public WarningCircle(AbstractGameScreen screen, float x, float y, AbstractGameObject objectToSpawn, float timeToLive, int size) {
        super(screen);
        this.x = x - (float) size / 2;
        this.y = y - (float) size / 2;
        this.objectToSpawn = objectToSpawn;
        this.timeToLive = timeToLive;
        this.width = size;
        this.height = size;
    }

    @Override
    public void update(float deltaTime) {
        lifeTime += deltaTime;
        if (lifeTime >= timeToLive) {
            objectToSpawn.x = x - (float) width / 2;
            objectToSpawn.y = y - (float) height / 2;
            screen.objects.add(objectToSpawn);
            remove();
        }
    }

    @Override
    public void render(Batch batch, float deltaTime) {
        float factor = lifeTime / timeToLive;
        batch.setColor(1, 1, 1, factor);
        batch.draw((Texture) screen.game.getAssetOrThrow("textures/game/warning_circle.png"), x - factor * width / 2, y - factor * height / 2, width * factor, height * factor);
        batch.setColor(Color.WHITE);
    }
}
