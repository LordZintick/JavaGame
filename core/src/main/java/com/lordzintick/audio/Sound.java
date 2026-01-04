package com.lordzintick.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.lordzintick.MainGame;
import com.lordzintick.core.Logger;

/**
 * An audio class simply holding a {@link Sound#fileName} of a sound to play and some sound metadata
 */
public class Sound {
    private static final Logger LOGGER = new Logger(Sound.class);

    public final String fileName;
    public final boolean looping;
    public final float volume;
    public final boolean stream;
    private com.badlogic.gdx.audio.Sound sfx;
    private Music music;

    /**
     * Constructs a new {@link Sound} with the provided sound file name
     * @param fileName The name of the sound file this sound is for
     * @param looping Whether this sound should loop when it is finished
     * @param volume The volume this sound should play at
     * @param stream Whether this sound should stream instead of play directly. If this is true, it is considered music. If false, it is a sound effect
     */
    public Sound(MainGame game, String fileName, boolean looping, float volume, boolean stream) {
        this.fileName = fileName;
        this.looping = looping;
        this.volume = volume;
        this.stream = stream;

        if (stream) {
            music = game.assets.get(fileName);
            music.setLooping(looping);
            music.setVolume(volume);
        } else {
            sfx = game.assets.get(fileName);
        }
    }

    /**
     * If this sound has {@code stream} set to {@code true}, pauses the music track
     */
    public void pause() {
        if (stream) {
            music.pause();
        }
    }

    /**
     * Plays the sound effect/music track and applies metadata
     */
    public void play() {
        if (stream) {
            music.play();
        } else {
            long id = sfx.play(volume);
            sfx.setLooping(id, looping);
        }
    }

    public void play(Music.OnCompletionListener onCompletionListener) {
        if (stream) {
            music.setOnCompletionListener(onCompletionListener);
            music.play();
        }
    }

    /**
     * Stops the sound effect/music track
     */
    public void stop() {
        if (stream) {
            music.stop();
        } else {
            sfx.stop();
        }
    }

    /**
     * Disposes the sound effect/music track, freeing up resources
     */
    public void dispose() {
        if (stream) {
            music.dispose();
        } else {
            sfx.dispose();
        }
    }
}
