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
import com.badlogic.gdx.scenes.scene2d.utils.*;
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
    private final Label weaponLabel, currentWeaponLabel,  sectionLabel;
    private final String[] weaponText = {"All Weapons", "You have selected Weapon: "};
    private final AssetManager assetManager;
    private final MainClient universeMap;
    private final MainClient mainClient;
    private final int counterEngine = 0;
    private final int counterWeapon = 0;
    private final OrthographicCamera camera;
    private final List<Weapon> weaponsToFire = new ArrayList<>();
    private final int shotDelta = 400;
    boolean isNewExpo, isNewExpo2, isNewExpo3;
    boolean isFired = false;
    boolean canFire = false;
    boolean canFireGegner = false;
    Texture bullet, shield;
    float x = 0;
    Sound rocketLaunch;
    ArrayList<Bullet> bullets;
    ArrayList<Bullet> bulletsEnemy;
    String validationGegner = "";
    List<Section> sectionsGegner = Global.combatSections.get(Global.currentShipGegner.getId());
    List<Section> sectionsPlayer = Global.combatSections.get(Global.currentShipPlayer.getId());
    Label lebengegnerShip;
    Label lebenplayerShip;
    private Viewport viewport;
    private Stage stage;
    private Skin sgxSkin, sgxSkin2, skin;
    private Sound click;
    private SpriteBatch batch;
    private Texture playerShip;
    private Texture enemyShip1, enemyShip2, enemyShip3;
    private Texture background, laser;
    private Texture crewMemberOne, crewMemberTwo, crewMemberThree;
    //CrewMember break
    private Texture hourglass;
    private boolean hourglass1, hourglass2, hourglass3, hourglass4, hourglass5, hourglass6;
    private float hourX1, hourY1;

    private Texture shieldSystem, weaponsSystem, driveSystem, energyWeaponsPanel;
    private Texture energy, redPin; // shieldSystem, weaponsSystem, driveSystem, energyWeaponsPanel,
    private Image shieldIconForEnergyPanel, weaponsIconForEnergyPanel, driveIconForEnergyPanel;
    //private Image imageCrewMemberOne, imageCrewMemberTwo, imageCrewMemberThree;
    private List<Image> listOfCrewImages;
    private List<CrewMember> myCrew() {
        if (!Global.combatCrew.containsKey(Global.currentShipPlayer.getId())) {
            return new ArrayList<>();
        }
        return Global.combatCrew.get(Global.currentShipPlayer.getId());
    }
    private Label breakCrewMember;
    private String breakinfo;
    private Boolean killTimer = false;
    private TextButton enableShield, enableEnemyShield;
    private Texture explosion, missille, weaponSystem;
    private boolean isExploied, isLaserActivated;
    private boolean isTargetSelected, isTargetEngine, isTargetCockpit, isTargetWeapon;
    private Skin skinButton;
    private boolean isShieldEnabled, isEnemyShield, isTargetO2, isTargetMedical;
    private ImageButton engine, weaponSection, cockpit, o2, healthPoint;
    private int counterCockpit = 0;
    private int randomNumber;
    private int aktiveWeapon = 0;
    private List<Weapon> selectedWeapons = Global.combatWeapons.get(Global.currentShipPlayer.getId());
    private boolean dragged = false;
    private Section selectedTarget;
    //    private final List<Weapon> weaponsToFire = new ArrayList<>();
    //private final int shotDelta = 400;
    private int yWeaponPos = 700;
    private TextButton liamButton;
    private boolean isRound;

    public CombatScreen(MainClient mainClient) {
        super(mainClient);
        this.universeMap = mainClient;
        this.mainClient = mainClient;
        assetManager = universeMap.getAssetManager();
        camera = new OrthographicCamera();



        int row_height = Gdx.graphics.getWidth() / 12;
        int col_width = Gdx.graphics.getWidth() / 12;
        Label.LabelStyle label1Style = new Label.LabelStyle();

        BitmapFont myFont = new BitmapFont(Gdx.files.internal("bitmap/amble.fnt"));
        label1Style.font = myFont;
        label1Style.fontColor = Color.RED;


        hourglass1 = false;
        hourglass2 = false;
        hourglass3 = false;
        hourglass4 = false;
        hourglass5 = false;
        hourglass6 = false;

        weaponLabel = new Label(weaponText[0], label1Style);
        weaponLabel.setSize(Gdx.graphics.getWidth(), 20);
        weaponLabel.setPosition(0, BaseScreen.HEIGHT-500);

        currentWeaponLabel = new Label(weaponText[0], label1Style);
        currentWeaponLabel.setSize(Gdx.graphics.getWidth(), 20);
        currentWeaponLabel.setPosition(0, BaseScreen.HEIGHT-100);

        sectionLabel = new Label(getSectionStats(Global.combatSections.get(Global.currentShipPlayer.getId())), label1Style);
        sectionLabel.setSize(Gdx.graphics.getWidth(), row_height);
        sectionLabel.setPosition(BaseScreen.WIDTH-800, 100);
        this.breakinfo = "All crewMember in action";

    }

    private void liamButtonFuntion() {
        liamButton = new TextButton("End Round", sgxSkin2, StyleNames.EMPHASISTEXTBUTTON);
        liamButton.setPosition(850, 50);
        stage.addActor(liamButton);
        liamButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (Global.combatActors.isEmpty()) {
                    liamButton.setText("Connecting. Try Again");
                } else {
                    de.spaceStudio.server.model.Actor actor1 = Global.combatActors.get(Global.currentPlayer.getId());
                    if ((actor1.getState().getFightState().equals(FightState.PLAYING))) {
                        actor1.getState().setFightState(FightState.WAITING_FOR_TURN);
                        if (Global.IS_SINGLE_PLAYER) {
                            liamButton.setText("Waiting for AI");
                            isRound = true;
                        } else {
                            liamButton.setText("Waiting for other Player");
                        }
                    } else {
                        actor1.getState().setFightState(FightState.PLAYING);
                        liamButton.setText("End Round");
                        isRound = false;
                    }
                    RequestUtils.setActor(actor1);
                }

            }
        });
    }

    private void findSectionByNameAndShip(String name, int id, Boolean currentTarget) {
        Optional<Section> result = Optional.empty();

        if (!Global.combatSections.containsKey(id)) {
            System.out.println("Stop no id");
        }
        for (Section s :
                Global.combatSections.get(id)) {
            if (s.getImg().equals(name)) {
                result = Optional.of(s);
                if (currentTarget) {
                    selectedTarget = s;
                    break;
                }
            }

        }
        if (result.isEmpty() ) {
            System.out.println("Stop no Result");
        }
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
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        skinButton = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        background = new Texture("Client/core/assets/combatAssets/CombatBG.jpg");
        playerShip = new Texture("Client/core/assets/data/ships/blueships1_section.png");
        enemyShip1 = new Texture("Client/core/assets/combatAssets/enemy1.png");
        enemyShip2 = new Texture("Client/core/assets/combatAssets/enemy_2.png");
        enemyShip3 = new Texture("Client/core/assets/combatAssets/enemy_3.png");
        missille = new Texture("Client/core/assets/combatAssets/missille_out.png");
        shield = new Texture("Client/core/assets/combatAssets/shield_2.png");
        explosion = new Texture("Client/core/assets/combatAssets/explosion1_0024.png");
        bullet = new Texture("Client/core/assets/combatAssets/bullet.png");
        laser = new Texture("Client/core/assets/combatAssets/laser.jpg");
        this.hourglass = new Texture("Client/core/assets/combatAssets/hourglass.png");
        shieldSystem = new Texture(Gdx.files.internal("Client/core/assets/data/ships/shield.png"));
        driveSystem = new Texture(Gdx.files.internal("Client/core/assets/data/ships/drive.png"));
        weaponsSystem = new Texture(Gdx.files.internal("Client/core/assets/data/ships/weapons.png"));
        redPin = new Texture(Gdx.files.internal("Client/core/assets/combatAssets/pin.png"));
        for (CrewMember crewMember : myCrew()) {
            listOfCrewImages.add(new Image(new Texture(Gdx.files.internal("Client/core/assets/combatAssets/" + crewMember.getImg()))));
        }
        for(int i = 0; i < listOfCrewImages.size(); i++){
            listOfCrewImages.get(i).setBounds(30,30,30,30);
            listOfCrewImages.get(i).setPosition(XPlayerShip + myCrew().get(i).getCurrentSection().getxPos(),
                    YPlayerShip + myCrew().get(i).getCurrentSection().getyPos());
            listOfCrewImages.get(i).setName(myCrew().get(i).getName());
            dragAndDrop(listOfCrewImages.get(i));
        }

        energyWeaponsPanel = new Texture(Gdx.files.internal("Client/core/assets/combatAssets/energyWeaponsPanel.png"));
        energy = new Texture(Gdx.files.internal("Client/core/assets/combatAssets/Energy.png"));
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

        liamButtonFuntion();

        bullets = new ArrayList<>();
        bulletsEnemy = new ArrayList<>();

        shieldIconForEnergyPanel = new Image(new Texture("Client/core/assets/combatAssets/2.png"));
        driveIconForEnergyPanel = new Image(new Texture("Client/core/assets/combatAssets/1.png"));
        weaponsIconForEnergyPanel = new Image(new Texture("Client/core/assets/combatAssets/3.png"));
        shieldIconForEnergyPanel.setPosition(185, 12);
        shieldIconForEnergyPanel.setPosition(185,12);
        shieldIconForEnergyPanel.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //hier
                if (Global.currentShipPlayer.getPower() - Global.sumCurrentPower(Global.combatSections.get(Global.currentShipPlayer.getId())) > 0) {
                    Global.combatSections.get(Global.currentShipPlayer.getId()).get(1).incrementPowerCurrent();
                    RequestUtils.updateEnergie(Global.combatSections.get(Global.currentShipPlayer.getId()));
                }
            }
        });
        shieldIconForEnergyPanel.addListener(new ClickListener(Input.Buttons.RIGHT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Global.combatSections.get(Global.currentShipPlayer.getId()).get(1).getPowerCurrent() > 0) {
                    Global.combatSections.get(Global.currentShipPlayer.getId()).get(1).decrementPowerCurrent();
                    RequestUtils.updateEnergie(Global.combatSections.get(Global.currentShipPlayer.getId()));
                }
            }
        });

        weaponsIconForEnergyPanel.setPosition(495, 16);
        weaponsIconForEnergyPanel.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Global.currentShipPlayer.getPower() - Global.sumCurrentPower(Global.combatSections.get(Global.currentShipPlayer.getId())) > 0) {
                    Global.combatSections.get(Global.currentShipPlayer.getId()).get(3).incrementPowerCurrent();
                    RequestUtils.updateEnergie(Global.combatSections.get(Global.currentShipPlayer.getId()));
                }
            }
        });
        weaponsIconForEnergyPanel.addListener(new ClickListener(Input.Buttons.RIGHT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Global.combatSections.get(Global.currentShipPlayer.getId()).get(3).getPowerCurrent() > 0) {
                    Global.combatSections.get(Global.currentShipPlayer.getId()).get(3).decrementPowerCurrent();
                    RequestUtils.updateEnergie(Global.combatSections.get(Global.currentShipPlayer.getId()));
                }
            }
        });

        driveIconForEnergyPanel.setPosition(345, 12);
        driveIconForEnergyPanel.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Global.currentShipPlayer.getPower() - Global.sumCurrentPower(Global.combatSections.get(Global.currentShipPlayer.getId())) > 0) {
                    Global.combatSections.get(Global.currentShipPlayer.getId()).get(5).incrementPowerCurrent();
                    RequestUtils.updateEnergie(Global.combatSections.get(Global.currentShipPlayer.getId()));
                }
            }
        });
        driveIconForEnergyPanel.addListener(new ClickListener(Input.Buttons.RIGHT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Global.combatSections.get(Global.currentShipPlayer.getId()).get(5).getPowerCurrent() > 0) {
                    Global.combatSections.get(Global.currentShipPlayer.getId()).get(5).decrementPowerCurrent();
                    RequestUtils.updateEnergie(Global.combatSections.get(Global.currentShipPlayer.getId()));
                }
            }
        });

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
                    case "gegner4":
                        selectedTarget = Global.section3Gegner4;
                        findSectionByNameAndShip("Section4Gegner4", Global.currentShipGegner.getId(), true);
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
                        findSectionByNameAndShip("Section2Gegner3", Global.currentShipGegner.getId(), true);
                        break;
                    case "gegner4":
                        findSectionByNameAndShip("Section2Gegner4", Global.currentShipGegner.getId(), true);
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
                    case "gegner4":
                        selectedTarget = Global.section3Gegner3;
                        findSectionByNameAndShip("Section3Gegner4", Global.currentShipGegner.getId(), true);
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
                    case "gegner4":

                        findSectionByNameAndShip("Section1Gegner4", Global.currentShipGegner.getId(), true);
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
        lebengegnerShip.setPosition(100, 20);
        lebenplayerShip.setPosition(20, 20);

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));


        int row_height = Gdx.graphics.getWidth() / 12;
        int col_width = Gdx.graphics.getWidth() / 12;
        Label.LabelStyle label1Style = new Label.LabelStyle();

        BitmapFont myFont = new BitmapFont(Gdx.files.internal("bitmap/amble.fnt"));
        label1Style.font = myFont;
        label1Style.fontColor = Color.RED;


        stage.addActor(lebenplayerShip);
        if (Global.currentShipGegner.getName().equals("Shipgegner2")) {
            stage.addActor(o2);
        }
        if (Global.currentShipGegner.getName().equals("Shipgegner3")) {
            stage.addActor(o2);
            stage.addActor(healthPoint);
        }
        stage.addActor(sectionLabel);
        stage.addActor(lebengegnerShip);
        stage.addActor(engine);
        stage.addActor(cockpit);
        stage.addActor(weaponSection);
        stage.addActor(weaponsIconForEnergyPanel);
        stage.addActor(driveIconForEnergyPanel);
        stage.addActor(shieldIconForEnergyPanel);
        stage.addActor(weaponLabel);
        stage.addActor(currentWeaponLabel);
        for (Image listOfCrewImage : listOfCrewImages) {
            stage.addActor(listOfCrewImage);
        }

        breakCrewMember = new Label(breakinfo, skin);
        breakCrewMember.setPosition(200,900);
        stage.addActor(breakCrewMember);

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Receives an Image and changes the section of the Crew Member
     *
     * @param imageCrewMember Image of Crew Member to move
     */
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
                CrewMember draggedCrewMember = getDraggedCrewMember(imageCrewMember);
                List<Section> sections = Global.combatSections.get(Global.currentShipPlayer.getId());

                if(draggedCrewMember == null){
                    System.out.println("draggedCrewMember is null");
                } else if (sectionOne.contains(tmp.x, tmp.y)) {
                    draggedCrewMember.setCurrentSection(sections.get(0));
                    moveCrewMember(draggedCrewMember,imageCrewMember,Net.HttpMethods.PUT);
                    setHourX1(tmp.x);
                    setHourY1(tmp.y);

                } else if (sectionTwo.contains(tmp.x, tmp.y)) {
                    draggedCrewMember.setCurrentSection(sections.get(1));
                    moveCrewMember(draggedCrewMember,imageCrewMember,Net.HttpMethods.PUT);
                    setHourX1(tmp.x);
                    setHourY1(tmp.y);

                } else if (sectionThree.contains(tmp.x, tmp.y)) {
                    draggedCrewMember.setCurrentSection(sections.get(2));
                    moveCrewMember(draggedCrewMember,imageCrewMember,Net.HttpMethods.PUT);
                    setHourX1(tmp.x);
                    setHourY1(tmp.y);

                } else if (sectionFour.contains(tmp.x, tmp.y)) {
                    draggedCrewMember.setCurrentSection(sections.get(3));
                    moveCrewMember(draggedCrewMember,imageCrewMember,Net.HttpMethods.PUT);
                    setHourX1(tmp.x);
                    setHourY1(tmp.y);

                } else if (sectionFive.contains(tmp.x, tmp.y)) {
                    draggedCrewMember.setCurrentSection(sections.get(4));
                    moveCrewMember(draggedCrewMember,imageCrewMember,Net.HttpMethods.PUT);
                    setHourX1(tmp.x);
                    setHourY1(tmp.y);

                } else if (sectionSix.contains(tmp.x, tmp.y)) {
                    draggedCrewMember.setCurrentSection(sections.get(5));
                    moveCrewMember(draggedCrewMember,imageCrewMember,Net.HttpMethods.PUT);
                    setHourX1(tmp.x);
                    setHourY1(tmp.y);

                } else {
                    imageCrewMember.setPosition(crewX, crewY);
                }
                dragged = false;
            }
        });
    }

    /**
     * Receives an Image and returns the crewMember object of the Image
     *
     * @param imageCrewMember Image of Crew Member which CrewMember object to find
     * @return CrewMember object of image
     */
    private CrewMember getDraggedCrewMember(Image imageCrewMember) {
        for (CrewMember crewMember : myCrew()) {
            if (crewMember.getName().equals(imageCrewMember.getName())) {
                return crewMember;
            }
        }
        return null;
    }

    /**
     * Receives x and y-position  of redPin in a section
     *
     * @param redPinXPosition of section
     * @param redPinYPosition of section
     * @return Rectangle of redPin section
     */
    private Rectangle getRectOfTextures(float redPinXPosition, float redPinYPosition) {
        return new Rectangle(XPlayerShip + redPinXPosition, YPlayerShip + redPinYPosition,
                redPin.getWidth(), redPin.getHeight());
    }

    private void logicOfFirePlayer() {
        for (Weapon w : selectedWeapons) {
            w.setObjectiv(selectedTarget);
        }
        if (!sectionsPlayer.isEmpty()) {
            for (Weapon w :
                    selectedWeapons) {
                w.setSection(Global.combatSections.get(Global.currentShipPlayer.getId()).get(1));
            }
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

    public void updateSectionEnergy(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        final String url = Global.SERVER_URL + "/" + Global.SECTIONS + Global.ENERGY;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed updateSectionEnergy");
                }
                System.out.println("statusCode updateSectionEnergy: " + statusCode);
                String result = httpResponse.getResultAsString();
                if (result == null) {
                    System.out.println("Requested List of Sections is null");
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
    }


    /**
     * Receives an Crew Member, the image of the Crew Member and the HTTP-method
     *
     * @param requestObject Crew Member object
     * @param imageCrewMember Image of Crew Member which CrewMember object to find
     * @param method HTTP-method to send to server
     */
    public void moveCrewMember(Object requestObject,Image imageCrewMember, String method) {
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

                if (result == null) {
                    System.out.println("Requested Crew Member is null");
                } else {
                    Gson gson = new Gson();
                    CrewMember newCrewMember = gson.fromJson(result, CrewMember.class);
                    Global.combatCrew.get(Global.currentShipPlayer.getId()).set(myCrew().indexOf(newCrewMember), newCrewMember);

                    imageCrewMember.setPosition(XPlayerShip + newCrewMember.getCurrentSection().getxPos(),
                            YPlayerShip + newCrewMember.getCurrentSection().getyPos());
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

                if (sectionsGegner.size() > 0)
                    Global.currentShipGegner = sectionsGegner.get(0).getShip();
                 if (Global.currentShipGegner.getHp() <= 0 || Global.combatCrew.get(Global.currentShipGegner.getId()).size() < 1
                        || allSectionsBroken(Global.combatSections.get(Global.currentShipGegner.getId()))) {
                    Global.combatWeapons.remove(Global.currentShipGegner.getId());
                    Global.combatSections.remove(Global.currentShipGegner.getId());
                    Global.combatActors.remove(Global.currentGegner.getId());
                    Global.combatCrew.remove(Global.currentShipGegner.getId());
                    LOG.info("You have Won the Fight");
                    // Update data to store
                    RequestUtils.findGameRoundsByActor(Global.currentPlayer);
                    final Dialog dialog = new Dialog("Congratulations!!!", skin, "dialog") {
                        public void result(Object obj) {
                            obj.toString();
                        }
                    };

                    if(Global.currentStop.equals(Global.planet5)){
                        winMessageDialog(dialog, " You won the Game .\n Holly shit You are GEIL Karsten!!!! ");
                    }else winMessageDialog(dialog, " You won this Fight.\n But the game is not over yet ");

                }

                RequestUtils.weaponsByShip(Global.currentShipPlayer);
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

                List<Boolean> firing = Arrays.asList(gson.fromJson(httpResponse.getResultAsString(), Boolean[].class));

                for (int i = 0; i < selectedWeapons.size(); i++) {
                    if (firing.get(i)) {
                        weaponsToFire.add(selectedWeapons.get(i));
                    }
                }

                LOG.info("Firing Wepaons: " + weaponsToFire.toString());

                if (!weaponsToFire.isEmpty()) {
                    makeAShot(weaponsToFire, Net.HttpMethods.POST);

                    int y = 42;
                    for (Weapon w :
                            weaponsToFire) {
                        bullets.add(new Bullet(XPlayerShip + 170, YPlayerShip + y));
                        y = y + shotDelta;

                        yWeaponPos -= shotDelta;
                    }
                    weaponsToFire.clear();
                }
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

    private String getWeaponsStats(List<Weapon> ws) {
        StringBuilder sb = new StringBuilder();

        for (Weapon w :
                ws) {
            sb.append(String.format("Weapon: %s%n Damage: %s%n Bullets: %s%n Warmup: %s%n", w.getName(), w.getDamage(), w.getCurrentBullets(), w.getWarmUp()));
        }
        return sb.toString();
    }

    private String getSectionStats(List<Section> xs) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Section s :
                xs) {
            String crewName = "None";
            for (CrewMember c :
                    myCrew()) {
                if (c.getCurrentSection().getId().equals(s.getId())) {
                    crewName = c.getName();
                    break;
                }
            }
            stringBuilder.append(String.format("%s, usable: %s, oxygen: %s, Role: %s, Crew: %s%n", s.getImg(), s.isUsable(), s.getOxygen(), s.getSectionTyp(), crewName));
        }
        return stringBuilder.toString();
    }

    public boolean allSectionsBroken(List<Section> sections) {
        boolean isNotBroken = false;
        for (Section s :
                sections) {
            if (s.getUsable()) {
                isNotBroken = true;
                break;
            }
        }
        return !isNotBroken;
    }


    // Called when the screen should render itself.
    @Override
    public void render(float delta) {

        stage.getBatch().begin();

        GdxUtils.clearScreen();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);


        if (!Global.weaponsToProcess.isEmpty()) {
            bulletsEnemy.add(new Bullet(1500, 500));
            Global.weaponsToProcess.remove(0);
        }

        if (Global.combatActors.containsKey(Global.currentPlayer.getId())) {
            ActorState state = Global.combatActors.get(Global.currentPlayer.getId()).getState();
            liamButton.setText(state.getFightState().getState());
        }

        // TODO wenn alle Sektionen kaputt sind, wird auch verlore
        // If der Player lose
        if (Global.combatCrew.size() == 2 && Global.combatSections.size() == 2) {
            if (Global.currentShipPlayer.getHp() < 1 || Global.combatCrew.get(Global.currentShipPlayer.getId()).isEmpty()
                    || allSectionsBroken(Global.combatSections.get(Global.currentShipPlayer.getId()))) {
                LOG.info("You have lost the Game");
                // Data update to store
                RequestUtils.findGameRoundsByActor(Global.currentPlayer);
                Global.combatWeapons.remove(Global.currentShipGegner.getId());
                Global.combatSections.remove(Global.currentShipGegner.getId());
                Global.combatActors.remove(Global.currentGegner.getId());
                Global.combatCrew.remove(Global.currentShipGegner.getId());
                final Dialog dialog = new Dialog("Congratulations!!!", skin, "dialog") {
                };
                loseMessageDialog(dialog, " You have lost the game!");
            }
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {

            //Set Target->Section of Player and gegner Weapons
            logicOfFirePlayer();
            //bullets.add(new Bullet(590, yWeaponPos));
            counterCockpit++;
//            if(isRound) bulletsEnemy.add(new Bullet(1500, 600));
            // else
            bullets.add(new Bullet(BaseScreen.WIDTH, 0));
        }
        weaponLabel.setText(getWeaponsStats(Global.combatWeapons.get(Global.currentShipPlayer.getId())));
        sectionLabel.setText(getSectionStats(Global.combatSections.get(Global.currentShipPlayer.getId())));

        if (Global.combatWeapons.size() >= 1) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
                aktiveWeapon++;
                aktiveWeapon = aktiveWeapon % (Global.combatWeapons.get(Global.currentShipPlayer.getId()).size() + 1);  // Add one more because index is +1
                if (aktiveWeapon == 0) {
                    selectedWeapons = Global.combatWeapons.get(Global.currentShipPlayer.getId());
                    currentWeaponLabel.setText(weaponText[0]);
                } else {
                    selectedWeapons = List.of(Global.combatWeapons.get(Global.currentShipPlayer.getId()).get(aktiveWeapon - 1)); // Weapons start at 0. Index at 1
                    currentWeaponLabel.setText(weaponText[1] + selectedWeapons.get(0).getName());
                }
            }

            stage.getBatch().draw(background, 0, 0, BaseScreen.WIDTH, BaseScreen.HEIGHT);
            // Render the Ship of the current Player
            stage.getBatch().draw(playerShip, XPlayerShip, YPlayerShip, WidthPlayerShip, HeightPlayerShip);
            stage.getBatch().draw(shieldSystem, XPlayerShip + 210, YPlayerShip + 290);
            stage.getBatch().draw(driveSystem, XPlayerShip + 110, YPlayerShip + 80);
            stage.getBatch().draw(weaponsSystem, XPlayerShip + 295, YPlayerShip + 180);
            stage.getBatch().draw(energyWeaponsPanel, 0, 0);

            //insgesamt Energy
            drawAvailableEnergy(Global.currentShipPlayer.getPower() -
                    Global.sumCurrentPower(Global.combatSections.get(Global.currentShipPlayer.getId())), 25);
            //shield Energy
            drawAvailableEnergy(Global.combatSections.get(Global.currentShipPlayer.getId()).get(1).getPowerCurrent(), 165);
            //weapons Energy
            drawAvailableEnergy(Global.combatSections.get(Global.currentShipPlayer.getId()).get(3).getPowerCurrent(), 467);
            //drive Energy
            drawAvailableEnergy(Global.combatSections.get(Global.currentShipPlayer.getId()).get(5).getPowerCurrent(), 320);

            if (dragged) {
                for(int i = 0; i < Global.sectionsPlayerList.size(); i++){
                    stage.getBatch().draw(redPin, XPlayerShip + Global.sectionsPlayerList.get(i).getxPos(),
                            YPlayerShip + Global.sectionsPlayerList.get(i).getyPos());
                }
                /*stage.getBatch().draw(redPin, XPlayerShip + Global.section1.getxPos(), YPlayerShip + Global.section1.getyPos());
                stage.getBatch().draw(redPin, XPlayerShip + Global.section2.getxPos(), YPlayerShip + Global.section2.getyPos());
                stage.getBatch().draw(redPin, XPlayerShip + Global.section3.getxPos(), YPlayerShip + Global.section3.getyPos());
                stage.getBatch().draw(redPin, XPlayerShip + Global.section4.getxPos(), YPlayerShip + Global.section4.getyPos());
                stage.getBatch().draw(redPin, XPlayerShip + Global.section5.getxPos(), YPlayerShip + Global.section5.getyPos());
                stage.getBatch().draw(redPin, XPlayerShip + Global.section6.getxPos(), YPlayerShip + Global.section6.getyPos());*/
            }

            // Spaawn Enemy Ship if (Global.currentShipGegner != null) {
            if (Global.currentStop == Global.planet2)
                stage.getBatch().draw(enemyShip1, XEnemyShip, YEnemyPos, WIDTHGegner, HEIGHTGegner);
            else if (Global.currentStop == Global.planet3)
                stage.getBatch().draw(enemyShip2, XEnemyShip, YEnemyPos, WIDTHGegner, HEIGHTGegner);
            else stage.getBatch().draw(enemyShip3, XEnemyShip, YEnemyPos, WIDTHGegner, HEIGHTGegner);
            // FIXME Brutal Online

        }

        int y = 22;
        for (Weapon w :
                Global.combatWeapons.get(Global.currentShipPlayer.getId())) {

            if (w.getName().contains("Rocket")) {
                stage.getBatch().draw(missille, XPlayerShip + 170, YPlayerShip + y, 400, 50);
                // stage.getBatch().draw(missille, XPlayerShip + 170, YPlayerShip + y, 400, 50);
                y += 223;
            } else if (w.getName().contains("Laser")) {
                stage.getBatch().draw(laser, XPlayerShip + 170, YPlayerShip + y, 400, 50);
            }
        }
        stage.getBatch().draw(missille, XPlayerShip + 170, 820, 400, 50);

        //Enemy shooting

        canFireGegner = true;
        if (!validationGegner.isEmpty() && validationGegner.equals("Fire Accepted")) {
            System.out.println("::Gegner Shot now");
            rocketLaunch.play();
        } else if (!validationGegner.isEmpty() && validationGegner.equals("Section unusable")) {
            System.out.println(":::::Section unusable Gegner");
            validationGegner = "";
        } else if (Global.currentShipPlayer.getHp() <= 0) {
            System.out.println(":::Defeat");
            validationGegner = "";

            killTimer = true;
            mainClient.setScreen(new MenuScreen(game));
        }

        if (!sectionsPlayer.isEmpty()) {
            Global.sectionsPlayerList = sectionsPlayer;
//                Global.updateVariableSectionShipPlayer(); WTF FIXME is this is important
            Global.currentShipPlayer = sectionsPlayer.get(0).getShip();
            Global.actualizierungSectionInWeapons();
            sectionsPlayer = new ArrayList<>();
        }


        lebengegnerShip.setText(String.valueOf(Global.currentShipGegner.getHp()));
        lebenplayerShip.setText(String.valueOf(Global.currentShipPlayer.getHp()));
        //A
        //Logic
        // for player
        if (Global.currentShipPlayer.getShield() > 0) {
            stage.getBatch().draw(shield, 70, 150, 1100, 1000);
        }
        //shield for enemy

        if (Global.currentShipGegner.getShield() > 0) {
            stage.getBatch().draw(shield, 1120, 150, 900, 1000);
        }

        //update bullets
        ArrayList<Bullet> bulletToRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            bullet.update(delta);
            if (bullet.remove) {
                if (isTargetCockpit && bullet.remove) stage.getBatch().draw(explosion, 1540, 550, 100, 100);
                bulletToRemove.add(bullet);
            }
        }

        bullets.removeAll(bulletToRemove);

        ArrayList<Bullet> bulletGegnerToRemove = new ArrayList<>();
        for (Bullet bullet : bulletsEnemy) {
            bullet.updateTo(delta);
            if (bullet.remove) {

                bulletGegnerToRemove.add(bullet);
            }

        }

        if (Global.currentShipGegner != null && Global.combatSections.size() == 2) {
            for (Section s :
                    Global.combatSections.get(Global.currentShipGegner.getId())) {
                if (!s.getUsable() && Objects.equals(s.getImg(), "Section1Gegner1"))   stage.getBatch().draw(explosion, 1530, 635, 100, 70);
                if (!s.getUsable() && Objects.equals(s.getImg(), "Section3Gegner1")) stage.getBatch().draw(explosion, 1410, 485, 100, 70);
                if (!s.getUsable() && Objects.equals(s.getImg(), "Section2Gegner1"))  stage.getBatch().draw(explosion, 1530, 450, 100, 70);
                if (!s.getUsable() && Objects.equals(s.getImg(), "Section5Gegner3"))  stage.getBatch().draw(explosion, 1610, 485, 100, 70);
                if (!s.getUsable() && Objects.equals(s.getImg(), "Section4Gegner2"))  stage.getBatch().draw(explosion, 1530, 510, 100, 70);
            }
        }

        stage.getBatch().end();

        mainClient.getBatch().begin();
        for (Bullet bullet : bullets) {
            bullet.render(mainClient.getBatch());
        }
        mainClient.getBatch().end();


        mainClient.getBatch().begin();
        for (Bullet bullet : bulletsEnemy) {
            bullet.render(mainClient.getBatch());
        }
        mainClient.getBatch().end();

        stage.getBatch().begin();

            if(hourglass1){ stage.getBatch().draw(hourglass,getHourX1(),getHourY1()); }
            if(hourglass2){ stage.getBatch().draw(hourglass,getHourX1(),getHourY1()); }
            if(hourglass3){ stage.getBatch().draw(hourglass,getHourX1(),getHourY1()); }

         stage.getBatch().end();

            mainClient.getBatch().begin();
            for (Bullet bullet : bulletsEnemy) {
                bullet.render(mainClient.getBatch());
            }
            mainClient.getBatch().end();

        proofCrewMembersAvailable();

        stage.act();
        stage.draw();
    }

    public void drawAvailableEnergy(int energyCounter, int xPosition) {
        int energyYPosition = 13;
        for (int i = 0; i < energyCounter; i++) {
            stage.getBatch().draw(energy, xPosition, energyYPosition, 80, 110);
            energyYPosition += 22;
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
                    RequestUtils.getShip(Global.currentShipPlayer);
                    RequestUtils.weaponsByShip(Global.currentShipPlayer);
                    RequestUtils.getActor(Global.currentPlayer);
                    RequestUtils.getActor(Global.currentGegner);
                }
            }

        }, 1000, 3000);
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
        shieldSystem.dispose();
        weaponsSystem.dispose();
        driveSystem.dispose();
        //crewMemberOne.dispose();
        //crewMemberTwo.dispose();
        //crewMemberThree.dispose();
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


    private void winMessageDialog(Dialog dialog, String action) {
        dialog.text(action);
        dialog.button("See Map", true).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                killTimer = true;
                game.setScreen(new StationsMap(game));
            }
        });
        dialog.key(Input.Keys.ENTER, true);
        dialog.key(Input.Keys.ESCAPE, false);
        click.play();
        dialog.show(stage);
    }

    private void loseMessageDialog(Dialog dialog, String action) {
        dialog.text(action);
        dialog.button("See Menu", true).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                killTimer = true;
                game.setScreen(new MenuScreen(game));
            }
        });
        dialog.key(Input.Keys.ENTER, true);
        dialog.key(Input.Keys.ESCAPE, false);
        click.play();
        dialog.show(stage);
    }


    public void proofCrewMembersAvailable() {
        if (Global.combatCrew.containsKey(Global.currentShipPlayer.getId())) {

            for (int i = 0; i < Global.combatCrew.get(Global.currentShipPlayer.getId()).size(); i++) {

                if (Global.combatCrew.get(Global.currentShipPlayer.getId()).get(i).getRoundsToDestination() == 1) {
                    stage.addActor(listOfCrewImages.get(i));
                    breakCrewMember.remove();

                    if (i == 0) {
                        //imageCrewMemberOne.remove();
                        setHourglass1(true);
                        setBreakinfo("CrewMember " + (i + 1) + " needs time to change the section");
                        this.breakCrewMember = new Label(breakinfo, skin);
                        stage.addActor(breakCrewMember);
                    }
                    if (i == 1) {
                        //imageCrewMemberTwo.remove();
                        setHourglass2(true);
                        setBreakinfo("CrewMember " + (i + 1) + " needs time to change the section");
                        this.breakCrewMember = new Label(breakinfo, skin);
                        stage.addActor(breakCrewMember);
                    }
                    if (i == 2) {
                        //imageCrewMemberThree.remove();
                        setHourglass3(true);
                        setBreakinfo("CrewMember " + (i + 1) + " needs time to change the section");
                        this.breakCrewMember = new Label(breakinfo, skin);
                        stage.addActor(breakCrewMember);
                    }
                    breakCrewMember.setPosition(200, 900);
                }

                if (Global.combatCrew.get(Global.currentShipPlayer.getId()).get(i).getRoundsToDestination() == 0) {
                    stage.addActor(listOfCrewImages.get(i));
                    if (i == 0) {
                        setHourglass1(false);
                        //System.out.println("setHourglass1(false);");
                    }
                    if (i == 1) {
                        setHourglass2(false);
                        //System.out.println("setHourglass2(false);");
                    }
                    if (i == 2) {
                        setHourglass3(false);
                        //System.out.println("setHourglass3(false);");
                    }
                }
            }
        }
    }


    public void setBreakinfo(String breakinfo) {
        this.breakinfo = breakinfo;
    }

    public void setHourglass1(boolean hourglass1) {
        this.hourglass1 = hourglass1;
    }

    public void setHourglass2(boolean hourglass2) {
        this.hourglass2 = hourglass2;
    }

    public void setHourglass3(boolean hourglass3) {
        this.hourglass3 = hourglass3;
    }

    public float getHourX1() {
        return hourX1;
    }

    public void setHourX1(float hourX1) {
        this.hourX1 = hourX1;
    }

    public float getHourY1() {
        return hourY1;
    }

    public void setHourY1(float hourY1) {
        this.hourY1 = hourY1;
    }

}
