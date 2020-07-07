package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.spaceStudio.MainClient;
import de.spaceStudio.assets.AssetDescriptors;
import de.spaceStudio.assets.RegionNames;
import de.spaceStudio.assets.StyleNames;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.config.GameConfig;
import de.spaceStudio.util.GdxUtils;

/**
 * NewGameScreen class
 */
public class NewGameScreen extends ScreenAdapter {


    private MainClient universeMap;
    private final AssetManager assetManager;
    private MainClient mainClient;
    private Viewport viewport;
    private Stage stage;

    private Skin sgxSkin;
    private TextureAtlas gamePlayAtlas;


    private Sound click;


    public NewGameScreen(MainClient mainClient) {
        this.universeMap = mainClient;
        this.mainClient = mainClient;
        assetManager = universeMap.getAssetmanager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT);
        stage = new Stage(viewport, universeMap.getBatch());
        click = Gdx.audio.newSound(Gdx.files.internal("Client/core/assets/data/music/mouseclick.wav"));

        sgxSkin = assetManager.get(AssetDescriptors.SGX_SKIN);
        gamePlayAtlas = assetManager.get(AssetDescriptors.BACKGROUND_AREA);

        Table table = new Table(sgxSkin);
        table.defaults().space(20);

        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.MENU_BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        TextButton textButtonSinglePlayer = new TextButton(" Single player ", sgxSkin, StyleNames.EMPHASISTEXTBUTTON);
        textButtonSinglePlayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Global.IS_SINGLE_PLAYER = true;
                mainClient.setScreen(new ShipSelectScreen(mainClient));

            }
        });

        TextButton textButtonMultiplayer = new TextButton(" Multiplayer ", sgxSkin, StyleNames.EMPHASISTEXTBUTTON);
        textButtonMultiplayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Global.IS_SINGLE_PLAYER = false;
                mainClient.setScreen(new ShipSelectScreen(mainClient));
            }
        });

        TextButton textButtonBackToMenu = new TextButton("  Back to menu  ", sgxSkin, StyleNames.EMPHASISTEXTBUTTON);
        textButtonBackToMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainClient.setScreen(new MenuScreen(mainClient));
            }
        });

        //Title:  Menu
        Label label = new Label("New game", sgxSkin, StyleNames.TITLELABEL);


        table.add(label).row();
        table.row();
        table.add(textButtonSinglePlayer).row();
        table.add(textButtonMultiplayer).row();
        table.add(textButtonBackToMenu).row();

        table.center();
        table.setFillParent(true);
        table.pack();

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }
    // Called when the screen should render itself.
    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();
        stage.act();
        stage.draw();
    }

    // Called when the Application is resized.
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    // Called when the Application is paused, usually when it's not active or visible on-screen.
    @Override
    public void pause() {
    }

    // Called when the Application is resumed from a paused state, usually when it regains focus.
    @Override
    public void resume() {
    }

    // Called when this screen is no longer the current screen for a Game.
    @Override
    public void hide() {
        dispose();
    }

    // Called when the Application is destroyed.
    @Override
    public void dispose() {
        stage.dispose();
    }
}
