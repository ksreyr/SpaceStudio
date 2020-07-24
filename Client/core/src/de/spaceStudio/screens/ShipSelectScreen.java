package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.gson.Gson;
import de.spaceStudio.MainClient;
import de.spaceStudio.client.util.Difficult;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.server.handler.SinglePlayerGame;
import de.spaceStudio.server.model.*;
import de.spaceStudio.service.InitialDataGameService;
import de.spaceStudio.service.SinglePlayerGameService;
import thirdParties.GifDecoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static de.spaceStudio.client.util.Global.*;
import static de.spaceStudio.client.util.RequestUtils.setupRequest;
import static de.spaceStudio.service.LoginService.fetchLoggedUsers;
import static de.spaceStudio.service.LoginService.logout;
//“Sound effects obtained from https://www.zapsplat.com“

public class ShipSelectScreen extends BaseScreen {

    private final static Logger LOG = Logger.getLogger(ShipSelectScreen.class.getName());

    private Label usernameLabel, playersOnlineLabel, displayOnlinePlayerName;

    private static final int X_POSITION = 750;
    private static final int Y_POSITION = 550;
    private static final int SHIP_WIDTH = 500;
    private static final int SHIP_HEIGHT = 500;

    private final MainClient mainClient;
    private SpriteBatch batch;
    private BitmapFont font;

    private Texture blueShip, redShip, greenship, topdownfighter;
    private Texture blueShipRoom, redShipRoom, greenshipRoom, topdownfighterRoom;

    private Texture relevantSystems;
    private Texture shield;
    private Texture weaponsSystem;
    private Texture drive;
    private Texture crewDisplay;
    private Texture crewTest;
    private Texture crewTest2;
    private Texture crewTest3;
    //private Texture redPoint;
    private RedPin redPin;
    private Image imageCrewTestSektion2;
    private Image imageCrewTestSektion4;
    private Image imageCrewTestSektion6;
    private TextField crew_1_name, crew_2_name, crew_3_name;

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
    private TextButton easyButton;
    private TextButton normalButton;

    int shipNumber = 0;
    int openNumber = 0;
    private Sound spaceShipChange, mouseClick;

    private InitialDataGameService idgs = new InitialDataGameService();

    private boolean isOpen;
    private InputHandler inputHandler;
    private int levelDifficult = 0;

    //
    Ship ship = new Ship();


    Universe universe1 = Global.universe1;
    Universe universe2 = Global.universe2;


    int requestcounter = 0;
    String responseJson;

    List<Section> sectionList = new ArrayList<>();
    List<CrewMember> crewMemberList = new ArrayList<>();
    List<AI> aiList = new ArrayList<>();
    int universeID = 0;
    int shipRessourceID = 0;
    List<Ship> shipsgerner = new ArrayList<>();
    List<Planet> planets = new ArrayList<>();
    List<Station> stations = new ArrayList<>();
    List<ShopRessource> shopRessources = new ArrayList<>();
    List<Weapon> weapons = new ArrayList<>();

    private OrthographicCamera camera;
    //
    public ShipSelectScreen(MainClient game) {
        super(game);
        this.mainClient = game;
        // Clear cache before reload data again to avoid fake numbers
        playersOnline.clear();
        // download data
        fetchLoggedUsers();

        camera = new OrthographicCamera();
        viewport = new StretchViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT, camera);
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
        background = new Texture(Gdx.files.internal("Client/core/assets/combatAssets/shipSelectionBG.jpg"));
        mouseClick = Gdx.audio.newSound(Gdx.files.internal("Client/core/assets/data/music/mouseclick.wav"));

        inputHandler = new InputHandler();

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(3);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        usernameLabel = new Label("UserName", skinButton);
        usernameLabel.setPosition(100, 350);

        crew_1_name = new TextArea("John", skinButton);
        crew_1_name.setPosition(120, 220);
        crew_2_name = new TextArea("Max", skinButton);
        crew_2_name.setPosition(120, 150);
        crew_3_name = new TextArea("Jack", skinButton);
        crew_3_name.setPosition(120, 80);


