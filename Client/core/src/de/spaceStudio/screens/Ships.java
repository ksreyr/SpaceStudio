package de.spaceStudio.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.spaceStudio.MainClient;
import de.spaceStudio.server.model.Player;
import de.spaceStudio.server.model.Ship;
import java.util.ArrayList;
import de.spaceStudio.server.controller.ShipControllerImpl;


//“Sound effects obtained from https://www.zapsplat.com“

public class Ships extends BaseScreen {


    private static final int X_POSITION = 600;
    private static final int Y_POSITION = 290;

    private final MainClient ships;
    private SpriteBatch batch;
    private BitmapFont font;

    private Texture blueShip, redShip, greenship, topdownfighter;
    private Texture blueShipRoom, redShipRoom, greenshipRoom, topdownfighterRoom;
    private Ship blue_ship;

    private Stage stage;
    private Skin skin;
    private Skin skinButton;
    private TextButton next;
    private TextButton previous;
    private TextButton showHideRoom;
    int shipNumber = 0;
    int openNumber = 0;
    private Sound spaceShipChange;
    private ArrayList<Ship> shipList;
    Player player;
    Ship ship;
    ShipControllerImpl shipController;
    boolean isOpen ;

    public Ships(MainClient game) {
        super(game);
        this.ships = game;

        stage = new Stage(new FitViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT));
        Gdx.input.setInputProcessor(stage);
        skinButton = new Skin(Gdx.files.internal("skin/glassy-ui.json"));


        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        blueShip = new Texture(Gdx.files.internal("Client/core/assets/data/ships/blueships1.png"));
        redShip = new Texture(Gdx.files.internal("Client/core/assets/data/ships/redship.png"));
        greenship = new Texture(Gdx.files.internal("Client/core/assets/data/ships/greenship.png"));
        topdownfighter = new Texture(Gdx.files.internal("Client/core/assets/data/ships/topdownfighter.png"));


        blueShipRoom = new Texture(Gdx.files.internal("Client/core/assets/data/ships/blueships1_section.png"));
        redShipRoom = new Texture(Gdx.files.internal("Client/core/assets/data/ships/redship_section.png"));
        greenshipRoom = new Texture(Gdx.files.internal("Client/core/assets/data/ships/green_section.png"));
        topdownfighterRoom = new Texture(Gdx.files.internal("Client/core/assets/data/ships/topdownfighter_section.png"));


        spaceShipChange = Gdx.audio.newSound(Gdx.files.internal("Client/core/assets/data/music/change.wav"));
        nextButton();
        previousButton();
        showHideRoom();



        stage.addActor(next);
        stage.addActor(previous);
        stage.addActor(showHideRoom);


    }

    private void showHideRoom() {
        showHideRoom = new TextButton("show rooms", skinButton, "small");
        showHideRoom.setPosition(BaseScreen.WIDTH/2,100);
        showHideRoom.getLabel().setColor(Color.BLACK);
      //  isOpen = true;


        showHideRoom.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                spaceShipChange.play();
                openNumber++;
                if(isOpen){
                    showHideRoom.setText("show rooms");
                    isOpen =false;
                }else {
                    showHideRoom.setText("hide rooms");
                    isOpen = true;
                }
            }
        });
    }


    private void previousButton() {
        previous = new TextButton("previous", skinButton, "small");
        previous.setPosition(200,620);
        previous.getLabel().setColor(Color.BLACK);


        previous.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                spaceShipChange.play();

                       if(shipNumber < 0){
                           shipNumber = 3;
                       }else {
                           shipNumber--;
                       }
            }
        });
    }

    private void nextButton() {
        next = new TextButton("next", skinButton, "small");
        next.setPosition(1600,620);

        next.getLabel().setColor(Color.BLACK);
        next.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                spaceShipChange.play();
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
                stage.getBatch().draw(blueShip, X_POSITION,Y_POSITION,720,720);
                if(isOpen){
                    stage.getBatch().draw(blueShipRoom, X_POSITION,Y_POSITION,720,720);
                }
                break;
            case 1:
                stage.getBatch().draw(redShip,X_POSITION,Y_POSITION,720,720);
                if(isOpen){
                    stage.getBatch().draw(redShipRoom, X_POSITION,Y_POSITION,720,720);
                }
                break;
            case 2:
                stage.getBatch().draw(greenship,X_POSITION,Y_POSITION,720,720);
                if(isOpen){
                    stage.getBatch().draw(greenshipRoom, X_POSITION,Y_POSITION,720,720);
                }
                break;
            case 3:
                stage.getBatch().draw(topdownfighter,X_POSITION,Y_POSITION,720,720);
                if(isOpen){
                    stage.getBatch().draw(topdownfighterRoom, X_POSITION,Y_POSITION,720,720);
                }
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
        skinButton.dispose();
        spaceShipChange.dispose();
        stage.dispose();
    }
}
