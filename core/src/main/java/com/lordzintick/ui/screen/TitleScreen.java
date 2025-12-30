package com.lordzintick.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.lordzintick.MainGame;
import com.lordzintick.audio.AudioManager;
import com.lordzintick.audio.Sound;
import com.lordzintick.ui.widget.BouncingImageLabel;
import com.lordzintick.ui.widget.TextButton;
import com.lordzintick.ui.widget.TextLabel;
import com.lordzintick.util.Direction;
import com.lordzintick.util.Text;
import com.lordzintick.util.UIUtil;

/**
 * An implementation of {@link AbstractUIScreen} for the Title screen
 */
public class TitleScreen extends AbstractUIScreen {
    public TitleScreen(MainGame game) {
        super(game);
    }

    @Override
    protected void addWidgets() {
        // Add bouncing java icons
        for (int i = 0; i < game.random.nextInt(4, 16); i++) {
            Direction dir = Direction.UP_LEFT;
            switch (game.random.nextInt(4)) {
                case 0: dir = Direction.UP_RIGHT; break;
                case 1: dir = Direction.DOWN_LEFT; break;
                case 2: dir = Direction.DOWN_RIGHT; break;
            }
            widgets.add(new BouncingImageLabel(this, new Texture(Gdx.files.internal("textures/java.png")), game.random.nextInt(0, Gdx.graphics.getWidth() - 100), game.random.nextInt(0, Gdx.graphics.getHeight() - 100), 99, 99, dir));
        }

        // Add title
        widgets.add(new TextLabel(this, new Text("JavaGame").setAlign(UIUtil.CENTER).mega(), getMidX(), (int) (getMidY() * 1.5)));
        // Add start button
        widgets.add(new TextButton(this, new Text("Start").setAlign(UIUtil.CENTER), getMidX(), getMidY(), 128, 64, () -> {
            game.changeScreen(game.screenHolder.START_GAME);
        }));
        // Add quit button
        widgets.add(new TextButton(this, new Text("Quit").setAlign(UIUtil.CENTER), getMidX(), getMidY() - 74, 128, 64, () -> {
            LOGGER.log("Stopping!");
            Gdx.app.exit();
        }));
    }

    @Override
    public Sound getBackgroundMusic() {
        return AudioManager.TITLE_MUSIC;
    }
}
