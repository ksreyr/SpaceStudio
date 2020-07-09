package de.spaceStudio.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.spaceStudio.MainClient;
import de.spaceStudio.client.util.Difficult;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.server.handler.SinglePlayerGame;
import de.spaceStudio.server.model.CrewMember;
import de.spaceStudio.server.model.Section;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.model.*;
import de.spaceStudio.service.InitialDataGameService;
import de.spaceStudio.service.SinglePlayerGameService;
import thirdParties.GifDecoder;

import java.util.ArrayList;

import static de.spaceStudio.client.util.Global.currentPlayer;
import static de.spaceStudio.client.util.Global.playersOnline;
import static de.spaceStudio.service.LoginService.fetchLoggedUsers;
import static de.spaceStudio.service.LoginService.logout;
//“Sound effects obtained from https://www.zapsplat.com“

public class ShipSelectScreen extends BaseScreen {
    private Label usernameLabel, playersOnlineLabel, displayOnlinePlayerName;

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

    private Label shieldPowerAnzeige;
    private Label antriebAnzeige;
    private Label weaponPowerAnzeige;

    Animation<TextureRegion> crew1;
    Animation<TextureRegion> crew2;
    Animation<TextureRegion> crew3;
    private Texture background;
    float state = 0f;


    private Stage stage;
    private Skin skinButton;
    private Viewport viewport;
    private TextButton next;
    private TextButton previous;
    private TextButton showHideRoom;
    private TextButton startButton;
    private TextButton backMenuButton;
    private TextButton saveShip;
    private TextButton saveStation;
    private TextButton easyButton;
    private TextButton normalButton;


    private ShapeRenderer shapeRenderer;
    int shipNumber = 0;
    int openNumber = 0;
    private Sound spaceShipChange, mouseClick;

    private InitialDataGameService idgs = new InitialDataGameService();

    private boolean isOpen;
    private InputHandler inputHandler;
    private int levelDifficult = 0;

    //
    Ship ship = new Ship();
    CrewMember crewMember0 = Global.crewMember0;
    CrewMember crewMember1 = Global.crewMember1;
    CrewMember crewMember2 = Global.crewMember2;

    Section section1 = Global.section1;
    Section section2 = Global.section2;
    Section section3 = Global.section3;
    Section section4 = Global.section4;
    Section section5 = Global.section5;
    Section section6 = Global.section6;

    Universe universe1 = Global.universe1;
    Universe universe2 = Global.universe2;

    Planet p1 = Global.planet1;
    Planet p2 = Global.planet2;
    Planet p3 = Global.planet3;
    Planet p4 = Global.planet4;
    Planet p5 = Global.planet5;

    ArrayList<Ship> shipsToP1 = new ArrayList<Ship>();
    ArrayList<Ship> shipsToP2 = new ArrayList<Ship>();
    ArrayList<Ship> shipsToP3 = new ArrayList<Ship>();
    ArrayList<Ship> shipsToP4 = new ArrayList<Ship>();
    ArrayList<Ship> shipsToP5 = new ArrayList<Ship>();

    AI gegner1 = Global.ai1;
    Ship shipOfGegner = Global.shipGegner1;

    Section section1Gegner = Global.section1Gegner;
    Section section2Gegner = Global.section2Gegner;
    Section section3Gegner = Global.section3Gegner;

    AI gegner2 = Global.ai2;
    Ship shipOfGegner2 = Global.shipGegner2;

    Section section1Gegner2 = Global.section1Gegner2;
    Section section2Gegner2 = Global.section2Gegner2;
    Section section3Gegner2 = Global.section3Gegner2;
    //
    public ShipSelectScreen(MainClient game) {
        super(game);
        this.ships = game;
        // Clear cache before reload data again to avoid fake numbers
        playersOnline.clear();
        // download data
        fetchLoggedUsers();

        OrthographicCamera camera = new OrthographicCamera(BaseScreen.WIDTH, BaseScreen.HEIGHT);
        viewport = new ExtendViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT, camera);
        //viewport = new FitViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);
        skinButton = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        if (Global.isOnlineGame) {
            fetchLoggedUsers();
            Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
            playersOnlineLabel = new Label(null, skin);
            playersOnlineLabel.setPosition(20, 950);
            playersOnlineLabel.setFontScale(2);
            displayOnlinePlayerName = new Label(null, skin);
            displayOnlinePlayerName.setPosition(20, 920);
            displayOnlinePlayerName.setFontScale(1.5F);
            stage.addActor(playersOnlineLabel);
            stage.addActor(displayOnlinePlayerName);

            // top left position
            drawLobby();
        }

        crew1 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/crew1.gif").read());
        crew2 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/crew2.gif").read());
        crew3 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/crew3.gif").read());

