package com.lordzintick.game.entity;

import com.lordzintick.game.entity.player.Player;
import com.lordzintick.game.screen.AbstractGameScreen;

public interface HostileEntityFactory<T extends HostileEntity> {
    T build(AbstractGameScreen screen, Player player);
}
