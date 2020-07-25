package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.gson.Gson;
import de.spaceStudio.MainClient;
import de.spaceStudio.assets.AssetDescriptors;
import de.spaceStudio.assets.RegionNames;
import de.spaceStudio.assets.StyleNames;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.client.util.RequestUtils;
import de.spaceStudio.config.GameConfig;
import de.spaceStudio.util.GdxUtils;

import java.util.logging.Logger;

/**
 * NewGameScreen class
 */
public class NewGameScreen extends ScreenAdapter {

    private final static Logger LOG = Logger.getLogger(NewGameScreen.class.getName());
    private final AssetManager assetManager;
    private final MainClient universeMap;
    private final MainClient mainClient;
    private Viewport viewport;
    private Stage stage;

    private Skin sgxSkin;
    private TextureAtlas gamePlayAtlas;


    private Sound click;
    private Boolean isMultiPayerRequestDone = false;


    public NewGameScreen(MainClient mainClient) {
        this.universeMap = mainClient;
        this.mainClient = mainClient;
        assetManager = universeMap.getAssetManager();
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
                click.play();
                Global.IS_SINGLE_PLAYER = true;
                mainClient.setScreen(new ShipSelectScreen(mainClient));

            }
        });

        TextButton textButtonMultiplayer = new TextButton(" Multiplayer ", sgxSkin, StyleNames.EMPHASISTEXTBUTTON);
        textButtonMultiplayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                click.play();
                Global.IS_SINGLE_PLAYER = false;
                final Gson gson = new Gson();
                String payLoad = gson.toJson(Global.currentPlayer);
                Net.HttpRequest request = RequestUtils.setupRequest(Global.SERVER_URL + Global.MULTIPLAYER_INIT, payLoad, Net.HttpMethods.POST);
                Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {

                    @Override
                    public void handleHttpResponse(Net.HttpResponse httpResponse) {
                        int statusCode = httpResponse.getStatus().getStatusCode();
                        String responseJson = httpResponse.getResultAsString();
                        LOG.info("User is now online");
                        System.out.println(statusCode);
                        LOG.info(responseJson);
                        if (statusCode == HttpStatus.SC_OK && responseJson.equals("202 ACCEPTED")) {
                            LOG.info("User is now online");
                            isMultiPayerRequestDone = true;
                        }
                    }

                    @Override
                    public void failed(Throwable t) {

                    }

                    @Override
                    public void cancelled() {

                    }
                });
            }
        });

        TextButton textButtonBackToMenu = new TextButton("  Back to menu  ", sgxSkin, StyleNames.EMPHASISTEXTBUTTON);
        textButtonBackToMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                click.play();
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
        if (isMultiPayerRequestDone) {
            mainClient.setScreen(new ShipSelectScreen(mainClient));
        }
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
        super.hide();
    }

    // Called when the Application is destroyed.
    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        click.dispose();
        sgxSkin.dispose();

    }
}
