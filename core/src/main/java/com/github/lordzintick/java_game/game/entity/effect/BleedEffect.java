package com.github.lordzintick.java_game.game.entity.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector4;
import com.github.lordzintick.pixel_krush.core.api.AbstractGame;
import com.github.lordzintick.pixel_krush.core.api.TiledAtlas;
import com.github.lordzintick.pixel_krush.core.api.ecs.Effect;
import com.github.lordzintick.pixel_krush.core.api.ecs.LivingEntity;
import com.github.lordzintick.java_game.game.entity.player.Player;

public class BleedEffect extends Effect {
    private float bleedTicks = 1f;

    public BleedEffect(AbstractGame game, float time, int level) {
        super(2, 0, game, time, level);
    }

    @Override
    public void apply(LivingEntity entity) {
        entity.colorModifier.set(Color.RED);
    }

    @Override
    public void tick(LivingEntity entity, float deltaTime) {
        bleedTicks -= deltaTime;
        if (bleedTicks <= 0) {
            bleedTicks = entity instanceof Player ? 3f : 1f;
            entity.damage(level * 2, true);
        }

        TiledAtlas particlesAtlas = game.getCachedAtlas("particles");
        entity.screen.addParticle(
            new TextureRegion[]{particlesAtlas.get(6, 0), particlesAtlas.get(7, 0)},
            entity.x + game.getRandom().nextFloat(entity.width * entity.scale + 1),
            entity.y + game.getRandom().nextFloat(entity.height * entity.scale + 1),
            new Vector4(game.getRandom().nextFloat(-50, 50), -game.getRandom().nextFloat(125), -3f, 5f),
            8f,
            0.1f,
            5f
        );
    }

    @Override
    public void end(LivingEntity entity) {
        entity.colorModifier.set(Color.WHITE);
    }
}
