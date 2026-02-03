package com.github.lordzintick.java_game.registrar;

import com.github.lordzintick.pixel_krush.core.util.audio.IntroLoopSound;
import com.github.lordzintick.pixel_krush.core.util.audio.Sound;
import com.github.lordzintick.pixel_krush.core.api.AbstractGame;
import com.github.lordzintick.pixel_krush.core.util.registry.DeferredRegister;

/**
 * A utility class used for managing sounds and music
 */
public final class AudioRegistrar {
    public static DeferredRegister<Sound> registrar(AbstractGame game) {
        DeferredRegister<Sound> registrar = DeferredRegister.create(game, AbstractGame.getGlobalId("audio"));

        // Music
        registrar.register("title_music", new IntroLoopSound(game, "audio/music/theme_song_intro.wav", "audio/music/theme_song_loop.wav", 1f));
        registrar.register("achievements_music", new Sound(game, "audio/music/pulsar.mp3", true, 1f, true));
        registrar.register("game_music_1", new Sound(game, "audio/music/leap.mp3", false, 1f, true));
        registrar.register("game_music_2", new Sound(game, "audio/music/rush_point.mp3", false, 0.5f, true));
        registrar.register("game_music_3", new Sound(game, "audio/music/perilous_dungeon.mp3", false, 0.5f, true));
        registrar.register("game_music_4", new Sound(game, "audio/music/monstervania_2.mp3", false, 0.5f, true));
        registrar.register("game_music_5", new Sound(game, "audio/music/monstervania_1.mp3", false, 0.5f, true));

        // SFX
        registrar.register("shoot", new Sound(game, "audio/sfx/shoot.ogg", false, 1, false));
        registrar.register("hit", new Sound(game, "audio/sfx/hit.ogg", false, 0.5f, false));
        registrar.register("levelup", new Sound(game, "audio/sfx/levelup.ogg", false, 1, false));
        registrar.register("player_hit", new Sound(game, "audio/sfx/player_hit.ogg", false, 1, false));
        registrar.register("kaboom", new Sound(game, "audio/sfx/kaboom.ogg", false, 0.5f, false));
        registrar.register("slash", new Sound(game, "audio/sfx/slash.ogg", false, 0.5f, false));
        registrar.register("kill", new Sound(game, "audio/sfx/kill.ogg", false, 0.5f, false));
        registrar.register("heal", new Sound(game, "audio/sfx/heal.ogg", false, 1, false));
        registrar.register("block", new Sound(game, "audio/sfx/block.ogg", false, 1, false));
        registrar.register("mega_slash", new Sound(game, "audio/sfx/mega_slash.ogg", false, 0.5f, false));
        registrar.register("mega_hit", new Sound(game, "audio/sfx/mega_hit.ogg", false, 0.5f, false));
        registrar.register("mega_kill", new Sound(game, "audio/sfx/mega_kill.ogg", false, 0.5f, false));
        registrar.register("laser", new Sound(game, "audio/sfx/laser.ogg", false, 0.5f, false));
        registrar.register("laser_ambient", new Sound(game, "audio/sfx/laser_ambient.ogg", false, 0.25f, false));
        registrar.register("roar", new Sound(game, "audio/sfx/roar.ogg", false, 0.5f, false));
        registrar.register("dash", new Sound(game, "audio/sfx/dash.ogg", false, 1, false));
        registrar.register("pickup", new Sound(game, "audio/sfx/pickup.ogg", false, 0.5f, false));
        registrar.register("place", new Sound(game, "audio/sfx/place.ogg", false, 0.5f, false));
        registrar.register("confirm", new Sound(game, "audio/sfx/confirm.ogg", false, 0.5f, false));
        registrar.register("back", new Sound(game, "audio/sfx/back.ogg", false, 0.5f, false));
        registrar.register("achievement", new Sound(game, "audio/sfx/achievement.ogg", false, 1, false));
        registrar.register("strike", new Sound(game, "audio/sfx/strike.ogg", false, 0.5f, false));

        return registrar;
    }
}
