package com.lordzintick.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.lordzintick.MainGame;
import com.lordzintick.audio.Sound;
import com.lordzintick.game.entity.player.PlayerClass;
import com.lordzintick.ui.widget.PlayerSkinDisplay;
import com.lordzintick.ui.widget.TextButton;
import com.lordzintick.ui.widget.TextLabel;
import com.lordzintick.util.ListUtil;
import com.lordzintick.util.Text;
import com.lordzintick.util.UIUtil;

import java.util.List;
import java.util.Locale;

/**
 * An implementation of {@link AbstractUIScreen} for the Start Game screen
 */
public class RunConfigScreen extends AbstractUIScreen {
    private TextLabel classnameLabel;
    private PlayerSkinDisplay skinDisplay;
    private TextLabel classDescriptionLabel;

    public RunConfigScreen(MainGame game) {
        super(game);
    }

    @Override
    protected void addWidgets() {
        // Add title
        widgets.add(new TextLabel(this, new Text("Run Configuration").setAlign(Align.center), getMidX(), Gdx.graphics.getHeight() - 20));

        // Add start button
        widgets.add(new TextButton(this, new Text("Start").setAlign(Align.center), getMidX() - 74, 20, 128, 40, () -> {
            if (game.gameData.unlockedClasses.get(game.selectedPlayerClass.name().toLowerCase(Locale.ROOT))) {
                LOGGER.log("Starting game...");
                game.audio.get("confirm").play();
                game.changeScreen(game.screenHolder.MAIN_GAME);
            } else {
                game.audio.get("back").play();
                LOGGER.log("Class not unlocked yet!");
            }
        }));

        // Add back button
        widgets.add(new TextButton(this, new Text("Back").setAlign(Align.center), getMidX() + 74, 20, 128, 40, () -> {
            game.audio.get("back").play();
            game.changeScreen(game.screenHolder.TITLE);
        }));

        classnameLabel = new TextLabel(this, new Text("classalomass").setAlign(Align.center), getMidX() - 84, getMidY() - 256);
        widgets.add(classnameLabel);

        classDescriptionLabel = new TextLabel(this, new Text("descriptionary"), getMidX() - 84, (int) (getMidY() - 256 - game.font.getLineHeight()));
        widgets.add(classDescriptionLabel);

        widgets.add(new TextButton(this, new Text("Next").setAlign(Align.center), getMidX() + 356, getMidY() - 256, 128, 40, () -> {
            game.audio.get("confirm").play();
            if (game.selectedPlayerClass.ordinal() >= PlayerClass.values().length - 1) {
                game.selectedPlayerClass = PlayerClass.values()[0];
            } else {
                game.selectedPlayerClass = PlayerClass.values()[game.selectedPlayerClass.ordinal() + 1];
            }
        }));

        widgets.add(new TextButton(this, new Text("Previous").setAlign(Align.center), getMidX() - 256, getMidY() - 256, 128, 40, () -> {
            game.audio.get("back").play();
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

        float width = 0;
        if (game.gameData.unlockedClasses.get(game.selectedPlayerClass.name().toLowerCase(Locale.ROOT))) {
            classnameLabel.text = new Text(game.selectedPlayerClass.name());
            StringBuilder descriptionBuilder = new StringBuilder();
            for (String line : game.selectedPlayerClass.description) {
                descriptionBuilder.append(line).append("\n");
                width = Math.max(width, UIUtil.getFontStringWidth(line, game.font));
            }
            classDescriptionLabel.text = new Text(descriptionBuilder.toString());
            classDescriptionLabel.x = getMidX() - width / 2;
        } else {
            classnameLabel.text = new Text(game.selectedPlayerClass.name()).glitchy();
            classDescriptionLabel.text = new Text(game.selectedPlayerClass.hint);
            classDescriptionLabel.x = getMidX() - UIUtil.getFontStringWidth(game.selectedPlayerClass.hint, game.font) / 2;
        }

        if (skinDisplay.getDisplayedClass() != game.selectedPlayerClass)
            skinDisplay.changeClass(game.selectedPlayerClass);
    }

    @Override
    public List<Sound> getBackgroundMusic() {
        return ListUtil.listOf(game.audio.get("title_music"));
    }

    @Override
    public Color getBackgroundColor() {
        return Color.DARK_GRAY;
    }
}
