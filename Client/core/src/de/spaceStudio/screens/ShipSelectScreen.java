package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import de.spaceStudio.MainClient;
import de.spaceStudio.client.util.Difficult;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.client.util.RequestUtils;
import de.spaceStudio.server.handler.SinglePlayerGame;
import de.spaceStudio.server.model.*;
import de.spaceStudio.service.InitialDataGameService;
import de.spaceStudio.service.SinglePlayerGameService;
import thirdParties.GifDecoder;

import java.util.List;
import java.util.*;
import java.util.logging.Logger;

import static de.spaceStudio.client.util.Global.*;
import static de.spaceStudio.client.util.RequestUtils.setupRequest;
import static de.spaceStudio.service.LoginService.fetchLoggedUsers;
import static de.spaceStudio.service.MultiPlayerService.fetchMultiPlayerSession;
import static de.spaceStudio.service.MultiPlayerService.joinMultiplayerRoom;
//“Sound effects obtained from https://www.zapsplat.com“

public class ShipSelectScreen extends BaseScreen {

    private final static Logger LOG = Logger.getLogger(ShipSelectScreen.class.getName());
    private static final int X_POSITION = 750;
    private static final int Y_POSITION = 550;
    private static final int SHIP_WIDTH = 500;
    private static final int SHIP_HEIGHT = 500;
    private final MainClient mainClient;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final Texture blueShip;
    private final Texture redShip;
    private final Texture greenship;
    private final Texture topdownfighter;
    private final Texture blueShipRoom;
    private final Texture redShipRoom;
    private final Texture greenshipRoom;
    private final Texture topdownfighterRoom;
    private final Texture relevantSystems;
    private final Texture shield;
    private final Texture weaponsSystem;
    private final Texture drive;
    private final Texture crewDisplay;
    private final Texture crewMember;
    private final Texture crewMember2;
    private final Texture crewMember3;
    private final Image imageCrewMemberSektion2;
    private final Image imageCrewMemberSektion4;
    private final Image imageCrewMemberSektion6;
    private final TextField crew_1_name;
    private final TextField crew_2_name;
    private final TextField crew_3_name;
    private final Texture background;
    private final Stage stage;
    private final Skin skinButton;
    private final Skin skin;

    private final Viewport viewport;
    private final Sound spaceShipChange;
    private final Sound mouseClick;
    private final InitialDataGameService idgs = new InitialDataGameService();
    private final InputHandler inputHandler;
    private final int timeoutMultiPlayer = 0;
    private final OrthographicCamera camera;
    Animation<TextureRegion> crew1;
    Animation<TextureRegion> crew2;
    Animation<TextureRegion> crew3;
    float state = 0f;
    int shipNumber = 0;
    int openNumber = 0;
    Ship ship = new Ship();
    Universe universe1 = Global.universe1;
    Universe universe2 = Global.universe2;
    private int requestcounter;
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
    private Label usernameLabel, playersOnlineLabel, displayOnlinePlayerName;
    private TextButton next;
    private TextButton previous;
    private TextButton showHideRoom;
    private TextButton startButton;
    private TextButton backMenuButton;
    private TextButton easyButton;
    private TextButton normalButton;

    private boolean isOpen;
    private int levelDifficult = 0;
    private boolean killTimer;
    private boolean logoutMultiPlayer;
    private boolean readyUpTriggered;
    private boolean deployMultiplayer;
    private int counter = 0;

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
        requestcounter = 0;

