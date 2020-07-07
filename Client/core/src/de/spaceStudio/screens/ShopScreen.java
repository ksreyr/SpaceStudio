package de.spaceStudio.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.spaceStudio.model.PlayerShip;
import de.spaceStudio.util.Base;
import de.spaceStudio.util.GdxUtils;

import static com.badlogic.gdx.graphics.Color.RED;

public class ShopScreen extends Base {

    //World Units
    private static final float WORLD_HEIGHT = 40f;
    private static final float WORLD_WIDTH = 80f;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private SpriteBatch batch;
    private PlayerShip ship;
    private Texture background;
    private BitmapFont font;


    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH,WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(this);

        // Add new Ship and center it
        ship = new PlayerShip(0,0,true,true,true,true);
        ship.x = (Gdx.graphics.getWidth() - ship.width) / 2;
        ship.y = (Gdx.graphics.getHeight() - ship.height) / 2;

        batch = new SpriteBatch();
        font = new BitmapFont();
        background = new Texture("ownAssets/sgx/backgrounds/spaceFight.png");
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width,height);
    }

    @Override
    public void render() {
        // Clear screen
        GdxUtils.clearScreen();
        renderer.setProjectionMatrix(camera.combined);

        drawGrid();

        batch.begin();
        // Draw Universe and Background
        //batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ship.render(batch);
        //if(drawSecurityFirstSection){ security.render(batch, 10,10); }
        font.draw(batch,"Money: " + "100.000$", 20, 1000);
        batch.end();


    }


    public void drawGrid() {
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);

        int worldWidth = (int) WORLD_WIDTH;
        int worldHeight = (int) WORLD_HEIGHT;

        for(int x = -worldWidth; x < worldHeight; x++){
            renderer.line(x, -worldHeight,x,worldHeight);
        }

        for(int y = -worldHeight; y < worldHeight; y++){
            renderer.line(-worldWidth, y, worldWidth, y);
        }

        renderer.setColor(RED);
        renderer.line(-worldWidth, 0.0f, worldWidth, 0.0f);
        renderer.line(0.0f, -worldHeight, 0.0f, worldHeight);

        renderer.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        ship.dispose();
    }
}