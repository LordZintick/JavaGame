package com.lordzintick.audio;

import com.lordzintick.MainGame;

import java.util.HashMap;

/**
 * A utility class used for managing sounds and music
 */
public final class AudioManager {
    public final HashMap<String, Sound> SOUNDS = new HashMap<>();
    private final MainGame game;

    public final Sound TITLE_MUSIC;
    public final Sound GAME_MUSIC;
    public final Sound SHOOT;
    public final Sound HIT;
    public final Sound LEVELUP;
    public final Sound PLAYER_HIT;
    public final Sound KABOOM;
    public final Sound SLASH;
    public final Sound KILL;
    public final Sound HEAL;
    public final Sound BLOCK;
    public final Sound MEGA_SLASH;
    public final Sound MEGA_HIT;
    public final Sound MEGA_KILL;
    public final Sound LASER;
    public final Sound LASER_AMBIENT;
    public final Sound ROAR;
    public final Sound DASH;

    public AudioManager(MainGame game) {
        this.game = game;

        TITLE_MUSIC = register("title_music", new Sound(game, "audio/lines_of_code.mp3", true, 1f, true));
        GAME_MUSIC = register("game_music", new Sound(game, "audio/the_great_strategy.mp3", true, 0.5f, true));
        SHOOT = register("shoot", new Sound(game, "audio/sfx/fire.wav", false, 1, false));
        HIT = register("hit", new Sound(game, "audio/sfx/hit.wav", false, 1, false));
        LEVELUP = register("levelup", new Sound(game, "audio/sfx/levelup_temp.wav", false, 1, false));
        PLAYER_HIT = register("player_hit", new Sound(game, "audio/sfx/player_hit.wav", false, 1, false));
        KABOOM = register("kaboom", new Sound(game, "audio/sfx/kaboom.wav", false, 1, false));
        SLASH = register("slash", new Sound(game, "audio/sfx/slash.wav", false, 1, false));
        KILL = register("kill", new Sound(game, "audio/sfx/kill.wav", false, 1, false));
        HEAL = register("heal", new Sound(game, "audio/sfx/heal.wav", false, 1, false));
        BLOCK = register("block", new Sound(game, "audio/sfx/block.wav", false, 1, false));
        MEGA_SLASH = register("mega_slash", new Sound(game, "audio/sfx/mega_slash.wav", false, 1, false));
        MEGA_HIT = register("mega_hit", new Sound(game, "audio/sfx/mega_hit.wav", false, 1, false));
        MEGA_KILL = register("mega_kill", new Sound(game, "audio/sfx/mega_kill.wav", false, 1, false));
        LASER = register("laser", new Sound(game, "audio/sfx/laser.wav", false, 1, false));
        LASER_AMBIENT = register("laser_ambient", new Sound(game, "audio/sfx/laser_ambient.wav", false, 0.25f, false));
        ROAR = register("roar", new Sound(game, "audio/sfx/roar.wav", false, 1, false));
        DASH = register("dash", new Sound(game, "audio/sfx/dash.wav", false, 1, false));
    }

    /**
     * Registers a new {@link Sound} into the {@link AudioManager#SOUNDS} map, and throws an error if the ID already exists
     * @param id The ID to register the sound under. Unused for now other than preventing two sounds with the same ID from existing at once
     * @param sound The actual sound to register
     * @return The sound registered
     */
    private Sound register(String id, Sound sound) {
        if (SOUNDS.containsKey(id))
            throw new IllegalArgumentException("ID already exists!");

        SOUNDS.put(id, sound);
        return sound;
    }
}