        Gdx.input.setInputProcessor(stage);
        skinButton = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        
        if (Global.isOnlineGame) {
            fetchLoggedUsers();
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

        crew_1_name = new TextArea("Clara", skinButton);
        crew_1_name.setPosition(120, 220);
        crew_2_name = new TextArea("Mehmet", skinButton);
        crew_2_name.setPosition(120, 150);
        crew_3_name = new TextArea("Santiago", skinButton);
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
        shield = new Texture(Gdx.files.internal("Client/core/assets/data/ships/shield.png"));
        drive = new Texture(Gdx.files.internal("Client/core/assets/data/ships/drive.png"));
        weaponsSystem = new Texture(Gdx.files.internal("Client/core/assets/data/ships/weapons.png"));
        crewDisplay = new Texture(Gdx.files.internal("Client/core/assets/data/ships/shipPanel.png"));
        crewMember = new Texture(Gdx.files.internal("Client/core/assets/combatAssets/female_human.png"));
        crewMember2 = new Texture(Gdx.files.internal("Client/core/assets/combatAssets/MaleHuman-3.png"));
        crewMember3 = new Texture(Gdx.files.internal("Client/core/assets/combatAssets/MaleHuman-3.png"));

        imageCrewMemberSektion2 = new Image(crewMember);
        imageCrewMemberSektion4 = new Image(crewMember2);
        imageCrewMemberSektion6 = new Image(crewMember3);
        imageCrewMemberSektion2.setBounds(30, 30, 30, 30);
        imageCrewMemberSektion4.setBounds(30, 30, 30, 30);
        imageCrewMemberSektion6.setBounds(30, 30, 30, 30);
        imageCrewMemberSektion2.setPosition(X_POSITION + section2.getxPos(), Y_POSITION + section2.getyPos());
        imageCrewMemberSektion4.setPosition(X_POSITION + section4.getxPos(), Y_POSITION + section4.getyPos());
        imageCrewMemberSektion6.setPosition(X_POSITION + section6.getxPos(), Y_POSITION + section6.getyPos());

        spaceShipChange = Gdx.audio.newSound(Gdx.files.internal("Client/core/assets/data/music/change.wav"));
        nextButton();
        previousButton();

        showHideRoom();
        selectLevelView();

        // top left position
        if (!Global.IS_SINGLE_PLAYER) {

            playersOnlineLabel = new Label(null, skinButton);
            playersOnlineLabel.setPosition(20, 950);
            playersOnlineLabel.setFontScale(2);

            displayOnlinePlayerName = new Label(null, skinButton);
            displayOnlinePlayerName.setPosition(20, 920);
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
        // Don't show online
        if (IS_SINGLE_PLAYER) {
            stage.addActor(easyButton);
            stage.addActor(normalButton);
        }

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
                        /*
                        271, 158
                        475, 293
                        647, 307
                        479, 469
                        646, 461
                        266, 607
                         */

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
        viewport.update(width, height);

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
        startButton.setColor(Color.GOLDENROD);
        if (!IS_SINGLE_PLAYER) {
            startButton.setText("READY UP");
            startButton.setColor(Color.CYAN);
            fetchMultiPlayerSession();
        }
        startButton.setScaleX(1.8f);
        startButton.setScaleY(1.5f);

        startButton.setPosition(BaseScreen.WIDTH - 250, BaseScreen.HEIGHT - 155);
        startButton.getLabel().setColor(Color.WHITE);
        startButton.getLabel().setFontScale(1.25f, 1.25f);
        startButton.setSize(90, 50);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (IS_SINGLE_PLAYER) {
                    createSinglePlayerGame();
                } else {
                    if (!readyUpTriggered) {
                        joinMultiplayerRoom();
                        startButton.setColor(Color.GREEN);
                        readyUpTriggered = true;

                    } else {
                        // send request
                        startButton.setColor(Color.CYAN);
                        readyUpTriggered = false;
                    }


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

                Gson gson = new Gson();
                String url = SERVER_URL + MULTIPLAYER_LOGOUT;
                String payLoad = gson.toJson(currentPlayer);
                Net.HttpRequest request = setupRequest(url, payLoad, Net.HttpMethods.POST);
                Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {

                    @Override
                    public void handleHttpResponse(Net.HttpResponse httpResponse) {
                        logoutMultiPlayer = true;
                        IS_SINGLE_PLAYER = true;
                        killTimer = true;
                    }

                    @Override
                    public void failed(Throwable t) {

                    }

                    @Override
                    public void cancelled() {

                    }
                });
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

        if (!IS_SINGLE_PLAYER) {
            scheduleLobby();
        } else {
            StartButton();
        }
    }

    /**
     * Ask server every 5 seconds
     */
    private void scheduleLobby() {
        Timer schedule = new Timer();
        schedule.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (killTimer) {
                    schedule.cancel();
                    schedule.purge();
                    counter = 0;
                    LOG.info("Timer killed");
                } else {
                    if(playersOnline.size() == 0){
                    counter += 1;
                    if (counter % 2 == 0) {
                        LOG.info("::::::::::::::::::: DIALOG:::::::::::::::::::::::");
                        killTimer = true;
                        new Dialog("No players found", skinButton) {

                            {
                                text("There is no player");
                                button("Try again", "try").getButtonTable().row();
                                button("Play with AI", "ai").getButtonTable().row();
                            }

                            @Override
                            protected void result(Object object) {
                                if (object.equals("ai")) {
                                    killTimer = true;
                                    IS_SINGLE_PLAYER = true;
                                    game.setScreen(new ShipSelectScreen(game));
                                    // make single player here
                                } else if (object.equals("try")) {
                                    killTimer = true;
                                    // reload all here
                                    game.setScreen(new ShipSelectScreen(game));
                                }
                            }

                        }.show(stage);
                    }
                    }


                    LOG.info("Fetching data from server...");
                    LOG.info(multiPlayerSessionID);
                    fetchLoggedUsers();
                    if (readyUpTriggered) {
                        checkMultiPlayerToStartSynchro();
                    }
                }
            }
        }, 1000, 5000);
    }

    public void checkMultiPlayerToStartSynchro() {
        String url = Global.SERVER_URL + Global.MULTIPLAYER_SYNCHRO_ROOM + Global.multiPlayerSessionID;
        Net.HttpRequest request = RequestUtils.setupRequest(url, "", Net.HttpMethods.GET);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                LOG.info("Waiting for players...");
                String response = httpResponse.getResultAsString();
                LOG.info(response);
                if (response.equals("true")) {
                    // SetScreen Station Map
                    LOG.info("Setting up stationStop Screen");
                    deployMultiplayer = true;
                } else {
                    LOG.info("False var");
                    // Notify Player try again or play single player
                    deployMultiplayer = false;
                }
            }

            @Override
            public void failed(Throwable t) {

            }

            @Override
            public void cancelled() {

            }
        });

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
        stage.getBatch().draw(crewDisplay, 0, 0);
        stage.getBatch().draw(crew1.getKeyFrame(state), 45, 220, 70, 70);
        stage.getBatch().draw(crew2.getKeyFrame(state), 45, 150, 70, 70);
        stage.getBatch().draw(crew3.getKeyFrame(state), 45, 80, 70, 70);

        if (deployMultiplayer) {
            killTimer = true;
            mainClient.setScreen(new StationsMap(game));
        }
        // Bock
        if (IS_SINGLE_PLAYER) {
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
                System.out.println("Current Power Nr.1: " + Global.sectionsPlayerList.get(0).getPowerCurrent());
                sendRequestAddSections(Global.sectionsPlayerList, Net.HttpMethods.POST);
                System.out.println("Current Power Nr.2: " + Global.sectionsPlayerList.get(0).getPowerCurrent());
                requestcounter = 2;
            }

            if (logoutMultiPlayer) {
                logoutMultiPlayer = false;
                mainClient.setScreen(new NewGameScreen(game));
            }
            /*Added sectionList*/
            if (!sectionList.isEmpty() && requestcounter == 2) {
                //Section with ID
                                    /*
                        271, 158
                        475, 293
                        647, 307
                        479, 469
                        646, 461
                        266, 607
                         */

                Global.sectionsPlayerList = sectionList;

                //Update Section variables
                updateVariableSectionShipPlayer();
                //Set crewMemebers
                int counter = 1;
                for (CrewMember c :
                        Global.crewMemberList) {
                    switch (counter) {
                        case 1:
                            c.setCurrentSection(section2);
                            c.setName(crew_1_name.getText());
                            break;
                        case 2:
                            c.setCurrentSection(section4);
                            c.setName(crew_2_name.getText());
                            break;
                        case 3:
                            c.setCurrentSection(section6);
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
                List<CrewMember> sizeO = new ArrayList<>();
                crewMemberList = sizeO;
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
                List<Section> sectionsforU2 = new ArrayList<>();
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
                    switch (s.getShip().getName()) {
                        case "Shipgegner1":
                            sectionsgegner1.add(s);
                            break;
                        case "Shipgegner2":
                            sectionsgegner2.add(s);
                            break;
                        case "Shipgegner3":
                            sectionsgegner3.add(s);
                            break;
                        case "Shipgegner4":
                            sectionsgegner4.add(s);
                            break;
                        case "Shipgegner5":
                            sectionsgegner5.add(s);
                            break;
                        case "Shipgegner6":
                            sectionsgegner6.add(s);
                            break;
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
                shipRessourceID = 0;
            }
            if (requestcounter == 20) {
                List<Weapon> weaponsUniver2 = new ArrayList<>();
                for (Weapon w :
                        weaponListPlayer) {
                    w.setSection(section2);
                    weaponsUniver2.add(w);
                }
                for (Weapon w :
                        weaponListGegner1) {
                    w.setSection(section3Gegner);
                    weaponsUniver2.add(w);
                }
                for (Weapon w :
                        weaponListGegner2) {
                    w.setSection(section3Gegner2);
                    weaponsUniver2.add(w);
                }
                for (Weapon w :
                        weaponListGegner3) {
                    w.setSection(section3Gegner3);
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

                requestcounter = 22;
            }
            if (requestcounter == 22) {
                for (Section section :
                        sectionsgegner1) {
                    switch (section.getImg()) {
                        case "Section1Gegner1":
                            crewMember1gegner1.setCurrentSection(section);
                            break;
                        case "Section2Gegner1":
                            crewMember2gegner1.setCurrentSection(section);
                            break;
                    }
                }
                for (Section section :
                        sectionsgegner2) {
                    switch (section.getImg()) {
                        case "Section1Gegner2":
                            crewMember1gegner2.setCurrentSection(section);
                            break;
                        case "Section2Gegner2":
                            crewMember2gegner2.setCurrentSection(section);
                            break;
                    }
                }
                for (Section section :
                        sectionsgegner3) {
                    switch (section.getImg()) {
                        case "Section1Gegner3":
                            crewMember1gegner3.setCurrentSection(section);
                            break;
                        case "Section2Gegner3":
                            crewMember2gegner3.setCurrentSection(section);
                            break;
                    }
                }
                sendRequestAddCrewMembers(List.of(crewMember1gegner1, crewMember2gegner1, crewMember1gegner2, crewMember2gegner2, crewMember1gegner3, crewMember2gegner3), Net.HttpMethods.POST);
                requestcounter = 23;
            }
            if (!crewMemberList.isEmpty() && requestcounter == 23) {
                Global.crewMember1gegner1 = crewMemberList.get(0);
                Global.crewMember2gegner1 = crewMemberList.get(1);
                Global.crewMember1gegner2 = crewMemberList.get(2);
                Global.crewMember2gegner2 = crewMemberList.get(3);
                Global.crewMember1gegner3 = crewMemberList.get(4);
                Global.crewMember2gegner3 = crewMemberList.get(5);
                mainClient.setScreen(new StationsMap(game));
                requestcounter = 24;
            }
            //}
        } else {
            // TODO Online game
        }
        /////
        switch (shipNumber) {
            case 0:
                stage.getBatch().draw(blueShip, X_POSITION, Y_POSITION, SHIP_WIDTH, SHIP_HEIGHT);
                if (isOpen) {
                    stage.getBatch().draw(blueShipRoom, X_POSITION, Y_POSITION, SHIP_WIDTH, SHIP_HEIGHT);
                    stage.getBatch().draw(shield, X_POSITION + 210, Y_POSITION + 290);
                    stage.getBatch().draw(drive, X_POSITION + 110, Y_POSITION + 80);
                    stage.getBatch().draw(weaponsSystem, X_POSITION + 295, Y_POSITION + 180);

                    //Crewmember befindet sich in Sektion 2
                    stage.addActor(imageCrewMemberSektion2);
                    //Crewmember befindet sich in Sektion 4
                    stage.addActor(imageCrewMemberSektion4);
                    //Crewmember befindet sich in Sektion 6
                    stage.addActor(imageCrewMemberSektion6);
                } else {
                    imageCrewMemberSektion2.remove();
                    imageCrewMemberSektion4.remove();
                    imageCrewMemberSektion6.remove();
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

        stage.getBatch().draw(relevantSystems, (BaseScreen.WIDTH / 2f) - 185, -140);
        stage.act();
        stage.getBatch().end();
        stage.draw();

        // top left position
        if (Global.isOnlineGame) {
            drawLobby();
        }
        if (!Global.IS_SINGLE_PLAYER) drawLobby();


    }

    /**
     * fill the online players list
     */
    public void drawLobby() {
        playersOnlineLabel.setText("Players online: " + playersOnline.size());
        // Get first position, we support max 2 players in the whole game
        if (playersOnline.size() > 0) {
            displayOnlinePlayerName.setText(playersOnline.get(0));
        } else {
            displayOnlinePlayerName.setText("");
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
        LOG.info("HIDE CALLED");
        isOnlineGame = false;
    }

    @Override
    public void dispose() {
        LOG.info("DISPOSE CALLED");
        super.dispose();
        skinButton.dispose();
        spaceShipChange.dispose();
        stage.dispose();
        mouseClick.dispose();
        batch.dispose();
        font.dispose();
        blueShip.dispose();
        redShip.dispose();
        greenship.dispose();
        topdownfighter.dispose();
        blueShipRoom.dispose();
        redShipRoom.dispose();
        greenshipRoom.dispose();
        topdownfighterRoom.dispose();
        relevantSystems.dispose();
        shield.dispose();
        weaponsSystem.dispose();
        drive.dispose();
        crewDisplay.dispose();
        crewMember.dispose();
        crewMember2.dispose();
        crewMember3.dispose();
        background.dispose();
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

        ObjectMapper objectMapper = new ObjectMapper();

        final String requestJson;
        try {
            requestJson = objectMapper.writeValueAsString(requestObject);
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
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
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
