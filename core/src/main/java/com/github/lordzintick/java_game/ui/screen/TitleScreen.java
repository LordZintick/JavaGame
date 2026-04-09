package com.github.lordzintick.java_game.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import com.github.lordzintick.java_game.MainGame;
import com.github.lordzintick.pixel_krush.core.util.Logger;
import com.github.lordzintick.pixel_krush.core.util.audio.Sound;
import com.github.lordzintick.pixel_krush.core.api.AbstractUIScreen;
import com.github.lordzintick.pixel_krush.core.util.Direction;
import com.github.lordzintick.pixel_krush.core.util.ListUtil;
import com.github.lordzintick.pixel_krush.core.util.Text;
import com.github.lordzintick.pixel_krush.core.ui.impl.TextButton;
import com.github.lordzintick.pixel_krush.core.ui.impl.TextLabel;
import com.github.lordzintick.java_game.ui.widget.BouncingImageLabel;

import java.util.List;

/**
 * An implementation of {@link AbstractUIScreen} for the Title screen
 */
public class TitleScreen extends AbstractUIScreen {
    private static final Logger LOGGER = new Logger(TitleScreen.class);

    public TitleScreen(MainGame game) {
        super(game);
    }

    @Override
    protected void addWidgets() {
        // Add bouncing java icons
        for (int i = 0; i < game.getRandom().nextInt(4, 16); i++) {
            Direction dir = Direction.UP_LEFT;
            switch (game.getRandom().nextInt(4)) {
                case 0: dir = Direction.UP_RIGHT; break;
                case 1: dir = Direction.DOWN_LEFT; break;
                case 2: dir = Direction.DOWN_RIGHT; break;
            }
            String textureName = "icons/icon" + (game.getRandom().nextInt(10) + 1) + ".png";
            widgets.add(new BouncingImageLabel(this, game.getAssetOrThrow("textures/" + textureName), game.getRandom().nextInt(0, Math.max(Gdx.graphics.getWidth() - 100, 100)), game.getRandom().nextInt(0, Math.max(Gdx.graphics.getHeight() - 100, 100)), 99, 99, dir));
        }

        // Add title
        widgets.add(new TextLabel(this, new Text("JavaGame").setAlign(Align.center).setFont("mega"), getMidX(), (int) (getMidY() * 1.5)));
        // Add start button
        widgets.add(new TextButton(this, new Text("Start").setAlign(Align.center), getMidX(), getMidY(), 128, 64, () -> {
            game.getAudioSample("confirm").play();
            game.changeScreen("run_config");
        }));
        // Add quit button
        widgets.add(new TextButton(this, new Text("Quit").setAlign(Align.center), getMidX(), getMidY() - 74, 128, 64, () -> {
            LOGGER.log("Stopping!");
            Gdx.app.exit();
        }));

        widgets.add(new TextButton(this, new Text("Achievements").setAlign(Align.center), Gdx.graphics.getWidth() - 138, 20, 256, 40, () -> {
            game.getAudioSample("confirm").play();
            game.changeScreen("achievements");
        }));
    }

    @Override
    public List<Sound> getBackgroundMusic() {
        return ListUtil.listOf(game.getAudioSample("title_music"));
    }
}
