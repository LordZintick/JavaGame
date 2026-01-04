package com.lordzintick.achievement;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lordzintick.GameData;
import com.lordzintick.MainGame;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.function.Function;
import java.util.function.Supplier;

public class Achievement {
    public final String displayName;
    public final String description;
    public final TextureRegion icon;
    public final Function<MainGame, Boolean> achiever;
    public boolean achieved = false;

    public Achievement(String displayName, String description, TextureRegion icon, Function<MainGame, Boolean> achiever) {
        this.displayName = displayName;
        this.description = description;
        this.icon = icon;
        this.achiever = achiever;
    }

    public boolean check(MainGame game) {
        return achiever.apply(game);
    }
}
