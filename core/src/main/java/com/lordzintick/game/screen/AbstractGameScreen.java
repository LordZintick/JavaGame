package com.lordzintick.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector4;
import com.lordzintick.MainGame;
import com.lordzintick.game.AbstractGameObject;
import com.lordzintick.game.entity.Entity;
import com.lordzintick.game.proj.Particle;
import com.lordzintick.ui.widget.Widget;
import com.lordzintick.util.BaseScreen;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * An abstract class representing the base of all "game" screens, that is to say they are part of the physical game and not part of any UI menu
 */
public abstract class AbstractGameScreen extends BaseScreen {
    private final ArrayList<AbstractGameObject> objects = new ArrayList<>();
    private final ArrayList<AbstractGameObject> queuedObjects = new ArrayList<>();

    /**
     * Constructs a new {@link AbstractGameScreen} with the provided {@link MainGame}
     * @param game The {@link MainGame} instance that this screen is for
     */
    protected AbstractGameScreen(MainGame game) {
        super(game);
        populateInitialObjects();
    }

    /**
     * Populates the screen with its starting objects
     */
    protected abstract void populateInitialObjects();

    @Override
    public void update(float deltaTime) {
        // Iterate through all game objects and update them
        Iterator<AbstractGameObject> iterator = objects.iterator();
        while (iterator.hasNext()) {
            AbstractGameObject gameObject = iterator.next();
            if (gameObject.shouldRemove) {
                iterator.remove();
                gameObject.dispose();
            } else {
                gameObject.update(deltaTime);

                if (!(gameObject instanceof Particle)) {
                    Iterator<AbstractGameObject> iterator1 = objects.iterator();
                    while (iterator1.hasNext()) {
                        AbstractGameObject gameObject1 = iterator1.next();

                        if (gameObject == gameObject1
                            || Math.abs(gameObject.x - gameObject1.x) > Math.max(gameObject.collisionRect.width * gameObject.scale, gameObject1.collisionRect.width * gameObject1.scale)
                            || Math.abs(gameObject.y - gameObject1.y) > Math.max(gameObject.collisionRect.height * gameObject.scale, gameObject1.collisionRect.height * gameObject1.scale)
                            || gameObject1 instanceof Particle) continue;
                        if (gameObject.collisionRect.overlaps(gameObject1.collisionRect)) {
                            gameObject.collide(gameObject1);
                            gameObject1.collide(gameObject);
                        }
                    }
                }
            }
        }

        objects.addAll(queuedObjects);
        queuedObjects.clear();
    }

    public void queueAddObject(AbstractGameObject toAdd) {
        queuedObjects.add(toAdd);
    }

    public void addParticle(TextureRegion[] frames, float x, float y, Vector4 velocity, float scale, float frameTime, float lifeTime) {
        if (Math.abs(x) >= Gdx.graphics.getWidth() * 2 || Math.abs(y) >= Gdx.graphics.getWidth() * 2 || x == 0 || y == 0) return;

        Particle particle = new Particle(this, frames, scale, frameTime, lifeTime);
        particle.x = x;
        particle.y = y;
        particle.velocity = velocity;
        queuedObjects.add(particle);
    }

    @Override
    public void renderGame(float deltaTime) {
        // Iterate through all game objects and render them
        for (AbstractGameObject gameObject : objects) {
            gameObject.render(game.gameBatch, deltaTime);
        }
    }

    @Override
    public void renderUI(float deltaTime) {
        // Iterate through all widgets and render them
        for (Widget widget : widgets) {
            widget.render(game.uiBatch, deltaTime);
        }
    }
}
