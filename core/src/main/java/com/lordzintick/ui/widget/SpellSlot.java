package com.lordzintick.ui.widget;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.lordzintick.core.Logger;
import com.lordzintick.game.entity.Player;
import com.lordzintick.game.spell.Spell;
import com.lordzintick.util.BaseScreen;

public class SpellSlot extends Widget {
    private static final Logger LOGGER = new Logger(SpellSlot.class);

    public Spell slottedSpell = null;
    public final Texture slotTexture;
    public final Texture cooldownTexture;
    private final Player player;
    private final int index;

    public SpellSlot(BaseScreen screen, Player player, int x, int y, int index) {
        super(screen, 128, 128);
        this.player = player;
        this.index = index;
        this.x = x;
        this.y = y;
        slotTexture = new Texture("textures/slot.png");
        cooldownTexture = new Texture("textures/cooldown.png");
    }

    @Override
    public void render(Batch batch, float deltaTime) {
        super.render(batch, deltaTime);
        if (!visible) return;

        if (slottedSpell == null) {
            batch.draw(slotTexture, x, y, width, height);
        } else {
            batch.setColor(slottedSpell.rarity.color);
            batch.draw(slotTexture, x, y, width, height);

            batch.setColor(Color.WHITE);

            if (index < 3) {
                float cooldownValue = slottedSpell.getRemainingCooldown() / slottedSpell.cooldown;
                batch.draw(cooldownTexture, x, y, width, Math.max(0, height * cooldownValue));
            }

            batch.draw(slottedSpell.icon, x + (float) width / 4, y + (float) height / 4, (float) width / 2, (float) height / 2);

            if (hovering) {
                slottedSpell.displayTooltip(batch, screen.game, x + width, y + height);
            }
        }
    }

    @Override
    public void click(int button) {
        if (button == Input.Buttons.LEFT && player.skillPoints > 0) {
            if (player.equippingSpell != null && index < 3) {
                LOGGER.log("Equipping spell \"" + player.equippingSpell.displayName + "\" into slot " + index);
                this.slottedSpell = player.equippingSpell;
                player.equippedSpells[index] = player.equippingSpell;
                player.equippingSpell = null;
                player.skillPoints--;
                if (player.skillPoints <= 0) {
                    screen.resume();
                }
                return;
            }

            if (player.equippingSpell == null && index >= 3) {
                LOGGER.log("Selecting spell \"" + slottedSpell.displayName + "\" from slot " + index);
                player.equippingSpell = this.slottedSpell;
            }
        }
    }
}