        blueShip = new Texture(Gdx.files.internal("Client/core/assets/data/ships/blueships1.png"));
        redShip = new Texture(Gdx.files.internal("Client/core/assets/data/ships/redship.png"));
        greenship = new Texture(Gdx.files.internal("Client/core/assets/data/ships/greenship.png"));
        topdownfighter = new Texture(Gdx.files.internal("Client/core/assets/data/ships/topdownfighter.png"));


        blueShipRoom = new Texture(Gdx.files.internal("Client/core/assets/data/ships/blueships1_section.png"));
        redShipRoom = new Texture(Gdx.files.internal("Client/core/assets/data/ships/redship_section.png"));
        greenshipRoom = new Texture(Gdx.files.internal("Client/core/assets/data/ships/green_section.png"));
        topdownfighterRoom = new Texture(Gdx.files.internal("Client/core/assets/data/ships/topdownfighter_section.png"));

        Pixmap pixmapOld = new Pixmap(Gdx.files.internal("Client/core/assets/data/ships/relevantSystems.png"));
        Pixmap pixmapNew = new Pixmap(600, 600, pixmapOld.getFormat());
        pixmapNew.drawPixmap(pixmapOld,
                0, 0, pixmapOld.getWidth(), pixmapOld.getHeight(),
                0, 0, pixmapNew.getWidth(), pixmapNew.getHeight()
        );
        relevantSystems = new Texture(pixmapNew);
        pixmapOld.dispose();
        pixmapNew.dispose();
        //redPoint = new Texture(Gdx.files.internal("Client/core/assets/data/ships/pin.png"));
        shield = new Texture(Gdx.files.internal("Client/core/assets/data/ships/shield.png"));
        drive = new Texture(Gdx.files.internal("Client/core/assets/data/ships/drive.png"));
        weaponsSystem = new Texture(Gdx.files.internal("Client/core/assets/data/ships/weapons.png"));
        crewDisplay = new Texture(Gdx.files.internal("Client/core/assets/data/ships/shipPanel.png"));
        crewTest = new Texture(Gdx.files.internal("Client/core/assets/combatAssets/MaleHuman-3.png"));
        crewTest2 = new Texture(Gdx.files.internal("Client/core/assets/combatAssets/MaleHuman-3.png"));
        crewTest3 = new Texture(Gdx.files.internal("Client/core/assets/combatAssets/female_human.png"));
        redPin = new RedPin();

        imageCrewTestSektion4 = new Image(crewTest);
        imageCrewTestSektion6 = new Image(crewTest2);
        imageCrewTestSektion2 = new Image(crewTest3);
        imageCrewTestSektion4.setBounds(30,30,30,30);
        imageCrewTestSektion6.setBounds(30,30,30,30);
        imageCrewTestSektion2.setBounds(30,30,30,30);
        imageCrewTestSektion4.setPosition((BaseScreen.WIDTH/2f)+110,BaseScreen.HEIGHT-320);
        //System.out.println("Zeile 226: " + ((BaseScreen.WIDTH/2f)+110));
        //System.out.println("Zeile 227: " + imageCrewTest.getX());
        imageCrewTestSektion6.setPosition(840,674);
        imageCrewTestSektion2.setPosition(990,886);

        spaceShipChange = Gdx.audio.newSound(Gdx.files.internal("Client/core/assets/data/music/change.wav"));
        nextButton();
        previousButton();

        showHideRoom();
        selectLevelView();

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
                ship.setMoney(40); // TODO FIXME change later
                sendRequestAddShip(ship, Net.HttpMethods.POST);

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
                ISEASY = true;
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
                ISEASY = false;
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
        StartButton();
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
        stage.getBatch().draw(crewDisplay,0,0);
        stage.getBatch().draw((TextureRegion) crew1.getKeyFrame(state), 45, 220, 70, 70);
        stage.getBatch().draw((TextureRegion) crew2.getKeyFrame(state), 45, 150, 70, 70);
        stage.getBatch().draw((TextureRegion) crew3.getKeyFrame(state), 45, 80, 70, 70);

