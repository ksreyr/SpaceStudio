package de.bremen.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.bremen.MainClient;

/**
 * Lobby screen to show online players
 *
 * @author Miguel Caceres
 * created on 6.17.2020
 */
public class LobbyScreen extends ScreenAdapter {

    private final MainClient game;

    private Stage stage;


    public LobbyScreen(MainClient game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void show() {
        super.show();
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
    public void dispose() {
        super.dispose();
    }

    public void hide(){

    }
}
