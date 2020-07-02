package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.spaceStudio.MainClient;
import de.spaceStudio.server.model.Ship;

public class CombatScreen extends BaseScreen{


    private Stage stage;
    private Skin skin;
    private Viewport viewport;

    private Ship playerShip;
    private Texture enemyShip;
    private Texture background;

    ShipSelectScreen shipSelectScreen;

    public CombatScreen(MainClient game) {
        super(game);

        viewport = new FitViewport(BaseScreen.WIDTH,BaseScreen.HEIGHT);
        stage = new Stage(viewport);
        background = new Texture(Gdx.files.internal("Client/core/assets/data/CombatBG.jpg"));
        setShip();
        //stage.addActor(playerShip);

    }

    void setShip(){
        playerShip = shipSelectScreen.getSelectedShip();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width,height);
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
    }
}