        /*Added Ship*/
        if (requestcounter == 1) {
            if (responseJson != null && !responseJson.isEmpty()) {
                ship.setId(Integer.valueOf(responseJson));
                Global.currentShipPlayer = ship;
                //Ship to Sections
                for (Section s :
                        Global.sectionsPlayerList) {
                    s.setShip(ship);
                }
            }
            //Sent Sections
            sendRequestAddSections(Global.sectionsPlayerList, Net.HttpMethods.POST);
            requestcounter = 2;
        }

        /*if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Vector3 mousePos = new Vector3();
            camera.unproject(mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            System.out.println("X Input: " + mousePos.x);
            System.out.println("Y Input: " + mousePos.y);
        }*/

        /*Added sectionList*/
        if (!sectionList.isEmpty() && requestcounter == 2) {
            //Section with ID
            Global.sectionsPlayerList = sectionList;
            //Update Section variables
            updateVariableSectionShipPlayer();
            //Set crewMemebers
            int counter = 1;
            for (CrewMember c :
                    Global.crewMemberList) {
                switch (counter) {
                    case 1:
                        c.setCurrentSection(Global.section1);
                        c.setName(crew_1_name.getText());
                        break;
                    case 2:
                        c.setCurrentSection(Global.section2);
                        c.setName(crew_2_name.getText());
                        break;
                    case 3:
                        c.setCurrentSection(Global.section3);
                        c.setName(crew_3_name.getText());
                        break;
                    default:
                        break;
                }
                counter++;
            }
            //CrewMembers sendet
            sendRequestAddCrewMembers(Global.crewMemberList, Net.HttpMethods.POST);
            requestcounter = 3;
            requestcounter = 4;
        }
        if (!crewMemberList.isEmpty() && requestcounter == 4) {
            Global.crewMemberList = crewMemberList;
            Global.updateVariableCrewMembersPlayer();
            requestcounter = 5;
        }
        //Add Universe
        if (requestcounter == 5) {
                universe2.setName(universe2.getName() + currentPlayer.getName());
                Global.universe2.setName(universe2.getName());
                sendRequestAddUniverse(universe2, Net.HttpMethods.POST);
            requestcounter = 6;
        }
        //updated Universe
        if (requestcounter == 6 && universeID != 0) {
                Global.universe2.setId(universeID);
                currentUniverse = Global.universe2;
                sendRequestAisCreation(Global.aisU2, Net.HttpMethods.POST);

            requestcounter = 7;
        }

        if (requestcounter == 7 && !aiList.isEmpty()) {
                Global.aisU2 = aiList;
                Global.updateVariableaiu2();
                requestcounter = 8;
            }
            if (requestcounter == 8) {
                for (Ship s :
                        shipsgegneru2) {
                    switch (s.getName()) {
                        case "Shipgegner1":
                            s.setOwner(ai1);
                            break;
                        case "Shipgegner2":
                            s.setOwner(ai2);
                            break;
                        case "Shipgegner3":
                            s.setOwner(ai3);
                            break;
                        case "Shipgegner4":
                            s.setOwner(ai4);
                            break;
                        case "Shipgegner5":
                            s.setOwner(ai5);
                            break;
                        case "Shipgegner6":
                            s.setOwner(ai6);
                            break;
                        default:
                            break;
                    }
                }
                sendRequestAddShips(shipsgegneru2, Net.HttpMethods.POST);
                requestcounter = 9;
            }

            if (!shipsgerner.isEmpty() && requestcounter == 9) {
                Global.shipsgegneru2 = shipsgerner;
                Global.updateShipsVariabelgegneru2();
                requestcounter = 10;
            }

