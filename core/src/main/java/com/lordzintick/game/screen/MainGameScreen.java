package com.lordzintick.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.lordzintick.MainGame;
import com.lordzintick.game.entity.Player;
import com.lordzintick.game.tile.TilemapHandler;
import com.lordzintick.ui.widget.TextButton;
import com.lordzintick.ui.widget.TextLabel;
import com.lordzintick.util.Text;
import com.lordzintick.util.UIUtil;

/**
 * An implementation of the {@link AbstractGameScreen} class representing the main game screen containing the primary game area
 */
public class MainGameScreen extends AbstractGameScreen {
    private Player player;
    private Texture map;
    private TextLabel manaLabel;
    private TextButton spellbookButton;
    private boolean spellbookOpen = false;
    private Texture slotTexture;

    /**
     * Constructs a new {@link MainGameScreen} with the provided {@link MainGame}
     * @param game The {@link MainGame} instance that this screen is for
     */
    public MainGameScreen(MainGame game) {
        super(game);
    }

    public Player getPlayer() {return player;}

    @Override
    protected void populateInitialObjects() {
        player = new Player(this);
        objects.add(player);

        map = new Texture("textures/map.png");
        slotTexture = new Texture("textures/slot.png");
    }

    @Override
    protected void addWidgets() {
        widgets.add(new TextButton(this, new Text("Exit").setAlign(UIUtil.CENTER), 74, 25, 128, 40, () -> {
            game.camera.position.set(0,0,0);
            game.camera.update();
            game.changeScreen(game.screenHolder.TITLE);
        }));

        manaLabel = new TextLabel(this, new Text("manabanana"), getMidX() - 32, Gdx.graphics.getHeight() - 60);
        widgets.add(manaLabel);

        spellbookButton = new TextButton(this, new Text("Spellbook").setAlign(UIUtil.CENTER), Gdx.graphics.getWidth() - 64, 25, 128, 40, () -> {
            spellbookOpen = !spellbookOpen;
        });
        widgets.add(spellbookButton);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        manaLabel.text = new Text("Mana: " + player.mana + "/" + player.maxMana).setColor(Color.BLUE);
    }

    @Override
    public void renderGame(float deltaTime) {
        game.gameBatch.draw(map, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getWidth());
        super.renderGame(deltaTime);
    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
    }
}
