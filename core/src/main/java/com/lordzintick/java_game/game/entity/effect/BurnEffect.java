package com.lordzintick.java_game.game.entity.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector4;
import com.lordzintick.pixel_krush.core.api.AbstractGame;
import com.lordzintick.pixel_krush.core.api.TiledAtlas;
import com.lordzintick.pixel_krush.core.api.ecs.Effect;
import com.lordzintick.pixel_krush.core.api.ecs.LivingEntity;
import com.lordzintick.java_game.game.entity.player.Player;

public class BurnEffect extends Effect {
    private float fireTicks = 0.5f;

    public BurnEffect(AbstractGame game, float time, int level) {
        super(1, 0, game, time, level);
    }

    @Override
    public void apply(LivingEntity entity) {
        entity.colorModifier.set(Color.ORANGE);
    }

    @Override
    public void tick(LivingEntity entity, float deltaTime) {
        fireTicks -= deltaTime;
        if (fireTicks <= 0) {
            fireTicks = entity instanceof Player ? 2f : 0.5f;
            entity.damage(level, true);
        }

        TiledAtlas particlesAtlas = game.getCachedAtlas("particles");
        entity.screen.addParticle(
            new TextureRegion[]{particlesAtlas.get(0, 0), particlesAtlas.get(1, 0)},
            entity.x + game.getRandom().nextFloat(entity.width * entity.scale + 1),
            entity.y + game.getRandom().nextFloat(entity.height * entity.scale + 1),
            new Vector4(game.getRandom().nextFloat(-250, 250), game.getRandom().nextFloat(150, 250), -4f, 10f),
            5f,
            0.1f,
            5f
        );
    }

    @Override
    public void end(LivingEntity entity) {
        entity.colorModifier.set(Color.WHITE);
    }
}
