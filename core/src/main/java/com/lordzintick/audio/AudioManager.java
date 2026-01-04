package com.lordzintick.audio;

import com.lordzintick.MainGame;

import java.util.HashMap;

/**
 * A utility class used for managing sounds and music
 */
public final class AudioManager {
    private final HashMap<String, Sound> SOUNDS = new HashMap<>();
    private final MainGame game;

    public AudioManager(MainGame game) {
        this.game = game;

        // Music
        register("title_music", new Sound(game, "audio/lines_of_code.mp3", true, 1f, true));
        register("game_music_1", new Sound(game, "audio/the_great_strategy.mp3", false, 0.5f, true));
        register("game_music_2", new Sound(game, "audio/rush_point.mp3", false, 0.5f, true));
        register("game_music_3", new Sound(game, "audio/perilous_dungeon.mp3", false, 0.5f, true));
        register("game_music_4", new Sound(game, "audio/monstervania_2.mp3", false, 0.5f, true));
        register("game_music_5", new Sound(game, "audio/monstervania_1.mp3", false, 0.5f, true));

        // SFX
        register("shoot", new Sound(game, "audio/sfx/fire.wav", false, 1, false));
        register("hit", new Sound(game, "audio/sfx/hit.wav", false, 0.5f, false));
        register("levelup", new Sound(game, "audio/sfx/levelup_temp.wav", false, 1, false));
        register("player_hit", new Sound(game, "audio/sfx/player_hit.wav", false, 1, false));
        register("kaboom", new Sound(game, "audio/sfx/kaboom.wav", false, 0.5f, false));
        register("slash", new Sound(game, "audio/sfx/slash.wav", false, 0.5f, false));
        register("kill", new Sound(game, "audio/sfx/kill.wav", false, 0.5f, false));
        register("heal", new Sound(game, "audio/sfx/heal.wav", false, 1, false));
        register("block", new Sound(game, "audio/sfx/block.wav", false, 1, false));
        register("mega_slash", new Sound(game, "audio/sfx/mega_slash.wav", false, 0.5f, false));
        register("mega_hit", new Sound(game, "audio/sfx/mega_hit.wav", false, 0.5f, false));
        register("mega_kill", new Sound(game, "audio/sfx/mega_kill.wav", false, 0.5f, false));
        register("laser", new Sound(game, "audio/sfx/laser.wav", false, 0.5f, false));
        register("laser_ambient", new Sound(game, "audio/sfx/laser_ambient.wav", false, 0.25f, false));
        register("roar", new Sound(game, "audio/sfx/roar.wav", false, 0.5f, false));
        register("dash", new Sound(game, "audio/sfx/dash.wav", false, 1, false));
        register("pickup", new Sound(game, "audio/sfx/pickup.wav", false, 0.5f, false));
        register("place", new Sound(game, "audio/sfx/place.wav", false, 0.5f, false));
        register("confirm", new Sound(game, "audio/sfx/confirm.wav", false, 0.5f, false));
        register("back", new Sound(game, "audio/sfx/back.wav", false, 0.5f, false));
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

    public Sound get(String id) {
        return SOUNDS.get(id);
    }
}
