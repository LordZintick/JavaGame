package com.github.lordzintick.java_game.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.github.lordzintick.java_game.MainGame;
import com.github.lordzintick.pixel_krush.core.util.audio.Sound;
import com.github.lordzintick.pixel_krush.core.api.AbstractUIScreen;
import com.github.lordzintick.pixel_krush.core.util.ListUtil;
import com.github.lordzintick.pixel_krush.core.util.Text;
import com.github.lordzintick.pixel_krush.core.util.UIUtil;
import com.github.lordzintick.java_game.game.entity.player.PlayerClass;
import com.github.lordzintick.pixel_krush.core.ui.impl.TextButton;
import com.github.lordzintick.pixel_krush.core.ui.impl.TextLabel;
import com.github.lordzintick.java_game.ui.widget.PlayerSkinDisplay;

import java.util.List;

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
            if (((PlayerClass) game.getMetadata("selectedPlayerClass")).checkUnlocked(game)) {
                LOGGER.log("Starting game...");
                game.getAudioSample("confirm").play();
                game.changeScreen("main_game");
            } else {
                game.getAudioSample("back").play();
                LOGGER.log("Class not unlocked yet!");
            }
        }));

        // Add back button
        widgets.add(new TextButton(this, new Text("Back").setAlign(Align.center), getMidX() + 74, 20, 128, 40, () -> {
            game.getAudioSample("back").play();
            game.changeScreen("title");
        }));

        classnameLabel = new TextLabel(this, new Text("classalomass").setAlign(Align.center), getMidX() - 84, getMidY() - 256);
        widgets.add(classnameLabel);

        classDescriptionLabel = new TextLabel(this, new Text("descriptionary"), getMidX() - 84, (int) (getMidY() - 256 - game.getFont("normal").getLineHeight()));
        widgets.add(classDescriptionLabel);

        widgets.add(new TextButton(this, new Text("Next").setAlign(Align.center), getMidX() + 356, getMidY() - 256, 128, 40, () -> {
            game.getAudioSample("confirm").play();
            PlayerClass selectedPlayerClass = game.getMetadata("selectedPlayerClass");
            if (selectedPlayerClass.ordinal() >= PlayerClass.values().length - 1) {
                game.setMetadata("selectedPlayerClass", PlayerClass.values()[0]);
            } else {
                game.setMetadata("selectedPlayerClass", PlayerClass.values()[selectedPlayerClass.ordinal() + 1]);
            }
        }));

        widgets.add(new TextButton(this, new Text("Previous").setAlign(Align.center), getMidX() - 256, getMidY() - 256, 128, 40, () -> {
            game.getAudioSample("back").play();
            PlayerClass selectedPlayerClass = game.getMetadata("selectedPlayerClass");
            if (selectedPlayerClass.ordinal() == 0) {
                game.setMetadata("selectedPlayerClass", PlayerClass.values()[PlayerClass.values().length - 1]);
            } else {
                game.setMetadata("selectedPlayerClass", PlayerClass.values()[selectedPlayerClass.ordinal() - 1]);
            }
        }));

        skinDisplay = new PlayerSkinDisplay(this, game.getMetadata("selectedPlayerClass"), getMidX() - 48, getMidY() - 96);
        widgets.add(skinDisplay);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        float width = 0;
        PlayerClass selectedPlayerClass = game.getMetadata("selectedPlayerClass");
        BitmapFont font = game.getFont("normal");
        if (selectedPlayerClass.checkUnlocked(game)) {
            classnameLabel.text = new Text(selectedPlayerClass.name());
            StringBuilder descriptionBuilder = new StringBuilder();
            for (String line : selectedPlayerClass.description) {
                descriptionBuilder.append(line).append("\n");
                width = Math.max(width, UIUtil.getFontStringWidth(line, font));
            }
            classDescriptionLabel.text = new Text(descriptionBuilder.toString());
            classDescriptionLabel.x = getMidX() - width / 2;
        } else {
            classnameLabel.text = new Text(selectedPlayerClass.name()).glitchy();
            String text = "Requires \"" + selectedPlayerClass.unlockAchievement.replace("_", " ") + "\" to unlock";
            classDescriptionLabel.text = new Text(text);
            classDescriptionLabel.x = getMidX() - UIUtil.getFontStringWidth(text, font) / 2;
        }

        if (skinDisplay.getDisplayedClass() != selectedPlayerClass)
            skinDisplay.changeClass(selectedPlayerClass);
    }

    @Override
    public List<Sound> getBackgroundMusic() {
        return ListUtil.listOf(game.getAudioSample("title_music"));
    }

    @Override
    public Color getBackgroundColor() {
        return Color.DARK_GRAY;
    }
}
