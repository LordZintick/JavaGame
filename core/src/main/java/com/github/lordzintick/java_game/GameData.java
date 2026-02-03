package com.github.lordzintick.java_game;

import com.github.lordzintick.java_game.achievement.Achievement;
import com.github.lordzintick.pixel_krush.core.api.AbstractGame;
import com.github.lordzintick.pixel_krush.core.api.IGameDataSerializer;
import com.github.lordzintick.pixel_krush.core.util.JsonAccessor;
import com.github.lordzintick.pixel_krush.core.util.registry.Registry;

import java.util.HashMap;

public class GameData implements IGameDataSerializer {
    public AbstractGame game;
    public int highscore = 0;
    public int totalEnemiesKilled = 0;
    public float totalDamageDone = 0;
    public float totalDamageTaken = 0;
    public float totalHealthHealed = 0;
    public int totalDeaths = 0;
    public float totalDamageBlocked = 0;

    public GameData(AbstractGame game) {
        this.game = game;
    }

    @Override
    public HashMap<String, Object> write() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("highscore", highscore);
        map.put("totalEnemiesKilled", totalEnemiesKilled);
        map.put("totalDamageDone", totalDamageDone);
        map.put("totalDamageTaken", totalDamageTaken);
        map.put("totalHealthHealed", totalHealthHealed);
        map.put("totalDeaths", totalDeaths);
        map.put("totalDamageBlocked", totalDamageBlocked);
        Registry<Achievement> achievementRegistry = game.queryRegistryOrThrow(game.getId("achievements"));
        achievementRegistry.forEachEntry((id, achievement) -> {
            if (!id.getNamespace().equals(game.getNamespace())) return;
            map.put("achievements." + id.getPath(), achievement.achieved);
        });
        return map;
    }

    @Override
    public void read(JsonAccessor jsonAccessor) {
        highscore = jsonAccessor.get("highscore", Integer.class);
        totalEnemiesKilled = jsonAccessor.get("totalEnemiesKilled", Integer.class);
        totalDeaths = jsonAccessor.get("totalDeaths", Integer.class);

        totalDamageDone = jsonAccessor.get("totalDamageDone", Float.class);
        totalDamageTaken = jsonAccessor.get("totalDamageTaken", Float.class);
        totalHealthHealed = jsonAccessor.get("totalHealthHealed", Float.class);
        totalDamageBlocked = jsonAccessor.get("totalDamageBlocked", Float.class);

        Registry<Achievement> achievementRegistry = game.queryRegistryOrThrow(game.getId("achievements"));
        achievementRegistry.forEachEntry((id, achievement) -> {
            Boolean value = jsonAccessor.get("achievements." + id.getPath(), Boolean.class);
            achievement.achieved = value != null && value;
        });
    }

    @Override
    public String getName() {
        return game.getNamespace();
    }
}
