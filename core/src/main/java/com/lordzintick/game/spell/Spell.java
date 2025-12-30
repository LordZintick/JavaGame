package com.lordzintick.game.spell;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lordzintick.MainGame;
import com.lordzintick.game.entity.Player;
import com.lordzintick.util.Text;

import java.util.List;
import java.util.function.Consumer;

public class Spell {
    public final String displayName;
    public final Rarity rarity;
    public final List<Text> description;
    public final TextureRegion icon;
    public final int manaCost;
    public final float cooldown;
    public final Consumer<Player> action;
    private float remainingCooldown;

    public Spell(String displayName, Rarity rarity, List<Text> description, TextureRegion icon, int manaCost, float cooldown, Consumer<Player> action) {
        this.displayName = displayName;
        this.rarity = rarity;
        this.description = description;
        this.icon = icon;
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        this.action = action;
    }

    public void displayTooltip(Batch batch, MainGame game, float mouseX, float mouseY, float width) {
        float height = (description.size() + 2) * game.font.getLineHeight();
        game.spells.tooltipTexture.draw(batch, mouseX, mouseY, width, height);
        batch.setColor(rarity.color);
        game.font.draw(batch, displayName, mouseX + width / 8, mouseY + height - height / 8);

        for (int i = 0; i < description.size(); i++) {
            Text line = description.get(i);
            batch.setColor(line.color);
            game.font.draw(batch, line.text, mouseX + width / 8, mouseY + height - (height / 8) - (game.font.getLineHeight() * (3 + i)));
        }
        batch.setColor(Color.WHITE);
    }

    public void cast(Player player) {
        if (player.mana >= manaCost && remainingCooldown <= 0) {
            remainingCooldown = cooldown;
            player.mana -= manaCost + 1;
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
