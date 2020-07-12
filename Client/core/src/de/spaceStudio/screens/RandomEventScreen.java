package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.spaceStudio.MainClient;
import de.spaceStudio.assets.AssetDescriptors;
import de.spaceStudio.assets.RegionNames;
import de.spaceStudio.assets.StyleNames;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.config.GameConfig;
import de.spaceStudio.service.PlayerDataService;
import de.spaceStudio.util.GdxUtils;

import java.util.Random;

import static de.spaceStudio.client.util.Global.currentPlayer;
import static de.spaceStudio.service.LoginService.logout;


public class RandomEventScreen extends ScreenAdapter {


    private MainClient universeMap;
    private MainClient mainClient;
    private Viewport viewport;
    private Stage stage;

    private Skin skin;


    private Sound click;

    Dialog randomDialog;

    AssetManager assetManager;
    //

    public RandomEventScreen(MainClient mainClient){
        this.universeMap = mainClient;
        this.mainClient = mainClient;
        assetManager = universeMap.getAssetManager();
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    @Override
    public void show()
    {
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        randomDialog = new Dialog("New Stop for Ship", skin)
        {
            protected void result(Object object)
            {
                System.out.println("Option: " + object);
                if (object.equals(1L))
                {
                    Global.currentShip.setHp(Global.currentShip.getHp() + getRandomNumberInRange(-20, 20));
//                    gameScreen.setIntScore(0);
                    System.out.println("Button Pressed");
                } else {
                    // Goto main menut
                }
                Timer.schedule(new Timer.Task()
                {

                    @Override
                    public void run()
                    {
                        randomDialog.show(stage);
                    }
                }, 1);
            };
        };

        randomDialog.button("Check out the other Ship", 1L);
        randomDialog.button("Check out the Station", 2L);

        Timer.schedule(new Timer.Task()
        {

            @Override
            public void run()
            {
                randomDialog.show(stage);
            }
        }, 1);

    }

    @Override
    public void render(float delta)
    {
//        GdxUtils.clearScreen();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

    }

    @Override
    public void dispose()
    {
        stage.dispose();
    }
    // Called when the Application is resized.
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    // Called when the Application is paused, usually when it's not active or visible on-screen.
    @Override
    public void pause() {}

    // Called when the Application is resumed from a paused state, usually when it regains focus.
    @Override
    public void resume() {}

    // Called when this screen is no longer the current screen for a Game.
    @Override
    public void hide() {
        dispose();
    }




}
