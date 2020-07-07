package de.spaceStudio.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.spaceStudio.MainClient;
import de.spaceStudio.service.InitialDataGameService;

import static de.spaceStudio.client.util.Global.currentPlayer;
import static de.spaceStudio.service.LoginService.logout;
//“Sound effects obtained from https://www.zapsplat.com“

public class TP extends BaseScreen {




    float state = 0f;

    MainClient ships;

    private Stage stage;
    private Skin skinButton;
    private Viewport viewport;

    private TextButton startButton;


    public TP(MainClient game) {
        super(game);
        this.ships = game;

        viewport = new FitViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);
        skinButton = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        startButton = new TextButton("Fire",skinButton,"small");
        startButton.setPosition(1000,300);
        startButton.setColor(Color.RED);
        stage.addActor(startButton);


    }




    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();


    }



    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        logout(currentPlayer);
        super.dispose();
        skinButton.dispose();
        stage.dispose();
    }
}
