package com.github.lordzintick.java_game.ui.widget;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.lordzintick.pixel_krush.core.api.BaseScreen;
import com.github.lordzintick.java_game.game.accessory.Accessory;
import com.github.lordzintick.java_game.game.entity.player.Player;
import com.github.lordzintick.pixel_krush.core.ui.api.Widget;

public class AccessorySlot extends Widget {

    public Accessory<?> slottedAccessory = null;
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
            batch.draw(screen.game.getCachedTexture("slot"), x, y, width, height);
        } else {
            batch.setColor(slottedAccessory.rarity.color);
            batch.draw(screen.game.getCachedTexture("slot"), x, y, width, height);

            batch.setColor(Color.WHITE);

            batch.draw(slottedAccessory.type.icon, x + (float) width / 4, y + (float) height / 4, (float) width / 2, (float) height / 2);

            if (hovering) {
                slottedAccessory.displayTooltip(batch, screen.game, x, y - height);
            }
        }
    }

    @Override
    public void click(int button) {
        if (button == Input.Buttons.LEFT && player.skillPoints.get() > 0) {
            screen.game.getAudioSample("place").play();
            player.equippedAccessories.add(slottedAccessory);
            player.equippingSkill = null;
            slottedAccessory.modify(player);
            player.skillPoints.set(player.skillPoints.get() - 1);
            if (player.skillPoints.get() <= 0) {
                screen.resume();
            }
        }
    }
}
