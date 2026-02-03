package com.lordzintick.java_game.ui.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.lordzintick.java_game.achievement.Achievement;
import com.lordzintick.pixel_krush.core.api.BaseScreen;
import com.lordzintick.pixel_krush.core.util.UIUtil;
import com.lordzintick.pixel_krush.core.ui.api.Widget;

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
        BitmapFont outlinedFont = screen.game.getFont("outlined");
        batch.draw(achievement.icon, x + (float) height / 16, y + (float) height / 16, height - (float) height / 8, height - (float) height / 8);
        if (achievement.achieved)
            outlinedFont.setColor(Color.LIME);
        UIUtil.renderText(screen.game, batch,
            achievement.displayName.concat(achievement.achieved ? " (Completed)" : ""),
            x + height,
            y + height - outlinedFont.getLineHeight(),
            (int) UIUtil.getFontStringWidth(achievement.displayName.toString(), outlinedFont), false);
        outlinedFont.setColor(Color.WHITE);
        UIUtil.renderText(screen.game, batch,
            achievement.description,
            x + height,
            y + height - outlinedFont.getLineHeight() * 2.25f,
            (int) UIUtil.getFontStringWidth(achievement.description.toString(), outlinedFont), false);
    }
}
