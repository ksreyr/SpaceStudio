package de.bremen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.bremen.MainClient;
import de.bremen.assets.AssetDescriptors;
import de.bremen.util.GdxUtils;


public class LoadingScreen extends ScreenAdapter {

    private static final float PROGRESS_BAR_WIDTH = 400f;
    private static final float PROGRESS_BAR_HEIGHT = 60f;

    private Stage stage;
    private Skin skin;

    private final MainClient game;
    private final AssetManager assetManager;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private float progress;
    private float waitTime = 0.75f;
    private boolean changeScreen;

    private Label loadingText;

    public LoadingScreen(MainClient game) {
        this.game = game;
        stage = new Stage(new FitViewport(800,600));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        assetManager = game.getAssetmanager();
        loadingText = new Label("Loading ...", skin);
        loadingText.setAlignment(Align.center);
        loadingText.setFontScale(2,2);
        loadingText.setPosition(stage.getWidth()/2f-50,400);
        loadingText.setColor(Color.WHITE);
        stage.addActor(loadingText);
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(800f, 480f, camera);
        renderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(stage);

        assetManager.load(AssetDescriptors.BACKGROUND_AREA);
        assetManager.load(AssetDescriptors.SGX_SKIN);
    }

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();

        update(delta);
        draw();

        if (changeScreen) {
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }


    private void update(float delta) {
        progress = assetManager.getProgress();

        if (assetManager.update()) {
            waitTime -= delta;

            if (waitTime <= 0) {
                changeScreen = true;
            }
        }
    }

    private void draw() {
        viewport.apply();
        stage.act();
        stage.draw();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        renderer.rect(
                (800f - PROGRESS_BAR_WIDTH) / 2f,
                (480f - PROGRESS_BAR_HEIGHT) / 2f,
                progress * PROGRESS_BAR_WIDTH,
                PROGRESS_BAR_HEIGHT
        );

        renderer.end();
    }
}
