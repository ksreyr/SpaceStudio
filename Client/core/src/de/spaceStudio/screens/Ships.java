package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.spaceStudio.MainClient;


public class Ships extends BaseScreen {



    private final MainClient ships;
    private SpriteBatch batch;
    private BitmapFont font;

    private Texture blueShip, redShip, greenship, topdownfighter;

    private Stage stage;
    private Skin skin;
    private TextButton next;
    private TextButton previous;
    int shipNumber = 0;
    private boolean isChanged;

    Sound mouseClick;
    Sound whiclePassing;

    public Ships(MainClient game) {
        super(game);
        this.ships = game;

        stage = new Stage(new FitViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT));
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        blueShip = new Texture(Gdx.files.internal("Client/core/assets/data/ships/blueships1.png"));
        redShip = new Texture(Gdx.files.internal("Client/core/assets/data/ships/redship.png"));
        greenship = new Texture(Gdx.files.internal("Client/core/assets/data/ships/greenship.png"));
        topdownfighter = new Texture(Gdx.files.internal("Client/core/assets/data/ships/topdownfighter.png"));
        whiclePassing = Gdx.audio.newSound(Gdx.files.internal("Client/core/assets/data/music/export.wav"));
        nextButton();

        previousButton();

        stage.addActor(next);
        stage.addActor(previous);
        //stage.addActor(mouseClick);


    }

    private void previousButton() {
        previous = new TextButton("Previous", skin);
        previous.setSize(150, 60);
        previous.setPosition(200,620);
        previous.getLabel().setColor(Color.FOREST);
        previous.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                whiclePassing.play();
                       if(shipNumber < 0){
                           shipNumber = 3;
                       }else shipNumber --;
            }
        });
    }

    private void nextButton() {
        next = new TextButton("Next", skin);
        next.setSize(150, 60);
        next.setPosition(1600,620);
        next.getLabel().setColor(Color.FOREST);
        next.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                whiclePassing.play();
                if(shipNumber > 2)
                shipNumber=0;
                else {
                    shipNumber++;
                }

            }
        });

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

        switch (shipNumber){
            case 0:
                stage.getBatch().draw(blueShip, 600,290,720,720);
                break;
            case 1:
                stage.getBatch().draw(redShip,600,290,650,650);
                break;
            case 2:
                stage.getBatch().draw(greenship,600,290,720,720);
                break;
            case 3:
                stage.getBatch().draw(topdownfighter,600,290,720,720);
                break;

        }
        stage.act();
        stage.getBatch().end();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
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
