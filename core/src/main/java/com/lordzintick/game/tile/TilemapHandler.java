package com.lordzintick.game.tile;

import com.badlogic.gdx.maps.tiled.*;
import com.lordzintick.MainGame;
import com.lordzintick.core.Logger;

/**
 * Handles tilemaps and game map loading/rendering
 */
public class TilemapHandler {
    private static final Logger LOGGER = new Logger(TilemapHandler.class);

    private final MainGame game;
    private TiledMap tilemap;
    public final GameTiledMapRenderer renderer;
    private final TmxMapLoader mapLoader;

    public TilemapHandler(MainGame game) {
        this.game = game;
        tilemap = new TiledMap();
        mapLoader = new TmxMapLoader();
        load("default_map.tmx");
        renderer = new GameTiledMapRenderer(tilemap, 0.001f, game.mapBatch);
    }

    public void load(String filename) {
        LOGGER.log("Loading tilemap: " + filename);
        tilemap = mapLoader.load(filename);
        if (renderer != null) {
            renderer.setMap(tilemap);
        }
    }

    public void render() {
        renderer.setView(game.camera);
        renderer.render();
    }

    public void dispose() {
        tilemap.dispose();
        renderer.dispose();
    }
}
