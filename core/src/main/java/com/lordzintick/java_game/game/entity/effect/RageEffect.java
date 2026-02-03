package com.lordzintick.java_game.game.entity.effect;

import com.badlogic.gdx.graphics.Color;
import com.lordzintick.pixel_krush.core.api.AbstractGame;
import com.lordzintick.pixel_krush.core.api.ecs.Effect;
import com.lordzintick.pixel_krush.core.api.ecs.LivingEntity;
import com.lordzintick.java_game.game.entity.HostileEntity;
import com.lordzintick.java_game.game.entity.player.Player;

public class RageEffect extends Effect {
    public RageEffect(AbstractGame game, float time, int level) {
        super(1, 1, game, time, level);
    }

    @Override
    public void apply(LivingEntity entity) {
        if (entity instanceof HostileEntity) {
            entity.speed.set2(entity.speed.get2() * 1 + (0.5f * level));
        } else if (entity instanceof Player) {
            entity.speed.set2(entity.speed.get2() + 0.5f * level);
            ((Player) entity).setMultiplier("global_damage", ((Player) entity).getMultiplier("global_damage").get() + 0.5f * level);
        }
    }

    @Override
    public void tick(LivingEntity entity, float deltaTime) {
        entity.colorModifier.set(Color.MAROON);
    }

    @Override
    public void end(LivingEntity entity) {
        entity.colorModifier.set(Color.WHITE);
        entity.speed.set2(entity.speed.get2() - 0.5f * level);
        if (entity instanceof HostileEntity) {
            ((HostileEntity) entity).damage /= 1 + (0.5f * level);
        } else if (entity instanceof Player) {
            ((Player) entity).setMultiplier("global_damage", ((Player) entity).getMultiplier("global_damage").get() + 0.5f * level);
        }
    }
}
