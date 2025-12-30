package com.lordzintick.util;

import com.badlogic.gdx.math.Vector2;

/**
 * A utility class defining a direction.
 * Diagonals are included
 */
public enum Direction {
    UP(new Vector2(0,1)),
    UP_LEFT(new Vector2(-1,1)),
    UP_RIGHT(new Vector2(1,1)),
    DOWN(new Vector2(0,-1)),
    DOWN_LEFT(new Vector2(-1,-1)),
    DOWN_RIGHT(new Vector2(1,-1)),
    LEFT(new Vector2(-1,0)),
    RIGHT(new Vector2(1,0));
    public final Vector2 vector;

    Direction(Vector2 vector) {
        this.vector = vector;
    }

    /**
     * Flips the direction over the provided axis
     * @param horizontalAxis Whether this flip should occur over the horizontal axis (vertical flip) or the vertical axis (horizontal flip)
     * @return The newly flipped {@link Direction}
     */
    public Direction flip(boolean horizontalAxis) {
        if (horizontalAxis) {
            switch (this) {
                case UP: return DOWN;
                case DOWN: return UP;
                case LEFT: return LEFT;
                case RIGHT: return RIGHT;
                case UP_LEFT: return DOWN_LEFT;
                case UP_RIGHT: return DOWN_RIGHT;
                case DOWN_LEFT: return UP_LEFT;
                case DOWN_RIGHT: return UP_RIGHT;
            }
        } else {
            switch (this) {
                case UP: return UP;
                case DOWN: return DOWN;
                case LEFT: return RIGHT;
                case RIGHT: return LEFT;
                case UP_LEFT: return UP_RIGHT;
                case UP_RIGHT: return UP_LEFT;
                case DOWN_LEFT: return DOWN_RIGHT;
                case DOWN_RIGHT: return DOWN_LEFT;
            }
        }

        return UP;
    }
}
