package de.bremen.screens;


import com.badlogic.gdx.Screen;
import de.bremen.MainClient;


public abstract class BaseScreen implements Screen {
    protected MainClient game;
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;


    public BaseScreen(MainClient game) {
        this.game = game;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

    }
}
