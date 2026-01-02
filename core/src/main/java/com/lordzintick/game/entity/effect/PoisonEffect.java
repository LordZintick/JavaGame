package com.lordzintick.game.entity.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector4;
import com.lordzintick.MainGame;
import com.lordzintick.game.entity.Entity;
import com.lordzintick.game.entity.player.Player;

public class PoisonEffect extends Effect {
    private float poisonTicks = 1f;

    public PoisonEffect(MainGame game, float time, int level) {
        super(game.effectAtlas[1][0], game, time, level);
    }

    @Override
    public void apply(Entity entity) {
        entity.colorModifier = Color.GREEN;
    }

    @Override
    public void tick(Entity entity, float deltaTime) {
        poisonTicks -= deltaTime;
        if (poisonTicks <= 0) {
            poisonTicks = entity instanceof Player ? 3f : 1f;
            entity.damage(level * 2, true);
        }

        entity.screen.addParticle(
            new TextureRegion[]{game.particlesAtlas[1][2]},
            entity.x + game.random.nextFloat(entity.width * entity.scale + 1),
            entity.y + game.random.nextFloat(entity.height * entity.scale + 1),
            new Vector4(game.random.nextFloat(-50, 50), game.random.nextFloat(50, 125), -3f, 5f),
            8f,
            0.1f,
            5f
        );
    }

    @Override
    public void end(Entity entity) {
        entity.colorModifier = Color.WHITE;
    }
}
