package com.spacestudio.screen.menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacestudio.UniverseMapBeforeGame;
import com.spacestudio.assets.AssetDescriptors;
import com.spacestudio.assets.StyleNames;
import com.spacestudio.assets.RegionNames;
import com.spacestudio.config.GameConfig;
import com.spacestudio.util.GdxUtils;

//Continue, New Game, Multiplayer Game, Options(Level Niveau), Exit
public class MenuScreen extends ScreenAdapter {


    private UniverseMapBeforeGame universeMap;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;

    private Skin sgxSkin;
    private TextureAtlas gamePlayAtlas;



    public MenuScreen(UniverseMapBeforeGame universeMap){
        this.universeMap = universeMap;
        assetManager = universeMap.getAssetmanager();
    }



    //Called when this screen becomes the current screen for a Game.
    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT);
        stage = new Stage(viewport, universeMap.getBatch());

        sgxSkin = assetManager.get(AssetDescriptors.SGX_SKIN);
        gamePlayAtlas = assetManager.get(AssetDescriptors.BACKGROUND_AREA);

        Table table = new Table(sgxSkin);
        table.defaults().space(20);

        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));


        //Button: Continue, New Game, Options, Exit
        TextButton textButtonContinue = new TextButton(" Continue  ", sgxSkin, StyleNames.EMPHASISTEXTBUTTON);
        textButtonContinue.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) { }
        });

        TextButton textButtonNewGame = new TextButton("New Game", sgxSkin, StyleNames.EMPHASISTEXTBUTTON);
        textButtonNewGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) { }
        });

        TextButton textButtonOptions = new TextButton("  Options  ", sgxSkin, StyleNames.EMPHASISTEXTBUTTON);
        textButtonOptions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) { }
        });

        TextButton textButtonExit = new TextButton("    Exit    ", sgxSkin, StyleNames.EMPHASISTEXTBUTTON);
        textButtonExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        //Title: Space Menu
        Label label = new Label("Space Studio Menu", sgxSkin, StyleNames.TITLELABEL);


        table.add(label).row();
        table.row();
        table.add(textButtonContinue).row();
        table.add(textButtonNewGame).row();
        table.add(textButtonOptions).row();
        table.add(textButtonExit).row();

        table.center();
        table.setFillParent(true);
        table.pack();

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

    }



    // Called when the screen should render itself.
    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();
        stage.act();
        stage.draw();
    }

    // Called when the Application is resized.
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    // Called when the Application is paused, usually when it's not active or visible on-screen.
    @Override
    public void pause() {}

    // Called when the Application is resumed from a paused state, usually when it regains focus.
    @Override
    public void resume() {}

    // Called when this screen is no longer the current screen for a Game.
    @Override
    public void hide() {
        dispose();
    }

    // Called when the Application is destroyed.
    @Override
    public void dispose() {
        stage.dispose();
    }



}