            if (requestcounter == 10) {
                List<Section> sectionsforU2 = new ArrayList<Section>();
                for (Section s :
                        Global.sectionsgegner1) {
                    s.setShip(shipGegner1);
                    sectionsforU2.add(s);
                }
                for (Section s :
                        Global.sectionsgegner2) {
                    s.setShip(shipGegner2);
                    sectionsforU2.add(s);
                }
                for (Section s :
                        Global.sectionsgegner3) {
                    s.setShip(shipGegner3);
                    sectionsforU2.add(s);
                }
                for (Section s :
                        Global.sectionsgegner4) {
                    s.setShip(shipGegner4);
                    sectionsforU2.add(s);
                }
                for (Section s :
                        Global.sectionsgegner5) {
                    s.setShip(shipGegner5);
                    sectionsforU2.add(s);
                }
                for (Section s :
                        Global.sectionsgegner6) {
                    s.setShip(shipGegner6);
                    sectionsforU2.add(s);
                }
                sendRequestAddSections(sectionsforU2, Net.HttpMethods.POST);
                //man kann nicht das method clear an der list nutzen. deswegen sizeO
                //i need clean sectionList because the data hat de User Section and i do not need it
                List<Section> sizeO = new ArrayList<>();
                sectionList = sizeO;
                requestcounter = 11;
            }
            if (requestcounter == 11 && !sectionList.isEmpty()) {
                List<Section> sectionsgegner1 = new ArrayList<>();
                List<Section> sectionsgegner2 = new ArrayList<>();
                List<Section> sectionsgegner3 = new ArrayList<>();
                List<Section> sectionsgegner4 = new ArrayList<>();
                List<Section> sectionsgegner5 = new ArrayList<>();
                List<Section> sectionsgegner6 = new ArrayList<>();
                for (Section s :
                        sectionList) {
                    if (s.getShip().getName().equals("Shipgegner1")) {
                        sectionsgegner1.add(s);
                    } else if (s.getShip().getName().equals("Shipgegner2")) {
                        sectionsgegner2.add(s);
                    } else if (s.getShip().getName().equals("Shipgegner3")) {
                        sectionsgegner3.add(s);
                    } else if (s.getShip().getName().equals("Shipgegner4")) {
                        sectionsgegner4.add(s);
                    } else if (s.getShip().getName().equals("Shipgegner5")) {
                        sectionsgegner5.add(s);
                    } else if (s.getShip().getName().equals("Shipgegner6")) {
                        sectionsgegner6.add(s);
                    }

                }
                Global.sectionsgegner1 = sectionsgegner1;
                Global.updateVariblesSectionsGegner1();
                Global.sectionsgegner2 = sectionsgegner2;
                Global.updateVariblesSectionsGegner2();
                Global.sectionsgegner3 = sectionsgegner3;
                Global.updateVariblesSectionsGegner3();
                Global.sectionsgegner4 = sectionsgegner4;
                Global.updateVariblesSectionsGegner4();
                Global.sectionsgegner5 = sectionsgegner5;
                Global.updateVariblesSectionsGegner5();
                Global.sectionsgegner6 = sectionsgegner6;
                Global.updateVariblesSectionsGegner6();
                requestcounter = 12;
            }
            //Planeten request
            if (requestcounter == 12) {
                //Universe2
                shipsP1.add(currentShipPlayer);
                shipsP2.add(shipGegner1);
                shipsP3.add(shipGegner2);
                shipsP4.add(shipGegner3);
                shipsP5.add(shipGegner4);
                shipsP6.add(shipGegner5);
                shipsP6.add(shipGegner6);
                for (Planet p :
                        Global.planetListU2) {
                    switch (p.getName()) {
                        case "p1":
                            p.setShips(Global.shipsP1);
                            break;
                        case "p2":
                            p.setShips(Global.shipsP2);
                            break;
                        case "p3":
                            p.setShips(Global.shipsP3);
                            break;
                        case "p4":
                              p.setShips(Global.shipsP4);
                            break;
                        case "p5":
                             p.setShips(Global.shipsP5);
                            break;
                        case "p6":
                            p.setShips(Global.shipsP6);
                            break;
                        default:
                            break;
                    }
                    p.setUniverse(currentUniverse);
                }
                sendRequestAddPlanets(planetListU2, Net.HttpMethods.POST);
                requestcounter = 13;
            }
            if (!planets.isEmpty() && requestcounter == 13) {
                Global.planetListU2 = planets;
                Global.updateVariblesPlanetsU2();
                List<Planet> size0 = new ArrayList<>();
                planets = size0;
                requestcounter = 14;
            }
            if (requestcounter == 14) {
                for (Station s :
                        stationListU2) {
                    s.setUniverse(currentUniverse);
                }
                sendRequestAddStation(stationListU2, Net.HttpMethods.POST);
                requestcounter = 15;
            }
            if (requestcounter == 15 && !stations.isEmpty()) {
                stationListU2 = stations;
                Global.updateVariblesStationsU2();
                List<Station> sizeO = new ArrayList<>();
                stations = sizeO;
                requestcounter = 16;
            }
            if (requestcounter == 16) {
                Global.shopRessourceList.get(0).setStation(station1);
                Global.shopRessourceList.get(1).setStation(station2);
                Global.shopRessourceList.get(2).setStation(station1);
                sendRequestAddShopRessources(Global.shopRessourceList, Net.HttpMethods.POST);
                requestcounter = 17;
            }
            if (requestcounter == 17 && !shopRessources.isEmpty()) {
                Global.shopRessourceList = shopRessources;
                Global.updateVariblesshopRessource();
                requestcounter = 18;
            }
            if (requestcounter == 18) {
                shipRessource.setShip(currentShipPlayer);
                sendRequestAddShipRessource(shipRessource, Net.HttpMethods.POST);
                requestcounter = 19;
            }
            if (requestcounter == 19 && shipRessourceID != 0) {
                shipRessource.setId(shipRessourceID);
                requestcounter = 20;
                shipRessourceID=0;
            }
            if (requestcounter == 20) {
                List<Weapon> weaponsUniver2=new ArrayList<>();
                for (Weapon w :
                        weaponListPlayer) {
                    w.setSection(section2);
                    weaponsUniver2.add(w);
                }
                for(Weapon w:
                        weaponListGegner1){
                    w.setSection(section3Gegner);
                    weaponsUniver2.add(w);
                }
                for(Weapon w:
                        weaponListGegner2){
                    w.setSection(section3Gegner2);
                    weaponsUniver2.add(w);
                }
                for(Weapon w:
                        weaponListGegner3){
                    w.setSection(section3Gegner3);
                    weaponsUniver2.add(w);
                }
                for(Weapon w:
                        weaponListGegner4){
                    w.setSection(section3Gegner4);
                    weaponsUniver2.add(w);
                }
                for(Weapon w:
                        weaponListGegner5){
                    w.setSection(section3Gegner5);
                    weaponsUniver2.add(w);
                }
                for(Weapon w:
                        weaponListGegner6){
                    w.setSection(section3Gegner6);
                    weaponsUniver2.add(w);
                }
                sendRequestAddWeapon(weaponsUniver2, Net.HttpMethods.POST);
                requestcounter = 21;
            }
            if (!weapons.isEmpty() && requestcounter == 21) {
                weaponListUniverse2 = weapons;
                Global.updateweaponVariabelUniverse2();
                Global.aktualizierenweaponListUniverse2();
                Global.actualiziertweaponListPlayer();
                Global.actualiziertweaponListGegner1();
                Global.actualiziertweaponListGegner2();
                Global.actualiziertweaponListGegner3();
                Global.actualiziertweaponListGegner4();
                Global.actualiziertweaponListGegner5();
                Global.actualiziertweaponListGegner6();
                System.out.println("Control: "+weapon1Player);
                requestcounter = 22;
            }
            if (requestcounter == 22) {
                mainClient.setScreen(new StationsMap(game));
                requestcounter = 23;
            }
        //}


