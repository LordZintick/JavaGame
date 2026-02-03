package com.lordzintick.java_game.game.entity;

import com.lordzintick.pixel_krush.core.api.AbstractGameScreen;
import com.lordzintick.java_game.game.entity.player.Player;

public interface HostileEntityFactory<T extends HostileEntity> {
    T build(AbstractGameScreen screen, Player player);
}
