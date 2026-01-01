package com.lordzintick.ui.widget;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.lordzintick.core.Logger;
import com.lordzintick.game.accessory.Accessory;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.game.entity.player.StatModifier;
import com.lordzintick.util.BaseScreen;

public class AccessorySlot extends Widget {
    private static final Logger LOGGER = new Logger(AccessorySlot.class);

    public Accessory slottedAccessory = null;
    private final Player player;

    public AccessorySlot(BaseScreen screen, Player player, int x, int y) {
        super(screen, 128, 128);
        this.player = player;
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(Batch batch, float deltaTime) {
        super.render(batch, deltaTime);
        if (!visible) return;

        if (slottedAccessory == null) {
            batch.draw(screen.game.slotTexture, x, y, width, height);
        } else {
            batch.setColor(slottedAccessory.rarity.color);
            batch.draw(screen.game.slotTexture, x, y, width, height);

            batch.setColor(Color.WHITE);

            batch.draw(slottedAccessory.type.icon, x + (float) width / 4, y + (float) height / 4, (float) width / 2, (float) height / 2);

            if (hovering) {
                slottedAccessory.displayTooltip(batch, screen.game, x, y - height);
            }
        }
    }

    @Override
    public void click(int button) {
        if (button == Input.Buttons.LEFT && player.skillPoints > 0) {
            player.accessories.add(slottedAccessory);
            slottedAccessory.modify(player);
            player.skillPoints--;
            if (player.skillPoints <= 0) {
                screen.resume();
            }
        }
    }
}
