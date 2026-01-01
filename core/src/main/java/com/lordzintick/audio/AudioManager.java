package com.lordzintick.audio;

import java.util.HashMap;

/**
 * A utility class used for managing sounds and music
 */
public final class AudioManager {
    public static final HashMap<String, Sound> SOUNDS = new HashMap<>();

    public static final Sound TITLE_MUSIC = register("title_music", new Sound("audio/lines_of_code.mp3", true, 1f, true));
    public static final Sound GAME_MUSIC = register("game_music", new Sound("audio/the_great_strategy.mp3", true, 0.5f, true));
    public static final Sound SHOOT = register("shoot", new Sound("audio/sfx/fire.wav", false, 1, false));
    public static final Sound HIT = register("hit", new Sound("audio/sfx/hit.wav", false, 1, false));
    public static final Sound LEVELUP = register("levelup", new Sound("audio/sfx/levelup_temp.mp3", false, 1, false));
    public static final Sound PLAYER_HIT = register("player_hit", new Sound("audio/sfx/player_hit.wav", false, 1, false));
    public static final Sound KABOOM = register("kaboom", new Sound("audio/sfx/kaboom.wav", false, 1, false));
    public static final Sound SLASH = register("slash", new Sound("audio/sfx/slash.wav", false, 1, false));
    public static final Sound KILL = register("kill", new Sound("audio/sfx/kill.wav", false, 1, false));
    public static final Sound HEAL = register("heal", new Sound("audio/sfx/heal.wav", false, 1, false));
    public static final Sound BLOCK = register("block", new Sound("audio/sfx/block.wav", false, 1, false));
    public static final Sound MEGA_SLASH = register("mega_slash", new Sound("audio/sfx/mega_slash.wav", false, 1, false));
    public static final Sound MEGA_HIT = register("mega_hit", new Sound("audio/sfx/mega_hit.wav", false, 1, false));
    public static final Sound MEGA_KILL = register("mega_kill", new Sound("audio/sfx/mega_kill.wav", false, 1, false));

    /**
     * Registers a new {@link Sound} into the {@link AudioManager#SOUNDS} map, and throws an error if the ID already exists
     * @param id The ID to register the sound under. Unused for now other than preventing two sounds with the same ID from existing at once
     * @param sound The actual sound to register
     * @return The sound registered
     */
    private static Sound register(String id, Sound sound) {
        if (SOUNDS.containsKey(id))
            throw new IllegalArgumentException("ID already exists!");

        SOUNDS.put(id, sound);
        return sound;
    }
}
