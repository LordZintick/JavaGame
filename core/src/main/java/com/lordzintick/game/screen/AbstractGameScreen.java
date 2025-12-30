package com.lordzintick.game.screen;

import com.lordzintick.MainGame;
import com.lordzintick.game.AbstractGameObject;
import com.lordzintick.ui.widget.Widget;
import com.lordzintick.util.BaseScreen;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * An abstract class representing the base of all "game" screens, that is to say they are part of the physical game and not part of any UI menu
 */
public abstract class AbstractGameScreen extends BaseScreen {
    public final ArrayList<AbstractGameObject> objects = new ArrayList<>();

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
        for (Iterator<AbstractGameObject> it = objects.iterator(); it.hasNext();) {
            AbstractGameObject gameObject = it.next();
            gameObject.update(deltaTime);
            if (gameObject.shouldRemove) {
                objects.remove(gameObject);
                gameObject.dispose();
            }
        }
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
