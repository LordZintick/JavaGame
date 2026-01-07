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
        register("title_music", new IntroLoopSound(game, "audio/theme_song_intro.wav", "audio/theme_song_loop.wav", 1f));
        register("achievements_music", new Sound(game, "audio/pulsar.mp3", true, 1f, true));
        register("game_music_1", new Sound(game, "audio/leap.mp3", false, 1f, true));
        register("game_music_2", new Sound(game, "audio/rush_point.mp3", false, 0.5f, true));
        register("game_music_3", new Sound(game, "audio/perilous_dungeon.mp3", false, 0.5f, true));
        register("game_music_4", new Sound(game, "audio/monstervania_2.mp3", false, 0.5f, true));
        register("game_music_5", new Sound(game, "audio/monstervania_1.mp3", false, 0.5f, true));

        // SFX
        register("shoot", new Sound(game, "audio/sfx/fire.ogg", false, 1, false));
        register("hit", new Sound(game, "audio/sfx/hit.ogg", false, 0.5f, false));
        register("levelup", new Sound(game, "audio/sfx/levelup.ogg", false, 1, false));
        register("player_hit", new Sound(game, "audio/sfx/player_hit.ogg", false, 1, false));
        register("kaboom", new Sound(game, "audio/sfx/kaboom.ogg", false, 0.5f, false));
        register("slash", new Sound(game, "audio/sfx/slash.ogg", false, 0.5f, false));
        register("kill", new Sound(game, "audio/sfx/kill.ogg", false, 0.5f, false));
        register("heal", new Sound(game, "audio/sfx/heal.ogg", false, 1, false));
        register("block", new Sound(game, "audio/sfx/block.ogg", false, 1, false));
        register("mega_slash", new Sound(game, "audio/sfx/mega_slash.ogg", false, 0.5f, false));
        register("mega_hit", new Sound(game, "audio/sfx/mega_hit.ogg", false, 0.5f, false));
        register("mega_kill", new Sound(game, "audio/sfx/mega_kill.ogg", false, 0.5f, false));
        register("laser", new Sound(game, "audio/sfx/laser.ogg", false, 0.5f, false));
        register("laser_ambient", new Sound(game, "audio/sfx/laser_ambient.ogg", false, 0.25f, false));
        register("roar", new Sound(game, "audio/sfx/roar.ogg", false, 0.5f, false));
        register("dash", new Sound(game, "audio/sfx/dash.ogg", false, 1, false));
        register("pickup", new Sound(game, "audio/sfx/pickup.ogg", false, 0.5f, false));
        register("place", new Sound(game, "audio/sfx/place.ogg", false, 0.5f, false));
        register("confirm", new Sound(game, "audio/sfx/confirm.ogg", false, 0.5f, false));
        register("back", new Sound(game, "audio/sfx/back.ogg", false, 0.5f, false));
        register("achievement", new Sound(game, "audio/sfx/achievement.ogg", false, 1, false));
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
