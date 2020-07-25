package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.gson.Gson;
import de.spaceStudio.MainClient;
import de.spaceStudio.assets.StyleNames;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.client.util.RequestUtils;
import de.spaceStudio.server.model.*;
import de.spaceStudio.util.GdxUtils;

import java.util.List;
import java.util.*;
import java.util.logging.Logger;

import static de.spaceStudio.client.util.RequestUtils.setupRequest;


public class CombatScreen extends BaseScreen {

    public static final int XEnemyShip = 1300;
    public static final int YEnemyPos = 370;
    public static final int WIDTHGegner = 550;
    public static final int HEIGHTGegner = 550;
    public static final int XPlayerShip = 400;
    public static final int YPlayerShip = 400;
    public static final int WidthPlayerShip = 500;
    public static final int HeightPlayerShip = 500;
    private final static Logger LOG = Logger.getLogger(CombatScreen.class.getName());
    private final Label weaponLabel;
    private final String[] weaponText = {"All Weapons", "You have selected Weapon: "};
    private final AssetManager assetManager;
    private final MainClient universeMap;
    private final MainClient mainClient;
    private final Label labelsection1;
    private final Label labelsection2;
    private final Label labelsection3;
    private final Label labelsection4;
    private final Label labelsection5;
    private final Label labelsection6;
    private final int disappearRight = 570;
    private final int disappearLeft = 570;
    private final int counterEngine = 0;
    private final int counterWeapon = 0;
    private final OrthographicCamera camera;
    boolean isNewExpo, isNewExpo2, isNewExpo3;
    boolean isFired = false;
    boolean canFire = false;
    boolean canFireGegner = false;
    int fuzeOffsetright, fuzeOffsetLeft;
    //private TextureAtlas gamePlayAtlas;
    boolean isWin;
    Texture bullet, shield;
    float x = 0;
    Sound rocketLaunch;
    ArrayList<Bullet> bullets;
    ArrayList<Bullet> bulletsEnemy;
    ShipSelectScreen shipSelectScreen;
    //
    String validation = "";
    String validationGegner = "";
    List<Section> sectionsGegner = Global.combatSections.get(Global.currentShipGegner.getId());
    List<Section> sectionsPlayer = Global.combatSections.get(Global.currentShipPlayer.getId());
    List<Section> sectionsPlayerTimer = Global.combatSections.get(Global.currentShipPlayer.getId());
    Label lebengegnerShip;
    Label lebenplayerShip;
    private Viewport viewport;
    private Stage stage;
    private Skin sgxSkin, sgxSkin2;
    private Sound click;
    private Skin skin;
    private SpriteBatch batch;
    private Texture playerShip;
    private Texture enemyShip1, enemyShip2, enemyShip3;
    private Texture hull;
    private Texture background;
    private Texture crewMemberOne;
    private Texture crewMemberTwo;
    //    private boolean isSectionw, sectiond, sectionOthers,    isSectiono2 ,isSectionOthers,  isSectiond , isSectionhealth ;
    private Texture crewMemberThree;
    private RedPin redPinSectionOne;
    private RedPin redPinSectionTwo;
    private RedPin redPinSectionThree;
    private RedPin redPinSectionFour;
    private RedPin redPinSectionFive;
    private RedPin redPinSectionSix;
    private Image imageCrewMemberOne;
    private Image imageCrewMemberTwo;
    private Image imageCrewMemberThree;
    private List<Image> listOfCrewImages;
    private List<CrewMember> myCrew;
    private Boolean killTimer = false;
    private TextButton enableShield, enableEnemyShield;
    private boolean isExploied;
    private Texture missilleRight, explosion, missilleLeft, weaponSystem;
    private boolean isTargetSelected, isTargetEngine, isTargetCockpit, isTargetWeapon;
    private Skin skinButton;
    private boolean isShieldEnabled, isEnemyShield, isTargetO2, isTargetMedical;
    private ImageButton engine, weaponSection, cockpit, o2, healthPoint;
    private int counterCockpit = 0;
    private int randomNumber;
    private int aktiveWeapon = 0;
    private List<Weapon> selectedWeapons;
    private boolean dragged = false;
    private Section selectedTarget;
    private Optional<Section> startSectionCrewMove;
    private Optional<Section> endSectionCrewMove;
    boolean movingAllowed = false;

    public CombatScreen(MainClient mainClient) {
        super(mainClient);
        this.universeMap = mainClient;
        this.mainClient = mainClient;
        assetManager = universeMap.getAssetManager();
        camera = new OrthographicCamera();

        myCrew = Global.combatCrew.get(Global.currentShipPlayer.getId());


        int row_height = Gdx.graphics.getWidth() / 12;
        int col_width = Gdx.graphics.getWidth() / 12;
        Label.LabelStyle label1Style = new Label.LabelStyle();

        BitmapFont myFont = new BitmapFont(Gdx.files.internal("bitmap/amble.fnt"));
        label1Style.font = myFont;
        label1Style.fontColor = Color.RED;

        labelsection1 = new Label("Section1", label1Style);
        labelsection2 = new Label("Section2", label1Style);
        labelsection3 = new Label("Section3", label1Style);
        labelsection4 = new Label("Section4", label1Style);
        labelsection5 = new Label("Section5", label1Style);
        labelsection6 = new Label("Section6", label1Style);

        weaponLabel = new Label(weaponText[0], label1Style);
        weaponLabel.setSize(Gdx.graphics.getWidth(), row_height);
        weaponLabel.setPosition(0, Gdx.graphics.getHeight() - row_height * 6);
        weaponLabel.setAlignment(Align.bottomRight);


    }

    private Optional<Section> findSectionByNameAndShip(String name, int id, Boolean currentTarget) {
        Optional<Section> result = Optional.empty();

        for (Section s :
                Global.combatSections.get(id)) {
            if (s.getImg().equals(name)) {
                result = Optional.of(s);
                if (currentTarget) {
                    selectedTarget = s;
                }
            }

        }
        return result;
    }

