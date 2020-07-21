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
import de.spaceStudio.server.model.Section;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.model.Weapon;
import de.spaceStudio.util.GdxUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static de.spaceStudio.client.util.RequestUtils.setupRequest;


public class CombatScreen extends BaseScreen {

    private final static Logger LOG = Logger.getLogger(CombatScreen.class.getName());
    private final Label weaponLabel;
    private final String[] weaponText = {"All Weapons", "You have selected Weapon: "  };

    private MainClient universeMap;
    private final AssetManager assetManager;
    private MainClient mainClient;
    private Viewport viewport;
    private Stage stage;

    private Skin sgxSkin, sgxSkin2;
    //private TextureAtlas gamePlayAtlas;


    private Sound click;


    private Skin skin;
    private SpriteBatch batch;

    private Texture playerShip;
    private Texture enemyShip1, enemyShip2, enemyShip3;
    private Texture hull;
    private Texture background;
    private Texture crewMemberOne;
    private Texture crewMemberTwo;
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

    private TextButton enableShield, enableEnemyShield;


    boolean isNewExpo, isNewExpo2, isNewExpo3;

    boolean isFired = false;
    boolean canFire = false;
    boolean canFireGegner = false;
    private boolean isExploied;
//    private boolean isSectionw, sectiond, sectionOthers,    isSectiono2 ,isSectionOthers,  isSectiond , isSectionhealth ;


    private Texture missilleRight, explosion, missilleLeft, weaponSystem;
    int fuzeOffsetright, fuzeOffsetLeft;
    private boolean isTargetSelected, isTargetEngine, isTargetCockpit, isTargetWeapon;
    private Skin skinButton;
    boolean isWin;

    Texture bullet, shield;
    private boolean isShieldEnabled, isEnemyShield, isTargetO2, isTargetMedical;

    private ImageButton engine, weaponSection, cockpit, o2, healthPoint;
    private int disappearRight = 570;
    private int disappearLeft = 570;
    private int counterEngine = 0;
    private int counterCockpit = 0;
    private int counterWeapon = 0;
    private int randomNumber;

    float x = 0;

    Sound rocketLaunch;


    ArrayList<Bullet> bullets;
    ArrayList<Bullet> bulletsEnemy;
    ShipSelectScreen shipSelectScreen;
    //
    String validation = "";
    String validationGegner="";
    List<Section> sectionsGegner = new ArrayList<Section>();
    List<Section> sectionsPlayer = new ArrayList<Section>();
    Label lebengegnerShip;
    Label lebenplayerShip;
    private int aktiveWeapon = 0;
    private List<Weapon> selectedWeapons;
    private boolean dragged = false;
    private OrthographicCamera camera;
    private Section selectedTarget;


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

        
        
