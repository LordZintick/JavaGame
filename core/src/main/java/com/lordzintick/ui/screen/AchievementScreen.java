package com.lordzintick.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import com.lordzintick.GameData;
import com.lordzintick.MainGame;
import com.lordzintick.achievement.Achievement;
import com.lordzintick.achievement.Achievements;
import com.lordzintick.audio.Sound;
import com.lordzintick.control.Keybinds;
import com.lordzintick.ui.widget.AchievementDisplay;
import com.lordzintick.ui.widget.TextButton;
import com.lordzintick.util.ListUtil;
import com.lordzintick.util.Text;

import java.util.ArrayList;
import java.util.Collection;
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

        ArrayList<Achievement> achievements = new ArrayList<>(game.achievements.getAchievements());
        for (int i = 0; i < achievements.size(); i++) {
            Achievement achievement = achievements.get(i);
            AchievementDisplay display = new AchievementDisplay(this, Gdx.graphics.getWidth() / 7, Gdx.graphics.getHeight() - 84 - i * 74, 512, 64, achievement);
            achievementDisplays.add(display);
            widgets.add(display);
        }

        game.input.scrollListeners.add((deltaX, deltaY) -> {
            offset += deltaY * 100;
        });
    }

    @Override
    protected void addWidgets() {
        widgets.add(new TextButton(this, new Text("Back").setAlign(Align.center), 74, 20, 128, 40, () -> {
            game.audio.get("back").play();
            game.changeScreen(game.screenHolder.TITLE);
        }));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (game.keybinds.get("scroll_up").isPressed) {
            offset += 100;
        }

        if (game.keybinds.get("scroll_down").isPressed) {
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
            game.audio.get("achievements_music")
        );
    }
}
