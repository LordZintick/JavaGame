package com.lordzintick.java_game.achievement;

import com.lordzintick.java_game.GameData;
import com.lordzintick.pixel_krush.core.api.AbstractGame;
import com.lordzintick.pixel_krush.core.api.TiledAtlas;
import com.lordzintick.pixel_krush.core.util.registry.DeferredRegister;
import com.lordzintick.pixel_krush.core.util.registry.Registry;
import com.lordzintick.java_game.game.screen.MainGameScreen;
import com.lordzintick.java_game.ui.widget.AchievementToast;
import com.lordzintick.pixel_krush.core.util.Text;

public final class Achievements {
    public static DeferredRegister<Achievement> registrar(AbstractGame game) {
        DeferredRegister<Achievement> register = DeferredRegister.create(game, game.getId("achievements"));
        TiledAtlas icons = game.getCachedAtlas("achievements");

        register.register(game.getId("play_game"), new Achievement(icons.get(0, 0), new Text("Welcome"), new Text("Play the game"), game1 -> true));

        register.register(game.getId("killer_1"), new Achievement(icons.get(1, 0), new Text("Killer I"), new Text("Kill 100 mobs"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalEnemiesKilled >= 100));
        register.register(game.getId("killer_2"), new Achievement(icons.get(2, 0), new Text("Killer II"), new Text("Kill 250 mobs"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalEnemiesKilled >= 250));
        register.register(game.getId("killer_3"), new Achievement(icons.get(3, 0), new Text("Killer III"), new Text("Kill 500 mobs"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalEnemiesKilled >= 500));
        register.register(game.getId("killer_4"), new Achievement(icons.get(4, 0), new Text("Killer IV"), new Text("Kill 1000 mobs"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalEnemiesKilled >= 1000));
        register.register(game.getId("killer_5"), new Achievement(icons.get(5, 0), new Text("Killer V"), new Text("Kill 2500 mobs"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalEnemiesKilled >= 2500));
        register.register(game.getId("killer_6"), new Achievement(icons.get(6, 0), new Text("Killer VI"), new Text("Kill 5000 mobs"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalEnemiesKilled >= 5000));
        register.register(game.getId("killer_7"), new Achievement(icons.get(7, 0), new Text("Killer VII"), new Text("Kill 10000 mobs"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalEnemiesKilled >= 10000));

        register.register(game.getId("blocker_1"), new Achievement(icons.get(1, 1), new Text("Blocker I"), new Text("Block 50 damage"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalDamageBlocked >= 50));
        register.register(game.getId("blocker_2"), new Achievement(icons.get(2, 1), new Text("Blocker II"), new Text("Block 100 damage"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalDamageBlocked >= 100));
        register.register(game.getId("blocker_3"), new Achievement(icons.get(3, 1), new Text("Blocker III"), new Text("Block 150 damage"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalDamageBlocked >= 150));
        register.register(game.getId("blocker_4"), new Achievement(icons.get(4, 1), new Text("Blocker IV"), new Text("Block 200 damage"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalDamageBlocked >= 200));
        register.register(game.getId("blocker_5"), new Achievement(icons.get(5, 1), new Text("Blocker V"), new Text("Block 250 damage"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalDamageBlocked >= 250));
        register.register(game.getId("blocker_6"), new Achievement(icons.get(6, 1), new Text("Blocker VI"), new Text("Block 300 damage"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalDamageBlocked >= 300));
        register.register(game.getId("blocker_7"), new Achievement(icons.get(7, 1), new Text("Blocker VII"), new Text("Block 350 damage"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalDamageBlocked >= 350));

        register.register(game.getId("damage_dealer_1"), new Achievement(icons.get(1, 2), new Text("Damage-Dealer I"), new Text("Deal 300 damage"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalDamageDone >= 300));
        register.register(game.getId("damage_dealer_2"), new Achievement(icons.get(2, 2), new Text("Damage-Dealer II"), new Text("Deal 1000 damage"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalDamageDone >= 1000));
        register.register(game.getId("damage_dealer_3"), new Achievement(icons.get(3, 2), new Text("Damage-Dealer III"), new Text("Deal 5000 damage"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalDamageDone >= 5000));
        register.register(game.getId("damage_dealer_4"), new Achievement(icons.get(4, 2), new Text("Damage-Dealer IV"), new Text("Deal 15000 damage"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalDamageDone >= 15000));
        register.register(game.getId("damage_dealer_5"), new Achievement(icons.get(5, 2), new Text("Damage-Dealer V"), new Text("Deal 25000 damage"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalDamageDone >= 25000));
        register.register(game.getId("damage_dealer_6"), new Achievement(icons.get(6, 2), new Text("Damage-Dealer VI"), new Text("Deal 60000 damage"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalDamageDone >= 60000));
        register.register(game.getId("damage_dealer_7"), new Achievement(icons.get(7, 2), new Text("Damage-Dealer VII"), new Text("Deal 100000 damage"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalDamageDone >= 100000));

        register.register(game.getId("masochist_1"), new Achievement(icons.get(1, 3), new Text("Masochist I"), new Text("Take 50 damage"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalDamageTaken >= 50));
        register.register(game.getId("masochist_2"), new Achievement(icons.get(2, 3), new Text("Masochist II"), new Text("Take 100 damage"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalDamageTaken >= 100));
        register.register(game.getId("masochist_3"), new Achievement(icons.get(3, 3), new Text("Masochist III"), new Text("Take 150 damage"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalDamageTaken >= 150));
        register.register(game.getId("masochist_4"), new Achievement(icons.get(4, 3), new Text("Masochist IV"), new Text("Take 200 damage"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalDamageTaken >= 200));
        register.register(game.getId("masochist_5"), new Achievement(icons.get(5, 3), new Text("Masochist V"), new Text("Take 250 damage"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalDamageTaken >= 250));
        register.register(game.getId("masochist_6"), new Achievement(icons.get(6, 3), new Text("Masochist VI"), new Text("Take 300 damage"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalDamageTaken >= 300));
        register.register(game.getId("masochist_7"), new Achievement(icons.get(7, 3), new Text("Masochist VII"), new Text("Take 350 damage"), game1 -> ((GameData) game1.getDataSerializerOrThrow("java_game_data")).totalDamageTaken >= 350));

        register.register(game.getId("survivor_1"), new Achievement(icons.get(1, 4), new Text("Survivor I"), new Text("Reach a difficulty multiplier of x1.5"), game1 -> {
            if (game.getScreen() instanceof MainGameScreen) {
                return ((MainGameScreen) game.getScreen()).difficultyMultiplier >= 1.5f;
            }
            return false;
        }));
        register.register(game.getId("survivor_2"), new Achievement(icons.get(2, 4), new Text("Survivor II"), new Text("Reach a difficulty multiplier of x2.5"), game1 -> {
            if (game.getScreen() instanceof MainGameScreen) {
                return ((MainGameScreen) game.getScreen()).difficultyMultiplier >= 2.5f;
            }
            return false;
        }));
        register.register(game.getId("survivor_3"), new Achievement(icons.get(3, 4), new Text("Survivor III"), new Text("Reach a difficulty multiplier of x4"), game1 -> {
            if (game.getScreen() instanceof MainGameScreen) {
                return ((MainGameScreen) game.getScreen()).difficultyMultiplier >= 4f;
            }
            return false;
        }));
        register.register(game.getId("survivor_4"), new Achievement(icons.get(4, 4), new Text("Survivor IV"), new Text("Reach a difficulty multiplier of x8"), game1 -> {
            if (game.getScreen() instanceof MainGameScreen) {
                return ((MainGameScreen) game.getScreen()).difficultyMultiplier >= 8f;
            }
            return false;
        }));
        register.register(game.getId("survivor_5"), new Achievement(icons.get(5, 4), new Text("Survivor V"), new Text("Reach a difficulty multiplier of x12"), game1 -> {
            if (game.getScreen() instanceof MainGameScreen) {
                return ((MainGameScreen) game.getScreen()).difficultyMultiplier >= 12f;
            }
            return false;
        }));
        register.register(game.getId("survivor_6"), new Achievement(icons.get(6, 4), new Text("Survivor VI"), new Text("Reach a difficulty multiplier of x20"), game1 -> {
            if (game.getScreen() instanceof MainGameScreen) {
                return ((MainGameScreen) game.getScreen()).difficultyMultiplier >= 20f;
            }
            return false;
        }));
        register.register(game.getId("survivor_7"), new Achievement(icons.get(7, 4), new Text("Survivor VII"), new Text("Reach a difficulty multiplier of x35"), game1 -> {
            if (game.getScreen() instanceof MainGameScreen) {
                return ((MainGameScreen) game.getScreen()).difficultyMultiplier >= 35f;
            }
            return false;
        }));

        register.register(game.getId("scholar_1"), new Achievement(icons.get(1, 5), new Text("Scholar I"), new Text("Reach level 10"), game1 -> {
            if (game.getScreen() instanceof MainGameScreen) {
                return ((MainGameScreen) game.getScreen()).getPlayer().level.get2() >= 10;
            }
            return false;
        }));
        register.register(game.getId("scholar_2"), new Achievement(icons.get(2, 5), new Text("Scholar II"), new Text("Reach level 25"), game1 -> {
            if (game.getScreen() instanceof MainGameScreen) {
                return ((MainGameScreen) game.getScreen()).getPlayer().level.get2() >= 25;
            }
            return false;
        }));
        register.register(game.getId("scholar_3"), new Achievement(icons.get(3, 5), new Text("Scholar III"), new Text("Reach level 50"), game1 -> {
            if (game.getScreen() instanceof MainGameScreen) {
                return ((MainGameScreen) game.getScreen()).getPlayer().level.get2() >= 50;
            }
            return false;
        }));
        register.register(game.getId("scholar_4"), new Achievement(icons.get(4, 5), new Text("Scholar IV"), new Text("Reach level 75"), game1 -> {
            if (game.getScreen() instanceof MainGameScreen) {
                return ((MainGameScreen) game.getScreen()).getPlayer().level.get2() >= 75;
            }
            return false;
        }));
        register.register(game.getId("scholar_5"), new Achievement(icons.get(5, 5), new Text("Scholar V"), new Text("Reach level 100"), game1 -> {
            if (game.getScreen() instanceof MainGameScreen) {
                return ((MainGameScreen) game.getScreen()).getPlayer().level.get2() >= 100;
            }
            return false;
        }));
        register.register(game.getId("scholar_6"), new Achievement(icons.get(6, 5), new Text("Scholar VI"), new Text("Reach level 125"), game1 -> {
            if (game.getScreen() instanceof MainGameScreen) {
                return ((MainGameScreen) game.getScreen()).getPlayer().level.get2() >= 125;
            }
            return false;
        }));
        register.register(game.getId("scholar_7"), new Achievement(icons.get(7, 5), new Text("Scholar VII"), new Text("Reach level 150"), game1 -> {
            if (game.getScreen() instanceof MainGameScreen) {
                return ((MainGameScreen) game.getScreen()).getPlayer().level.get2() >= 150;
            }
            return false;
        }));
        return register;
    }

    public static void checkAllUnachieved(AbstractGame game) {
        Registry<Achievement> achievements = game.queryRegistryOrThrow(game.getId("achievements"));
        achievements.forEachEntry((id, achievement) -> {
            if (achievement.achieved) return;

            if (achievement.check(game)) {
                game.getAudioSample("achievement").play();
                achievement.achieved = true;
                game.showToast(new AchievementToast(game.getScreen(), achievement), 3f);
            }
        });
    }
}
