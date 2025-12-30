package com.lordzintick.game.entity.effect;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lordzintick.MainGame;
import com.lordzintick.game.entity.Entity;

public abstract class Effect {
    public final TextureRegion sprite;
    public final MainGame game;
    public float timeLeft;
    public int level;

    protected Effect(TextureRegion sprite, MainGame game, float time, int level) {
        this.sprite = sprite;
        this.game = game;
        this.timeLeft = time;
        this.level = level;
    }

    public abstract void apply(Entity entity);
    public abstract void tick(Entity entity, float deltaTime);
    public abstract void end(Entity entity);
}
