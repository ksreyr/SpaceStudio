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


/**
 * Created by julienvillegas on 17/01/2017.
 */
public class TravelScreen extends ScreenAdapter {

    private Stage stage;
    private MainClient game;
    private OrthographicCamera camera;

    float timePassed = 0;
    String travelText = "Traveling threw Space Time since " + (int) timePassed + " Seconds";
    String playerText = "Waiting for other Player ...";
    Label travelLabel;
    Label playerLabel;

    int dot = 0;

    public TravelScreen(MainClient game) {
        super();
        this.game = game;
        stage = new Stage(new ScreenViewport());
        camera = (OrthographicCamera) stage.getViewport().getCamera();


        Array<Texture> textures = new Array<Texture>();
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
            playerLabel = new Label(playerText, label1Style);
            playerLabel.setSize(Gdx.graphics.getWidth(), row_height);
            playerLabel.setPosition(0, Gdx.graphics.getHeight() - row_height * 2 + 100);
            playerLabel.setAlignment(Align.center);
            stage.addActor(playerLabel);
        }
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
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < dot % 5; i++) {
                sb.append('.');
            }
            dots = sb.toString();
            playerLabel.setText(playerText + dots);
        }

        // Switch Screen after 10 Seconds or when all Players are ready
        boolean jumpReady = !Global.isOnlineGame || Global.allReady;
        if (timePassed > 15 && jumpReady) {
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

    }

    @Override
    public void dispose() {
        stage.dispose();
    }


}