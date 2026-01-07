package com.lordzintick.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Align;
import com.lordzintick.MainGame;
import com.lordzintick.audio.AudioManager;
import com.lordzintick.audio.Sound;
import com.lordzintick.game.Rarity;
import com.lordzintick.game.accessory.Accessory;
import com.lordzintick.game.accessory.AccessoryType;
import com.lordzintick.game.entity.HostileEntity;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.game.entity.EntityHelper;
import com.lordzintick.game.HealingCrystal;
import com.lordzintick.game.skill.SkillType;
import com.lordzintick.ui.widget.AccessorySlot;
import com.lordzintick.ui.widget.SkillSlot;
import com.lordzintick.ui.widget.TextButton;
import com.lordzintick.ui.widget.TextLabel;
import com.lordzintick.util.ListUtil;
import com.lordzintick.util.Text;
import com.lordzintick.util.UIUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An implementation of the {@link AbstractGameScreen} class representing the main game screen containing the primary game area
 */
public class MainGameScreen extends AbstractGameScreen {
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
    public final float mapScale = 8;

    /**
     * Constructs a new {@link MainGameScreen} with the provided {@link MainGame}
     * @param game The {@link MainGame} instance that this screen is for
     */
    public MainGameScreen(MainGame game) {
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

        for (int i = 0; i < player.equippedSkills.length; i++) {
            skillSlots[i].slottedSkill = player.equippedSkills[i];
        }
    }

    public Player getPlayer() {return player;}

    @Override
    protected void populateInitialObjects() {
        player = new Player(this, game.selectedPlayerClass);
        queueAddObject(player);

        map = game.assets.get(game.selectedMap.filename);
    }

    @Override
    protected void addWidgets() {
        widgets.add(new TextButton(this, new Text("Exit").setAlign(Align.center), 74, 25, 128, 40, () -> {
            game.camera.position.set(0,0,0);
            game.camera.update();
            game.changeScreen(game.screenHolder.TITLE);
            game.screenHolder.MAIN_GAME = new MainGameScreen(game);
        }));

        manaLabel = new TextLabel(this, new Text("manabanana"), getMidX() - 32, 158);
        widgets.add(manaLabel);

        healthLabel = new TextLabel(this, new Text("health-o-max"), getMidX() - 32, (int) (158 + game.font.getLineHeight() * 1.5f));
        widgets.add(healthLabel);

        levelLabel = new TextLabel(this, new Text("levelavelo"), getMidX() - 32, (int) (158 + game.font.getLineHeight() * 3));
        widgets.add(levelLabel);

        scoreLabel = new TextLabel(this, new Text("scoreo creme pie"), 140, (int) (Gdx.graphics.getHeight() - game.font.getLineHeight() * 4));
        widgets.add(scoreLabel);

        skipLevelupButton = new TextButton(this, new Text("Skip").setAlign(Align.center), getMidX(), getMidY() - 20, 128, 40, () -> {
            player.equippingSkill = null;
            player.skillPoints--;
            if (player.skillPoints <= 0) {
                resume();
            }
        });
        skipLevelupButton.visible = false;
        widgets.add(skipLevelupButton);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (player.score > game.gameData.highscore) {
            game.gameData.highscore = player.score;
        }

        healthLabel.text = new Text("HP: " + (int) player.health + "/" + player.getMaxHealth()).setColor(Color.RED);
        healthLabel.x = getMidX() - UIUtil.getFontStringWidth(healthLabel.text.text, game.font) / 2;
        manaLabel.text = new Text("Mana: " + player.mana + "/" + player.maxMana).setColor(Color.BLUE);
        manaLabel.x = getMidX() - UIUtil.getFontStringWidth(manaLabel.text.text, game.font) / 2;
        scoreLabel.text = new Text("Score: " + player.score);
        levelLabel.text = new Text("Level " + player.level + " (" + (int) player.xp + "/" + player.getRequiredXP() + ")").setColor(Color.GOLDENROD);
        levelLabel.x = getMidX() - UIUtil.getFontStringWidth(levelLabel.text.text, game.font) / 2;

        difficultyMultiplier += 0.002f * deltaTime;
        spawnCooldown -= deltaTime;
        if (spawnCooldown <= 0) {
            spawnCooldown = Math.max(game.random.nextFloat(0.75f, 1.5f) - difficultyMultiplier / 2, 0.1f);
            HostileEntity mob = EntityHelper.getWeightedRandomEntity(game.random).build(this, player);
            float w = 320 * mapScale;
            float hw = w / 2;
            float bw = w * 1.5f;
            float x = game.random.nextFloat(-bw - 1, bw);
            float y = game.random.nextFloat(-bw - 1, bw);

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
            mob.health *= difficultyMultiplier;
            mob.damage *= Math.max(1, (difficultyMultiplier - 1f) / 2);
            queueAddObject(mob);
        }

        objCooldown -= deltaTime;
        if (objCooldown <= 0) {
            objCooldown = 30f;
            HealingCrystal crystal = new HealingCrystal(this);
            crystal.x = game.random.nextFloat(0, Gdx.graphics.getWidth());
            crystal.y = game.random.nextFloat(0, Gdx.graphics.getWidth());
            queueAddObject(crystal);
        }
    }

    @Override
    public void renderGame(float deltaTime) {
        game.gameBatch.draw(map, 0, 0, 320 * mapScale, 320 * mapScale);
        super.renderGame(deltaTime);
    }

    @Override
    public void renderUI(float deltaTime) {
        super.renderUI(deltaTime);
        player.skillPoints = Math.max(0, player.skillPoints);

        if (player.skillPoints > 0) {
            if (!shuffledSlots) {
                shuffleLevelupSlots();
                shuffleAccessorySlots();
            }

            for (SkillSlot slot : levelupSlots) {
                slot.render(game.uiBatch, deltaTime);
                slot.visible = true;
            }

            for (AccessorySlot slot : accessorySlots) {
                slot.render(game.uiBatch, deltaTime);
                slot.visible = true;
            }

            skipLevelupButton.visible = true;

            if (player.equippingSkill != null) {
                game.uiBatch.setColor(player.equippingSkill.type.rarity.color);
                game.uiBatch.draw(game.slotTexture, Gdx.input.getX(), -Gdx.input.getY() + Gdx.graphics.getHeight(), 128, 128);
                game.uiBatch.setColor(Color.WHITE);
                game.uiBatch.draw(player.equippingSkill.type.icon, Gdx.input.getX() + 32, -Gdx.input.getY() + Gdx.graphics.getHeight() + 32, 64, 64);
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
            game.font.draw(game.uiBatch, text, getMidX() - UIUtil.getFontStringWidth(text, game.font) / 2, (int) (158 + game.font.getLineHeight() * 4.5));
        }
    }

    public void shuffleLevelupSlots() {
        shuffledSlots = true;
        for (SkillSlot slot : levelupSlots) {
            SkillType type = game.skillTypes.getWeightedRandomSkillType(player, levelupSlots);
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
            AccessoryType type = game.accessoryTypes.getWeightedRandomAccessoryType(accessorySlots);
            if (type != null) {
                slot.slottedAccessory = type.getInstance(
                    Rarity.getWeightedRandomRarity(game.random),
                    type.global ? ListUtil.listOf("ALL") : Accessory.getRandomTargets(player, game.random)
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
            game.audio.get("game_music_1"),
            game.audio.get("game_music_2"),
            game.audio.get("game_music_3"),
            game.audio.get("game_music_4"),
            game.audio.get("game_music_5")
        );
    }
}