        /////
        switch (shipNumber) {
            case 0:
                stage.getBatch().draw(blueShip, X_POSITION, Y_POSITION, SHIP_WIDTH, SHIP_HEIGHT);
                if (isOpen) {
                    stage.getBatch().draw(blueShipRoom, X_POSITION, Y_POSITION, SHIP_WIDTH, SHIP_HEIGHT);
                    stage.getBatch().draw(shield, BaseScreen.WIDTH/2f,BaseScreen.HEIGHT-240);
                    stage.getBatch().draw(drive,(BaseScreen.WIDTH/2f)-100,BaseScreen.HEIGHT-450);
                    stage.getBatch().draw(weaponsSystem,(BaseScreen.WIDTH/2f)+85,BaseScreen.HEIGHT-350);

                    RedPin redPin2 = new RedPin();
                    RedPin redPin3 = new RedPin();
                    RedPin redPin4 = new RedPin();
                    RedPin redPin5 = new RedPin();
                    RedPin redPin6 = new RedPin();
                    //Sektion 1
                    stage.getBatch().draw(redPin.texture, X_POSITION + 90, Y_POSITION + 350);
                    //Sektion 2
                    stage.getBatch().draw(redPin2.texture, X_POSITION + 240, Y_POSITION + 336);
                    //Sektion 3
                    stage.getBatch().draw(redPin3.texture, X_POSITION + 320, Y_POSITION + 270);
                    //Sektion 4
                    stage.getBatch().draw(redPin4.texture, X_POSITION + 320, Y_POSITION + 210);
                    //Sektion 5
                    stage.getBatch().draw(redPin5.texture, X_POSITION + 240, Y_POSITION + 135);
                    //Sektion 6
                    stage.getBatch().draw(redPin6.texture, X_POSITION + 90, Y_POSITION + 124);

                    //Crewmember befindet sich in Sektion 2
                    stage.addActor(imageCrewTestSektion2);
                    //Crewmember befindet sich in Sektion 4
                    stage.addActor(imageCrewTestSektion4);
                    //Crewmember befindet sich in Sektion 6
                    stage.addActor(imageCrewTestSektion6);
                }else {
                    imageCrewTestSektion4.remove();
                    imageCrewTestSektion6.remove();
                    imageCrewTestSektion2.remove();
                }
                break;
            case 1:
                stage.getBatch().draw(redShip, X_POSITION, Y_POSITION, SHIP_WIDTH, SHIP_HEIGHT);
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

        stage.getBatch().draw(relevantSystems,(BaseScreen.WIDTH/2f)-185,-140);
        stage.act();
        stage.getBatch().end();
        stage.draw();

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
        mouseClick.dispose();
    }

