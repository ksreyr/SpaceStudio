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
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
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

import static de.spaceStudio.client.util.Global.*;
import static de.spaceStudio.client.util.RequestUtils.setupRequest;
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
    Weapon weaponShip = Global.weapon;
    CrewMember crewMember0 = Global.crewMember0;
    CrewMember crewMember1 = Global.crewMember1;
    CrewMember crewMember2 = Global.crewMember2;

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

    Station station1 = Global.station1;
    Station station2 = Global.station2;

    ShopRessource shopRessource1 = Global.shopRessource1;
    ShopRessource shopRessource2 = Global.shopRessource2;
    ShipRessource shipRessource = Global.shipRessource1;

    String responseJson;
    List<Section> sectionWithId;
    Boolean addShip = false;
    Boolean crewmembervariableupdated = false;
    Boolean crewmembersendet = false;
    Boolean airesquesSent = false;
    Boolean aivariableUpdated = false;
    Boolean sectionsPlayersendet = false;
    Boolean sectionsadded = false;

    Boolean universeRequestSent = false;
    Boolean variableaiupdated = false;
    Boolean sectionsgegnersent = false;
    Boolean sectionsgegnerVariableUpdate= false;
    Boolean planetsRequestSended=false;
    Boolean shipsgegneru1sent = false;
    Boolean shipsgegneru1valuesupdated = false;
    List<Section> sectionList = new ArrayList<>();
    List<CrewMember> crewMemberList = new ArrayList<>();
    List<AI> aiList = new ArrayList<>();
    int universeID = 0;
    List<Ship> shipsgerner = new ArrayList<>();
    List<Planet> planets = new ArrayList<>();
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
                sendRequestAddShip(ship, Net.HttpMethods.POST);

            }
        });
    }

    //
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
        final String url = Global.SERVER_URL + Global.SHIP_CREATION_ENDPOINT;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed sendRequestAddShip");
                }
                responseJson = httpResponse.getResultAsString();
                addShip =true;
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
                airesquesSent =true;
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
        final String url = Global.SERVER_URL + PLANET_CREATION_ENDPOINT;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed aiCreation");
                }
                System.out.println("statusCode of aiCreation: " + statusCode);
                String platenList = httpResponse.getResultAsString();
                Gson gson = new Gson();
                Planet[] aiArray = gson.fromJson(platenList, Planet[].class);
                planetList = Arrays.asList(aiArray);

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
        stage.getBatch().draw((TextureRegion) crew1.getKeyFrame(state), 10, 250, 70, 70);
        stage.getBatch().draw((TextureRegion) crew2.getKeyFrame(state), 10, 180, 70, 70);
        stage.getBatch().draw((TextureRegion) crew3.getKeyFrame(state), 10, 110, 70, 70);

        /*Added Ship*/
        if (addShip) {
            if (responseJson != null && !responseJson.isEmpty()) {
                ship.setId(Integer.valueOf(responseJson));
                Global.currentShip=ship;
                //Ship to Sections
                for (Section s :
                        Global.sectionPlayerList) {
                    s.setShip(ship);
                }
            }
            //Sent Sections
            if (sectionsPlayersendet == false) {
                sendRequestAddSections(Global.sectionPlayerList, Net.HttpMethods.POST);
                sectionsPlayersendet = true;
            }
            addShip = false;
        }

        /*Added sectionList*/
        if (!sectionList.isEmpty() && sectionsadded == false) {
            //Section with ID
            Global.sectionPlayerList = sectionList;
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
            sectionsadded = true;
            crewmembersendet = true;
        }
        if (!crewMemberList.isEmpty() && crewmembersendet) {
            Global.crewMemberList = crewMemberList;
            Global.updateVariableCrewMembersPlayer();
            crewmembervariableupdated = true;
            crewmembersendet = false;
        }

        if (crewmembervariableupdated && universeRequestSent == false) {
            if (levelDifficult == Difficult.NORMAL.getLevelCode()) {
                universe2.setName(universe2.getName() + currentPlayer.getName());
                Global.universe2.setName(universe2.getName());
                sendRequestAddUniverse(universe2, Net.HttpMethods.POST);

            } else {
                universe1.setName(universe1.getName() + currentPlayer.getName());
                Global.universe1.setName(universe1.getName());
                sendRequestAddUniverse(universe1, Net.HttpMethods.POST);
            }
            universeRequestSent = true;
        }
        if (universeRequestSent && universeID != 0 && airesquesSent == false) {
            if (levelDifficult == Difficult.NORMAL.getLevelCode()) {
                Global.universe2.setId(universeID);
                currentUniverse = Global.universe2;
                //AISFORU2
            } else {
                Global.universe1.setId(universeID);
                currentUniverse = Global.universe1;
                sendRequestAisCreation(Global.aisU1, Net.HttpMethods.POST);

            }
        }
        if (airesquesSent && !aiList.isEmpty() && variableaiupdated == false) {
            Global.aisU1 = aiList;
            Global.updateVariableaiu1();
            variableaiupdated = true;
        }
        if (variableaiupdated && shipsgegneru1sent == false) {
            //UNIVERSE2????
            for (Ship s :
                    shipsgegneru1) {
                switch (s.getName()) {
                    case "Shipgegner1":
                        s.setOwner(ai1);
                        break;
                    case "Shipgegner2":
                        s.setOwner(ai2);
                        break;
                    default:
                        break;
                }
            }
            sendRequestAddShips(shipsgegneru1, Net.HttpMethods.POST);
            shipsgegneru1sent = true;
        }

        if (!shipsgerner.isEmpty() && shipsgegneru1valuesupdated == false) {
            Global.shipsgegneru1=shipsgerner;
            Global.updateShipsVariabelgegneru1();
            shipsgegneru1valuesupdated=true;
        }

        if (sectionsgegnersent ==false&&shipsgegneru1valuesupdated) {
            List<Section> sectionsforU1= new ArrayList<Section>();
            for (Section s :
                    Global.sectionsgegner1) {
                s.setShip(shipGegner1);
                sectionsforU1.add(s);
            }
            for (Section s :
                    Global.sectionsgegner2) {
                s.setShip(shipGegner2);
                sectionsforU1.add(s);
            }
            sendRequestAddSections(sectionsforU1, Net.HttpMethods.POST);
            //man kann nicht das method clear an der list nutzen. deswegen sizeO
            List<Section> sizeO=new ArrayList<>();
            sectionList=sizeO;
            sectionsgegnersent=true;
        }
        if(sectionsgegnersent&&!sectionList.isEmpty()){
            List<Section> sectionsgegner1= new ArrayList<>();
            List<Section> sectionsgegner2= new ArrayList<>();
            for (Section s :
                    sectionList) {
                if(s.getShip().getName().equals("Shipgegner1")){
                    sectionsgegner1.add(s);
                }else if(s.getShip().getName().equals("Shipgegner2")){
                    sectionsgegner2.add(s);
                }
            }
            Global.sectionsgegner1=sectionsgegner1;
            Global.updateVariblesGegner1();
            Global.sectionsgegner2=sectionsgegner2;
            Global.updateVariblesGegner2();
            sectionsgegnerVariableUpdate=true;
        }
        //Planeten request
        if(sectionsgegnerVariableUpdate==true&&planetsRequestSended==false){
            //Universe1
            List<Ship> shipsP1=new ArrayList<Ship>(){{add(currentShip);}};
            List<Ship> shipsP2=new ArrayList<Ship>(){{add(shipGegner1);}};
            List<Ship> shipsP3=new ArrayList<Ship>(){{add(shipGegner2);}};
            for (Planet p :
                    planetList) {
                switch (p.getName()){
                    case "p1":
                        p.setShips(shipsP1);
                        break;
                    case "p2":
                        p.setShips(shipsP2);
                        break;
                    case "p3":
                        p.setShips(shipsP3);
                        break;
                    default:
                        break;
                }
            }
            sendRequestAddPlanets(planetList, Net.HttpMethods.POST);
            planetsRequestSended=true;
        }




        /////
        /////
        /////
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
