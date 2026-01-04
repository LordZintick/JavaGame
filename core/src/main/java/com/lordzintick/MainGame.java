package com.lordzintick;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.ScreenUtils;
import com.lordzintick.achievement.Achievement;
import com.lordzintick.achievement.Achievements;
import com.lordzintick.audio.AudioManager;
import com.lordzintick.control.GamepadInput;
import com.lordzintick.control.Input;
import com.lordzintick.control.Keybinds;
import com.lordzintick.core.Logger;
import com.lordzintick.game.accessory.AccessoryTypes;
import com.lordzintick.game.entity.player.PlayerClass;
import com.lordzintick.game.screen.MainGameScreen;
import com.lordzintick.game.skill.SkillTypes;
import com.lordzintick.ui.screen.AchievementScreen;
import com.lordzintick.ui.screen.RunConfigScreen;
import com.lordzintick.ui.screen.TitleScreen;
import com.lordzintick.util.BaseScreen;
import com.lordzintick.util.UIUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
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
    public BitmapFont outlinedFont;
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
    public SkillTypes skillTypes;
    public AccessoryTypes accessoryTypes;
    public TextureRegion[][] effectAtlas;
    public TextureRegion[][] particlesAtlas;
    public final HashMap<PlayerClass, TextureRegion[][]> playerTextures = new HashMap<>();
    public Texture debugTexture;
    public Texture slotTexture;
    public Texture cooldownTexture;
    public PlayerClass selectedPlayerClass = PlayerClass.MAGE;
    public AssetManager assets;
    public FileHandle dataFile;
    public GameData gameData;
    public boolean loadedAssets = false;
    public AudioManager audio;
    public Keybinds keybinds;
    public GamepadInput gamepadInput;
    public int gamepadCursorX, gamepadCursorY;
    public Achievements achievements;
    public float achievementTicks = 0;
    public Achievement displayingAchievement = null;

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
        gamepadCursorX = Gdx.graphics.getWidth() / 2;
        gamepadCursorY = Gdx.graphics.getHeight() / 2;
        // Initialize the asset manager
        LOGGER.log("Initializing asset manager...");
        assets = new AssetManager(new InternalFileHandleResolver());
        assets.setLoader(Texture.class, "png", new TextureLoader(new InternalFileHandleResolver()));
        assets.setLoader(com.badlogic.gdx.audio.Sound.class, "wav", new SoundLoader(new InternalFileHandleResolver()));
        assets.setLoader(Music.class, "mp3", new MusicLoader(new InternalFileHandleResolver()));

        // Load textures and audio
        LOGGER.log("Loading assets...");
        FileHandle audioFolder = Gdx.files.local("audio");
        FileHandle texturesFolder = Gdx.files.local("textures");
        LOGGER.log("Audio folder exists: " + audioFolder.exists());
        LOGGER.log("Textures folder exists: " + texturesFolder.exists());
        loadAllAssets(audioFolder);
        loadAllAssets(texturesFolder);

        // Initialize non-asset related instances
        LOGGER.log("Creating random and sprite batches...");
        random = new Random(System.currentTimeMillis());
        gameBatch = new SpriteBatch();
        uiBatch = new SpriteBatch();
        mapBatch = new SpriteBatch();
        LOGGER.log("Creating camera, JSON, and input handler...");
        camera = new OrthographicCamera(2, 2);
        camera.setToOrtho(false, 2, 2);
        json = new Json();
        keybinds = new Keybinds(this);
        input = new Input(this);
        Gdx.input.setInputProcessor(input);
        gamepadInput = new GamepadInput(this);
        Controllers.addListener(gamepadInput);

        // Generate fonts
        LOGGER.log("Generating fonts...");
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.classpath("Monocraft.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.color = Color.WHITE;
        param.size = 22;
        font = fontGenerator.generateFont(param);
        param.borderColor = Color.BLACK;
        param.borderWidth = 2;
        outlinedFont = fontGenerator.generateFont(param);
        param.size = 66;
        param.borderColor = Color.DARK_GRAY;
        megaFont = fontGenerator.generateFont(param);
        fontGenerator.dispose();
    }

    @Override
    public void render() {
        if (assets.update()) {
            if (!loadedAssets) {
                // Initialize asset-related instances
                loadedAssets = true;
                audio = new AudioManager(this);
                achievements = new Achievements(this);

                // Initialize data file
                gameData = new GameData(this);
                dataFile = Gdx.files.local("gameData.json");
                if (!dataFile.exists()) {
                    dataFile.parent().mkdirs();
                    try {
                        if (dataFile.file().createNewFile()) {
                            LOGGER.log("Successfully created data file at path " + dataFile.file().getAbsolutePath());
                        }
                    } catch (IOException e) {
                        LOGGER.log("IOException: " + e.getMessage());
                    }
                    json.toJson(gameData, dataFile);
                    LOGGER.log("Finished creating data file");
                }
                JsonReader reader = json.getReader();
                gameData.read(json, reader.parse(dataFile));

                effectAtlas = TextureRegion.split(assets.get("textures/ui/effects.png"), 8, 8);
                particlesAtlas = TextureRegion.split(assets.get("textures/game/particles.png"), 2, 2);
                debugTexture = assets.get("textures/debug.png");
                slotTexture = assets.get("textures/ui/slot.png");
                cooldownTexture = assets.get("textures/ui/cooldown.png");

                for (PlayerClass clazz : PlayerClass.values()) {
                    playerTextures.put(clazz, TextureRegion.split(assets.get("textures/game/player/player_" + clazz.name().toLowerCase(Locale.ROOT) + ".png"), 6, 12));
                }

                skillTypes = new SkillTypes(this);
                accessoryTypes = new AccessoryTypes(this);

                // Initialize the ScreenHolder after the other initializations
                // This is VERY IMPORTANT, as this initializes the UI and requires the font to be loaded
                screenHolder = new ScreenHolder();

                // Set the initial screen
                screen = screenHolder.TITLE;
                screen.startMusic();
            }

            // Clear the screen to the background color and update screen objects + the camera
            ScreenUtils.clear(screen.getBackgroundColor());
            if (Controllers.getCurrent() != null && Controllers.getCurrent().isConnected()) {
                Controller controller = Controllers.getCurrent();
                gamepadCursorX = MathUtils.clamp(gamepadCursorX + (int) (controller.getAxis(2) * 50), 0, Gdx.graphics.getWidth());
                gamepadCursorY = MathUtils.clamp(gamepadCursorY + (int) (controller.getAxis(3) * 50), 0, Gdx.graphics.getHeight());

                Gdx.input.setCursorPosition(gamepadCursorX, gamepadCursorY);
            }
            if (!screen.isPaused()) {
                screen.update(Gdx.graphics.getDeltaTime());
            }
            camera.update();

            // Update the gameBatch transform matrix
            gameBatch.setTransformMatrix(camera.combined);

            // Render the current screen
            gameBatch.begin();
            screen.renderGame(Gdx.graphics.getDeltaTime());
            gameBatch.end();

            achievements.checkAllUnachieved();

            uiBatch.begin();
            screen.renderUI(Gdx.graphics.getDeltaTime());
            outlinedFont.draw(uiBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 40, Gdx.graphics.getHeight() - font.getLineHeight() * 2);
            outlinedFont.setColor(Color.GOLD);
            outlinedFont.draw(uiBatch, "Highscore: " + gameData.highscore, 40, Gdx.graphics.getHeight() - font.getLineHeight() * 5);
            outlinedFont.setColor(Color.WHITE);

            if (screen.isPaused()) {
                megaFont.draw(uiBatch, "PAUSED", (float) Gdx.graphics.getWidth() / 2 - UIUtil.getFontStringWidth("PAUSED", megaFont) / 2, (float) Gdx.graphics.getHeight() - font.getLineHeight());
            }

            if (achievementTicks > 0) {
                achievementTicks -= Gdx.graphics.getDeltaTime();
                if (displayingAchievement != null) {
                    uiBatch.draw(displayingAchievement.icon, Gdx.graphics.getWidth() - 74, Gdx.graphics.getHeight() - 74, 64, 64);
                    String text = "Achievement get! " + displayingAchievement.displayName;
                    float width = UIUtil.getFontStringWidth(text, outlinedFont);
                    outlinedFont.draw(
                        uiBatch,
                        text,
                        Gdx.graphics.getWidth() - 84 - width,
                        Gdx.graphics.getHeight() - outlinedFont.getLineHeight() * 2f,
                        width,
                        Align.right, false
                    );
                }
            }

            uiBatch.end();
        } else {
            ScreenUtils.clear(Color.BLACK);

            float progress = assets.getProgress();
            String text = (int) (progress * 100) + "%";
            uiBatch.begin();
            megaFont.draw(uiBatch, text, (float) Gdx.graphics.getWidth() / 2 - UIUtil.getFontStringWidth(text, megaFont) / 2, (float) Gdx.graphics.getHeight() / 2 - megaFont.getLineHeight() / 2);
            uiBatch.end();
        }
    }

    /**
     * Changes the current screen to the provided one.<br>
     * This is generally preferred over directly doing {@code game.screen = newScreen} as ths method handles music playback and pausing
     * @param newScreen The {@link BaseScreen} to change the screen to
     */
    public void changeScreen(BaseScreen newScreen) {
        boolean musicChange = !Objects.equals(this.screen.getPlayingBackgroundMusic(), newScreen.getPlayingBackgroundMusic());
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
        // Dispose the batches, fonts, and the current screen
        gameBatch.dispose();
        uiBatch.dispose();
        mapBatch.dispose();
        font.dispose();
        megaFont.dispose();
        screen.dispose();

        // Dispose of all assets
        assets.dispose();

        // Write the new data to the data file
        json.toJson(gameData, dataFile);
    }

    private void loadAllAssets(FileHandle folder) {
        LOGGER.log("Loading assets inside of folder " + folder.file().getAbsolutePath());
        int count = 0;
        for (FileHandle child : folder.list()) {
            if (child.isDirectory()) {
                loadAllAssets(child);
            } else {
                String name = child.name().toLowerCase(Locale.ROOT);
                if (name.endsWith("png")) {
                    LOGGER.log("Loading image file " + child.path());
                    assets.load(child.path(), Texture.class);
                } else if (name.endsWith("wav")) {
                    LOGGER.log("Loading sound file " + child.path());
                    assets.load(child.path(), Sound.class);
                } else if (name.endsWith("mp3")) {
                    LOGGER.log("Loading music file " + child.path());
                    assets.load(child.path(), Music.class);
                }
            }
            count++;
        }
        LOGGER.log("Finished loading files from folder " + folder.file().getAbsolutePath());
        LOGGER.log("Total files loaded: " + count);
    }

    /**
     * A small "record" class used for storing the static instances of the different screens
     */
    public final class ScreenHolder {
        public final TitleScreen TITLE;
        public final RunConfigScreen RUN_CONFIG;
        public final MainGameScreen MAIN_GAME;
        public final AchievementScreen ACHIEVEMENTS;

        private ScreenHolder() {
            TITLE = new TitleScreen(MainGame.this);
            RUN_CONFIG = new RunConfigScreen(MainGame.this);
            MAIN_GAME = new MainGameScreen(MainGame.this);
            ACHIEVEMENTS = new AchievementScreen(MainGame.this);
        }
    }
}