    public void sendRequestAddCrewMembers(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        final String url = Global.SERVER_URL + Global.CREWMEMBERS_CREATION_ENDPOINT;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed sendRequestAddCrewMembers");
                }
                String listofsections = httpResponse.getResultAsString();
                Gson gson = new Gson();
                CrewMember[] crewMembersArray = gson.fromJson(listofsections, CrewMember[].class);
                crewMemberList = Arrays.asList(crewMembersArray);
                System.out.println("statusCode sendRequestAddCrewMembers: " + statusCode);
            }

            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }

            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });


    }

    //
    public void sendRequestAddSections(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        final String url = Global.SERVER_URL + Global.SECTIONS_CREATION_ENDPOINT;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed sendRequestAddSections");
                }
                String listofsections = httpResponse.getResultAsString();
                Gson gson = new Gson();
                Section[] sectionArray = gson.fromJson(listofsections, Section[].class);
                sectionList = Arrays.asList(sectionArray);
                System.out.println("statusCode sendRequestAddSections: " + statusCode);
            }

            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }

            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });

    }
    //
    //
    public void sendRequestAddShips(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        final String url = Global.SERVER_URL + Global.SHIPS_CREATION_ENDPOINT;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed sendRequestAddShip");
                }
                String ships = httpResponse.getResultAsString();
                Gson gson = new Gson();
                Ship[] aiArray = gson.fromJson(ships, Ship[].class);
                shipsgerner = Arrays.asList(aiArray);
                System.out.println("statusCode sendRequestAddShip: " + statusCode);
            }

            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }

            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });


    }

    //
    //
    public void sendRequestAddShip(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        final String url = Global.SERVER_URL + Global.SHIP_ENDPOINT;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed sendRequestAddShip");
                }
                responseJson = httpResponse.getResultAsString();
                requestcounter++;
                System.out.println("statusCode sendRequestAddShip: " + statusCode);
            }

            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }

            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
    }

    //
    public void sendRequestAddUniverse(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        final String url = Global.SERVER_URL + Global.UNIVERSE_CREATION_ENDPOINT;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed AddUniverise");
                }
                System.out.println("statusCode AddUniverise: " + statusCode);
                universeID = Integer.parseInt(httpResponse.getResultAsString());
            }
            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }
            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
    }
    //
    public void sendRequestAisCreation(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        final String url = Global.SERVER_URL + AIS_CREATION_ENDPOINT;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed aiCreation");
                }
                System.out.println("statusCode of aiCreation: " + statusCode);
                String listAIS = httpResponse.getResultAsString();
                Gson gson = new Gson();
                AI[] aiArray = gson.fromJson(listAIS, AI[].class);
                aiList = Arrays.asList(aiArray);
            }
            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }
            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
    }
    //
    public void sendRequestAddPlanets(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        final String url = Global.SERVER_URL + PLANETS_CREATION_ENDPOINT;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed AddPlanets");
                }
                System.out.println("statusCode of AddPlanets: " + statusCode);
                String platenList = httpResponse.getResultAsString();
                Gson gson = new Gson();
                Planet[] aiArray = gson.fromJson(platenList, Planet[].class);
                planets = Arrays.asList(aiArray);

            }

            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }

            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
    }

    //
    public void sendRequestAddStation(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        final String url = Global.SERVER_URL + STATIONS_CREATION_ENDPOINT;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed AddStation");
                }
                System.out.println("statusCode AddStation: " + statusCode);
                String stationList = httpResponse.getResultAsString();
                Gson gson = new Gson();
                Station[] aiArray = gson.fromJson(stationList, Station[].class);
                stations = Arrays.asList(aiArray);
            }

            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }

            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
    }

    //
    public void sendRequestAddShopRessources(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        final String url = Global.SERVER_URL + Global.RESSOURCES_SHOP_CREATION_ENDPOINT;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed AddShopRessource");
                }
                System.out.println("statusCode AddShopRessource: " + statusCode);
                String ressourcesList = httpResponse.getResultAsString();
                Gson gson = new Gson();
                ShopRessource[] ressourcenArray = gson.fromJson(ressourcesList, ShopRessource[].class);
                shopRessources = Arrays.asList(ressourcenArray);
            }

            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }

            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
    }

    //
    public void sendRequestAddShipRessource(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        final String url = Global.SERVER_URL + RESSOURCE_SHIP_CREATION_ENDPOINT;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed AddShipRessource");
                }
                System.out.println("statusCode AddShipRessource: " + statusCode);
                shipRessourceID = Integer.parseInt(httpResponse.getResultAsString());
            }

            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }

            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
    }
    //
    public void sendRequestAddWeapon(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        final String url = Global.SERVER_URL + WEAPONS_SHIP_CREATION_ENDPOINT;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed sendRequestAddWeapon");
                }
                System.out.println("statusCode sendRequestAddWeapon: " + statusCode);
                String weaponsList = httpResponse.getResultAsString();
                Gson gson = new Gson();
                Weapon[] weaponsArray = gson.fromJson(weaponsList, Weapon[].class);
                weapons = Arrays.asList(weaponsArray);
            }
            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }
            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
    }
    //
}
