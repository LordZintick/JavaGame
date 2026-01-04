package com.lordzintick.achievement;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lordzintick.MainGame;
import com.lordzintick.game.screen.MainGameScreen;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class Achievements {
    private final HashMap<String, Achievement> ACHIEVEMENTS = new HashMap<>();
    private final MainGame mainGame;

    public Achievements(MainGame mainGame) {
        this.mainGame = mainGame;
        TextureRegion[][] icons = TextureRegion.split(mainGame.assets.get("textures/achievements.png"), 16, 16);

        register("play_game", new Achievement("Welcome", "Play the game", icons[0][0], game -> true));

        register("killer_1", new Achievement("Killer I", "Kill 100 mobs", icons[0][1], game -> game.gameData.totalEnemiesKilled >= 100));
        register("killer_2", new Achievement("Killer II", "Kill 250 mobs", icons[0][2], game -> game.gameData.totalEnemiesKilled >= 250));
        register("killer_3", new Achievement("Killer III", "Kill 500 mobs", icons[0][3], game -> game.gameData.totalEnemiesKilled >= 500));
        register("killer_4", new Achievement("Killer IV", "Kill 1000 mobs", icons[0][4], game -> game.gameData.totalEnemiesKilled >= 1000));
        register("killer_5", new Achievement("Killer V", "Kill 2500 mobs", icons[0][5], game -> game.gameData.totalEnemiesKilled >= 2500));
        register("killer_6", new Achievement("Killer VI", "Kill 5000 mobs", icons[0][6], game -> game.gameData.totalEnemiesKilled >= 5000));
        register("killer_7", new Achievement("Killer VII", "Kill 10000 mobs", icons[0][7], game -> game.gameData.totalEnemiesKilled >= 10000));

        register("blocker_1", new Achievement("Blocker I", "Block 50 damage", icons[1][1], game -> game.gameData.totalDamageBlocked >= 50));
        register("blocker_2", new Achievement("Blocker II", "Block 100 damage", icons[1][2], game -> game.gameData.totalDamageBlocked >= 100));
        register("blocker_3", new Achievement("Blocker III", "Block 150 damage", icons[1][3], game -> game.gameData.totalDamageBlocked >= 150));
        register("blocker_4", new Achievement("Blocker IV", "Block 200 damage", icons[1][4], game -> game.gameData.totalDamageBlocked >= 200));
        register("blocker_5", new Achievement("Blocker V", "Block 250 damage", icons[1][5], game -> game.gameData.totalDamageBlocked >= 250));
        register("blocker_6", new Achievement("Blocker VI", "Block 300 damage", icons[1][6], game -> game.gameData.totalDamageBlocked >= 300));
        register("blocker_7", new Achievement("Blocker VII", "Block 350 damage", icons[1][7], game -> game.gameData.totalDamageBlocked >= 350));

        register("damage_dealer_1", new Achievement("Damage-Dealer I", "Deal 300 damage", icons[2][1], game -> game.gameData.totalDamageDone >= 300));
        register("damage_dealer_2", new Achievement("Damage-Dealer II", "Deal 1000 damage", icons[2][2], game -> game.gameData.totalDamageDone >= 1000));
        register("damage_dealer_3", new Achievement("Damage-Dealer III", "Deal 5000 damage", icons[2][3], game -> game.gameData.totalDamageDone >= 5000));
        register("damage_dealer_4", new Achievement("Damage-Dealer IV", "Deal 15000 damage", icons[2][4], game -> game.gameData.totalDamageDone >= 15000));
        register("damage_dealer_5", new Achievement("Damage-Dealer V", "Deal 25000 damage", icons[2][5], game -> game.gameData.totalDamageDone >= 25000));
        register("damage_dealer_6", new Achievement("Damage-Dealer VI", "Deal 60000 damage", icons[2][6], game -> game.gameData.totalDamageDone >= 60000));
        register("damage_dealer_7", new Achievement("Damage-Dealer VII", "Deal 100000 damage", icons[2][7], game -> game.gameData.totalDamageDone >= 100000));

        register("masochist_1", new Achievement("Masochist I", "Take 50 damage", icons[3][1], game -> game.gameData.totalDamageTaken >= 50));
        register("masochist_2", new Achievement("Masochist II", "Take 100 damage", icons[3][2], game -> game.gameData.totalDamageTaken >= 100));
        register("masochist_3", new Achievement("Masochist III", "Take 150 damage", icons[3][3], game -> game.gameData.totalDamageTaken >= 150));
        register("masochist_4", new Achievement("Masochist IV", "Take 200 damage", icons[3][4], game -> game.gameData.totalDamageTaken >= 200));
        register("masochist_5", new Achievement("Masochist V", "Take 250 damage", icons[3][5], game -> game.gameData.totalDamageTaken >= 250));
        register("masochist_6", new Achievement("Masochist VI", "Take 300 damage", icons[3][6], game -> game.gameData.totalDamageTaken >= 300));
        register("masochist_7", new Achievement("Masochist VII", "Take 350 damage", icons[3][7], game -> game.gameData.totalDamageTaken >= 350));

        register("survivor_1", new Achievement("Survivor I", "Reach a difficulty multiplier of x1.5", icons[4][1], game -> {
            if (game.screen instanceof MainGameScreen) {
                return ((MainGameScreen) game.screen).difficultyMultiplier >= 1.5f;
            }
            return false;
        }));
        register("survivor_2", new Achievement("Survivor II", "Reach a difficulty multiplier of x2.5", icons[4][2], game -> {
            if (game.screen instanceof MainGameScreen) {
                return ((MainGameScreen) game.screen).difficultyMultiplier >= 2.5f;
            }
            return false;
        }));
        register("survivor_3", new Achievement("Survivor III", "Reach a difficulty multiplier of x4", icons[4][3], game -> {
            if (game.screen instanceof MainGameScreen) {
                return ((MainGameScreen) game.screen).difficultyMultiplier >= 4f;
            }
            return false;
        }));
        register("survivor_4", new Achievement("Survivor IV", "Reach a difficulty multiplier of x8", icons[4][4], game -> {
            if (game.screen instanceof MainGameScreen) {
                return ((MainGameScreen) game.screen).difficultyMultiplier >= 8f;
            }
            return false;
        }));
        register("survivor_5", new Achievement("Survivor V", "Reach a difficulty multiplier of x12", icons[4][5], game -> {
            if (game.screen instanceof MainGameScreen) {
                return ((MainGameScreen) game.screen).difficultyMultiplier >= 12f;
            }
            return false;
        }));
        register("survivor_6", new Achievement("Survivor VI", "Reach a difficulty multiplier of x20", icons[4][6], game -> {
            if (game.screen instanceof MainGameScreen) {
                return ((MainGameScreen) game.screen).difficultyMultiplier >= 20f;
            }
            return false;
        }));
        register("survivor_7", new Achievement("Survivor VII", "Reach a difficulty multiplier of x35", icons[4][7], game -> {
            if (game.screen instanceof MainGameScreen) {
                return ((MainGameScreen) game.screen).difficultyMultiplier >= 35f;
            }
            return false;
        }));

        register("scholar_1", new Achievement("Scholar I", "Reach level 10", icons[5][1], game -> {
            if (game.screen instanceof MainGameScreen) {
                return ((MainGameScreen) game.screen).getPlayer().level >= 10;
            }
            return false;
        }));
        register("scholar_2", new Achievement("Scholar II", "Reach level 35", icons[5][2], game -> {
            if (game.screen instanceof MainGameScreen) {
                return ((MainGameScreen) game.screen).getPlayer().level >= 35;
            }
            return false;
        }));
        register("scholar_3", new Achievement("Scholar III", "Reach level 75", icons[5][3], game -> {
            if (game.screen instanceof MainGameScreen) {
                return ((MainGameScreen) game.screen).getPlayer().level >= 75;
            }
            return false;
        }));
        register("scholar_4", new Achievement("Scholar IV", "Reach level 150", icons[5][4], game -> {
            if (game.screen instanceof MainGameScreen) {
                return ((MainGameScreen) game.screen).getPlayer().level >= 150;
            }
            return false;
        }));
        register("scholar_5", new Achievement("Scholar V", "Reach level 250", icons[5][5], game -> {
            if (game.screen instanceof MainGameScreen) {
                return ((MainGameScreen) game.screen).getPlayer().level >= 250;
            }
            return false;
        }));
        register("scholar_6", new Achievement("Scholar VI", "Reach level 450", icons[5][6], game -> {
            if (game.screen instanceof MainGameScreen) {
                return ((MainGameScreen) game.screen).getPlayer().level >= 450;
            }
            return false;
        }));
        register("scholar_7", new Achievement("Scholar VII", "Reach level 750", icons[5][7], game -> {
            if (game.screen instanceof MainGameScreen) {
                return ((MainGameScreen) game.screen).getPlayer().level >= 750;
            }
            return false;
        }));
    }

    public void checkAllUnachieved() {
        for (Achievement achievement : getAchievements()) {
            if (achievement.achieved) continue;

            if (achievement.check(mainGame)) {
                mainGame.audio.get("achievement").play();
                achievement.achieved = true;
                mainGame.achievementTicks = 3f;
                mainGame.displayingAchievement = achievement;
            }
        }
    }

    private Achievement register(String id, Achievement achievement) {
        if (ACHIEVEMENTS.containsKey(id))
            throw new IllegalArgumentException("ID already exists!");

        ACHIEVEMENTS.put(id, achievement);
        return achievement;
    }

    public Achievement get(String id) {
        return ACHIEVEMENTS.get(id);
    }

    public Collection<Achievement> getAchievements() {
        return ACHIEVEMENTS.values();
    }

    public Set<Map.Entry<String, Achievement>> getEntries() {
        return ACHIEVEMENTS.entrySet();
    }
}
