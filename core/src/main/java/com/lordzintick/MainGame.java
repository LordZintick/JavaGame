package com.lordzintick;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import com.lordzintick.audio.AudioManager;
import com.lordzintick.audio.Sound;
import com.lordzintick.control.Input;
import com.lordzintick.core.Logger;
import com.lordzintick.game.screen.AbstractGameScreen;
import com.lordzintick.game.screen.MainGameScreen;
import com.lordzintick.game.spell.Spells;
import com.lordzintick.ui.screen.StartGameScreen;
import com.lordzintick.ui.screen.TitleScreen;
import com.lordzintick.util.BaseScreen;

import java.util.Random;

/**
 * The main game class used for everything else
 */
public class MainGame extends ApplicationAdapter {
    private static final Logger LOGGER = new Logger(MainGame.class);

    /**
     * The {@link SpriteBatch} used for rendering the tilemaps and tiles
     */
    public SpriteBatch mapBatch;
    /**
     * The {@link SpriteBatch} used for rendering things that should move with the camera (e.g. game objects)
     */
    public SpriteBatch gameBatch;
    /**
     * The {@link SpriteBatch} used for rendering things that should stay static on the screen (e.g. UI, HUD)
     */
    public SpriteBatch uiBatch;
    /**
     * The pre-generated font
     */
    public BitmapFont font;
    /**
     * {@link MainGame#font} but 3x the size and with a dark gray border
     */
    public BitmapFont megaFont;
    /**
     * The {@link OrthographicCamera} used for world transformation
     */
    public OrthographicCamera camera;
    /**
     * The {@link Input} instance used for input handling
     */
    public Input input;
    /**
     * The {@link Random} instance used for random number generation
     */
    public Random random;
    /**
     * The {@link Json} instance used for JSON parsing and writing
     */
    public Json json;
    public Spells spells;
    public TextureRegion[][] effectAtlas;
    public TextureRegion[][] particlesAtlas;

    /**
     * The current {@link BaseScreen} of the game
     */
    public BaseScreen screen;
    /**
     * A {@link ScreenHolder} instance to hold all the screen instances
     */
    public ScreenHolder screenHolder;

    @Override
    public void create() {
        // Initialize instances
        effectAtlas = TextureRegion.split(new Texture("textures/effects.png"), 8, 8);
        particlesAtlas = TextureRegion.split(new Texture("textures/particles.png"), 2, 2);

        random = new Random(System.currentTimeMillis());
        gameBatch = new SpriteBatch();
        uiBatch = new SpriteBatch();
        mapBatch = new SpriteBatch();
        camera = new OrthographicCamera(2, 2);
        camera.setToOrtho(false, 2, 2);
        json = new Json();
        spells = new Spells(this);
        input = new Input(this);
        Gdx.input.setInputProcessor(input);

        // Generate fonts
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Monocraft.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.color = Color.WHITE;
        param.size = 22;
        font = fontGenerator.generateFont(param);
        param.size = 66;
        param.borderColor = Color.DARK_GRAY;
        param.borderWidth = 2;
        megaFont = fontGenerator.generateFont(param);
        fontGenerator.dispose();

        // Initialize the ScreenHolder after the other initializations
        // This is VERY IMPORTANT, as this initializes the UI and requires the font to be loaded
        screenHolder = new ScreenHolder();

        // Set the initial screen
        screen = screenHolder.TITLE;
        screen.startMusic();
    }

    @Override
    public void render() {
        // Clear the screen to the background color and update screen objects + the camera
        ScreenUtils.clear(screen.getBackgroundColor());
        if (!screen.isPaused()) {
            spells.tickAll(Gdx.graphics.getDeltaTime());
            screen.update(Gdx.graphics.getDeltaTime());
        }
        camera.update();

        // Update the gameBatch transform matrix
        gameBatch.setTransformMatrix(camera.combined);

        // Render the current screen
        gameBatch.begin();
        screen.renderGame(Gdx.graphics.getDeltaTime());
        gameBatch.end();

        uiBatch.begin();
        screen.renderUI(Gdx.graphics.getDeltaTime());
        font.draw(uiBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 40, Gdx.graphics.getHeight() - font.getLineHeight() * 2);
        uiBatch.end();
    }

    /**
     * Changes the current screen to the provided one.<br>
     * This is generally preferred over directly doing {@code game.screen = newScreen} as ths method handles music playback and pausing
     * @param newScreen The {@link BaseScreen} to change the screen to
     */
    public void changeScreen(BaseScreen newScreen) {
        boolean musicChange = this.screen.getBackgroundMusic() != newScreen.getBackgroundMusic();
        if (musicChange) {
            this.screen.pauseMusic();
        }
        this.screen = newScreen;
        if (musicChange) {
            this.screen.startMusic();
        }
    }

    @Override
    public void dispose() {
        // Dispose the batches, fonts, the current screen, and the tilemap handler
        gameBatch.dispose();
        uiBatch.dispose();
        mapBatch.dispose();
        font.dispose();
        megaFont.dispose();
        screen.dispose();

        effectAtlas[0][0].getTexture().dispose();
        particlesAtlas[0][0].getTexture().dispose();

        // Iterate through all sounds and dispose of them
        for (Sound sound : AudioManager.SOUNDS.values()) {
            sound.dispose();
        }
    }

    /**
     * A small "record" class used for storing the static instances of the different screens
     */
    public final class ScreenHolder {
        public final TitleScreen TITLE;
        public final StartGameScreen START_GAME;
        public final MainGameScreen MAIN_GAME;

        private ScreenHolder() {
            TITLE = new TitleScreen(MainGame.this);
            START_GAME = new StartGameScreen(MainGame.this);
            MAIN_GAME = new MainGameScreen(MainGame.this);
        }
    }
}
