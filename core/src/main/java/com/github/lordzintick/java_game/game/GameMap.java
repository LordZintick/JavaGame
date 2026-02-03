package com.github.lordzintick.java_game.game;

public enum GameMap {
    CLASSIC("textures/game/maps/classic.png"),
    FOREST("textures/game/maps/forest.png");
    public final String filename;

    GameMap(String filename) {
        this.filename = filename;
    }
}
