package de.spaceStudio.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.spaceStudio.MainClient;
import de.spaceStudio.client.util.Difficult;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.server.model.CrewMember;
import de.spaceStudio.server.model.Section;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.service.InitialDataGameService;
import thirdParties.GifDecoder;

import static de.spaceStudio.client.util.Global.*;
import static de.spaceStudio.service.LoginService.fetchLoggedUsers;
import static de.spaceStudio.service.LoginService.logout;
//“Sound effects obtained from https://www.zapsplat.com“

public class ShipSelectScreen extends BaseScreen {
    private Label usernameLabel;

    private static final int X_POSITION = 750;
    private static final int Y_POSITION = 550;
    private static final int SHIP_WIDTH = 500;
    private static final int SHIP_HEIGHT = 500;

    private final MainClient ships;
    private SpriteBatch batch;
    private BitmapFont font;

    private Texture blueShip, redShip, greenship, topdownfighter;
    private Texture blueShipRoom, redShipRoom, greenshipRoom, topdownfighterRoom;

    private Texture shield;
    private Texture weapon;
    private Texture drive;
    private TextField crew_1_name, crew_2_name, crew_3_name;
   // private TextField textField;

    Animation<TextureRegion> crew1;
    Animation<TextureRegion>  crew2;
    Animation<TextureRegion>  crew3;
    private Texture background;
    float state = 0f;


    private Stage stage;
    private Skin skinButton;
    private TextButton next;
    private TextButton previous;
    private TextButton showHideRoom;
    private TextButton startButton;
    private TextButton saveShip;
    private TextButton saveStation;
    private TextButton easyButton;
    private TextButton normalButton;

    private ShapeRenderer shapeRenderer;
    int shipNumber = 0;
    int openNumber = 0;
    private Sound spaceShipChange;

    private InitialDataGameService idgs = new InitialDataGameService();

    boolean isOpen;
    private InputHandler inputHandler;
    private int levelDifficult;

    //
    Ship ship;
    CrewMember crewMember0 = Global.crewMember0;
    CrewMember crewMember1 = Global.crewMember1;
    CrewMember crewMember2 = Global.crewMember2;

    Section section1 = Global.section1;
    Section section2 = Global.section2;
    Section section3 = Global.section3;
    Section section4 = Global.section4;
    Section section5 = Global.section5;
    Section section6 = Global.section6;

    //
    public ShipSelectScreen(MainClient game) {
        super(game);
        this.ships = game;
        fetchLoggedUsers();

        stage = new Stage(new FitViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT));

        Gdx.input.setInputProcessor(stage);
        skinButton = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        crew1 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/crew1.gif").read());
        crew2 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/crew2.gif").read());
        crew3 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/crew3.gif").read());

        usernameLabel = new Label("Pruebe", skinButton);
        usernameLabel.setPosition(100, 350);
        stage.addActor(usernameLabel);
        background = new Texture(Gdx.files.internal("Client/core/assets/data/ast.jpg"));
        shapeRenderer = new ShapeRenderer();

        inputHandler = new InputHandler();

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(3);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        crew_1_name = new TextArea("John", skinButton);
        crew_1_name.setPosition(70, 250);
        crew_2_name = new TextArea("Max", skinButton);
        crew_2_name.setPosition(70, 180);
        crew_3_name = new TextArea("Jack", skinButton);
        crew_3_name.setPosition(70, 110);


        blueShip = new Texture(Gdx.files.internal("Client/core/assets/data/ships/blueships1.png"));
        redShip = new Texture(Gdx.files.internal("Client/core/assets/data/ships/redship.png"));
        greenship = new Texture(Gdx.files.internal("Client/core/assets/data/ships/greenship.png"));
        topdownfighter = new Texture(Gdx.files.internal("Client/core/assets/data/ships/topdownfighter.png"));


        blueShipRoom = new Texture(Gdx.files.internal("Client/core/assets/data/ships/blueships1_section.png"));
        redShipRoom = new Texture(Gdx.files.internal("Client/core/assets/data/ships/redship_section.png"));
        greenshipRoom = new Texture(Gdx.files.internal("Client/core/assets/data/ships/green_section.png"));
        topdownfighterRoom = new Texture(Gdx.files.internal("Client/core/assets/data/ships/topdownfighter_section.png"));


