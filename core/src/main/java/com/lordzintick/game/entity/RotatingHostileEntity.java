package com.lordzintick.game.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lordzintick.game.entity.effect.Effect;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.game.screen.AbstractGameScreen;

public abstract class RotatingHostileEntity extends HostileEntity {
    public float angle = 0;

    /**
     * Constructs a new {@link Entity} with the provided spritesheet, which will be split into regions of the provided size
     *
     * @param screen  The {@link AbstractGameScreen} containing this entity
     * @param texture The spritesheet for this entity to use
     * @param width   The width of the entity's image
     * @param height  The height of the entity's image
     */
    public RotatingHostileEntity(AbstractGameScreen screen, Texture texture, int width, int height, Player player) {
        super(screen, texture, width, height, player);
    }

    @Override
    public void render(Batch batch, float deltaTime) {
        animTicks += deltaTime;
        if (animTicks >= getFrameTime()) {
            animTicks = 0;
            if (frame == getFrameCount() - 1)
                frame = 0;
            else
                frame++;
        }

        batch.draw(textures[frame][0], x, y, (float) width / 2 * scale, (float) height / 2 * scale, width * scale, height * scale, 1, 1, angle + 90);

        batch.setColor(colorModifier.cpy().sub(0, 0, 0, 0.5f));
        if (iframes > 0) batch.setColor(Color.RED);
        batch.draw(textures[frame][0], x, y, (float) width / 2 * scale, (float) height / 2 * scale, width * scale, height * scale, 1, 1, angle + 90);
        batch.setColor(Color.WHITE);

        for (int i = 0; i < effects.size(); i++) {
            Effect effect = effects.get(i);
            batch.draw(effect.sprite, x + (float) width / 2 * scale - 8, y + height * scale + 10 + i * 26, 16, 16);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        angle = moveVector.angleDeg();
    }
}
