package de.spaceStudio.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import de.spaceStudio.config.GameConfig;
import de.spaceStudio.server.handler.SinglePlayerGame;
import de.spaceStudio.service.PlayerDataService;
import de.spaceStudio.util.GdxUtils;

import java.util.logging.Logger;

import static de.spaceStudio.client.util.Global.*;
import static de.spaceStudio.client.util.RequestUtils.setupRequest;
import static de.spaceStudio.service.LoginService.logout;


//Continue, New Game, Multiplayer Game, Options(Level Niveau), Exit
public class MenuScreen extends ScreenAdapter {

    private final static Logger LOG = Logger.getLogger(MenuScreen.class.getName());
    private final AssetManager assetManager;
    private final MainClient universeMap;
    private final MainClient mainClient;
    //
    PlayerDataService pds = new PlayerDataService();
    private Viewport viewport;
    private Stage stage;
    private Skin sgxSkin;
    private TextureAtlas gamePlayAtlas;
    private Sound click;
    private boolean isLoaded = false;
    private String screenToLoad;
    //

    public MenuScreen(MainClient mainClient) {
        this.universeMap = mainClient;
        this.mainClient = mainClient;
        assetManager = universeMap.getAssetManager();
    }

    private void UpdateGlobalSinglePlayer(SinglePlayerGame singlePlayerGame) {
        Global.singlePlayerGame = singlePlayerGame;
        currentShipPlayer = singlePlayerGame.getPlayerShip();
        screenToLoad = singlePlayerGame.getLastScreen();
        currentShipGegner = singlePlayerGame.getShipGegner();
        section1 = singlePlayerGame.getShipSection1();
        section2 = singlePlayerGame.getShipSection2();
        section3 = singlePlayerGame.getShipSection3();
        section4 = singlePlayerGame.getShipSection4();
        section5 = singlePlayerGame.getShipSection5();
        section6 = singlePlayerGame.getShipSection6();
    }

    //Called when this screen becomes the current screen for a Game.
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


        //Button: Continue, New Game, Options, Exit
        TextButton textButtonContinue = new TextButton(" Continue  ", sgxSkin, StyleNames.EMPHASISTEXTBUTTON);
        textButtonContinue.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                click.play();
                IS_SINGLE_PLAYER = true;
                String url = Global.SERVER_URL + Global.PLAYER_CONTINUE_ENDPOINT + currentPlayer.getName();

                final Net.HttpRequest request = setupRequest(url, "", Net.HttpMethods.GET);

                Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
                    public void handleHttpResponse(Net.HttpResponse httpResponse) {
                        int statusCode = httpResponse.getStatus().getStatusCode();
                        LOG.info("statusCode: " + statusCode);
                        String responseJson = httpResponse.getResultAsString();

                        if (statusCode == 200 && responseJson != null) {
                            Gson gson = new Gson();
                            LOG.info("Game load success for player: " + currentPlayer.getName());
                            singlePlayerGame = gson.fromJson(responseJson, SinglePlayerGame.class);
                            UpdateGlobalSinglePlayer(singlePlayerGame);
                            LOG.info(singlePlayerGame.getDifficult());
                            LOG.info(singlePlayerGame.getPlayerShip().toString());
                            isLoaded = true;
                        }
                    }

                    @Override
                    public void failed(Throwable t) {
                    }

                    @Override
                    public void cancelled() {
                        LOG.severe("Request Canceled");
                    }
                });


            }
        });


        TextButton textButtonNewGame = new TextButton("New Game", sgxSkin, StyleNames.EMPHASISTEXTBUTTON);
        textButtonNewGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                click.play();
                pds.cleaningData(currentPlayer, Net.HttpMethods.POST);
                mainClient.setScreen(new NewGameScreen(mainClient));
            }
        });

        TextButton textButtonOptions = new TextButton("  Options  ", sgxSkin, StyleNames.EMPHASISTEXTBUTTON);


        TextButton textButtonExit = new TextButton("    Exit    ", sgxSkin, StyleNames.EMPHASISTEXTBUTTON);
        textButtonExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //When application closes, session muss be closed
                click.play();
                logout(currentPlayer);
                Gdx.app.exit();
            }
        });

        //Title: Space Menu
        Label label = new Label("Menu", sgxSkin, StyleNames.TITLELABEL);


        table.add(label).row();
        table.row();
        // Player does not have saved game
        if (currentPlayer.getSavedGame() == null) {
            textButtonContinue.setDisabled(true);
        } else {
            table.add(textButtonContinue).row();
        }
        table.add(textButtonNewGame).row();
        table.add(textButtonOptions).row();
        table.add(textButtonExit).row();

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
        if (isLoaded) {
            loadGameScreen();
        }

    }

    public void loadGameScreen() {
        switch (screenToLoad) {
            case "COMBAT":
                mainClient.setScreen(new CombatScreen(mainClient));
                break;
            case "SHOP":
                mainClient.setScreen(new StopScreen(mainClient));
                break;
            default:
                mainClient.setScreen(new StationsMap(mainClient));
                break;
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
        super.pause();
    }

    // Called when the Application is resumed from a paused state, usually when it regains focus.
    @Override
    public void resume() {
        super.resume();
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
        sgxSkin.dispose();
    }


}
