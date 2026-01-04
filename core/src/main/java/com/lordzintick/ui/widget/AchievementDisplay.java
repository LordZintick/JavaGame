package com.lordzintick.ui.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Align;
import com.lordzintick.achievement.Achievement;
import com.lordzintick.util.BaseScreen;
import com.lordzintick.util.UIUtil;

public class AchievementDisplay extends Widget {
    private final Achievement achievement;

    public AchievementDisplay(BaseScreen screen, int x, int y, int width, int height, Achievement achievement) {
        super(screen, width, height);
        this.x = x;
        this.y = y;
        this.achievement = achievement;
    }

    @Override
    public void render(Batch batch, float deltaTime) {
        super.render(batch, deltaTime);
        batch.draw(achievement.icon, x + (float) height / 16, y + (float) height / 16, height - (float) height / 8, height - (float) height / 8);
        if (achievement.achieved)
            screen.game.outlinedFont.setColor(Color.LIME);
        screen.game.outlinedFont.draw(
            batch,
            achievement.displayName + (achievement.achieved ? " (Completed)" : ""),
            x + height,
            y + height - screen.game.outlinedFont.getLineHeight(),
            UIUtil.getFontStringWidth(achievement.displayName, screen.game.outlinedFont),
            Align.left, false
        );
        screen.game.outlinedFont.setColor(Color.WHITE);
        screen.game.outlinedFont.draw(
            batch,
            achievement.description,
            x + height,
            y + height - screen.game.outlinedFont.getLineHeight() * 2.25f,
            UIUtil.getFontStringWidth(achievement.description, screen.game.outlinedFont),
            Align.left, false
        );
    }
}
