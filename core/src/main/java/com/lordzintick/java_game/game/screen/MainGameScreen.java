package com.lordzintick.java_game.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.lordzintick.java_game.GameData;
import com.lordzintick.java_game.game.GameMap;
import com.lordzintick.pixel_krush.core.util.audio.Sound;
import com.lordzintick.pixel_krush.core.api.AbstractGame;
import com.lordzintick.pixel_krush.core.api.AbstractGameScreen;
import com.lordzintick.pixel_krush.core.util.ListUtil;
import com.lordzintick.pixel_krush.core.util.Text;
import com.lordzintick.pixel_krush.core.util.UIUtil;
import com.lordzintick.java_game.game.Rarity;
import com.lordzintick.java_game.game.accessory.Accessory;
import com.lordzintick.java_game.game.accessory.AccessoryType;
import com.lordzintick.java_game.game.accessory.AccessoryTypes;
import com.lordzintick.java_game.game.entity.HostileEntity;
import com.lordzintick.java_game.game.entity.player.Player;
import com.lordzintick.java_game.game.entity.HostileSpawnManager;
import com.lordzintick.java_game.game.HealingCrystal;
import com.lordzintick.java_game.game.skill.SkillType;
import com.lordzintick.java_game.game.skill.SkillTypes;
import com.lordzintick.pixel_krush.core.ui.impl.TextButton;
import com.lordzintick.pixel_krush.core.ui.impl.TextLabel;
import com.lordzintick.java_game.ui.widget.AccessorySlot;
import com.lordzintick.java_game.ui.widget.SkillSlot;

import java.util.Collections;
import java.util.List;

/**
 * An implementation of the {@link AbstractGameScreen} class representing the main game screen containing the primary game area
 */
public class MainGameScreen extends AbstractGameScreen {
    public static final float MAP_SCALE = 8;
    private Player player;
    private Texture map;
    private TextLabel manaLabel;
    private TextLabel healthLabel;
    private TextLabel levelLabel;
    private TextLabel scoreLabel;
    public SkillSlot[] skillSlots;
    private final SkillSlot[] levelupSlots;
    private final AccessorySlot[] accessorySlots;
    private TextButton skipLevelupButton;
    private float spawnCooldown = 2f;
    private float objCooldown = 60f;
    private boolean shuffledSlots = false;
    public float difficultyMultiplier = 1;

    /**
     * Constructs a new {@link MainGameScreen} with the provided {@link AbstractGame}
     * @param game The {@link AbstractGame} instance that this screen is for
     */
    public MainGameScreen(AbstractGame game) {
        super(game);

        skillSlots = new SkillSlot[] {
            new SkillSlot(this, player, getMidX() - 202, 10, 0),
            new SkillSlot(this, player, getMidX() - 64, 10, 1),
            new SkillSlot(this, player, getMidX() + 74, 10, 2)
        };
        Collections.addAll(widgets, skillSlots);

        levelupSlots = new SkillSlot[] {
            new SkillSlot(this, player, getMidX() - 202, getMidY(), 3),
            new SkillSlot(this, player, getMidX() - 64, getMidY(), 4),
            new SkillSlot(this, player, getMidX() + 74, getMidY(), 5)
        };
        for (SkillSlot slot : levelupSlots) {
            slot.visible = false;
        }

        Collections.addAll(widgets, levelupSlots);

        accessorySlots = new AccessorySlot[] {
            new AccessorySlot(this, player, getMidX() - 202, getMidY() + 312),
            new AccessorySlot(this, player, getMidX() - 64, getMidY() + 312),
            new AccessorySlot(this, player, getMidX() + 74, getMidY() + 312)
        };
        Collections.addAll(widgets, accessorySlots);

        for (int i = 0; i < 3; i++) {
            skillSlots[i].slottedSkill = player.equippedSkills.get(i);
        }
    }

    public Player getPlayer() {return player;}

