package de.bremen.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.bremen.MainClient;
import de.bremen.assets.AssetDescriptors;
import de.bremen.util.GdxUtils;


public class LoadingScreen extends ScreenAdapter {

    private static final float PROGRESS_BAR_WIDTH = 400f;
    private static final float PROGRESS_BAR_HEIGHT = 60f;

    private final MainClient game;
    private final AssetManager assetManager;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private float progress;
    private float waitTime = 0.75f;
    private boolean changeScreen;

    public LoadingScreen(MainClient game) {
        this.game = game;
        assetManager = game.getAssetmanager();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(800f, 480f, camera);
        renderer = new ShapeRenderer();

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
