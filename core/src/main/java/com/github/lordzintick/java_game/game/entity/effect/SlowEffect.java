package com.github.lordzintick.java_game.game.entity.effect;

import com.badlogic.gdx.graphics.Color;
import com.github.lordzintick.pixel_krush.core.api.AbstractGame;
import com.github.lordzintick.pixel_krush.core.api.ecs.Effect;
import com.github.lordzintick.pixel_krush.core.api.ecs.LivingEntity;

public class SlowEffect extends Effect {
    public SlowEffect(AbstractGame game, float time, int level) {
        super(0, 0, game, time, level);
    }

    @Override
    public void apply(LivingEntity entity) {
        entity.speed.set2(entity.speed.get2() - level / 10);
    }

    @Override
    public void tick(LivingEntity entity, float deltaTime) {
        entity.colorModifier.set(Color.BLUE);
    }

    @Override
    public void end(LivingEntity entity) {
        entity.speed.set2(entity.speed.get2() + level / 10);
        entity.colorModifier.set(Color.WHITE);
    }
}