    @Override
    protected void populateInitialObjects() {
        player = new Player(this, game.getMetadata("selectedPlayerClass"));
        objects.add(player);

        map = game.getAssetOrThrow(((GameMap) game.getMetadata("selectedMap")).filename);
    }

    @Override
    protected void addWidgets() {
        widgets.add(new TextButton(this, new Text("Exit").setAlign(Align.center), 74, 25, 128, 40, () -> {
            game.getCamera().position.set(0,0,0);
            game.getCamera().update();
            game.changeScreen("title");
            game.resetScreen("main_game", new MainGameScreen(game));
        }));

        manaLabel = new TextLabel(this, new Text("manabanana"), getMidX() - 32, 158);
        widgets.add(manaLabel);

        healthLabel = new TextLabel(this, new Text("health-o-max"), getMidX() - 32, (int) (158 + game.getFont("normal").getLineHeight() * 1.5f));
        widgets.add(healthLabel);

        levelLabel = new TextLabel(this, new Text("levelavelo"), getMidX() - 32, (int) (158 + game.getFont("normal").getLineHeight() * 3));
        widgets.add(levelLabel);

        scoreLabel = new TextLabel(this, new Text("scoreo creme pie"), 140, (int) (Gdx.graphics.getHeight() - game.getFont("normal").getLineHeight() * 4));
        widgets.add(scoreLabel);

        skipLevelupButton = new TextButton(this, new Text("Skip").setAlign(Align.center), getMidX(), getMidY() - 20, 128, 40, () -> {
            player.equippingSkill = null;
            player.skillPoints.set(player.skillPoints.get() - 1);
            if (player.skillPoints.get() <= 0) {
                resume();
            }
        });
        skipLevelupButton.visible = false;
        widgets.add(skipLevelupButton);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        GameData gameData = game.getDataSerializerOrThrow("java_game_data");

        if (player.score.get() > gameData.highscore) {
            gameData.highscore = player.score.get();
        }

        BitmapFont font = game.getFont("normal");
        healthLabel.text = new Text("HP: " + player.health.get().intValue() + "/" + player.getMaxHealth()).setColor(Color.RED);
        healthLabel.x = getMidX() - UIUtil.getFontStringWidth(healthLabel.text.text, font) / 2;
        manaLabel.text = new Text("Mana: " + player.mana.get().intValue() + "/" + player.maxMana.get().intValue()).setColor(Color.BLUE);
        manaLabel.x = getMidX() - UIUtil.getFontStringWidth(manaLabel.text.text, font) / 2;
        scoreLabel.text = new Text("Score: " + player.score.get());
        levelLabel.text = new Text("Level " + player.level.get2() + " (" + player.level.get() + "/" + player.getRequiredXP() + ")").setColor(Color.GOLDENROD);
        levelLabel.x = getMidX() - UIUtil.getFontStringWidth(levelLabel.text.text, font) / 2;

        difficultyMultiplier += 0.002f * deltaTime;
        spawnCooldown -= deltaTime;
        if (spawnCooldown <= 0) {
            spawnCooldown = Math.max(game.getRandom().nextFloat(0.75f, 1.5f) - difficultyMultiplier / 2, 0.1f);
            HostileEntity mob = HostileSpawnManager.getWeightedRandomEntity(game.getRandom()).build(this, player);
            float w = 320 * MAP_SCALE;
            float hw = w / 2;
            float bw = w * 1.5f;
            float x = game.getRandom().nextFloat(-bw - 1, bw);
            float y = game.getRandom().nextFloat(-bw - 1, bw);

            if (x <= hw && x >= 0) {
                x = 0;
            }

            if (x > hw && x <= w) {
                x = w;
            }

            if (y <= hw && y >= 0) {
                y = 0;
            }

            if (y > hw && y <= w) {
                y = w;
            }

            mob.x = x;
            mob.y = y;
            mob.health.set(mob.health.get() * difficultyMultiplier);
            mob.damage *= Math.max(1, (difficultyMultiplier - 1f) / 2);
            objects.add(mob);
        }

        objCooldown -= deltaTime;
        if (objCooldown <= 0) {
            objCooldown = 30f;
            HealingCrystal crystal = new HealingCrystal(this);
            crystal.x = game.getRandom().nextFloat(0, Gdx.graphics.getWidth());
            crystal.y = game.getRandom().nextFloat(0, Gdx.graphics.getWidth());
            objects.add(crystal);
        }

        for (int i = 0; i < 3; i++) {
            skillSlots[i].slottedSkill = player.equippedSkills.get(i);
        }
    }

