package com.lordzintick.control;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.lordzintick.MainGame;
import com.lordzintick.core.Logger;
import com.lordzintick.ui.widget.Widget;
import com.lordzintick.util.MathUtil;

/**
 * A wrapper class around {@link InputProcessor} for game-specific input handling
 */
public final class Input implements InputProcessor {
    private static final Logger LOGGER = new Logger(Input.class);

    private final MainGame game;
    public boolean[] mouseButtonsPressed = new boolean[] {false, false, false, false, false};

    public Input(MainGame game) {
        this.game = game;
    }

    @Override
    public boolean keyDown(int key) {
        // Iterate through all the keybinds
        for (Keybind keybind : Keybinds.KEYBINDS.values()) {
            // If the keybind's key is the one that was pressed, run the keybind's action and set it to pressed
            if (keybind.defaultKey == key && keybind.checkContext(game.screen)) {
                keybind.isPressed = true;
                keybind.action.run();
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int key) {
        // Iterate through all the keybinds
        for (Keybind keybind : Keybinds.KEYBINDS.values()) {
            // If the keybind's key is the one that was pressed, set it unpressed
            if (keybind.defaultKey == key && keybind.checkContext(game.screen)) {
                keybind.isPressed = false;
            }
        }

        return false;
    }

    @Override
    public boolean keyTyped(char key) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        mouseButtonsPressed[button] = true;
        LOGGER.log("Mouse button " + button + " pressed!");

        // Iterate through all the screen's widgets
        for (Widget widget : game.screen.widgets) {
            LOGGER.log("Widget loop");
            // Check if the mouse position is in the widgets' area, and call the according method
            if (MathUtil.isPointInArea(screenX, screenY, widget.x, widget.y, widget.width, widget.height)) {
                LOGGER.log("Mouse is in area. Clicking!");
                widget.click(button);
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        mouseButtonsPressed[button] = false;
        LOGGER.log("Mouse button " + button + " released!");

        // Iterate through all the screen's widgets
        for (Widget widget : game.screen.widgets) {
            // Check if the mouse position is in the widgets' area, and call the according method
            if (MathUtil.isPointInArea(screenX, screenY, widget.x, widget.y, widget.width, widget.height)) {
                widget.release(button);
            }
        }
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // Iterate through all the screen's widgets
        for (Widget widget : game.screen.widgets) {
            // Check if the mouse position is in the widgets' area, and call the according method
            if (MathUtil.isPointInArea(screenX, screenY, widget.x, widget.y, widget.width, widget.height)) {
                widget.hover();
            } else {
                widget.unHover();
            }
        }
        return false;
    }

    @Override
    public boolean scrolled(float deltaX, float deltaY) {
        return false;
    }
}
