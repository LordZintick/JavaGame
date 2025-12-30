package com.lordzintick.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.lordzintick.MainGame;
import com.lordzintick.audio.AudioManager;
import com.lordzintick.audio.Sound;
import com.lordzintick.game.entity.HostileEntity;
import com.lordzintick.game.entity.Player;
import com.lordzintick.game.entity.EntityHelper;
import com.lordzintick.ui.widget.SpellSlot;
import com.lordzintick.ui.widget.TextButton;
import com.lordzintick.ui.widget.TextLabel;
import com.lordzintick.util.Text;
import com.lordzintick.util.UIUtil;

import java.util.Collections;

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
    public SpellSlot[] spellSlots;
    private final SpellSlot[] levelupSlots;
    private TextButton skipLevelupButton;
    private float spawnCooldown = 2f;
    private boolean shuffledSlots = false;
    private int totalEnemyWeight = 0;

    /**
     * Constructs a new {@link MainGameScreen} with the provided {@link MainGame}
     * @param game The {@link MainGame} instance that this screen is for
     */
    public MainGameScreen(MainGame game) {
        super(game);

        spellSlots = new SpellSlot[] {
            new SpellSlot(this, player, getMidX() - 202, 10, 0),
            new SpellSlot(this, player, getMidX() - 64, 10, 1),
            new SpellSlot(this, player, getMidX() + 74, 10, 2)
        };
        Collections.addAll(widgets, spellSlots);

        levelupSlots = new SpellSlot[] {
            new SpellSlot(this, player, getMidX() - 202, getMidY(), 3),
            new SpellSlot(this, player, getMidX() - 64, getMidY(), 4),
            new SpellSlot(this, player, getMidX() + 74, getMidY(), 5)
        };
        for (SpellSlot slot : levelupSlots) {
            slot.visible = false;
        }

        Collections.addAll(widgets, levelupSlots);
    }

    public Player getPlayer() {return player;}

    @Override
    protected void populateInitialObjects() {
        player = new Player(this);
        queueAddObject(player);

        map = new Texture("textures/map.png");
    }

    @Override
    protected void addWidgets() {
        widgets.add(new TextButton(this, new Text("Exit").setAlign(UIUtil.CENTER), 74, 25, 128, 40, () -> {
            game.camera.position.set(0,0,0);
            game.camera.update();
            game.changeScreen(game.screenHolder.TITLE);
        }));

        manaLabel = new TextLabel(this, new Text("manabanana"), getMidX() - 32, 158);
        widgets.add(manaLabel);

        healthLabel = new TextLabel(this, new Text("health-o-max"), getMidX() - 32, (int) (158 + game.font.getLineHeight() * 1.5f));
        widgets.add(healthLabel);

        levelLabel = new TextLabel(this, new Text("levelavelo"), getMidX() - 32, (int) (158 + game.font.getLineHeight() * 3));
        widgets.add(levelLabel);

        scoreLabel = new TextLabel(this, new Text("scoreo creme pie"), 140, (int) (Gdx.graphics.getHeight() - game.font.getLineHeight() * 4));
        widgets.add(scoreLabel);

        skipLevelupButton = new TextButton(this, new Text("Skip"), getMidX() - 64, getMidY() - 20, 128, 40, () -> {
            player.equippingSpell = null;
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
        healthLabel.text = new Text("HP: " + player.health + "/" + player.getMaxHealth()).setColor(Color.RED);
        manaLabel.text = new Text("Mana: " + player.mana + "/" + player.maxMana).setColor(Color.BLUE);
        scoreLabel.text = new Text("Score: " + player.score);
        levelLabel.text = new Text("Level " + player.level + " (" + player.xp + "/" + player.getRequiredXP() + ")").setColor(Color.GOLDENROD);

        for (int i = 0; i < player.equippedSpells.length; i++) {
            spellSlots[i].slottedSpell = player.equippedSpells[i];
        }

        spawnCooldown -= deltaTime;
        if (spawnCooldown <= 0) {
            spawnCooldown = game.random.nextFloat(0.75f, 1.5f);
            HostileEntity mob = EntityHelper.getWeightedRandomEntity(game.random).build(this, player);
            float w = Gdx.graphics.getWidth();
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
            queueAddObject(mob);
        }
    }

    @Override
    public void renderGame(float deltaTime) {
        game.gameBatch.draw(map, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getWidth());
        super.renderGame(deltaTime);
    }

    @Override
    public void renderUI(float deltaTime) {
        super.renderUI(deltaTime);
        player.skillPoints = Math.max(0, player.skillPoints);

        if (player.skillPoints > 0) {
            if (!shuffledSlots) shuffleLevelupSlots();

            for (SpellSlot slot : levelupSlots) {
                slot.render(game.uiBatch, deltaTime);
                slot.visible = true;
            }
            skipLevelupButton.visible = true;

            if (player.equippingSpell != null) {
                game.uiBatch.setColor(player.equippingSpell.rarity.color);
                game.uiBatch.draw(levelupSlots[0].slotTexture, Gdx.input.getX(), -Gdx.input.getY() + Gdx.graphics.getHeight(), 128, 128);
                game.uiBatch.setColor(Color.WHITE);
                game.uiBatch.draw(player.equippingSpell.icon, Gdx.input.getX() + 32, -Gdx.input.getY() + Gdx.graphics.getHeight() + 32, 64, 64);
            }
        } else {
            shuffledSlots = false;

            for (SpellSlot slot : levelupSlots) {
                slot.visible = false;
            }
            skipLevelupButton.visible = false;
        }
    }

    public void shuffleLevelupSlots() {
        shuffledSlots = true;
        for (SpellSlot slot : levelupSlots) {
            slot.slottedSpell = game.spells.getRandomSpell();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
    }

    @Override
    public Sound getBackgroundMusic() {
        return AudioManager.GAME_MUSIC;
    }
}
