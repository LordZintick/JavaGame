package com.lordzintick;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.lordzintick.game.entity.player.PlayerClass;

import java.util.HashMap;
import java.util.Locale;

public class GameData implements Json.Serializable {
    public int highscore = 0;
    public int enemiesKilled = 0;
    public HashMap<String, Boolean> unlockedClasses = new HashMap<>();

    public GameData() {
        for (PlayerClass playerClass : PlayerClass.values()) {
            if (playerClass.name().equals("MAGE")) {
                unlockedClasses.put(playerClass.name().toLowerCase(Locale.ROOT), true);
                continue;
            }
            unlockedClasses.put(playerClass.name().toLowerCase(Locale.ROOT), false);
        }
    }

    @Override
    public void write(Json json) {
        json.writeValue("\"highscore\"", highscore);
        json.writeValue("\"enemiesKilled\"", enemiesKilled);
        for (String key : unlockedClasses.keySet()) {
            json.writeValue("\"unlockedClasses." + key + "\"", unlockedClasses.get(key));
        }
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        highscore = json.readValue("\"highscore\"", Integer.class, jsonValue);
        enemiesKilled = json.readValue("\"enemiesKilled\"", Integer.class, jsonValue);
        for (PlayerClass playerClass : PlayerClass.values()) {
            String name = playerClass.name().toLowerCase(Locale.ROOT);
            unlockedClasses.put(name, jsonValue.getBoolean("\"unlockedClasses." + name + "\""));
        }
    }
}
