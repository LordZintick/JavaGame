package com.lordzintick.game.spell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lordzintick.core.Logger;
import com.lordzintick.util.Text;

import java.util.HashMap;
import java.util.List;

public final class Spells {
    private static final Logger LOGGER = new Logger(Spells.class);
    public final HashMap<String, Spell> SPELLS = new HashMap<>();
    private final TextureRegion[][] splitSprites;
    public final NinePatch tooltipTexture;
    public final NinePatch tooltipOverlay;

    public Spells() {
        tooltipTexture = new NinePatch(new Texture("textures/tooltip.png"), 2, 2, 2, 2);
        tooltipOverlay = new NinePatch(new Texture("textures/tooltip_overlay.png"), 3, 3, 3, 3);
        splitSprites = TextureRegion.split(new Texture(Gdx.files.internal("textures/spells.png")), 8, 8);
        TEST_SPELL = register("test", new Spell("Test Spell", Rarity.COMMON, List.of(new Text("A spell used to test the spell system")), splitSprites[0][0], 2, 3, (player) -> {
            LOGGER.log("Test spell cast!");
        }));
    }

    public final Spell TEST_SPELL;

    private Spell register(String id, Spell spell) {
        if (SPELLS.containsKey(id))
            throw new IllegalArgumentException("ID already exists!");

        SPELLS.put(id, spell);
        return spell;
    }

    public void tickAll(float deltaTime) {
        for (Spell spell : SPELLS.values()) {
            spell.tick(deltaTime);
        }
    }
}
