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

        //drawGrid();

        batch.begin();
        // Draw Universe and Background
        //batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ship.render(batch);
        font.draw(batch,"Money: " + ship.getMoney() , 20, 1000);

        securityOptions();
        weaponOptions();
        batch.end();


    }

    public void securityOptions(){
        // keys
        boolean qPressed = Gdx.input.isKeyPressed(Input.Keys.Q);
        font.draw(batch,"press 'q' to buy security ", 400, 960);

        if(qPressed){
            ship.setSecure(ship.getSecure() +10);
            ship.setMoney(-10);
        }
        boolean aPressed = Gdx.input.isKeyPressed(Input.Keys.A);
        font.draw(batch,"press 'a' to sell security ", 400, 880);
        font.draw(batch, ship.getSecure() + "%", 850,280);

        if(aPressed){
            ship.setSecure(ship.getSecure()-10);
            ship.setMoney(+10);
        }

    }

    public void weaponOptions(){
        // keys
        boolean tPressed = Gdx.input.isKeyPressed(Input.Keys.T);
        boolean zPressed = Gdx.input.isKeyPressed(Input.Keys.Y);
        boolean uPressed = Gdx.input.isKeyPressed(Input.Keys.U);
        boolean iPressed = Gdx.input.isKeyPressed(Input.Keys.I);
        font.draw(batch,"press 't' to activate weapon in first Section ", 800, 960);
        font.draw(batch,"press 'z' to activate weapon in second Section ", 800, 940);
        font.draw(batch,"press 'u' to activate weapon in third Section ", 800, 920);
        font.draw(batch,"press 'i' to activate weapon in fourth Section ", 800, 900);
        if (tPressed) {
            ship.setWeaponFst(true);
            ship.setMoney(-10);
        }
        if (zPressed) {
            ship.setWeaponSnd(true);
            ship.setMoney(-10);
        }
        if (uPressed) {
            ship.setWeaponTrd(true);
            ship.setMoney(-10);
        }
        if (iPressed) {
            ship.setWeaponFth(true);
            ship.setMoney(-10);
        }

        boolean gPressed = Gdx.input.isKeyPressed(Input.Keys.G);
        boolean hPressed = Gdx.input.isKeyPressed(Input.Keys.H);
        boolean jPressed = Gdx.input.isKeyPressed(Input.Keys.J);
        boolean kPressed = Gdx.input.isKeyPressed(Input.Keys.K);
        font.draw(batch,"press 'g' to delete weapon in first Section ", 800, 880);
        font.draw(batch,"press 'h' to delete weapon in second Section ", 800, 860);
        font.draw(batch,"press 'j' to delete weapon in third Section ", 800, 840);
        font.draw(batch,"press 'k' to delete weapon in fourth Section ", 800, 820);
        if (gPressed) {
            ship.setWeaponFst(false);
            ship.setMoney(+10);
        }
        if (hPressed) {
            ship.setWeaponSnd(false);
            ship.setMoney(+10);
        }
        if (jPressed) {
            ship.setWeaponTrd(false);
            ship.setMoney(+10);
        }
        if (kPressed) {
            ship.setWeaponFth(false);
            ship.setMoney(+10);
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