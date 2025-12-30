package com.lordzintick.ui.screen;

import com.badlogic.gdx.Gdx;
import com.lordzintick.MainGame;
import com.lordzintick.audio.AudioManager;
import com.lordzintick.audio.Sound;
import com.lordzintick.ui.widget.TextButton;
import com.lordzintick.ui.widget.TextLabel;
import com.lordzintick.util.Text;
import com.lordzintick.util.UIUtil;

/**
 * An implementation of {@link AbstractUIScreen} for the Start Game screen
 */
public class StartGameScreen extends AbstractUIScreen {
    public StartGameScreen(MainGame game) {
        super(game);
    }

    @Override
    protected void addWidgets() {
        // Add title
        widgets.add(new TextLabel(this, new Text("Start Game").setAlign(UIUtil.CENTER), getMidX(), Gdx.graphics.getHeight() - 20));

        // Add start button
        widgets.add(new TextButton(this, new Text("Start").setAlign(UIUtil.CENTER), getMidX() - 74, 20, 128, 40, () -> {
            LOGGER.log("Starting game...");
            game.changeScreen(game.screenHolder.MAIN_GAME);
        }));

        // Add back button
        widgets.add(new TextButton(this, new Text("Back").setAlign(UIUtil.CENTER), getMidX() + 74, 20, 128, 40, () -> {
            game.changeScreen(game.screenHolder.TITLE);
        }));
    }

    @Override
    public Sound getBackgroundMusic() {
        return AudioManager.TITLE_MUSIC;
    }
}
