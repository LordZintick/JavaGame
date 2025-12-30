package com.lordzintick.audio;

import com.badlogic.gdx.Input;
import com.lordzintick.control.Keybind;
import com.lordzintick.control.Keybinds;

import java.util.HashMap;

/**
 * A utility class used for managing sounds and music
 */
public final class AudioManager {
    public static final HashMap<String, Sound> SOUNDS = new HashMap<>();

    public static final Sound TITLE_MUSIC = register("title_music", new Sound("audio/lines_of_code.mp3", true, 0.5f, true));

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