    @Override
    public void renderGame(float deltaTime) {
        game.getBatch("game").draw(map, 0, 0, 320 * MAP_SCALE, 320 * MAP_SCALE);
        super.renderGame(deltaTime);
    }

    @Override
    public void renderUI(float deltaTime) {
        super.renderUI(deltaTime);
        SpriteBatch uiBatch = game.getBatch("ui");
        player.skillPoints.set(Math.max(0, player.skillPoints.get()));

        if (player.skillPoints.get() > 0) {
            if (!shuffledSlots) {
                shuffleLevelupSlots();
                shuffleAccessorySlots();
            }

            for (SkillSlot slot : levelupSlots) {
                slot.render(uiBatch, deltaTime);
                slot.visible = true;
            }

            for (AccessorySlot slot : accessorySlots) {
                slot.render(uiBatch, deltaTime);
                slot.visible = true;
            }

            skipLevelupButton.visible = true;

            if (player.equippingSkill != null) {
                uiBatch.setColor(player.equippingSkill.type.rarity.color);
                uiBatch.draw(game.getCachedTexture("slot"), Gdx.input.getX(), -Gdx.input.getY() + Gdx.graphics.getHeight(), 128, 128);
                uiBatch.setColor(Color.WHITE);
                uiBatch.draw(player.equippingSkill.type.icon, Gdx.input.getX() + 32, -Gdx.input.getY() + Gdx.graphics.getHeight() + 32, 64, 64);
            }
        } else {
            shuffledSlots = false;

            for (SkillSlot slot : levelupSlots) {
                slot.visible = false;
            }

            for (AccessorySlot slot : accessorySlots) {
                slot.visible = false;
            }

            skipLevelupButton.visible = false;
        }

        if (player.remainingDashCooldown > 0) {
            String text = Math.floor(player.remainingDashCooldown * 10) / 10 + "s";
            game.getFont("normal").draw(uiBatch, text, getMidX() - UIUtil.getFontStringWidth(text, game.getFont("normal")) / 2, (int) (158 + game.getFont("normal").getLineHeight() * 4.5));
        }
    }

    public void shuffleLevelupSlots() {
        shuffledSlots = true;
        for (SkillSlot slot : levelupSlots) {
            SkillType type = SkillTypes.getWeightedRandomSkillType(player, levelupSlots);
            if (type != null) {
                slot.slottedSkill = type.getInstance();
            } else {
                slot.slottedSkill = null;
            }
        }
    }

    public void shuffleAccessorySlots() {
        shuffledSlots = true;
        for (AccessorySlot slot : accessorySlots) {
            AccessoryType type = AccessoryTypes.getWeightedRandomAccessoryType(game, accessorySlots);
            if (type != null) {
                slot.slottedAccessory = type.getInstance(
                    Rarity.getWeightedRandomRarity(game.getRandom()),
                    type.global ? ListUtil.listOf(Accessory.ALL) : Accessory.getRandomTargets(player, game.getRandom())
                );
            } else {
                slot.slottedAccessory = null;
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
    }

    @Override
    public List<Sound> getBackgroundMusic() {
        return ListUtil.listOf(
            game.getAudioSample("game_music_1"),
            game.getAudioSample("game_music_2"),
            game.getAudioSample("game_music_3"),
            game.getAudioSample("game_music_4"),
            game.getAudioSample("game_music_5")
        );
    }
}
