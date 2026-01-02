package com.lordzintick.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.MathUtils;
import com.lordzintick.MainGame;
import com.lordzintick.ui.widget.Widget;
import com.lordzintick.util.MathUtil;

public final class GamepadInput implements ControllerListener {
    private final MainGame game;

    public GamepadInput(MainGame game) {
        this.game = game;
    }

    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {

    }

    @Override
    public boolean buttonDown(Controller controller, int button) {
        for (Keybind keybind : game.keybinds.KEYBINDS.values()) {
            // If the keybind's button is the one that was pressed, run the keybind's action and set it to pressed
            if (keybind.defaultKey == button && keybind.checkContext(game.screen) && keybind.context == Keybind.Context.GAMEPAD) {
                keybind.isPressed = true;
                keybind.action.run();
            }
        }

        if (button == controller.getMapping().buttonA) {
            for (Widget widget : game.screen.widgets) {
                // Check if the mouse position is in the widgets' area, and call the according method
                if (MathUtil.isPointInArea((int) game.gamepadCursorX,(int)  game.gamepadCursorY, widget.x, widget.y, widget.width, widget.height)) {
                    widget.click(button);
                }
            }
        }
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int button) {
        for (Keybind keybind : game.keybinds.KEYBINDS.values()) {
            // If the keybind's button is the one that was pressed, run the keybind's action and set it to pressed
            if (keybind.defaultKey == button && keybind.checkContext(game.screen) && keybind.context == Keybind.Context.GAMEPAD) {
                keybind.isPressed = false;
            }
        }
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axis, float value) {
        return false;
    }
}