        shield = new Texture(Gdx.files.internal("Client/core/assets/data/ships/security.png"));
        weapon = new Texture(Gdx.files.internal("Client/core/assets/data/ships/attack.png"));
        drive = new Texture(Gdx.files.internal("Client/core/assets/data/ships/rocket.png"));
        spaceShipChange = Gdx.audio.newSound(Gdx.files.internal("Client/core/assets/data/music/change.wav"));
        nextButton();
        previousButton();
        showHideRoom();
        selectLevelView();
        StartButton();


        stage.addActor(next);
        stage.addActor(previous);
        stage.addActor(showHideRoom);
        stage.addActor(startButton);
        stage.addActor(easyButton);
        stage.addActor(normalButton);
        stage.addActor(crew_1_name);
        stage.addActor(crew_2_name);
        stage.addActor(crew_3_name);

        this.levelDifficult = 0;
    }

    private void StartButton() {
        startButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switch (shipNumber) {
                    case 0:
                        ship = Global.ship0;
                        break;
                    case 1:
                        ship = Global.ship1;
                        break;
                    case 2:
                        ship = Global.ship2;
                        break;
                    default:
                        ship = Global.ship3;
                        break;
                }
                ship.setOwner(Global.currentPlayer);
                section1.setShip(ship);
                section2.setShip(ship);
                section3.setShip(ship);
                section4.setShip(ship);
                section5.setShip(ship);
                section6.setShip(ship);

                idgs.sendRequestAddShip(ship, Net.HttpMethods.POST);

                idgs.sendRequestAddSection(section1, Net.HttpMethods.POST);
                idgs.sendRequestAddSection(section2, Net.HttpMethods.POST);
                idgs.sendRequestAddSection(section3, Net.HttpMethods.POST);
                idgs.sendRequestAddSection(section4, Net.HttpMethods.POST);
                idgs.sendRequestAddSection(section5, Net.HttpMethods.POST);
                idgs.sendRequestAddSection(section6, Net.HttpMethods.POST);
                try{
                    Thread.sleep(100);
                }catch (Exception e){

                }
                crewMember0.setCurrentSection(section1);
                crewMember1.setCurrentSection(section2);
                crewMember2.setCurrentSection(section3);
                crewMember0.setName(crew_1_name.getText());
                crewMember1.setName(crew_2_name.getText());
                crewMember2.setName(crew_3_name.getText());
                idgs.sendRequestAddCrew(crewMember0,Net.HttpMethods.POST);
                idgs.sendRequestAddCrew(crewMember1,Net.HttpMethods.POST);
                idgs.sendRequestAddCrew(crewMember2,Net.HttpMethods.POST);

            }
        });
    }

    private void showHideRoom() {
        showHideRoom = new TextButton("show rooms", skinButton, "small");
        showHideRoom.setPosition((BaseScreen.WIDTH/2)-50,500);
        showHideRoom.getLabel().setColor(Color.BLACK);

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
        previous.setPosition(500,750);
        previous.getLabel().setColor(Color.BLACK);

        previous.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                spaceShipChange.play();
                       if(shipNumber < 0){
                           shipNumber = 3;
                       }else {
                           if (shipNumber == 0) {
                               shipNumber = 3;
                           } else {
                               shipNumber--;
                           }
                       }
            }
        });
    }


    private void nextButton() {
        next = new TextButton("next", skinButton, "small");
        next.setPosition(1400,750);
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

    private void selectLevelView(){
      easyButton = new TextButton("EASY", skinButton, "small");
      easyButton.setTransform(true);
      easyButton.setScale(0.85f);
      easyButton.setColor(Color.CYAN);
      easyButton.setPosition(BaseScreen.WIDTH-330,BaseScreen.HEIGHT-100);
      easyButton.getLabel().setColor(Color.WHITE);
      easyButton.getLabel().setFontScale(1.25f, 1.25f);
      easyButton.setSize(100,70);

      easyButton.addCaptureListener(new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
              levelDifficult = Difficult.EASY.getLevelCode();
              normalButton.setColor(Color.BLACK);
              easyButton.setColor(Color.CYAN);
          }
      });

      normalButton = new TextButton("NORMAL", skinButton, "small");
      normalButton.setTransform(true);
      normalButton.setScale(0.85f);
      normalButton.setColor(Color.BLACK);
      normalButton.setPosition(BaseScreen.WIDTH-330,BaseScreen.HEIGHT-200);
      normalButton.getLabel().setColor(Color.WHITE);
      normalButton.getLabel().setFontScale(1.25f, 1.25f);
      normalButton.setSize(100,70);

      normalButton.addCaptureListener(new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
             levelDifficult = Difficult.NORMAL.getLevelCode();
              normalButton.setColor(Color.CYAN);
              easyButton.setColor(Color.BLACK);
          }
      });



      startButton = new TextButton("START", skinButton, "small");
      startButton.setTransform(true);
      startButton.setScaleX(1.8f);
      startButton.setScaleY(1.5f);
      startButton.setColor(Color.GOLDENROD);
      startButton.setPosition(BaseScreen.WIDTH-250,BaseScreen.HEIGHT-155);
      startButton.getLabel().setColor(Color.WHITE);
      startButton.getLabel().setFontScale(1.25f, 1.25f);
      startButton.setSize(90,50);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        state += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Global.currentPlayer != null) {
            usernameLabel.setText(Global.currentPlayer.getName());
        }
        batch.begin();
        font.draw(batch, "Crew", 200, 200);
        batch.end();
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage.getBatch().draw((TextureRegion) crew1.getKeyFrame(state), 10, 250, 70, 70);
        stage.getBatch().draw((TextureRegion) crew2.getKeyFrame(state), 10, 180, 70, 70);
        stage.getBatch().draw((TextureRegion) crew3.getKeyFrame(state), 10, 110, 70, 70);
        //
        switch (shipNumber) {
            case 0:
                stage.getBatch().draw(blueShip, X_POSITION,Y_POSITION,SHIP_WIDTH,SHIP_HEIGHT);
                if(isOpen){
                    stage.getBatch().draw(blueShipRoom, X_POSITION,Y_POSITION,SHIP_WIDTH,SHIP_HEIGHT);
                }
                break;
            case 1:
                stage.getBatch().draw(redShip,X_POSITION,Y_POSITION,SHIP_WIDTH,SHIP_HEIGHT);
                if(isOpen){
                    stage.getBatch().draw(redShipRoom, X_POSITION,Y_POSITION,SHIP_WIDTH,SHIP_HEIGHT);
                }
                break;
            case 2:
                stage.getBatch().draw(greenship,X_POSITION,Y_POSITION,SHIP_WIDTH,SHIP_HEIGHT);
                if(isOpen){
                    stage.getBatch().draw(greenshipRoom, X_POSITION,Y_POSITION,SHIP_WIDTH,SHIP_HEIGHT);
                }
                break;
            case 3:
                stage.getBatch().draw(topdownfighter,X_POSITION,Y_POSITION,SHIP_WIDTH,SHIP_HEIGHT);
                if(isOpen){
                    stage.getBatch().draw(topdownfighterRoom, X_POSITION,Y_POSITION,SHIP_WIDTH,SHIP_HEIGHT);
                }
                break;

        }

        stage.getBatch().draw(shield,850.0f,150.0f,60.0f,70.0f);
        stage.getBatch().draw(weapon,930.0f,150.0f,60.0f,70.0f);
        stage.getBatch().draw(drive, 1015.0f,150.0f,60.0f,60.0f);
        stage.act();
        stage.getBatch().end();
        stage.draw();


        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(193, 205, 193, 0.1f));
        shapeRenderer.box(600.0f,100.0f,100.0f,50.0f,120.0f,100.0f);
        shapeRenderer.box(660.0f,100.0f,100.0f,50.0f,120.0f,100.0f);
        shapeRenderer.box(720.0f,100.0f,100.0f,50.0f,120.0f,100.0f);
        shapeRenderer.box(780.0f,100.0f,100.0f,50.0f,120.0f,100.0f);


        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.MAGENTA);
        shapeRenderer.line(0,320, BaseScreen.WIDTH,320);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.line(0,316, BaseScreen.WIDTH,316);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.line(0,313, BaseScreen.WIDTH,313);
        shapeRenderer.setColor(Color.CORAL);
        shapeRenderer.line(0,310, BaseScreen.WIDTH,310);

       // shapeRenderer.
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        // top left position
        drawLobby();
    }

    /**
     * fill the online players list
     */
    public void drawLobby(){
        batch.begin();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        Gdx.gl.glClearColor(0, 0, 0, 1);

        font.draw(batch, "Players online: " + String.valueOf(playersOnline.size()), 20, 1050);
        int count = 0;
        for (String playerName : playersOnline) {
            count = count+15;
            font.draw(batch, playerName , 20, (1018 + (count)));
        }
        batch.end();
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
        logout(currentPlayer);
        super.dispose();
        skinButton.dispose();
        spaceShipChange.dispose();
        stage.dispose();
        shapeRenderer.dispose();
    }
}
