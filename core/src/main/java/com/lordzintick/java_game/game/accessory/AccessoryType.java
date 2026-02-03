package com.lordzintick.java_game.game.accessory;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lordzintick.pixel_krush.core.util.Identifier;
import com.lordzintick.pixel_krush.core.util.Text;
import com.lordzintick.java_game.game.Rarity;
import com.lordzintick.java_game.game.entity.player.StatModifier;

import java.util.List;
import java.util.function.Function;

public class AccessoryType {
    public TextureRegion icon;
    public final String displayName;
    public final int weight;
    public final boolean global;
    protected final Function<Integer, List<StatModifier<?>>> statModifiers;
    public final List<Text> description;

    public AccessoryType(TextureRegion icon, String displayName, int weight, boolean global, Function<Integer, List<StatModifier<?>>> statModifiers, List<Text> description) {
        this.icon = icon;
        this.displayName = displayName;
        this.weight = weight;
        this.global = global;
        this.statModifiers = statModifiers;
        this.description = description;
    }

    public Accessory<AccessoryType> getInstance(Rarity rarity, List<Identifier> targets) {return new Accessory<>(this, rarity, targets);}
}
