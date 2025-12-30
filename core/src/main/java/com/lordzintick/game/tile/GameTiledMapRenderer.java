package com.lordzintick.game.tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.lordzintick.core.Logger;

public class GameTiledMapRenderer extends OrthogonalTiledMapRenderer {
    private static final Logger LOGGER = new Logger(GameTiledMapRenderer.class);

    public GameTiledMapRenderer(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
    }

    @Override
    public void setView(OrthographicCamera camera) {
        this.batch.setProjectionMatrix(camera.combined);
        this.viewBounds.set(
            camera.position.x,
            camera.position.y,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );
        LOGGER.log("New view bounds: " + viewBounds.toString());
    }
}
