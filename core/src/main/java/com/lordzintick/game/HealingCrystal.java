package com.lordzintick.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.lordzintick.audio.AudioManager;
import com.lordzintick.game.entity.player.Player;
import com.lordzintick.game.screen.AbstractGameScreen;

public class HealingCrystal extends AbstractGameObject {
    private final Texture texture;
    /**
     * Constructs a new game object in the provided screen
     *
     * @param screen The {@link AbstractGameScreen} that is the parent/holder of this game object
     */
    public HealingCrystal(AbstractGameScreen screen) {
        super(screen);
        this.width = 16;
        this.height = 16;
        texture = screen.game.assets.get("textures/game/healing_crystal.png");
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.collisionRect.set(x, y, width * 6, height * 6);
    }

    @Override
    public void render(Batch batch, float deltaTime) {
        batch.draw(texture, x, y, width * 6, height * 6);
    }

    @Override
    public void collide(AbstractGameObject other) {
        if (other instanceof Player && ((Player) other).health < ((Player) other).maxHealth) {
            ((Player) other).heal(5);
            this.shouldRemove = true;
        }
    }
}
