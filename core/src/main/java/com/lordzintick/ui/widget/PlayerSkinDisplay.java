package com.lordzintick.ui.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lordzintick.game.entity.player.PlayerClass;
import com.lordzintick.util.BaseScreen;

import java.util.Locale;

public class PlayerSkinDisplay extends Widget {
    private PlayerClass playerClass;
    private float animTicks = 0;
    private int frame = 0;
    private TextureRegion[][] classFrames;

    public PlayerSkinDisplay(BaseScreen screen, PlayerClass playerClass, int x, int y) {
        super(screen, 96, 192);
        this.x = x;
        this.y = y;
        this.playerClass = playerClass;
        classFrames = screen.game.playerTextures.get(playerClass);
    }

    public void changeClass(PlayerClass newClass) {
        playerClass = newClass;
        classFrames = screen.game.playerTextures.get(playerClass);
    }

    public PlayerClass getDisplayedClass() {return playerClass;}

    @Override
    public void render(Batch batch, float deltaTime) {
        super.render(batch, deltaTime);
        animTicks += deltaTime;
        if (animTicks >= 0.35f) {
            animTicks = 0;
            if (frame >= classFrames.length - 1) {
                frame = 0;
            } else {
                frame++;
            }
        }

        if (!screen.game.gameData.unlockedClasses.get(playerClass.name().toLowerCase(Locale.ROOT))) {
            batch.setColor(Color.BLACK);
        }
        batch.draw(classFrames[frame][0], x, y, width, height);
        batch.setColor(Color.WHITE);
    }
}
