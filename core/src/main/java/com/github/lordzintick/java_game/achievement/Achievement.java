package com.github.lordzintick.java_game.achievement;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.lordzintick.pixel_krush.core.api.AbstractGame;
import com.github.lordzintick.pixel_krush.core.util.Text;

import java.util.function.Function;

public class Achievement {
    public final TextureRegion icon;
    public final Text displayName;
    public final Text description;
    public final Function<AbstractGame, Boolean> achiever;
    public boolean achieved = false;

    protected Achievement(TextureRegion icon, Text displayName, Text description, Function<AbstractGame, Boolean> achiever) {
        this.icon = icon;
        this.displayName = displayName;
        this.description = description;
        this.achiever = achiever;
    }

    public boolean check(AbstractGame game) {
        return achiever.apply(game);
    }
}
