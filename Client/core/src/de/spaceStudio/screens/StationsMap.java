package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.spaceStudio.MainClient;
import thirdParties.GifDecoder;

import static de.spaceStudio.client.util.Global.currentPlayer;

public class StationsMap extends BaseScreen {


    private Stage stage;
    private Skin skin;
    private Texture background;
    private Viewport viewport;

    Animation<TextureRegion> station1,station2,station3,station4,station5,
            station6,station7,station8,station9,station10,statio11,station12;
    private float state =0.0f;

    public StationsMap(MainClient game, AssetManager assetManager) {
        super(game);

        viewport = new FitViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        station1 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/start.gif").read());
        station2 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/start.gif").read());
        station3 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/start.gif").read());
        station4 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/start.gif").read());
        station5 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/start.gif").read());
        station6 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/start.gif").read());
        station7 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/start.gif").read());
        station8 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/start.gif").read());
        station9 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/start.gif").read());
        station10 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/start.gif").read());
        statio11 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/start.gif").read());
        station12 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/start.gif").read());

        background = new Texture(Gdx.files.internal("Client/core/assets/data/maps/sky-map.jpg"));
    }
    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        state += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.01f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().begin();
        stage.getBatch().draw(background,0,0,BaseScreen.WIDTH,BaseScreen.HEIGHT);
        stage.getBatch().draw(station1.getKeyFrame(state), 300, 500, 25,25);
        stage.getBatch().draw(station2.getKeyFrame(state), 800, 700, 25,25);
        stage.getBatch().draw(station3.getKeyFrame(state), 256, 450, 25,25);
        stage.getBatch().draw(station4.getKeyFrame(state), 1100, 900, 25,25);
        stage.getBatch().draw(station5.getKeyFrame(state), 500, 300, 25,25);
        stage.getBatch().draw(station6.getKeyFrame(state), 365, 950, 25,25);
        stage.getBatch().draw(station1.getKeyFrame(state), 1500, 500, 25,25);
        stage.getBatch().draw(station2.getKeyFrame(state), 600, 250, 25,25);
        stage.getBatch().draw(station3.getKeyFrame(state), 1250, 1000, 25,25);
        stage.getBatch().draw(station4.getKeyFrame(state), 100, 900, 25,25);
        stage.getBatch().draw(station5.getKeyFrame(state), 405, 678, 25,25);
        stage.getBatch().draw(station6.getKeyFrame(state), 1500, 250, 25,25);
        ;
        stage.getBatch().end();
        stage.act();

        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
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
        super.dispose();
        stage.dispose();
    }


}
