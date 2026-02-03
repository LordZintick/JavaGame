package com.github.lordzintick.java_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.lordzintick.java_game.achievement.Achievements;
import com.github.lordzintick.java_game.game.GameMap;
import com.github.lordzintick.java_game.game.entity.player.StatModifierTypes;
import com.github.lordzintick.java_game.game.screen.MainGameScreen;
import com.github.lordzintick.java_game.registrar.AudioRegistrar;
import com.github.lordzintick.java_game.ui.screen.AchievementScreen;
import com.github.lordzintick.java_game.ui.screen.RunConfigScreen;
import com.github.lordzintick.java_game.ui.screen.TitleScreen;
import com.github.lordzintick.pixel_krush.core.api.AbstractGame;
import com.github.lordzintick.java_game.game.accessory.AccessoryTypes;
import com.github.lordzintick.java_game.game.entity.player.PlayerClass;
import com.github.lordzintick.java_game.game.skill.SkillTypes;
import com.github.lordzintick.java_game.registrar.KeybindRegistrar;

import java.util.Locale;

/**
 * The main game class used for everything else
 */
public class MainGame extends AbstractGame {
    private GameData gameData;

    @Override
    public String getNamespace() {
        return "java_game";
    }

    @Override
    public String getStartScreen() {
        return "title";
    }

    @Override
    protected void initialize() {
        gameData = registerDataSerializer("java_game_data", new GameData(this));

        createRegistry("achievements");
        createRegistry("accessories");
        createRegistry("skill_types");
        createRegistry("stat_modifier_types");

        connectRegistrar(getId("audio"), AudioRegistrar::registrar);
        connectRegistrar(getId("keybinds"), KeybindRegistrar::registrar);

        connectRegistrar(getId("skill_types"), SkillTypes::registrar);
        connectRegistrar(getId("stat_modifier_types"), StatModifierTypes::registrar);
        connectRegistrar(getId("accessories"), AccessoryTypes::registrar);
        connectRegistrar(getId("achievements"), Achievements::registrar);

        putMetadata("selectedPlayerClass", PlayerClass.MAGE);
        putMetadata("selectedMap", GameMap.CLASSIC);
    }

    @Override
    protected void postInit() {
        addScreen("title", new TitleScreen(this));
        addScreen("run_config", new RunConfigScreen(this));
        addScreen("achievements", new AchievementScreen(this));
        addScreen("main_game", new MainGameScreen(this));
    }

    @Override
    protected void loadAssets() {
        createAtlas("textures/achievements.png", 16, 16);
        createAtlas("textures/game/accessories.png", 8, 8);
        createAtlas("textures/ui/effects.png", 8, 8);
        createAtlas("textures/game/particles.png", 2, 2);
        createAtlas("textures/game/skills/skills.png", 8, 8);
        for (PlayerClass clazz : PlayerClass.values()) {
            createAtlas("textures/game/player/player_" + clazz.name().toLowerCase(Locale.ROOT) + ".png", 6, 12);
        }

        createNinePatch("textures/ui/tooltip.png", 2, 2, 2, 2);
        createNinePatch("textures/ui/tooltip_overlay.png", 3, 3, 3, 3);

        cacheTexture("textures/ui/slot.png");
        cacheTexture("textures/ui/cooldown.png");
    }

    @Override
    protected void gameRender() {
        Achievements.checkAllUnachieved(this);
    }

    @Override
    protected void uiRender() {
        BitmapFont outlinedFont = getFont("outlined");
        SpriteBatch uiBatch = getBatch("ui");
        outlinedFont.draw(uiBatch, "Highscore: " + gameData.highscore, 40, Gdx.graphics.getHeight() - outlinedFont.getLineHeight() * 5);
        outlinedFont.setColor(Color.WHITE);
    }
}
