package com.lordzintick.game.entity.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector4;
import com.lordzintick.MainGame;
import com.lordzintick.game.entity.Entity;
import com.lordzintick.game.entity.player.Player;

public class BurnEffect extends Effect {
    private float fireTicks = 0.5f;

    public BurnEffect(MainGame game, float time, int level) {
        super(game.effectAtlas[0][1], game, time, level);
    }

    @Override
    public void apply(Entity entity) {
        entity.colorModifier = Color.ORANGE;
    }

    @Override
    public void tick(Entity entity, float deltaTime) {
        fireTicks -= deltaTime;
        if (fireTicks <= 0) {
            fireTicks = entity instanceof Player ? 2f : 0.5f;
            entity.damage(level);
        }

        entity.screen.addParticle(
            new TextureRegion[]{game.particlesAtlas[0][0], game.particlesAtlas[0][1]},
            entity.x + game.random.nextFloat(entity.width * entity.scale + 1),
            entity.y + game.random.nextFloat(entity.height * entity.scale + 1),
            new Vector4(game.random.nextFloat(-250, 250), game.random.nextFloat(150, 250), -4f, 10f),
            5f,
            0.1f,
            5f
        );
    }

    @Override
    public void end(Entity entity) {
        entity.colorModifier = Color.WHITE;
    }
}
