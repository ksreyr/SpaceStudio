package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.spaceStudio.Actors.ParallaxBackground;
import de.spaceStudio.MainClient;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.client.util.RequestUtils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import static de.spaceStudio.client.util.Global.*;


/**
 * Created by julienvillegas on 17/01/2017.
 */
public class TravelScreen extends ScreenAdapter {

    private final static Logger LOG = Logger.getLogger(TravelScreen.class.getName());
    private final Stage stage;
    private final MainClient game;
    private final OrthographicCamera camera;
    float timePassed = 0;
    String travelText = "Traveling threw Space Time since " + (int) timePassed + " Seconds";
    String playerText = "Waiting for other Player ...";
    Label travelLabel;
    Label playerLabel;
    int dot = 0;
    private boolean killTimer;
    private boolean requestSend = false;

    public TravelScreen(MainClient game) {
        super();
        this.game = game;
        stage = new Stage(new ScreenViewport());
        camera = (OrthographicCamera) stage.getViewport().getCamera();


        Array<Texture> textures = new Array<>();
        for (int i = 1; i <= 6; i++) {
            textures.add(new Texture(Gdx.files.internal("parallax/img" + i + ".png")));
            textures.get(textures.size - 1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }

        ParallaxBackground parallaxBackground = new ParallaxBackground(textures);
        parallaxBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        parallaxBackground.setSpeed(1);
        stage.addActor(parallaxBackground);

        Label.LabelStyle label1Style = new Label.LabelStyle();
        BitmapFont myFont = new BitmapFont(Gdx.files.internal("bitmap/amble.fnt"));
        label1Style.font = myFont;
        label1Style.fontColor = Color.BLUE;


        int row_height = Gdx.graphics.getWidth() / 12;
        int col_width = Gdx.graphics.getWidth() / 12;

        travelLabel = new Label(travelText, label1Style);
        travelLabel.setSize(Gdx.graphics.getWidth(), row_height);
        travelLabel.setPosition(0, Gdx.graphics.getHeight() - row_height * 2);
        travelLabel.setAlignment(Align.center);
        stage.addActor(travelLabel);

        if (Global.isOnlineGame) {
            scheduleLobby();
            playerLabel = new Label(playerText, label1Style);
            playerLabel.setSize(Gdx.graphics.getWidth(), row_height);
            playerLabel.setPosition(0, Gdx.graphics.getHeight() - row_height * 2 + 100);
            playerLabel.setAlignment(Align.center);
            stage.addActor(playerLabel);
        }
    }

    /**
     * Ask server every 5 seconds
     */
    private void scheduleLobby() {
        Timer schedule = new Timer();
        schedule.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (killTimer) {
                    schedule.cancel();
                    schedule.purge();
                    LOG.info("Timer killed");
                } else {
                    LOG.info("Fetching data from server...");
                    LOG.info(multiPlayerSessionID);

                    RequestUtils.canJump(Global.currentPlayer);

                }
            }
        }, 1000, 5000);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        travelText = "Traveling threw Space Time since " + (int) timePassed + " Seconds";
        timePassed += delta;
        travelLabel.setText(travelText);
        if (Global.isOnlineGame) {
            dot++;
            String dots = "";
            dots = ".".repeat(Math.max(0, dot % 5));
            playerLabel.setText(playerText + dots);
        }

        // Switch Screen after 10 Seconds or when all Players are ready
        boolean jumpReady = !Global.isOnlineGame || Global.allReady;
        if (timePassed > 5 && jumpReady) {
            if (!requestSend) {
//            Global.weaponListPlayer = RequestUtils.weaponsByShip(Global.currentShipPlayer); // Load all the Weapons  FIXME make async
                RequestUtils.sectionsByShip(Global.currentShipPlayer);
                RequestUtils.weaponsByShip(Global.currentShipPlayer);
                RequestUtils.crewMemeberByShip(Global.currentShipPlayer);
                if (Global.currentGegner != null && currentShipGegner != null) {
                    RequestUtils.crewMemeberByShip(Global.currentShipGegner);
                    RequestUtils.sectionsByShip(Global.currentShipGegner);
                    RequestUtils.weaponsByShip(Global.currentShipGegner);
                }
                requestSend = true;
            }
            killTimer = true;
            if (combatCrew.size() > 0 && combatSections.size() > 0 && combatWeapons.size() > 0)
                game.setScreen(new StopScreen(game));
        }
        stage.act();
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }


}