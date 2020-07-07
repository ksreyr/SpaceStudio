package de.spaceStudio.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.spaceStudio.model.PlayerShip;
import de.spaceStudio.server.model.Player;
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

        drawShip(false, false, false,false);

        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("Client/core/assets/skin/default.fnt"));
        background = new Texture("ownAssets/sgx/backgrounds/spaceFight.png");
    }

    public void drawShip(boolean a, boolean b, boolean c, boolean d){
        // Add new Ship and center it
        ship = new PlayerShip(0,0);
        ship.x = (Gdx.graphics.getWidth() - ship.width) / 2;
        ship.y = (Gdx.graphics.getHeight() - ship.height) / 2;
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

        drawOptions();
        batch.end();


    }

    public void drawOptions(){
        // keys
        boolean qPressed = Gdx.input.isKeyPressed(Input.Keys.Q);
        boolean wPressed = Gdx.input.isKeyPressed(Input.Keys.W);
        boolean ePressed = Gdx.input.isKeyPressed(Input.Keys.E);
        boolean rPressed = Gdx.input.isKeyPressed(Input.Keys.R);
        font.draw(batch,"press 'q' to activate/deactivate security in first Section ", 400, 960);
        font.draw(batch,"press 'w' to activate/deactivate security in second Section ", 400, 940);
        font.draw(batch,"press 'e' to activate/deactivate security in third Section ", 400, 920);
        font.draw(batch,"press 'r' to activate/deactivate security in fourth Section ", 400, 900);
        if (qPressed) {
            ship.setSecurityFst(true);
        }
        if (wPressed) {
            ship.setSecuritySnd(true);
        }
        if (ePressed) {
            ship.setSecurityTrd(true);
        }
        if (rPressed) {
            ship.setSecurityFth(true);
        }
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