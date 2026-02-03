package com.lordzintick.java_game.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import com.lordzintick.java_game.MainGame;
import com.lordzintick.java_game.achievement.Achievement;
import com.lordzintick.pixel_krush.core.util.audio.Sound;
import com.lordzintick.pixel_krush.core.api.AbstractUIScreen;
import com.lordzintick.pixel_krush.core.util.ListUtil;
import com.lordzintick.pixel_krush.core.util.Text;
import com.lordzintick.pixel_krush.core.ui.impl.TextButton;
import com.lordzintick.java_game.ui.widget.AchievementDisplay;

import java.util.ArrayList;
import java.util.List;

public class AchievementScreen extends AbstractUIScreen {
    private float offset = 0;
    private final ArrayList<AchievementDisplay> achievementDisplays = new ArrayList<>();
    /**
     * Constructs a new {@link AbstractUIScreen} with the provided {@link MainGame}
     *
     * @param game The {@link MainGame} instance that this game is for
     */
    public AchievementScreen(MainGame game) {
        super(game);

        List<Achievement> achievements = game.queryRegistryOrThrow(game.getId("achievements")).list();
        for (int i = 0; i < achievements.size(); i++) {
            Achievement achievement = achievements.get(i);
            AchievementDisplay display = new AchievementDisplay(this, Gdx.graphics.getWidth() / 7, Gdx.graphics.getHeight() - 84 - i * 74, 512, 64, achievement);
            achievementDisplays.add(display);
            widgets.add(display);
        }

        game.getInput().scrollListeners.add((deltaX, deltaY) -> {
            offset += deltaY * 100;
        });
    }

    @Override
    protected void addWidgets() {
        widgets.add(new TextButton(this, new Text("Back").setAlign(Align.center), 74, 20, 128, 40, () -> {
            game.getAudioSample("back").play();
            game.changeScreen("title");
        }));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (game.getKeybind("scroll_up").isPressed) {
            offset += 100;
        }

        if (game.getKeybind("scroll_down").isPressed) {
            offset -= 100;
        }

        for (int i = 0; i < achievementDisplays.size(); i++) {
            AchievementDisplay display = achievementDisplays.get(i);
            display.y = Gdx.graphics.getHeight() - 84 - i * 74 + offset;
        }
    }

    @Override
    public List<Sound> getBackgroundMusic() {
        return ListUtil.listOf(
            game.getAudioSample("achievements_music")
        );
    }
}
