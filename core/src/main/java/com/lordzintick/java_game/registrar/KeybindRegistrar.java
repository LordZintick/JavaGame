package com.lordzintick.java_game.registrar;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerMapping;
import com.badlogic.gdx.controllers.Controllers;
import com.lordzintick.pixel_krush.core.api.AbstractGame;
import com.lordzintick.pixel_krush.core.util.registry.DeferredRegister;
import com.lordzintick.pixel_krush.core.util.input.GamepadInput;
import com.lordzintick.pixel_krush.core.util.input.Keybind;

public final class KeybindRegistrar {
    public static DeferredRegister<Keybind> registrar(AbstractGame game) {
        DeferredRegister<Keybind> register = DeferredRegister.create(game, AbstractGame.getGlobalId("keybinds"));
        Controller controller = Controllers.getCurrent();
        ControllerMapping mapping = (controller == null ? null : controller.getMapping());

        register.register(game.getId("up"), new Keybind(Keybind.Context.GAME,
            Input.Keys.W, Input.Keys.UP,
            GamepadInput.tryGetButton(mapping, map -> map.buttonDpadUp)
            )
        );
        register.register(game.getId("down"), new Keybind(Keybind.Context.GAME,
                Input.Keys.S, Input.Keys.DOWN,
                GamepadInput.tryGetButton(mapping, map -> map.buttonDpadDown)
            )
        );
        register.register(game.getId("left"), new Keybind(Keybind.Context.GAME,
                Input.Keys.A, Input.Keys.LEFT,
                GamepadInput.tryGetButton(mapping, map -> map.buttonDpadLeft)
            )
        );
        register.register(game.getId("right"), new Keybind(Keybind.Context.GAME,
                Input.Keys.D, Input.Keys.RIGHT,
                GamepadInput.tryGetButton(mapping, map -> map.buttonDpadRight)
            )
        );
        register.register(game.getId("scroll_up"), new Keybind(Keybind.Context.UI,
                GamepadInput.tryGetButton(mapping, map -> map.buttonDpadUp)
            )
        );
        register.register(game.getId("scroll_down"), new Keybind(Keybind.Context.UI,
                GamepadInput.tryGetButton(mapping, map -> map.buttonDpadDown)
            )
        );
        register.register(game.getId("attack_1"), new Keybind(Keybind.Context.GAME,
                Input.Buttons.LEFT,
                GamepadInput.tryGetButton(mapping, map -> map.buttonX)
            )
        );
        register.register(game.getId("attack_2"), new Keybind(Keybind.Context.GAME,
                Input.Buttons.MIDDLE,
                GamepadInput.tryGetButton(mapping, map -> map.buttonY)
            )
        );
        register.register(game.getId("attack_3"), new Keybind(Keybind.Context.GAME,
                Input.Buttons.RIGHT,
                GamepadInput.tryGetButton(mapping, map -> map.buttonB)
            )
        );
        register.register(game.getId("pause"), new Keybind(Keybind.Context.GAME, game::togglePaused,
                Input.Keys.ESCAPE,
                GamepadInput.tryGetButton(mapping, map -> map.buttonStart)
            )
        );
        register.register(game.getId("dash"), new Keybind(Keybind.Context.GAME,
                Input.Keys.SPACE,
                GamepadInput.tryGetButton(mapping, map -> map.buttonL1)
            )
        );
        return register;
    }
}