        usernameLabel = new Label("Pruebe", skinButton);
        usernameLabel.setPosition(100, 350);
        stage.addActor(usernameLabel);
        background = new Texture(Gdx.files.internal("Client/core/assets/data/ast.jpg"));
        shapeRenderer = new ShapeRenderer();
        mouseClick = Gdx.audio.newSound(Gdx.files.internal("Client/core/assets/data/music/mouseclick.wav"));

        shieldPowerAnzeige = new Label("100%", skinButton);
        weaponPowerAnzeige = new Label("100%", skinButton);
        antriebAnzeige = new Label("100%", skinButton);

        shieldPowerAnzeige.setFontScale(2,2);
        weaponPowerAnzeige.setFontScale(2,2);
        antriebAnzeige.setFontScale(2,2);
        shieldPowerAnzeige.setColor(Color.GOLD);
        shieldPowerAnzeige.setPosition(stage.getWidth()-1120,270);

        weaponPowerAnzeige.setColor(Color.GOLD);
        weaponPowerAnzeige.setPosition(stage.getWidth()-1035,270);

        antriebAnzeige.setColor(Color.GOLD);
        antriebAnzeige.setPosition(stage.getWidth()-950,270);

        inputHandler = new InputHandler();

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(3);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        usernameLabel = new Label("UserName", skinButton);
        usernameLabel.setPosition(100, 350);

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
        // top left position
        if(!Global.IS_SINGLE_PLAYER){

            playersOnlineLabel = new Label(null, skinButton);
            playersOnlineLabel.setPosition(20,950);
            playersOnlineLabel.setFontScale(2);

            displayOnlinePlayerName = new Label(null, skinButton);
            displayOnlinePlayerName.setPosition(20,920);
            displayOnlinePlayerName.setFontScale(1.5F);

            drawLobby();

            stage.addActor(playersOnlineLabel);
            stage.addActor(displayOnlinePlayerName);
        }




        stage.addActor(usernameLabel);
        stage.addActor(next);
        stage.addActor(previous);
        stage.addActor(showHideRoom);
        stage.addActor(startButton);
        stage.addActor(backMenuButton);
        stage.addActor(easyButton);
        stage.addActor(normalButton);
        stage.addActor(crew_1_name);
        stage.addActor(crew_2_name);
        stage.addActor(crew_3_name);
        stage.addActor(weaponPowerAnzeige);
        stage.addActor(shieldPowerAnzeige);
        stage.addActor(antriebAnzeige);

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

                section1Gegner.setShip(shipOfGegner);
                section2Gegner.setShip(shipOfGegner);
                section3Gegner.setShip(shipOfGegner);

                section1Gegner2.setShip(shipOfGegner2);
                section2Gegner2.setShip(shipOfGegner2);
                section3Gegner2.setShip(shipOfGegner2);

                idgs.sendRequestAddShip(ship, Net.HttpMethods.POST);
                Global.currentShip = ship;
                idgs.aiCreation(gegner1, Net.HttpMethods.POST);
                idgs.aiCreation(gegner2, Net.HttpMethods.POST);
                try {
                    Thread.sleep(100);
                } catch (Exception e) {

                }

                shipOfGegner.setOwner(gegner1);
                shipOfGegner2.setOwner(gegner2);
                idgs.sendRequestAddShip(shipOfGegner, Net.HttpMethods.POST);
                idgs.sendRequestAddShip(shipOfGegner2, Net.HttpMethods.POST);
                try {
                    Thread.sleep(100);
                } catch (Exception e) {

                }
                idgs.sendRequestAddSection(section1Gegner, Net.HttpMethods.POST);
                idgs.sendRequestAddSection(section2Gegner, Net.HttpMethods.POST);
                idgs.sendRequestAddSection(section3Gegner, Net.HttpMethods.POST);

                idgs.sendRequestAddSection(section1Gegner2, Net.HttpMethods.POST);
                idgs.sendRequestAddSection(section2Gegner2, Net.HttpMethods.POST);
                idgs.sendRequestAddSection(section3Gegner2, Net.HttpMethods.POST);

                idgs.sendRequestAddSection(section1, Net.HttpMethods.POST);
                idgs.sendRequestAddSection(section2, Net.HttpMethods.POST);
                idgs.sendRequestAddSection(section3, Net.HttpMethods.POST);
                idgs.sendRequestAddSection(section4, Net.HttpMethods.POST);
                idgs.sendRequestAddSection(section5, Net.HttpMethods.POST);
                idgs.sendRequestAddSection(section6, Net.HttpMethods.POST);
                try {
                    Thread.sleep(100);
                } catch (Exception e) {

                    }
                    //
                    //Todo: Liam Conecting Teil
                    //
                    crewMember0.setCurrentSection(section1);
                    crewMember1.setCurrentSection(section2);
                    crewMember2.setCurrentSection(section3);
                    crewMember0.setName(crew_1_name.getText());
                    crewMember1.setName(crew_2_name.getText());
                    crewMember2.setName(crew_3_name.getText());
                    idgs.sendRequestAddCrew(crewMember0, Net.HttpMethods.POST);
                    idgs.sendRequestAddCrew(crewMember1, Net.HttpMethods.POST);
                    idgs.sendRequestAddCrew(crewMember2, Net.HttpMethods.POST);


