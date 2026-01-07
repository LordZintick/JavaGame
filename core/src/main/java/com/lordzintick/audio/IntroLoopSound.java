package com.lordzintick.audio;

import com.badlogic.gdx.audio.Music;
import com.lordzintick.MainGame;

public class IntroLoopSound extends Sound {
    private final String loopFileName;
    private final Music loop;
    public boolean hasPlayedIntro = false;

    /**
     * Constructs a new {@link Sound} with the provided sound file name
     *
     * @param game
     * @param introFileName The name of the intro sound file this sound is for
     * @param volume   The volume this sound should play at
     */
    public IntroLoopSound(MainGame game, String introFileName, String loopFileName, float volume) {
        super(game, introFileName, false, volume, true);
        this.loopFileName = loopFileName;

        loop = game.assets.get(loopFileName);
        loop.setLooping(true);
        loop.setVolume(volume);
    }

    @Override
    public void pause() {
        if (hasPlayedIntro) {
            loop.pause();
        } else {
            music.pause();
        }
    }

    @Override
    public void play() {
        if (hasPlayedIntro) {
            loop.play();
        } else {
            music.setOnCompletionListener(music1 -> {
                hasPlayedIntro = true;
                play();
            });
            music.play();
        }
    }

    @Override
    public void play(Music.OnCompletionListener onCompletionListener) {
        loop.setOnCompletionListener(onCompletionListener);
        this.play();
    }

    @Override
    public void stop() {
        hasPlayedIntro = false;
        loop.stop();
        music.stop();
    }

    @Override
    public void dispose() {
        music.dispose();
        loop.dispose();
    }
}