        weaponLabel = new Label(weaponText[0], label1Style);
        weaponLabel.setSize(Gdx.graphics.getWidth(), row_height);
        weaponLabel.setPosition(0, Gdx.graphics.getHeight() - row_height * 6);
        weaponLabel.setAlignment(Align.bottomRight);
        RequestUtils.sectionsByShip(Global.currentShipPlayer);
        RequestUtils.sectionsByShip(Global.currentShipGegner);
        RequestUtils.weaponsByShip(Global.currentShipGegner);
        RequestUtils.weaponsByShip(Global.currentShipPlayer);

    }

    @Override
    public void show() {

        viewport = new StretchViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT,camera);
        stage = new Stage(viewport, universeMap.getBatch());
        click = Gdx.audio.newSound(Gdx.files.internal("Client/core/assets/data/music/mouseclick.wav"));

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
        crewMemberOne = new Texture(Gdx.files.internal("Client/core/assets/combatAssets/MaleHuman-3.png"));
        crewMemberTwo = new Texture(Gdx.files.internal("Client/core/assets/combatAssets/MaleHuman-3.png"));
        crewMemberThree = new Texture(Gdx.files.internal("Client/core/assets/combatAssets/female_human.png"));

        imageCrewMemberOne = new Image(crewMemberOne);
        imageCrewMemberTwo = new Image(crewMemberTwo);
        imageCrewMemberThree = new Image(crewMemberThree);
        listOfCrewImages.add(imageCrewMemberOne);
        listOfCrewImages.add(imageCrewMemberTwo);
        listOfCrewImages.add(imageCrewMemberThree);
        imageCrewMemberOne.setBounds(30,30,30,30);
        imageCrewMemberTwo.setBounds(30,30,30,30);
        imageCrewMemberThree.setBounds(30,30,30,30);
        imageCrewMemberOne.setPosition(BaseScreen.WIDTH/4f-60,BaseScreen.HEIGHT-600);
        imageCrewMemberTwo.setPosition(BaseScreen.WIDTH/4f+170,BaseScreen.HEIGHT-300);
        imageCrewMemberThree.setPosition(BaseScreen.WIDTH/4f+280,BaseScreen.HEIGHT-545);


       lebengegnerShip = new Label(String.valueOf(Global.currentShipGegner.getHp()),skin);
        lebenplayerShip = new Label(String.valueOf(Global.currentShipPlayer.getHp()),skin);
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
               selectedTarget = sectionsGegner.get(4);
                isTargetSelected = true;

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
                selectedTarget = sectionsGegner.get(0);
                isTargetSelected = true;

                healthPoint.getStyle().imageUp = medical_sym_red;
                engine.getStyle().imageUp = engine_sym;
                weaponSection.getStyle().imageUp = weapon_section;
                cockpit.getStyle().imageUp = cockpit_nat;
                o2.getStyle().imageUp = oxygen_sym;


            }
        });

        engine = new ImageButton(engine_sym);
        engine.setPosition(1560,470);
        engine.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectedTarget = sectionsGegner.get(1);
                isTargetSelected = true;

                engine.getStyle().imageUp = engine_red;
                weaponSection.getStyle().imageUp = weapon_section;
                cockpit.getStyle().imageUp = cockpit_nat;
                o2.getStyle().imageUp = oxygen_sym;
                healthPoint.getStyle().imageUp = medical_sym;


            }
        });


        weaponSection = new ImageButton(weapon_section);
        weaponSection.setPosition(1450,500);
        weaponSection.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                selectedTarget = sectionsGegner.get(2);
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
        cockpit.setPosition(1560,650);
        cockpit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectedTarget = sectionsGegner.get(3);
                isTargetSelected = true;

                cockpit.getStyle().imageUp = cockpit_red;
                engine.getStyle().imageUp = engine_sym;
                weaponSection.getStyle().imageUp = weapon_section;
                o2.getStyle().imageUp = oxygen_sym;
                healthPoint.getStyle().imageUp = medical_sym;



            }
        });

        Gdx.input.setInputProcessor(stage);



        TextButton saveGameButton = new TextButton(" Save Game ", sgxSkin2, StyleNames.EMPHASISTEXTBUTTON);
        saveGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Global.IS_SINGLE_PLAYER = false;
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
        for(int i = 0; i < listOfCrewImages.size(); i++){
            dragAndDrop(listOfCrewImages.get(i));
        }

        lebengegnerShip.setPosition(100,20);
        lebenplayerShip.setPosition(20,20);

        stage.addActor(lebenplayerShip);
        if(Global.currentShipGegner.getName().equals("Shipgegner2")){
            stage.addActor(o2);
        }
        if(Global.currentShipGegner.getName().equals("Shipgegner3")){
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



        stage.addActor(saveGameButton);

        Gdx.input.setInputProcessor(stage);
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private void dragAndDrop(Image imageCrewMember) {
        imageCrewMember.addListener(new DragListener() {
            float crewOneX;
            float crewOneY;
            public void drag(InputEvent event, float x, float y, int pointer) {
                dragged = true;
                imageCrewMember.moveBy(x - imageCrewMember.getWidth() / 2, y - imageCrewMember.getHeight() / 2);
            }
            public void dragStart (InputEvent event, float x, float y, int pointer) {
                crewOneX = imageCrewMember.getX();
                crewOneY = imageCrewMember.getY();
            }
            public void dragStop (InputEvent event, float x, float y, int pointer) {
                //do something when texture is touched
                Vector3 tmp= new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
                camera.unproject(tmp);
                Rectangle sectionOne = getRectOfTextures(redPinSectionOne);
                Rectangle sectionTwo = getRectOfTextures(redPinSectionTwo);
                Rectangle sectionThree = getRectOfTextures(redPinSectionThree);
                Rectangle sectionFour = getRectOfTextures(redPinSectionFour);
                Rectangle sectionFive = getRectOfTextures(redPinSectionFive);
                Rectangle sectionSix = getRectOfTextures(redPinSectionSix);
                if(sectionOne.contains(tmp.x,tmp.y)) {
                    imageCrewMember.setPosition(redPinSectionOne.x_position,redPinSectionOne.y_position);
                } else if(sectionTwo.contains(tmp.x,tmp.y)){
                    imageCrewMember.setPosition(redPinSectionTwo.x_position, redPinSectionTwo.y_position);
                } else if(sectionThree.contains(tmp.x,tmp.y)){
                    imageCrewMember.setPosition(redPinSectionThree.x_position, redPinSectionThree.y_position);
                } else if(sectionFour.contains(tmp.x,tmp.y)){
                    imageCrewMember.setPosition(redPinSectionFour.x_position, redPinSectionFour.y_position);
                } else if(sectionFive.contains(tmp.x,tmp.y)){
                    imageCrewMember.setPosition(redPinSectionFive.x_position, redPinSectionFive.y_position);
                } else if(sectionSix.contains(tmp.x,tmp.y)){
                    imageCrewMember.setPosition(redPinSectionSix.x_position, redPinSectionSix.y_position);
                }
                else {
                    imageCrewMember.setPosition(crewOneX, crewOneY);
                }
                dragged = false;
            }
        });
    }

    private Rectangle getRectOfTextures(RedPin redPin){
        return new Rectangle(redPin.x_position,redPin.y_position,
                redPin.texture.getWidth(),redPin.texture.getHeight());
    }

    private void logicOfFirePlayer() {
        Ship enemyShip = Global.currentShipGegner;
        for (Section s :
                sectionsGegner) {
            for (Weapon w: selectedWeapons) {
                selectedTarget = sectionsGegner.get(0); // FIXME
                w.setObjectiv(selectedTarget);
            }
        }
        
        Global.updateweaponPlayerVariabel();
        shotValidation(Global.combatWeapons.get(Global.currentShipPlayer.getId()), Net.HttpMethods.POST);
    }
    //ShotValidation
    ////Para ponerlas en la armas
    //encontrar la section que fue seleccionada
    ////Quien es el gegner
    ////sectiones del gegner
    ////set de

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
                System.out.println("statusCode PlayershotValidation: " + statusCode);
                validation = httpResponse.getResultAsString();
                System.out.println("PlayerShot: "+ validation);
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
        stage.getBatch().draw(playerShip, 300, 300, 700, 700);
        if(dragged){
            redPinSectionOne.x_position = BaseScreen.WIDTH/4f-60;
            redPinSectionOne.y_position = BaseScreen.HEIGHT-290;
            redPinSectionTwo.x_position = BaseScreen.WIDTH/4f-60;
            redPinSectionTwo.y_position = BaseScreen.HEIGHT-600;
            redPinSectionThree.x_position = BaseScreen.WIDTH/4f+170;
            redPinSectionThree.y_position = BaseScreen.HEIGHT-300;
            redPinSectionFour.x_position = BaseScreen.WIDTH/4f+170;
            redPinSectionFour.y_position = BaseScreen.HEIGHT-590;
            redPinSectionFive.x_position = BaseScreen.WIDTH/4f+280;
            redPinSectionFive.y_position = BaseScreen.HEIGHT-340;
            redPinSectionSix.x_position = BaseScreen.WIDTH/4f+280;
            redPinSectionSix.y_position = BaseScreen.HEIGHT-545;
            stage.getBatch().draw(redPinSectionOne.texture,redPinSectionOne.x_position,redPinSectionOne.y_position);
            stage.getBatch().draw(redPinSectionOne.texture,redPinSectionTwo.x_position,redPinSectionTwo.y_position);
            stage.getBatch().draw(redPinSectionOne.texture,redPinSectionThree.x_position,redPinSectionThree.y_position);
            stage.getBatch().draw(redPinSectionOne.texture,redPinSectionFour.x_position,redPinSectionFour.y_position);
            stage.getBatch().draw(redPinSectionOne.texture,redPinSectionFive.x_position,redPinSectionFive.y_position);
            stage.getBatch().draw(redPinSectionOne.texture,redPinSectionSix.x_position,redPinSectionSix.y_position);
        }

            selectedWeapons = Global.combatWeapons.get(Global.currentShipPlayer.getId());

            if (Global.currentShipGegner != null) {
                if (Global.currentStop == Global.planet2) stage.getBatch().draw(enemyShip1, 1300, 370, 550, 550);
                else if (Global.currentStop == Global.planet3) stage.getBatch().draw(enemyShip2, 1300, 370, 550, 550);
                else stage.getBatch().draw(enemyShip3, 1300, 370, 550, 550);

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
                Global.updateVariableSectionShipPlayer();
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
                logicOfFireGegner(randomNumber);
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
                System.out.println("GegnerShot: "+ validationGegner);
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
        List<Weapon> weaponList= new ArrayList<>();
        if (Global.currentShipGegner != null) {
            if (Global.currentUniverse.getName().equals("Normal" + Global.currentPlayer.getName())) {
                switch (Global.currentShipGegner.getName()) {
                    case "Shipgegner1":
                        for (Weapon w :
                                Global.weaponListGegner1) {
                            if(w.getSection().getShip().getId()==Global.currentShipGegner.getId()){
                                //Weapons gegner set Weapons Section of Player
                                if(sectionNumber==2){
                                    w.setObjectiv(Global.section2);
                                    System.out.println("::::::::::::::::::::.WEAPONS FOR PLAYER UNUSABLE:::::::::::::::");
                                    weaponList.add(w);
                                }else if(sectionNumber==4){
                                    w.setObjectiv(Global.section1);
                                    weaponList.add(w);
                                }else{
                                    w.setObjectiv(Global.section3);
                                    weaponList.add(w);
                                }
                            }
                        }
                        shotValidationGegner(Global.weaponListGegner1,Net.HttpMethods.POST);
                        break;
                    case "Shipgegner2":
                        for (Weapon w :
                                Global.weaponListGegner2) {
                            if(w.getSection().getShip().getId()==Global.currentShipGegner.getId()){
                                //Weapons gegner set Weapons Section of Player
                                if(sectionNumber==2){
                                    w.setObjectiv(Global.section2);
                                    System.out.println("::::::::::::::::::::.WEAPONS FOR PLAYER UNUSABLE:::::::::::::::");
                                    weaponList.add(w);
                                }else if(sectionNumber==4){
                                    w.setObjectiv(Global.section1);
                                    weaponList.add(w);
                                }else{
                                    w.setObjectiv(Global.section3);
                                    weaponList.add(w);
                                }
                            }
                        }
                        shotValidationGegner(Global.weaponListGegner2,Net.HttpMethods.POST);
                        break;
                    case "Shipgegner3":
                        for (Weapon w :
                                Global.weaponListGegner3) {
                            if(w.getSection().getShip().getId()==Global.currentShipGegner.getId()){
                                //Weapons gegner set Weapons Section of Player
                                if(sectionNumber==2){
                                    w.setObjectiv(Global.section2);
                                    System.out.println("::::::::::::::::::::.WEAPONS FOR PLAYER UNUSABLE:::::::::::::::");
                                    weaponList.add(w);
                                }else if(sectionNumber==4){
                                    w.setObjectiv(Global.section1);
                                    weaponList.add(w);
                                }else{
                                    w.setObjectiv(Global.section3);
                                    weaponList.add(w);
                                }
                            }
                        }
                        shotValidationGegner(Global.weaponListGegner3,Net.HttpMethods.POST);
                        break;
                    case "Shipgegner4":
                        for (Weapon w :
                                Global.weaponListGegner4) {
                            if(w.getSection().getShip().getId()==Global.currentShipGegner.getId()){
                                //Weapons gegner set Weapons Section of Player
                                if(sectionNumber==2){
                                    w.setObjectiv(Global.section2);
                                    System.out.println("::::::::::::::::::::.WEAPONS FOR PLAYER UNUSABLE:::::::::::::::");
                                    weaponList.add(w);
                                }else if(sectionNumber==4){
                                    w.setObjectiv(Global.section1);
                                    weaponList.add(w);
                                }else{
                                    w.setObjectiv(Global.section3);
                                    weaponList.add(w);
                                }
                            }
                        }
                        shotValidationGegner(Global.weaponListGegner4,Net.HttpMethods.POST);
                        break;
                    case "Shipgegner5":
                        for (Weapon w :
                                Global.weaponListGegner5) {
                            if(w.getSection().getShip().getId()==Global.currentShipGegner.getId()){
                                //Weapons gegner set Weapons Section of Player
                                if(sectionNumber==2){
                                    w.setObjectiv(Global.section2);
                                    System.out.println("::::::::::::::::::::.WEAPONS FOR PLAYER UNUSABLE:::::::::::::::");
                                    weaponList.add(w);
                                }else if(sectionNumber==4){
                                    w.setObjectiv(Global.section1);
                                    weaponList.add(w);
                                }else{
                                    w.setObjectiv(Global.section3);
                                    weaponList.add(w);
                                }
                            }
                        }
                        shotValidationGegner(Global.weaponListGegner5,Net.HttpMethods.POST);
                        break;
                    case "Shipgegner6":
                        for (Weapon w :
                                Global.weaponListGegner6) {
                            if(w.getSection().getShip().getId()==Global.currentShipGegner.getId()){
                                //Weapons gegner set Weapons Section of Player
                                if(sectionNumber==2){
                                    w.setObjectiv(Global.section2);
                                    System.out.println("::::::::::::::::::::.WEAPONS FOR PLAYER UNUSABLE:::::::::::::::");
                                    weaponList.add(w);
                                }else if(sectionNumber==4){
                                    w.setObjectiv(Global.section1);
                                    weaponList.add(w);
                                }else{
                                    w.setObjectiv(Global.section3);
                                    weaponList.add(w);
                                }
                            }
                        }
                        shotValidationGegner(Global.weaponListGegner6,Net.HttpMethods.POST);
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
