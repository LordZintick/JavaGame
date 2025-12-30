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
    private final ArrayList<Particle> particles = new ArrayList<>();
    private final ArrayList<Particle> queuedParticles = new ArrayList<>();

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
        for (AbstractGameObject gameObject : objects) {
            gameObject.update(deltaTime);
            for (AbstractGameObject gameObject1 : objects) {
                if (gameObject == gameObject1) continue;
                if (gameObject.collisionRect.overlaps(gameObject1.collisionRect)) {
                    gameObject.collide(gameObject1);
                    gameObject1.collide(gameObject);
                }
            }
        }

        for (Particle particle : particles) {
            particle.update(deltaTime);
        }

        for (int i = objects.size() - 1; i > 0; i--) {
            AbstractGameObject gameObject = objects.get(i);
            if (gameObject.shouldRemove) {
                objects.remove(gameObject);
                gameObject.dispose();
            }
        }

        for (int i = particles.size() - 1; i > 0; i--) {
            Particle particle = particles.get(i);
            if (particle.shouldRemove) {
                particles.remove(particle);
            }
        }

        objects.addAll(queuedObjects);
        queuedObjects.clear();

        particles.addAll(queuedParticles);
        queuedParticles.clear();
    }

    public void queueAddObject(AbstractGameObject toAdd) {
        queuedObjects.add(toAdd);
    }

    public void addParticle(TextureRegion[] frames, float x, float y, Vector4 velocity, float scale, float frameTime, float lifeTime) {
        if (Math.abs(x) >= Gdx.graphics.getWidth() * 2 || Math.abs(y) >= Gdx.graphics.getWidth() * 2 || x == 0 || y == 0) return;

        Particle particle = new Particle(frames, scale, frameTime, lifeTime);
        particle.pos = new Vector2(x, y);
        particle.velocity = velocity;
        queuedParticles.add(particle);
    }

    @Override
    public void renderGame(float deltaTime) {
        // Iterate through all game objects and render them
        for (AbstractGameObject gameObject : objects) {
            gameObject.render(game.gameBatch, deltaTime);
        }

        for (Particle particle : particles) {
            particle.render(game.gameBatch, deltaTime);
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
