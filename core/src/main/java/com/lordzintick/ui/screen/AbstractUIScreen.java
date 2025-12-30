package com.lordzintick.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.lordzintick.MainGame;
import com.lordzintick.core.Logger;
import com.lordzintick.game.screen.AbstractGameScreen;
import com.lordzintick.ui.widget.Widget;
import com.lordzintick.util.BaseScreen;

import java.util.ArrayList;

/**
 * An abstract class representing the base of all "UI" screens, that is to say they are part of an out-of-game menu and not part of the physical game
 */
public abstract class AbstractUIScreen extends BaseScreen {
    /**
     * Constructs a new {@link AbstractUIScreen} with the provided {@link MainGame}
     * @param game The {@link MainGame} instance that this game is for
     */
    protected AbstractUIScreen(MainGame game) {
        super(game);
    }

    @Override
    public void renderUI(float deltaTime) {
        // Iterate through all widgets and render them
        for (Widget widget : widgets) {
            widget.render(game.uiBatch, deltaTime);
        }
    }
}
