package com.lordzintick.game.entity.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lordzintick.MainGame;
import com.lordzintick.game.entity.Entity;
import com.lordzintick.game.entity.HostileEntity;
import com.lordzintick.game.entity.player.Player;

public class RageEffect extends Effect {
    public RageEffect(MainGame game, float time, int level) {
        super(game.effectAtlas[1][1], game, time, level);
    }

    @Override
    public void apply(Entity entity) {
        entity.speedMultiplier += 0.5f * level;
        if (entity instanceof HostileEntity) {
            ((HostileEntity) entity).damage *= 1 + (0.5f * level);
        } else if (entity instanceof Player) {
            ((Player) entity).globalDamageMultiplier += 0.5f * level;
        }
    }

    @Override
    public void tick(Entity entity, float deltaTime) {
        entity.colorModifier = Color.MAROON;
    }

    @Override
    public void end(Entity entity) {
        entity.colorModifier = Color.WHITE;
        entity.speedMultiplier -= 0.5f * level;
        if (entity instanceof HostileEntity) {
            ((HostileEntity) entity).damage /= 1 + (0.5f * level);
        } else if (entity instanceof Player) {
            ((Player) entity).globalDamageMultiplier -= 0.5f * level;
        }
    }
}
