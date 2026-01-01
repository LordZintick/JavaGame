package com.lordzintick.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.lordzintick.MainGame;
import com.lordzintick.audio.AudioManager;
import com.lordzintick.audio.Sound;
import com.lordzintick.game.entity.player.PlayerClass;
import com.lordzintick.ui.widget.PlayerSkinDisplay;
import com.lordzintick.ui.widget.TextButton;
import com.lordzintick.ui.widget.TextLabel;
import com.lordzintick.util.Text;
import com.lordzintick.util.UIUtil;

/**
 * An implementation of {@link AbstractUIScreen} for the Start Game screen
 */
public class SelectClassScreen extends AbstractUIScreen {
    private TextLabel classnameLabel;
    private PlayerSkinDisplay skinDisplay;
    private TextLabel classDescriptionLabel;

    public SelectClassScreen(MainGame game) {
        super(game);
    }

    @Override
    protected void addWidgets() {
        // Add title
        widgets.add(new TextLabel(this, new Text("Select Class").setAlign(Align.center), getMidX(), Gdx.graphics.getHeight() - 20));

        // Add start button
        widgets.add(new TextButton(this, new Text("Start").setAlign(Align.center), getMidX() - 74, 20, 128, 40, () -> {
            LOGGER.log("Starting game...");
            game.changeScreen(game.screenHolder.MAIN_GAME);
        }));

        // Add back button
        widgets.add(new TextButton(this, new Text("Back").setAlign(Align.center), getMidX() + 74, 20, 128, 40, () -> {
            game.changeScreen(game.screenHolder.TITLE);
        }));

        classnameLabel = new TextLabel(this, new Text("classalomass").setAlign(Align.center), getMidX() - 84, getMidY() - 256);
        widgets.add(classnameLabel);

        classDescriptionLabel = new TextLabel(this, new Text("descriptionary"), getMidX() - 84, (int) (getMidY() - 256 - game.font.getLineHeight()));
        widgets.add(classDescriptionLabel);

        widgets.add(new TextButton(this, new Text("Next").setAlign(Align.center), getMidX() + 356, getMidY() - 256, 128, 40, () -> {
            if (game.selectedPlayerClass.ordinal() >= PlayerClass.values().length - 1) {
                game.selectedPlayerClass = PlayerClass.values()[0];
            } else {
                game.selectedPlayerClass = PlayerClass.values()[game.selectedPlayerClass.ordinal() + 1];
            }
        }));

        widgets.add(new TextButton(this, new Text("Previous").setAlign(Align.center), getMidX() - 256, getMidY() - 256, 128, 40, () -> {
            if (game.selectedPlayerClass.ordinal() == 0) {
                game.selectedPlayerClass = PlayerClass.values()[PlayerClass.values().length - 1];
            } else {
                game.selectedPlayerClass = PlayerClass.values()[game.selectedPlayerClass.ordinal() - 1];
            }
        }));

        skinDisplay = new PlayerSkinDisplay(this, game.selectedPlayerClass, getMidX() - 48, getMidY() - 96);
        widgets.add(skinDisplay);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        classnameLabel.text = new Text(game.selectedPlayerClass.name());

        StringBuilder descriptionBuilder = new StringBuilder();
        for (String line : game.selectedPlayerClass.description) {
            descriptionBuilder.append(line).append("\n");
        }
        classDescriptionLabel.text = new Text(descriptionBuilder.toString());

        skinDisplay.changeClass(game.selectedPlayerClass);
    }

    @Override
    public Sound getBackgroundMusic() {
        return AudioManager.TITLE_MUSIC;
    }

    @Override
    public Color getBackgroundColor() {
        return Color.GRAY;
    }
}