    //called when the Screen gains focus
    @Override
    public void show() {

        viewport = new StretchViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT, camera);
        stage = new Stage(viewport, universeMap.getBatch());
        click = Gdx.audio.newSound(Gdx.files.internal("Client/core/assets/data/music/mouseclick.wav"));
        scheduleLobby();
        sgxSkin2 = new Skin(Gdx.files.internal("Client/core/assets/ownAssets/sgx/skin/sgx-ui.json"));
        listOfCrewImages = new ArrayList<>();
        redPinSectionOne = new RedPin();
        redPinSectionTwo = new RedPin();
        redPinSectionThree = new RedPin();
        redPinSectionFour = new RedPin();
        redPinSectionFive = new RedPin();
        redPinSectionSix = new RedPin();

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        skinButton = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        background = new Texture("Client/core/assets/combatAssets/CombatBG.jpg");
        playerShip = new Texture("Client/core/assets/data/ships/blueships1_section.png");
        // playerShip = new Texture("Client/core/assets/combatAssets/blueships_fulled.png");
        enemyShip1 = new Texture("Client/core/assets/combatAssets/enemy1.png");
        enemyShip2 = new Texture("Client/core/assets/combatAssets/enemy_2.png");
        enemyShip3 = new Texture("Client/core/assets/combatAssets/enemy_3.png");
        missilleRight = new Texture("Client/core/assets/combatAssets/missille_out.png");
        missilleLeft = new Texture("Client/core/assets/combatAssets/missille_out.png");
        shield = new Texture("Client/core/assets/combatAssets/shield_2.png");
        explosion = new Texture("Client/core/assets/combatAssets/explosion1_0024.png");
        bullet = new Texture("Client/core/assets/combatAssets/bullet.png");
        crewMemberOne = new Texture(Gdx.files.internal("Client/core/assets/combatAssets/female_human.png"));
        crewMemberTwo = new Texture(Gdx.files.internal("Client/core/assets/combatAssets/MaleHuman-3.png"));
        crewMemberThree = new Texture(Gdx.files.internal("Client/core/assets/combatAssets/MaleHuman-3.png"));

        imageCrewMemberOne = new Image(crewMemberOne);
        imageCrewMemberTwo = new Image(crewMemberTwo);
        imageCrewMemberThree = new Image(crewMemberThree);
        imageCrewMemberOne.setBounds(30, 30, 30, 30);
        imageCrewMemberTwo.setBounds(30, 30, 30, 30);
        imageCrewMemberThree.setBounds(30, 30, 30, 30);
        imageCrewMemberOne.setPosition(XPlayerShip + Global.section2.getxPos(), YPlayerShip + Global.section2.getyPos());
        imageCrewMemberTwo.setPosition(XPlayerShip + Global.section4.getxPos(), YPlayerShip + Global.section4.getyPos());
        imageCrewMemberThree.setPosition(XPlayerShip + Global.section6.getxPos(), YPlayerShip + Global.section6.getyPos());

        listOfCrewImages.add(imageCrewMemberOne);
        listOfCrewImages.add(imageCrewMemberTwo);
        listOfCrewImages.add(imageCrewMemberThree);

        lebengegnerShip = new Label(String.valueOf(Global.currentShipGegner.getHp()), skin);
        lebenplayerShip = new Label(String.valueOf(Global.currentShipPlayer.getHp()), skin);
        lebengegnerShip = new Label(String.valueOf(Global.currentShipGegner.getHp()), skin);
        lebenplayerShip = new Label(String.valueOf(Global.currentShipPlayer.getHp()), skin);

        final Drawable engine_sym = new TextureRegionDrawable(new Texture("Client/core/assets/combatAssets/enginesSymbol.png"));
        final Drawable engine_red = new TextureRegionDrawable(new Texture("Client/core/assets/combatAssets/engineRed.png"));
        final Drawable cockpit_nat = new TextureRegionDrawable(new Texture("Client/core/assets/combatAssets/PilotingSymbol.png"));
        final Drawable cockpit_red = new TextureRegionDrawable(new Texture("Client/core/assets/combatAssets/PilotingRed.png"));

        final Drawable weapon_section = new TextureRegionDrawable(new Texture("Client/core/assets/combatAssets/weaponEnemy.png"));
        final Drawable weapon_section_red = new TextureRegionDrawable(new Texture("Client/core/assets/combatAssets/weapon_red.png"));
        final Drawable oxygen_sym = new TextureRegionDrawable(new Texture("Client/core/assets/combatAssets/oxygen_sym.png"));
        final Drawable oxygen_sym_red = new TextureRegionDrawable(new Texture("Client/core/assets/combatAssets/oxygen_red.png"));

        final Drawable medical_sym = new TextureRegionDrawable(new Texture("Client/core/assets/combatAssets/medical.png"));
        final Drawable medical_sym_red = new TextureRegionDrawable(new Texture("Client/core/assets/combatAssets/medical_red.png"));

        rocketLaunch = Gdx.audio.newSound(Gdx.files.internal("Client/core/assets/data/music/shoot.wav"));
        fuzeOffsetright = 570;
        fuzeOffsetLeft = 570;


        fuzeOffsetright = 570;
        fuzeOffsetLeft = 570;

        bullets = new ArrayList<>();
        bulletsEnemy = new ArrayList<>();

        o2 = new ImageButton(oxygen_sym);
        o2.setPosition(1560, 510);
        o2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isTargetSelected = true;


