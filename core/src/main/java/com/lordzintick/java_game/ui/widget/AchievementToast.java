package com.lordzintick.java_game.ui.widget;

import com.lordzintick.java_game.achievement.Achievement;
import com.lordzintick.pixel_krush.core.api.BaseScreen;
import com.lordzintick.pixel_krush.core.ui.api.Toast;

public class AchievementToast extends Toast {
    public final Achievement achievement;

    public AchievementToast(BaseScreen screen, Achievement achievement) {
        super(screen, achievement.icon, achievement.displayName);
        this.achievement = achievement;
    }
}
