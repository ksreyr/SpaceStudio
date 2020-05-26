package com.spacestudio;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.spacestudio.screen.loading.LoadingScreen;

public class UniverseMapBeforeGame extends Game {

    private AssetManager assetManager;
    private SpriteBatch batch;


    @Override
    public void create() {

        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        assetManager = new AssetManager();
        assetManager.getLogger().setLevel(Logger.DEBUG);

        batch = new SpriteBatch();

        setScreen(new LoadingScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        assetManager.dispose();
        batch.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;

    }
    public AssetManager getAssetmanager() {
        return assetManager;
    }



}