                switch (Global.currentGegner.getName()) {

                    case "gegner2":
                        findSectionByNameAndShip("Section4Gegner2", Global.currentShipGegner.getId(), true);
                        break;

                    case "gegner3":
                        selectedTarget = Global.section3Gegner3;
                        findSectionByNameAndShip("Section4Gegner3", Global.currentShipGegner.getId(), true);
                        break;

                    default:
                        break;

                }
                o2.getStyle().imageUp = oxygen_sym_red;
                engine.getStyle().imageUp = engine_sym;
                weaponSection.getStyle().imageUp = weapon_section;
                cockpit.getStyle().imageUp = cockpit_nat;
                healthPoint.getStyle().imageUp = medical_sym;
            }
        });
        healthPoint = new ImageButton(medical_sym);
        healthPoint.setPosition(1650, 500);
        healthPoint.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isTargetSelected = true;
                selectedTarget = Global.section2Gegner;

                healthPoint.getStyle().imageUp = medical_sym_red;
                engine.getStyle().imageUp = engine_sym;
                weaponSection.getStyle().imageUp = weapon_section;
                cockpit.getStyle().imageUp = cockpit_nat;
                o2.getStyle().imageUp = oxygen_sym;


            }
        });

        engine = new ImageButton(engine_sym);
        engine.setPosition(1560, 470);
        engine.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switch (Global.currentGegner.getName()) {

                    case "gegner1":
                        findSectionByNameAndShip("Section2Gegner1", Global.currentShipGegner.getId(), true);
                        break;

                    case "gegner2":
                        findSectionByNameAndShip("Section2Gegner2", Global.currentShipGegner.getId(), true);
                        break;

                    case "gegner3":
                        selectedTarget = Global.section3Gegner3;
                        findSectionByNameAndShip("Section2Gegner3", Global.currentShipGegner.getId(), true);
                        break;

                    default:
                        break;

                }
                isTargetSelected = true;
                engine.getStyle().imageUp = engine_red;
                weaponSection.getStyle().imageUp = weapon_section;
                cockpit.getStyle().imageUp = cockpit_nat;
                o2.getStyle().imageUp = oxygen_sym;
                healthPoint.getStyle().imageUp = medical_sym;


            }
        });


        weaponSection = new ImageButton(weapon_section);
        weaponSection.setPosition(1450, 500);
        weaponSection.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                System.out.println("Weapon");

                System.out.println(Global.currentGegner.getName());
                switch (Global.currentGegner.getName()) {

                    case "gegner1":
                        findSectionByNameAndShip("Section3Gegner1", Global.currentShipGegner.getId(), true);
                        break;

                    case "gegner2":
                        findSectionByNameAndShip("Section3Gegner2", Global.currentShipGegner.getId(), true);
                        break;

                    case "gegner3":
                        selectedTarget = Global.section3Gegner3;
                        findSectionByNameAndShip("Section3Gegner3", Global.currentShipGegner.getId(), true);
                        break;

                    default:
                        break;

                }

                isTargetSelected = true;
                weaponSection.getStyle().imageUp = weapon_section_red;
                engine.getStyle().imageUp = engine_sym;
                cockpit.getStyle().imageUp = cockpit_nat;
                o2.getStyle().imageUp = oxygen_sym;
                healthPoint.getStyle().imageUp = medical_sym;


            }
        });
        // isNewExpo = !sectionsToPlayerResponse.isEmpty();

        cockpit = new ImageButton(cockpit_nat);
        cockpit.setPosition(1560, 650);
        cockpit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switch (Global.currentGegner.getName()) {

                    case "gegner1":

                        findSectionByNameAndShip("Section1Gegner1", Global.currentShipGegner.getId(), true);
                        break;
                    case "gegner2":

                        findSectionByNameAndShip("Section1Gegner2", Global.currentShipGegner.getId(), true);
                        break;
                    case "gegner3":

                        findSectionByNameAndShip("Section1Gegner3", Global.currentShipGegner.getId(), true);
                        break;
                    default:
                        break;
                }
                isTargetSelected = true;
                cockpit.getStyle().imageUp = cockpit_red;
                engine.getStyle().imageUp = engine_sym;
                weaponSection.getStyle().imageUp = weapon_section;
                o2.getStyle().imageUp = oxygen_sym;
                healthPoint.getStyle().imageUp = medical_sym;


            }
        });

        Gdx.input.setInputProcessor(stage);


        if(Global.IS_SINGLE_PLAYER) {
        TextButton saveGameButton = new TextButton(" Save Game ", sgxSkin2, StyleNames.EMPHASISTEXTBUTTON);
        saveGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Global.IS_SINGLE_PLAYER = false;
                killTimer = true;
                mainClient.setScreen(new StationsMap(mainClient));
                LOG.info("Button CLicked");
                click.play();
                Gson gson = new Gson();
                Global.singlePlayerGame.setLastScreen("COMBAT");
                Global.singlePlayerGame.setPlayerShip(Global.currentShipPlayer);
                Global.singlePlayerGame.setShipGegner(Global.currentShipGegner);
                String requestBody = gson.toJson(Global.singlePlayerGame);
                final String url = Global.SERVER_URL + Global.PLAYER_SAVE_GAME + Global.currentPlayer.getName();
                Net.HttpRequest request = setupRequest(url, requestBody, Net.HttpMethods.POST);
                Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
                    public void handleHttpResponse(Net.HttpResponse httpResponse) {
                        final Dialog dialog = new Dialog("Save game", skin, "dialog");
                        int statusCode = httpResponse.getStatus().getStatusCode();
                        String responseJson = httpResponse.getResultAsString();
                        if (responseJson.equals("202 ACCEPTED")) {
                            LOG.info("Success save game " + statusCode);
                            saveMessageDialog(dialog, " Saving Game was Successful ");
                        } else {
                            LOG.info("Error saving game");
                            saveMessageDialog(dialog, " Saving Game was not Successful ");
                        }
                    }

                    public void failed(Throwable t) {

                    }

                    @Override
                    public void cancelled() {
                    }
                });
            }
        });
        saveGameButton.setPosition(1000, 200);
        stage.addActor(saveGameButton);
        }
        for (int i = 0; i < listOfCrewImages.size(); i++) {
            dragAndDrop(listOfCrewImages.get(i));
        }

        lebengegnerShip.setPosition(100, 20);
        lebenplayerShip.setPosition(20, 20);

        stage.addActor(lebenplayerShip);
        if (Global.currentShipGegner.getName().equals("Shipgegner2")) {
            stage.addActor(o2);
        }
        if (Global.currentShipGegner.getName().equals("Shipgegner3")) {
            stage.addActor(o2);
            stage.addActor(healthPoint);
        }
        stage.addActor(lebengegnerShip);
        stage.addActor(engine);
        stage.addActor(cockpit);
        stage.addActor(weaponSection);
        stage.addActor(weaponLabel);
        stage.addActor(imageCrewMemberOne);
        stage.addActor(imageCrewMemberTwo);
        stage.addActor(imageCrewMemberThree);


        Gdx.input.setInputProcessor(stage);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private void dragAndDrop(Image imageCrewMember) {
        imageCrewMember.addListener(new DragListener() {
            float crewX;
            float crewY;

            public void drag(InputEvent event, float x, float y, int pointer) {
                dragged = true;
                imageCrewMember.moveBy(x - imageCrewMember.getWidth() / 2, y - imageCrewMember.getHeight() / 2);
            }

            public void dragStart(InputEvent event, float x, float y, int pointer) {
                crewX = imageCrewMember.getX();
                crewY = imageCrewMember.getY();
                startSectionCrewMove = findSection(imageCrewMember);
                System.out.println("Starting with Section: " + startSectionCrewMove.get().getId());
            }

            public void dragStop(InputEvent event, float x, float y, int pointer) {
                //do something when texture is touched
                Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(tmp);
                Rectangle sectionOne = getRectOfTextures(Global.section1.getxPos(), Global.section1.getyPos());
                Rectangle sectionTwo = getRectOfTextures(Global.section2.getxPos(), Global.section2.getyPos());
                Rectangle sectionThree = getRectOfTextures(Global.section3.getxPos(), Global.section3.getyPos());
                Rectangle sectionFour = getRectOfTextures(Global.section4.getxPos(), Global.section4.getyPos());
                Rectangle sectionFive = getRectOfTextures(Global.section5.getxPos(), Global.section5.getyPos());
                Rectangle sectionSix = getRectOfTextures(Global.section6.getxPos(), Global.section6.getyPos());
                if (sectionOne.contains(tmp.x, tmp.y)) {
                    imageCrewMember.setPosition(XPlayerShip + Global.section1.getxPos(), YPlayerShip + Global.section1.getyPos());
                } else if (sectionTwo.contains(tmp.x, tmp.y)) {
                    imageCrewMember.setPosition(XPlayerShip + Global.section2.getxPos(), YPlayerShip + Global.section2.getyPos());
                } else if (sectionThree.contains(tmp.x, tmp.y)) {
                    imageCrewMember.setPosition(XPlayerShip + Global.section3.getxPos(), YPlayerShip + Global.section3.getyPos());
                } else if (sectionFour.contains(tmp.x, tmp.y)) {
                    imageCrewMember.setPosition(XPlayerShip + Global.section4.getxPos(), YPlayerShip + Global.section4.getyPos());
                } else if (sectionFive.contains(tmp.x, tmp.y)) {
                    imageCrewMember.setPosition(XPlayerShip + Global.section5.getxPos(), YPlayerShip + Global.section5.getyPos());
                } else if (sectionSix.contains(tmp.x, tmp.y)) {
                    imageCrewMember.setPosition(XPlayerShip + Global.section6.getxPos(), YPlayerShip + Global.section6.getyPos());
                } else {
                    imageCrewMember.setPosition(crewX, crewY);
                }
                endSectionCrewMove = findSection(imageCrewMember);
                System.out.println("Going to Section: " + startSectionCrewMove.get().getId());
                if (startSectionCrewMove.isPresent() && endSectionCrewMove.isPresent()) {
                    Optional<CrewMember> crewMember = Global.combatCrew.get(Global.currentShipPlayer.getId()).stream()
                            .filter(cm -> cm.getCurrentSection().equals(startSectionCrewMove.get()))
                            .findFirst();
                    if (crewMember.isPresent()) {
                        System.out.println("Crew Member has is: " + crewMember.get().getName() + " " + crewMember.get().getId());
                    } else {
                        System.out.println("I have not found a Crew Member");
                    }
                }

                //System.out.println("Hallo " + myCrew.get(0).getImg());
                // make Move Request c from start to end
                dragged = false;
            }
        });
    }

    /**
     * This method gives you the Position of the Ship to which something could belong
     *
     * @param x Coordinates
     * @param y Coordinates
     * @return Coordinates of the nearest Ship
     */
    private Pair getShipPos(float x, float y) {
        return (x < 600 ? new Pair((float) XPlayerShip, (float) YPlayerShip) : new Pair((float) XEnemyShip, (float) YEnemyPos));
    }

    /**
     * Recieves an Image and Returns the Section which is nearest to the imagge
     *
     * @param image which is where the user Klicks
     * @return the Section to which this belongs;
     */
    private Optional<Section> findSection(Image image) {
        return findSection(image.getImageX(), image.getImageY());
    }


    /**
     * Recieves an Image and Returns the Section which is nearest to the imagge
     *
     * @param x postion
     * @param y position
     * @return the Section to which this belongs;
     */
    private Optional<Section> findSection(float x, float y) {

        Pair shipsPos = getShipPos(x, y);

        float xAbs = x + shipsPos.getLeft();
        float yAbs = y + shipsPos.getRight();

        float delta = Float.MAX_VALUE;
        Optional<Section> nearest = Optional.empty();
        for (List<Section> xs :
                Global.combatSections.values()) {
            for (Section s :
                    xs) {

                float diff = distance(xAbs, yAbs, s.getxPos(), s.getyPos());
                if (diff < delta) {
                    nearest = Optional.of(s);
                    delta = diff;
                }
            }
        }
        System.out.println("You have Selected" + nearest.get().getId() + " " + nearest.get().getImg());
        return nearest;
    }

    /**
     * Use the Euclidiean Formular to calculate the Disance of 4 Points in a 2d Pane
     * https://en.wikipedia.org/wiki/Euclidean_distancek
     *
     * @param Ax 1st x
     * @param Ay 1st y
     * @param Bx 2nd x
     * @param By 2nd y
     * @return the distance as a float
     */
    private float distance(float Ax, float Ay, float Bx, float By) {
        return Float.parseFloat(String.valueOf(Math.sqrt(Math.pow((Ax - Bx), 2) + Math.pow((Ay - By), 2))));
    }

    private Rectangle getRectOfTextures(float redPinXPosition, float redPinYPosition) {
        return new Rectangle(XPlayerShip + redPinXPosition, YPlayerShip + redPinYPosition,
                redPinSectionOne.texture.getWidth(), redPinSectionOne.texture.getHeight());
    }

    private void logicOfFirePlayer() {
        for (Weapon w : selectedWeapons) {
            w.setObjectiv(selectedTarget);
        }

//        Global.updateweaponPlayerVariabel();
        shotValidation(selectedWeapons, Net.HttpMethods.POST);
    }
    //ShotValidation
    ////Para ponerlas en la armas
    //encontrar la section que fue seleccionada
    ////Quien es el gegner
    ////sectiones del gegner
    ////set de

    public boolean moveCrewMember(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        final String url = Global.SERVER_URL + Global.CREWMEMBER_UPDATE_ENDPOINT;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed moveCrewMember");
                }
                System.out.println("statusCode moveCrewMember: " + statusCode);
                String result = httpResponse.getResultAsString();
                if(result.equals("true")){
                    movingAllowed = true;
                }else{
                    movingAllowed = false;
                }
            }

            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }

            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
        return movingAllowed;
    }

    public void makeAShot(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        final String url = Global.SERVER_URL + Global.MAKE_SHOT_VALIDATION;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed playerMakeAShot");
                }
                System.out.println("statusCode playerMakeAShot: " + statusCode);
                String SectionsGegner = httpResponse.getResultAsString();
                Gson gson = new Gson();
                Section[] aiArray = gson.fromJson(SectionsGegner, Section[].class);
                sectionsGegner = Arrays.asList(aiArray);
                System.out.println("statusCode playerMakeAShot: " + statusCode);
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

    public void shotValidation(Object requestObject, String method) {
        final Gson gson = new Gson();
        final String requestJson = gson.toJson(requestObject);
        System.out.println("Firing Shots \n" + requestJson);
        final String url = Global.SERVER_URL + Global.SHOT_VALIDATION_VALIDATION;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed shotValidation");
                }
                System.out.println("statusCode PlayershotValidation: " + statusCode);
                validation = httpResponse.getResultAsString();
                System.out.println("PlayerShot: " + validation);
            }

            public void failed(Throwable t) {
                System.out.println("Request Failed shotValidation Completely");
            }

            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
    }


    // Called when the screen should render itself.
    @Override
    public void render(float delta) {

        GdxUtils.clearScreen();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);
        if (!Global.combatSections.get(Global.currentShipPlayer.getId()).isEmpty()) {
            labelsection1.setText("\n Usable: " + Global.combatSections.get(Global.currentShipPlayer.getId()).get(0).getUsable() + "\n Oxigen: " + Global.combatSections.get(Global.currentShipPlayer.getId()).get(0).getOxygen());
            labelsection1.setPosition(Global.combatSections.get(Global.currentShipPlayer.getId()).get(0).getxPos(), Global.combatSections.get(Global.currentShipPlayer.getId()).get(0).getyPos());
            stage.addActor(labelsection1);
        }

        //Global.combatSections.get(Global.currentShipPlayer.getId()).get(1);
        //Global.combatSections.get(Global.currentShipPlayer.getId()).get(2);
        //Global.combatSections.get(Global.currentShipPlayer.getId()).get(3);
        //Global.combatSections.get(Global.currentShipPlayer.getId()).get(4);
        //Global.combatSections.get(Global.currentShipPlayer.getId()).get(5);

        if (Global.combatWeapons.size() >= 1) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
                aktiveWeapon++;
                aktiveWeapon = aktiveWeapon % (Global.combatWeapons.get(Global.currentShipPlayer.getId()).size() + 1);  // Add one more because index is +1
                if (aktiveWeapon == 0) {
                    selectedWeapons = Global.combatWeapons.get(Global.currentShipPlayer.getId());
                    weaponLabel.setText(weaponText[0]);
                } else {
                    selectedWeapons = List.of(Global.combatWeapons.get(Global.currentShipPlayer.getId()).get(aktiveWeapon - 1)); // Weapons start at 0. Index at 1
                    weaponLabel.setText(weaponText[1] + selectedWeapons.get(0).getName());
                }
            }


            stage.getBatch().begin();
            stage.getBatch().draw(background, 0, 0, BaseScreen.WIDTH, BaseScreen.HEIGHT);
            // Render the Ship of the current Player
            stage.getBatch().draw(playerShip, XPlayerShip, YPlayerShip, WidthPlayerShip, HeightPlayerShip);
            if (dragged) {
                stage.getBatch().draw(redPinSectionOne.texture, XPlayerShip + Global.section1.getxPos(), YPlayerShip + Global.section1.getyPos());
                stage.getBatch().draw(redPinSectionTwo.texture, XPlayerShip + Global.section2.getxPos(), YPlayerShip + Global.section2.getyPos());
                stage.getBatch().draw(redPinSectionThree.texture, XPlayerShip + Global.section3.getxPos(), YPlayerShip + Global.section3.getyPos());
                stage.getBatch().draw(redPinSectionFour.texture, XPlayerShip + Global.section4.getxPos(), YPlayerShip + Global.section4.getyPos());
                stage.getBatch().draw(redPinSectionFive.texture, XPlayerShip + Global.section5.getxPos(), YPlayerShip + Global.section5.getyPos());
                stage.getBatch().draw(redPinSectionSix.texture, XPlayerShip + Global.section6.getxPos(), YPlayerShip + Global.section6.getyPos());
            }

            selectedWeapons = Global.combatWeapons.get(Global.currentShipPlayer.getId());


            // Spaawn Enemy Ship
            if (Global.currentShipGegner != null) {
                if (Global.currentStop == Global.planet2)
                    stage.getBatch().draw(enemyShip1, XEnemyShip, YEnemyPos, WIDTHGegner, HEIGHTGegner);
                else if (Global.currentStop == Global.planet3)
                    stage.getBatch().draw(enemyShip2, XEnemyShip, YEnemyPos, WIDTHGegner, HEIGHTGegner);
                else stage.getBatch().draw(enemyShip3, XEnemyShip, YEnemyPos, WIDTHGegner, HEIGHTGegner);

                // FIXME Brutal Online

            }
            stage.getBatch().draw(missilleRight, disappearRight, 422, 400, 50);
            stage.getBatch().draw(missilleLeft, disappearLeft, 825, 400, 50);
            //Gegner
            //Shot
            if (!validationGegner.isEmpty() && validationGegner.equals("Fire Accepted")) {
                System.out.println("::Gegner Shot now");
                bulletsEnemy.add(new Bullet(1600, 743));
                bulletsEnemy.add(new Bullet(1600, 544));
                rocketLaunch.play();
                canFireGegner = true;
            } else if (!validationGegner.isEmpty() && validationGegner.equals("Section unusable")) {
                System.out.println(":::::Section unusable Gegner");
                validationGegner = "";
            } else if (Global.currentShipPlayer.getHp() <= 0) {
                System.out.println(":::Defeat");
                validationGegner = "";

                mainClient.setScreen(new MenuScreen(game));
            }
            if (canFireGegner) {
                makeAShotGegner(Global.currentShipGegner, Net.HttpMethods.POST);

                canFireGegner = false;
                validationGegner = "";
            }
            //on weapon system Explosion
            if ((!sectionsPlayer.isEmpty() && sectionsPlayer.get(1).getUsable() == false) || isNewExpo) {
                isNewExpo = true;
                stage.getBatch().draw(explosion, 555, 520, 100, 100);
            }
            if ((!sectionsPlayer.isEmpty() && sectionsPlayer.get(1).getUsable() == false) || isNewExpo2) {
                isNewExpo2 = true;
                stage.getBatch().draw(explosion, 700, 520, 100, 100);
            }
            if ((!sectionsPlayer.isEmpty() && sectionsPlayer.get(1).getUsable() == false) || isNewExpo3) {
                isNewExpo3 = true;
                stage.getBatch().draw(explosion, 710, 670, 100, 100);
            }

            if (!sectionsPlayer.isEmpty()) {
                Global.sectionsPlayerList = sectionsPlayer;
//                Global.updateVariableSectionShipPlayer(); WTF FIXME is this is important
                Global.currentShipPlayer = sectionsPlayer.get(0).getShip();
                Global.actualizierungSectionInWeapons();
                List<Section> sizeO = new ArrayList<>();
                sectionsPlayer = sizeO;
            }
            /////
            ////PLAYER SHOT
            //////
            if (!validation.isEmpty() && validation.equals("Fire Accepted")) {
                System.out.println(":::::Player Shot");
                bullets.add(new Bullet(590, 843));
                bullets.add(new Bullet(590, 444));
                canFire = true;
            } else if (!validation.isEmpty() && validation.equals("Section unusable")) {
                System.out.println("::::Section not usable Player");
                validation = "";
            } else if (Global.currentShipGegner.getHp() <= 0) {
                System.out.println(":::Defeat gegner");
                validation = "";
                mainClient.setScreen(new StationsMap(game));
            }
            if (canFire) {
                makeAShot(Global.combatWeapons.get(Global.currentShipPlayer.getId()), Net.HttpMethods.POST);
                isFired = true;
                canFire = false;
                validation = "";
            }
            //Update Server Response
            if (!sectionsGegner.isEmpty()) {
                Section sectionResponse = sectionsGegner.get(0);
                Ship shiptoUpdate = sectionResponse.getShip();
                Global.currentShipGegner = shiptoUpdate;
                switch (sectionResponse.getShip().getName()) {
                    case "Shipgegner1":
                        Global.sectionsgegner1 = sectionsGegner;
                        Global.updateVariblesSectionsGegner1();
                        Global.shipGegner1 = shiptoUpdate;
                        Global.currentShipGegner = shiptoUpdate;
                        Global.aktualizierenweaponListUniverse2();
                        break;
                    case "Shipgegner2":
                        Global.sectionsgegner2 = sectionsGegner;
                        Global.updateVariblesSectionsGegner2();
                        Global.shipGegner2 = shiptoUpdate;
                        Global.currentShipGegner = shiptoUpdate;
                        Global.aktualizierenweaponListUniverse2();
                        break;
                    case "Shipgegner3":
                        Global.sectionsgegner3 = sectionsGegner;
                        Global.updateVariblesSectionsGegner3();
                        Global.shipGegner3 = shiptoUpdate;
                        Global.currentShipGegner = shiptoUpdate;
                        Global.aktualizierenweaponListUniverse2();
                        break;
                    case "Shipgegner4":
                        Global.sectionsgegner4 = sectionsGegner;
                        Global.updateVariblesSectionsGegner4();
                        Global.shipGegner4 = shiptoUpdate;
                        Global.currentShipGegner = shiptoUpdate;
                        Global.aktualizierenweaponListUniverse2();
                        break;
                    case "Shipgegner5":
                        Global.sectionsgegner5 = sectionsGegner;
                        Global.updateVariblesSectionsGegner5();
                        Global.shipGegner5 = shiptoUpdate;
                        Global.currentShipGegner = shiptoUpdate;
                        Global.aktualizierenweaponListUniverse2();
                        break;
                    case "Shipgegner6":
                        Global.sectionsgegner6 = sectionsGegner;
                        Global.updateVariblesSectionsGegner6();
                        Global.shipGegner6 = shiptoUpdate;
                        Global.currentShipGegner = shiptoUpdate;
                        Global.aktualizierenweaponListUniverse2();
                        break;
                }
                Global.updateShipsListgegneru2();
                List<Section> sizeO = new ArrayList<>();
                sectionsGegner = sizeO;
                //GEGNER FIRE
            }
            lebengegnerShip.setText(String.valueOf(Global.currentShipGegner.getHp()));
            lebenplayerShip.setText(String.valueOf(Global.currentShipPlayer.getHp()));
            //A
            //Logic
            //Create and launch missiles

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                randomNumber = (int) ((Math.random() * (5)) + 0);
                //Set Target->Section of Player and gegner Weapons
                //logicOfFireGegner(randomNumber);
                //Set Target->Section of  gegner and User Weapons
                logicOfFirePlayer();
                counterCockpit++;
            }


            //shield for player
            if (Global.currentShipPlayer.getShield() > 0) stage.getBatch().draw(shield, 70, 150, 1100, 1000);
            //shield for enemy
            if (Global.currentShipGegner.getShield() > 0) stage.getBatch().draw(shield, 1120, 150, 900, 1000);

            //explosion on enemy's engine
            if (counterEngine >= 3 && !isEnemyShield) {
                stage.getBatch().draw(explosion, 1515, 422, 100, 100);
            }

            //explosion on enemy's weapon
            if (counterWeapon >= 3 && !isEnemyShield) {
                stage.getBatch().draw(explosion, 1450, 500, 100, 100);
            }
            //explosion on enemy's cockpit
            if (counterCockpit >= 2 && !isEnemyShield) {
                stage.getBatch().draw(explosion, 1515, 690, 100, 100);
            }
            //update bullets
            ArrayList<Bullet> bulletToRemove = new ArrayList<>();
            for (Bullet bullet : bullets) {
                bullet.update(delta);
                if (bullet.remove) {
                    bulletToRemove.add(bullet);
                }
            }
            int p = Global.combatSections.get(Global.currentShipPlayer.getId()).get(0).getPowerCurrent();
            //int u = Global.combatWeapons.get(Global.currentShipPlayer.getId()).get(0).getDamage();
            //int i = Global.combatCrew.get(Global.currentShipPlayer.getId()).get(0).getHealth();

            ArrayList<Bullet> bulletGegnerToRemove = new ArrayList<>();
            for (Bullet bullet : bulletsEnemy) {
                bullet.updateTo(delta);
                if (bullet.remove) {
                    bulletGegnerToRemove.add(bullet);
                }
            }

            bulletToRemove.removeAll(bulletToRemove);
            stage.getBatch().end();
            mainClient.getBatch().begin();
            for (Bullet bullet : bullets) {
                bullet.render(mainClient.getBatch());
            }

            bulletToRemove.removeAll(bulletGegnerToRemove);
            stage.getBatch().end();
            mainClient.getBatch().begin();
            for (Bullet bullet : bulletsEnemy) {
                bullet.render(mainClient.getBatch());
            }
            mainClient.getBatch().end();
        }

        stage.act();
        stage.draw();
    }

    public void shotValidationGegner(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        final String url = Global.SERVER_URL + Global.SHOT_VALIDATION_VALIDATION;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed shotValidation");
                }
                System.out.println("statusCode GegnerShot: " + statusCode);
                validationGegner = httpResponse.getResultAsString();
                System.out.println("GegnerShot: " + validationGegner);
            }

            public void failed(Throwable t) {
                System.out.println("Request Failed shotValidation Completely");
            }

            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
    }

    public void makeAShotGegner(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        final String url = Global.SERVER_URL + Global.MAKE_SHOT_VALIDATION;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed GegnermakeShot");
                }
                System.out.println("statusCode GegnermakeShot: " + statusCode);
                String SectionsGegner = httpResponse.getResultAsString();
                Gson gson = new Gson();
                Section[] sectiongegnerArray = gson.fromJson(SectionsGegner, Section[].class);
                sectionsPlayer = Arrays.asList(sectiongegnerArray);
                System.out.println("statusCode makeAShot: " + statusCode);
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

    private void logicOfFireGegner(int sectionNumber) {
        List<Weapon> weaponList = new ArrayList<>();
        if (Global.currentShipGegner != null) {
            if (Global.currentUniverse.getName().equals("Normal" + Global.currentPlayer.getName())) {
                switch (Global.currentShipGegner.getName()) {
                    case "Shipgegner1":
                        for (Weapon w :
                                Global.weaponListGegner1) {
                            if (w.getSection().getShip().getId() == Global.currentShipGegner.getId()) {
                                //Weapons gegner set Weapons Section of Player
                                if (sectionNumber == 2) {
                                    w.setObjectiv(Global.section2);
                                    System.out.println("::::::::::::::::::::.WEAPONS FOR PLAYER UNUSABLE:::::::::::::::");
                                    weaponList.add(w);
                                } else if (sectionNumber == 4) {
                                    w.setObjectiv(Global.section1);
                                    weaponList.add(w);
                                } else {
                                    w.setObjectiv(Global.section3);
                                    weaponList.add(w);
                                }
                            }
                        }
                        shotValidationGegner(Global.weaponListGegner1, Net.HttpMethods.POST);
                        break;
                    case "Shipgegner2":
                        for (Weapon w :
                                Global.weaponListGegner2) {
                            if (w.getSection().getShip().getId() == Global.currentShipGegner.getId()) {
                                //Weapons gegner set Weapons Section of Player
                                if (sectionNumber == 2) {
                                    w.setObjectiv(Global.section2);
                                    System.out.println("::::::::::::::::::::.WEAPONS FOR PLAYER UNUSABLE:::::::::::::::");
                                    weaponList.add(w);
                                } else if (sectionNumber == 4) {
                                    w.setObjectiv(Global.section1);
                                    weaponList.add(w);
                                } else {
                                    w.setObjectiv(Global.section3);
                                    weaponList.add(w);
                                }
                            }
                        }
                        shotValidationGegner(Global.weaponListGegner2, Net.HttpMethods.POST);
                        break;
                    case "Shipgegner3":
                        for (Weapon w :
                                Global.weaponListGegner3) {
                            if (w.getSection().getShip().getId() == Global.currentShipGegner.getId()) {
                                //Weapons gegner set Weapons Section of Player
                                if (sectionNumber == 2) {
                                    w.setObjectiv(Global.section2);
                                    System.out.println("::::::::::::::::::::.WEAPONS FOR PLAYER UNUSABLE:::::::::::::::");
                                    weaponList.add(w);
                                } else if (sectionNumber == 4) {
                                    w.setObjectiv(Global.section1);
                                    weaponList.add(w);
                                } else {
                                    w.setObjectiv(Global.section3);
                                    weaponList.add(w);
                                }
                            }
                        }
                        shotValidationGegner(Global.weaponListGegner3, Net.HttpMethods.POST);
                        break;
                    case "Shipgegner4":
                        for (Weapon w :
                                Global.weaponListGegner4) {
                            if (w.getSection().getShip().getId() == Global.currentShipGegner.getId()) {
                                //Weapons gegner set Weapons Section of Player
                                if (sectionNumber == 2) {
                                    w.setObjectiv(Global.section2);
                                    System.out.println("::::::::::::::::::::.WEAPONS FOR PLAYER UNUSABLE:::::::::::::::");
                                    weaponList.add(w);
                                } else if (sectionNumber == 4) {
                                    w.setObjectiv(Global.section1);
                                    weaponList.add(w);
                                } else {
                                    w.setObjectiv(Global.section3);
                                    weaponList.add(w);
                                }
                            }
                        }
                        shotValidationGegner(Global.weaponListGegner4, Net.HttpMethods.POST);
                        break;
                    case "Shipgegner5":
                        for (Weapon w :
                                Global.weaponListGegner5) {
                            if (w.getSection().getShip().getId() == Global.currentShipGegner.getId()) {
                                //Weapons gegner set Weapons Section of Player
                                if (sectionNumber == 2) {
                                    w.setObjectiv(Global.section2);
                                    System.out.println("::::::::::::::::::::.WEAPONS FOR PLAYER UNUSABLE:::::::::::::::");
                                    weaponList.add(w);
                                } else if (sectionNumber == 4) {
                                    w.setObjectiv(Global.section1);
                                    weaponList.add(w);
                                } else {
                                    w.setObjectiv(Global.section3);
                                    weaponList.add(w);
                                }
                            }
                        }
                        shotValidationGegner(Global.weaponListGegner5, Net.HttpMethods.POST);
                        break;
                    case "Shipgegner6":
                        for (Weapon w :
                                Global.weaponListGegner6) {
                            if (w.getSection().getShip().getId() == Global.currentShipGegner.getId()) {
                                //Weapons gegner set Weapons Section of Player
                                if (sectionNumber == 2) {
                                    w.setObjectiv(Global.section2);
                                    System.out.println("::::::::::::::::::::.WEAPONS FOR PLAYER UNUSABLE:::::::::::::::");
                                    weaponList.add(w);
                                } else if (sectionNumber == 4) {
                                    w.setObjectiv(Global.section1);
                                    weaponList.add(w);
                                } else {
                                    w.setObjectiv(Global.section3);
                                    weaponList.add(w);
                                }
                            }
                        }
                        shotValidationGegner(Global.weaponListGegner6, Net.HttpMethods.POST);
                        break;
                }

                Global.updateweaponVariabelUniverse2();
                Global.actualiziertweaponListGegner1();
                Global.actualiziertweaponListGegner2();
                Global.actualiziertweaponListGegner3();
                Global.actualiziertweaponListGegner4();
                Global.actualiziertweaponListGegner5();
                Global.actualiziertweaponListGegner6();
            }
        }

    }

    private void scheduleLobby() {
        Timer schedule = new Timer();
        schedule.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (killTimer) {
                    schedule.cancel();
                    schedule.purge();
                    LOG.info("Timer killed");
                } else {
                    LOG.info("Reparation");
                    LOG.info(Global.currentShipPlayer.getId().toString());
                    RequestUtils.sectionsByShip(Global.currentShipPlayer);
                }
            }

        }, 1000, 5000);
    }

    // Called when the Application is resized.
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    // Called when the Application is paused, usually when it's not active or visible on-screen.
    @Override
    public void pause() {
    }

    // Called when the Application is resumed from a paused state, usually when it regains focus.
    @Override
    public void resume() {
    }

    // Called when this screen is no longer the current screen for a Game.
    //called when the Screen loses focus
    @Override
    public void hide() {
        dispose();
    }

    // Called when the Application is destroyed.
    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
        rocketLaunch.dispose();
        stage.dispose();
    }

    private void saveMessageDialog(Dialog dialog, String action) {
        dialog.text(action);
        dialog.button("OK", false);
        dialog.key(Input.Keys.ENTER, true);
        dialog.key(Input.Keys.ESCAPE, false);
        click.play();
        dialog.show(stage);
    }
}
