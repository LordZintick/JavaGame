package com.lordzintick.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import com.lordzintick.MainGame;
import com.lordzintick.audio.Sound;
import com.lordzintick.ui.widget.BouncingImageLabel;
import com.lordzintick.ui.widget.TextButton;
import com.lordzintick.ui.widget.TextLabel;
import com.lordzintick.util.Direction;
import com.lordzintick.util.ListUtil;
import com.lordzintick.util.Text;

import java.util.List;

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
            widgets.add(new BouncingImageLabel(this, game.assets.get("textures/java.png"), game.random.nextInt(0, Math.max(Gdx.graphics.getWidth() - 100, 100)), game.random.nextInt(0, Math.max(Gdx.graphics.getHeight() - 100, 100)), 99, 99, dir));
        }

        // Add title
        widgets.add(new TextLabel(this, new Text("JavaGame").setAlign(Align.center).mega(), getMidX(), (int) (getMidY() * 1.5)));
        // Add start button
        widgets.add(new TextButton(this, new Text("Start").setAlign(Align.center), getMidX(), getMidY(), 128, 64, () -> {
            game.audio.get("confirm").play();
            game.changeScreen(game.screenHolder.RUN_CONFIG);
        }));
        // Add quit button
        widgets.add(new TextButton(this, new Text("Quit").setAlign(Align.center), getMidX(), getMidY() - 74, 128, 64, () -> {
            LOGGER.log("Stopping!");
            Gdx.app.exit();
        }));
    }

    @Override
    public List<Sound> getBackgroundMusic() {
        return ListUtil.listOf(game.audio.get("title_music"));
    }
}
