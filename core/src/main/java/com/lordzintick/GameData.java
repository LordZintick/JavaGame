package com.lordzintick;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.lordzintick.achievement.Achievement;

import java.util.Map;

public class GameData implements Json.Serializable {
    public MainGame game;
    public int highscore = 0;
    public int totalEnemiesKilled = 0;
    public float totalDamageDone = 0;
    public float totalDamageTaken = 0;
    public float totalHealthHealed = 0;
    public int totalDeaths = 0;
    public float totalDamageBlocked = 0;

    public GameData(MainGame game) {
        this.game = game;
    }

    public GameData() {}

    @Override
    public void write(Json json) {
        json.writeValue("\"highscore\"", highscore);
        json.writeValue("\"totalEnemiesKilled\"", totalEnemiesKilled);
        json.writeValue("\"totalDamageDone\"", totalDamageDone);
        json.writeValue("\"totalDamageTaken\"", totalDamageTaken);
        json.writeValue("\"totalHealthHealed\"", totalHealthHealed);
        json.writeValue("\"totalDeaths\"", totalDeaths);
        json.writeValue("\"totalDamageBlocked\"", totalDamageBlocked);
        for (Map.Entry<String, Achievement> entry : game.achievements.getEntries()) {
            json.writeValue("\"achievements." + entry.getKey() + "\"", entry.getValue().achieved);
        }
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        highscore = json.readValue("\"highscore\"", Integer.class, jsonValue);
        totalEnemiesKilled = json.readValue("\"totalEnemiesKilled\"", Integer.class, jsonValue);
        totalDeaths = json.readValue("\"totalDeaths\"", Integer.class, jsonValue);

        totalDamageDone = json.readValue("\"totalDamageDone\"", Float.class, jsonValue);
        totalDamageTaken = json.readValue("\"totalDamageTaken\"", Float.class, jsonValue);
        totalHealthHealed = json.readValue("\"totalHealthHealed\"", Float.class, jsonValue);
        totalDamageBlocked = json.readValue("\"totalDamageBlocked\"", Float.class, jsonValue);

        for (Map.Entry<String, Achievement> entry : game.achievements.getEntries()) {
            Boolean value = json.readValue("\"achievements." + entry.getKey() + "\"", Boolean.class, jsonValue);
            entry.getValue().achieved = value != null && value;
        }
    }
}
