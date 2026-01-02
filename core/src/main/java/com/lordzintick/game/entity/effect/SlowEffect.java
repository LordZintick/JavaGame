package com.lordzintick.game.entity.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lordzintick.MainGame;
import com.lordzintick.game.entity.Entity;

public class SlowEffect extends Effect {
    public SlowEffect(MainGame game, float time, int level) {
        super(game.effectAtlas[0][0], game, time, level);
    }

    @Override
    public void apply(Entity entity) {
        entity.speedMultiplier -= (float) level / 10;
    }

    @Override
    public void tick(Entity entity, float deltaTime) {
        entity.colorModifier = Color.BLUE;
    }

    @Override
    public void end(Entity entity) {
        entity.speedMultiplier += (float) level / 10;
        entity.colorModifier = Color.WHITE;
    }
}