                    ////
                    if (levelDifficult == Difficult.NORMAL.getLevelCode()) {
                        universe2.setName(universe2.getName()+currentPlayer.getName());
                        Global.universe1.setName(universe2.getName());
                        idgs.sendRequestAddUniverse(universe2, Net.HttpMethods.POST);
                        try {
                            Thread.sleep(200);
                        } catch (Exception e) {

                        }
                        p1.setUniverse(universe2);
                        p2.setUniverse(universe2);
                        p3.setUniverse(universe2);
                        p4.setUniverse(universe2);
                        p5.setUniverse(universe2);
                        shipsToP1.add(ship);
                        shipsToP2.add(shipOfGegner);
                        shipsToP3.add(shipOfGegner2);
                        p1.setShips(shipsToP1);
                        p2.setShips(shipsToP2);
                        p3.setShips(shipsToP3);
                        idgs.sendRequestAddPlanet(p1, Net.HttpMethods.POST);
                        idgs.sendRequestAddPlanet(p2, Net.HttpMethods.POST);
                        idgs.sendRequestAddPlanet(p3, Net.HttpMethods.POST);
                        idgs.sendRequestAddPlanet(p4, Net.HttpMethods.POST);
                        idgs.sendRequestAddPlanet(p5, Net.HttpMethods.POST);
                    } else {
                        universe1.setName(universe1.getName()+currentPlayer.getName());
                        Global.universe1.setName(universe1.getName());
                        idgs.sendRequestAddUniverse(universe1, Net.HttpMethods.POST);
                        try {
                            Thread.sleep(200);
                        } catch (Exception e) {

                        }
                        p1.setUniverse(universe1);
                        p2.setUniverse(universe1);
                        p3.setUniverse(universe1);
                        p4.setUniverse(universe1);
                        p5.setUniverse(universe1);
                        shipsToP1.add(ship);
                        shipsToP2.add(shipOfGegner);
                        shipsToP3.add(shipOfGegner2);
                        p1.setShips(shipsToP1);
                        p2.setShips(shipsToP2);
                        p3.setShips(shipsToP3);
                        idgs.sendRequestAddPlanet(p1, Net.HttpMethods.POST);
                        idgs.sendRequestAddPlanet(p2, Net.HttpMethods.POST);
                        idgs.sendRequestAddPlanet(p3, Net.HttpMethods.POST);
                        idgs.sendRequestAddPlanet(p4, Net.HttpMethods.POST);
                        idgs.sendRequestAddPlanet(p5, Net.HttpMethods.POST);
                    }

            }
        });
    }

    private void showHideRoom() {
        showHideRoom = new TextButton("show rooms", skinButton, "small");
        showHideRoom.setPosition((BaseScreen.WIDTH / 2) - 50, 500);
        showHideRoom.getLabel().setColor(Color.BLACK);

        showHideRoom.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                spaceShipChange.play();
                openNumber++;
                if (isOpen) {
                    showHideRoom.setText("show rooms");
                    isOpen = false;
                } else {
                    showHideRoom.setText("hide rooms");
                    isOpen = true;
                }
            }
        });
    }


    private void previousButton() {
        previous = new TextButton("previous", skinButton, "small");
        previous.setPosition(500, 750);
        previous.getLabel().setColor(Color.BLACK);

        previous.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                spaceShipChange.play();
                if (shipNumber < 0) {
                    shipNumber = 3;
                } else {
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
        next.setPosition(1400, 750);
        next.getLabel().setColor(Color.BLACK);

        next.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                spaceShipChange.play();
                if (shipNumber > 2)
                    shipNumber = 0;
                else {
                    shipNumber++;
                }

            }
        });

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);

    }

    private void selectLevelView() {
        easyButton = new TextButton("EASY", skinButton, "small");
        easyButton.setTransform(true);
        easyButton.setScale(0.85f);
        easyButton.setColor(Color.CYAN);
        easyButton.setPosition(BaseScreen.WIDTH - 330, BaseScreen.HEIGHT - 100);
        easyButton.getLabel().setColor(Color.WHITE);
        easyButton.getLabel().setFontScale(1.25f, 1.25f);
        easyButton.setSize(100, 70);

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
        normalButton.setPosition(BaseScreen.WIDTH - 330, BaseScreen.HEIGHT - 200);
        normalButton.getLabel().setColor(Color.WHITE);
        normalButton.getLabel().setFontScale(1.25f, 1.25f);
        normalButton.setSize(100, 70);

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
        startButton.setPosition(BaseScreen.WIDTH - 250, BaseScreen.HEIGHT - 155);
        startButton.getLabel().setColor(Color.WHITE);
        startButton.getLabel().setFontScale(1.25f, 1.25f);
        startButton.setSize(90, 50);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (!Global.isOnlineGame) {
                    createSinglePlayerGame();
                }

                ships.setScreen(new StationsMap(game));
                mouseClick.play();

            }
        });

        backMenuButton = new TextButton("BACK", skinButton, "small");
        backMenuButton.setTransform(true);
        backMenuButton.setScaleX(1.8f);
        backMenuButton.setScaleY(1.5f);
        backMenuButton.setColor(Color.GOLDENROD);
        backMenuButton.setPosition(20, 1000);
        backMenuButton.getLabel().setColor(Color.WHITE);
        backMenuButton.getLabel().setFontScale(1.25f, 1.25f);
        backMenuButton.setSize(90, 50);

        backMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new NewGameScreen(game));
            }
        });

    }

    public void createSinglePlayerGame() {
        Global.singlePlayerGame = new SinglePlayerGame();
        if (levelDifficult == Difficult.NORMAL.getLevelCode()) {
            Global.singlePlayerGame.setDifficult("normal");
        } else {
            Global.singlePlayerGame.setDifficult("easy");
        }
        SinglePlayerGameService.initSinglePlayerGame(Global.singlePlayerGame);
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

        switch (shipNumber) {
            case 0:
                stage.getBatch().draw(blueShip, X_POSITION, Y_POSITION, SHIP_WIDTH, SHIP_HEIGHT);
                if (isOpen) {
                    stage.getBatch().draw(blueShipRoom, X_POSITION, Y_POSITION, SHIP_WIDTH, SHIP_HEIGHT);
                }
                break;
            case 1:
                stage.getBatch().draw(redShip, X_POSITION, Y_POSITION, SHIP_WIDTH, SHIP_HEIGHT);
                if (isOpen) {
                    stage.getBatch().draw(redShipRoom, X_POSITION, Y_POSITION, SHIP_WIDTH, SHIP_HEIGHT);
                }
                break;
            case 2:
                stage.getBatch().draw(greenship, X_POSITION, Y_POSITION, SHIP_WIDTH, SHIP_HEIGHT);
                if (isOpen) {
                    stage.getBatch().draw(greenshipRoom, X_POSITION, Y_POSITION, SHIP_WIDTH, SHIP_HEIGHT);
                }
                break;
            case 3:
                stage.getBatch().draw(topdownfighter, X_POSITION, Y_POSITION, SHIP_WIDTH, SHIP_HEIGHT);
                if (isOpen) {
                    stage.getBatch().draw(topdownfighterRoom, X_POSITION, Y_POSITION, SHIP_WIDTH, SHIP_HEIGHT);
                }
                break;

        }

        stage.getBatch().draw(shield, 850.0f, 150.0f, 60.0f, 70.0f);
        stage.getBatch().draw(weapon, 930.0f, 150.0f, 60.0f, 70.0f);
        stage.getBatch().draw(drive, 1015.0f, 150.0f, 60.0f, 60.0f);
        stage.act();
        stage.getBatch().end();
        stage.draw();


        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(193, 205, 193, 0.1f));
        shapeRenderer.box(600.0f, 100.0f, 100.0f, 50.0f, 120.0f, 100.0f);
        shapeRenderer.box(660.0f, 100.0f, 100.0f, 50.0f, 120.0f, 100.0f);
        shapeRenderer.box(720.0f, 100.0f, 100.0f, 50.0f, 120.0f, 100.0f);
        shapeRenderer.box(780.0f, 100.0f, 100.0f, 50.0f, 120.0f, 100.0f);


        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.MAGENTA);
        shapeRenderer.line(0, 320, BaseScreen.WIDTH, 320);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.line(0, 316, BaseScreen.WIDTH, 316);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.line(0, 313, BaseScreen.WIDTH, 313);
        shapeRenderer.setColor(Color.CORAL);
        shapeRenderer.line(0, 310, BaseScreen.WIDTH, 310);

        // shapeRenderer.
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        // top left position
        if (Global.isOnlineGame) {
            drawLobby();
        }
        if(!Global.IS_SINGLE_PLAYER)  drawLobby();


    }

    /**
     * fill the online players list
     */
    public void drawLobby() {
        playersOnlineLabel.setText("Players online: " + String.valueOf(playersOnline.size()));
        // Get first position, we support max 2 players in the whole game
        if (playersOnline.size() > 0) {
            displayOnlinePlayerName.setText(playersOnline.get(0));
        }
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
        mouseClick.dispose();
    }
}
