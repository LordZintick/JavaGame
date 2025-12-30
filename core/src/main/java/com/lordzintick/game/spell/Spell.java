package com.lordzintick.game.spell;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lordzintick.MainGame;
import com.lordzintick.audio.Sound;
import com.lordzintick.game.entity.Player;
import com.lordzintick.game.screen.AbstractGameScreen;
import com.lordzintick.util.Text;
import com.lordzintick.util.UIUtil;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Spell {
    public final String displayName;
    public final Rarity rarity;
    public final List<Text> description;
    public final TextureRegion icon;
    public final int manaCost;
    public final float cooldown;
    public final Sound castSound;
    public final Consumer<Player> action;
    private float remainingCooldown;

    public Spell(String displayName, Rarity rarity, List<Text> description, TextureRegion icon, int manaCost, float cooldown, Sound castSound, Consumer<Player> action) {
        this.displayName = displayName;
        this.rarity = rarity;
        this.description = description;
        this.icon = icon;
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        this.castSound = castSound;
        this.action = action;
    }

    public void displayTooltip(Batch batch, MainGame game, float x, float y) {
        float height = (description.size() + 4) * game.font.getLineHeight();
        float width = Math.max(UIUtil.getFontStringWidth(displayName + "(0.0s)", game.font), UIUtil.getFontStringWidth(rarity.name(), game.font));

        for (Text line : description) {
            float lineWidth = UIUtil.getFontStringWidth(line.text, game.font);
            if (lineWidth > width) {
                width = lineWidth;
            }
        }

        width += 40;

        game.spells.tooltipTexture.draw(batch, x, y, width, height);
        batch.setColor(rarity.color);
        game.font.setColor(rarity.color);
        if (isOnCooldown()) {
            game.font.draw(batch, displayName + "(" + Math.floor(remainingCooldown * 10) / 10 + "s)", x + width / 8, y + height - height / 8);
        } else {
            game.font.draw(batch, displayName, x + width / 16, y + height - height / 8);
        }
        game.spells.tooltipOverlay.draw(batch, x, y, width, height);
        game.font.draw(batch, rarity.name(), x + width / 16, y + height - height / 3f);

        for (int i = 0; i < description.size(); i++) {
            Text line = description.get(i);
            game.font.setColor(line.color);
            game.font.draw(batch, line.text, x - width / 16, y + height - (game.font.getLineHeight() * (4 + i)), width, UIUtil.RIGHT, true);
        }
        batch.setColor(Color.WHITE);
        game.font.setColor(Color.WHITE);
    }

    public void cast(Player player) {
        if (player.mana >= manaCost && remainingCooldown <= 0) {
            remainingCooldown = cooldown;
            player.mana -= manaCost + 1;
            if (!castSound.stream) {
                castSound.play();
            }
            action.accept(player);
        }
    }

    public void tick(float deltaTime) {
        if (remainingCooldown > 0) {
            remainingCooldown -= deltaTime;
        }
    }

    public boolean isOnCooldown() {
        return remainingCooldown > 0;
    }

    public float getRemainingCooldown() {
        return remainingCooldown;
    }
}
