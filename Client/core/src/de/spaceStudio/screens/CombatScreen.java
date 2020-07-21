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
import de.spaceStudio.server.model.Section;
import de.spaceStudio.server.model.SectionTyp;
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


    private Sound click;


    private Skin skin;

    private Texture playerShip;
    private Texture enemyShip;
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

    boolean isFired = false;
    boolean canFire = false;
    boolean canFireGegner = false;
    private boolean isExploied;
    private boolean sectionw, sectiond, sectionOthers;
    private Texture missilleRight, explosion, missilleLeft, weaponSystem;
    int fuzeOffsetright, fuzeOffsetLeft;
    private boolean isTargetSelected, isTargetEngine, isTargetCockpit, isTargetWeapon;
    private Skin skinButton;
    boolean isWin;

    Texture bullet, shield;
    private boolean isShieldEnabled,  isEnemyShield;

    private ImageButton engine, weaponSection,cockpit;
    private int disappearRight = 570;
    private int disappearLeft = 570;
    private int counterEngine = 0;
    private int counterCockpit = 0;
    private int counterWeapon = 0;
    private int randomNumber;

    float x=0;

    Sound rocketLaunch;

    ArrayList<Bullet> bullets;
    ArrayList<Bullet> bulletsEnemy;
    //
    String validation = "";
    String validationGegner="";
    List<Section> sectionsToGernerResponse = new ArrayList<Section>();
    List<Section> sectionsToPlayerResponse = new ArrayList<Section>();
    Label lebengegnerShip;
    Label lebenplayerShip;
    private int aktiveWeapon = 0;
    private List<Weapon> selectedWeapons;
    private boolean dragged = false;
    private OrthographicCamera camera;


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
        selectedWeapons = Global.weaponListPlayer;

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
        enemyShip = new Texture("Client/core/assets/combatAssets/enemy1.png");
        enemyShip = new Texture("Client/core/assets/combatAssets/enemy1.png");
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


        //lebengegnerShip = new Label(String.valueOf(Global.currentShipGegner.getHp()),skin);
        //lebenplayerShip = new Label(String.valueOf(Global.currentShipPlayer.getHp()),skin);

        final Drawable engine_sym = new TextureRegionDrawable(new Texture("Client/core/assets/combatAssets/enginesSymbol.png"));
        final Drawable engine_red = new TextureRegionDrawable(new Texture("Client/core/assets/combatAssets/engineRed.png"));
        final Drawable cockpit_nat = new TextureRegionDrawable(new Texture("Client/core/assets/combatAssets/PilotingSymbol.png"));
        final Drawable cockpit_red = new TextureRegionDrawable(new Texture("Client/core/assets/combatAssets/PilotingRed.png"));

        final Drawable weapon_section = new TextureRegionDrawable(new Texture("Client/core/assets/combatAssets/weaponEnemy.png"));
        final Drawable weapon_section_red = new TextureRegionDrawable(new Texture("Client/core/assets/combatAssets/weapon_red.png"));

        rocketLaunch = Gdx.audio.newSound(Gdx.files.internal("Client/core/assets/data/music/shoot.wav"));
        fuzeOffsetright = 570;
        fuzeOffsetLeft = 570;


        fuzeOffsetright = 570;
        fuzeOffsetLeft = 570;

        bullets = new ArrayList<>();
        bulletsEnemy = new ArrayList<>();

        engine = new ImageButton(engine_sym);
        engine.setPosition(1075, 440);
        engine.setSize(1000, 100);
        engine.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isTargetEngine = true;
                isTargetSelected=true;
                sectionw = false;
                sectionOthers = false;
                sectiond = true;
                engine.getStyle().imageUp = engine_red;

            }
        });


        weaponSection = new ImageButton(weapon_section);
        weaponSection.setPosition(1450, 500);
        weaponSection.setSize(100, 100);
        weaponSection.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sectionw = true;
                sectionOthers = false;
                sectiond = false;
                isTargetSelected = true;
                isTargetWeapon = true;
                weaponSection.getStyle().imageUp = weapon_section_red;

            }
        });

        cockpit = new ImageButton(cockpit_nat);
        cockpit.setPosition(1075, 660);
        cockpit.setSize(1000, 100);
        cockpit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isTargetSelected = true;
                isTargetCockpit = true;
                sectionw = false;
                sectionOthers = true;
                sectiond = false;
                cockpit.getStyle().imageUp = cockpit_red;

            }
        });

        Gdx.input.setInputProcessor(stage);
        enableShield = new TextButton("Activate Shield", sgxSkin2, StyleNames.EMPHASISTEXTBUTTON);
        enableShield.setPosition(BaseScreen.WIDTH - 1500, 200);
        enableShield.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (isShieldEnabled) isShieldEnabled = false;
                else isShieldEnabled = true;
            }
        });


        enableEnemyShield = new TextButton("Enemy Shield", sgxSkin2, StyleNames.EMPHASISTEXTBUTTON);
        enableEnemyShield.setPosition(BaseScreen.WIDTH - 400, 200);
        enableEnemyShield.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (isEnemyShield) isEnemyShield = false;
                else isEnemyShield = true;

            }
        });


        TextButton saveGameButton = new TextButton(" Save Game ", sgxSkin2, StyleNames.EMPHASISTEXTBUTTON);
        saveGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
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


        //lebengegnerShip.setPosition(100,20);
        //lebenplayerShip.setPosition(20,20);

        //stage.addActor(lebenplayerShip);
        //stage.addActor(lebengegnerShip);
        stage.addActor(enableEnemyShield);
        stage.addActor(engine);
        stage.addActor(cockpit);
        stage.addActor(enableShield);
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
        //Sections
        Global.currentShipGegner.getName();
        switch (Global.currentShipGegner.getName()) {
            //An the fall that the Gegner 1 ist
            case "Shipgegner1":
                List<Section> sectionListShipgegner1 = Global.sectionsgegner1;
                //setSectionZiel
                for (Section s :
                        sectionListShipgegner1) {
                    //if section Zeil is weapons
                    if (sectionw) {
                        System.out.println("::: Shipgegner1 WEAPONS GEGNER UNUSABLE::::");
                        if(s.getSectionTyp().equals(SectionTyp.WEAPONS)){
                            //jedes Weapons der User muss dieses section haben
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        };
                        //if section Zeil is drive
                    }else if(sectiond){
                        System.out.println(":::Shipgegner1 Drive GEGNER UNUSABLE::::");
                        if(s.getSectionTyp().equals(SectionTyp.DRIVE)){
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        };
                        //if section Zeil is others
                    }else{
                        System.out.println(":::Shipgegner1 Other GEGNER UNUSABLE::::");
                        if(s.getSectionTyp().equals(SectionTyp.NORMAL)){
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        };
                    }
                }
                break;
            //An the fall that the Gegner 2 ist
            case "Shipgegner2":
                List<Section> sectionListShipgegner2 = Global.sectionsgegner2;
                //setSectionZiel
                for (Section s :
                        sectionListShipgegner2) {
                    //if section Zeil is weapons
                    if (sectionw) {
                        System.out.println("::: Shipgegner2 WEAPONS GEGNER UNUSABLE::::");
                        if(s.getSectionTyp().equals(SectionTyp.WEAPONS)){
                            //jedes Weapons der User muss dieses section haben
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        };
                    }else if(sectiond){
                        System.out.println(":::Shipgegner2 Drive GEGNER UNUSABLE::::");
                        if(s.getSectionTyp().equals(SectionTyp.DRIVE)){
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        };
                    }else{
                        System.out.println(":::Shipgegner2 Other GEGNER UNUSABLE::::");
                        if(s.getSectionTyp().equals(SectionTyp.NORMAL)){
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        };
                    }
                }
                break;
            //An the fall that the Gegner 3 ist
            case "Shipgegner3":
                List<Section> sectionListShipgegner3 = Global.sectionsgegner3;
                //setSectionZiel
                for (Section s :
                        sectionListShipgegner3) {
                    //if section Zeil is weapons
                    if (sectionw) {
                        System.out.println(":::Shipgegner3 WEAPONS GEGNER UNUSABLE::::");
                        if (s.getSectionTyp().equals(SectionTyp.WEAPONS)) {
                            //jedes Weapons der User muss dieses section haben
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        }
                        ;
                    } else if (sectiond) {
                        System.out.println(":::Shipgegner3 DRIVE GEGNER UNUSABLE::::");
                        if (s.getSectionTyp().equals(SectionTyp.DRIVE)) {
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        }
                        ;
                    } else {
                        System.out.println(":::Shipgegner3 OTHER GEGNER UNUSABLE::::");
                        if (s.getSectionTyp().equals(SectionTyp.NORMAL)) {
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);

                            }
                        }
                        ;
                    }
                }
                break;
            //An the fall that the Gegner 3 ist

        }
        // NO Hay ID en WEAPONS??
        Global.updateweaponPlayerVariabel();
        shotValidation(Global.weaponListPlayer, Net.HttpMethods.POST);
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
                sectionsToGernerResponse = Arrays.asList(aiArray);
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            aktiveWeapon++;
             aktiveWeapon = aktiveWeapon % (Global.weaponListPlayer.size() + 1);  // Add one more because index is +1
            if (aktiveWeapon == 0) {
                selectedWeapons = Global.weaponListPlayer;
                weaponLabel.setText(weaponText[0]);
            } else {
                selectedWeapons = List.of(Global.weaponListPlayer.get(aktiveWeapon - 1)); // Weapons start at 0. Index at 1
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
        if (Global.currentShipGegner != null) {
            stage.getBatch().draw(enemyShip, 1300, 370, 550, 550);
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
        //}else if (Global.currentShipPlayer.getHp()<=0) {
         //   System.out.println(":::Defeat");
           // validationGegner = "";

            //mainClient.setScreen(new MenuScreen(game));
        }
        if(canFireGegner){
            switch (Global.currentShipGegner.getName()){
                case "Shipgegner1":
                    Global.actualiziertweaponListGegner1();
                    makeAShotGegner(Global.weaponListGegner1,Net.HttpMethods.POST);
                    break;
                case "Shipgegner2":
                    Global.actualiziertweaponListGegner2();
                    makeAShotGegner(Global.weaponListGegner2,Net.HttpMethods.POST);
                    break;
                case "Shipgegner3":
                    Global.actualiziertweaponListGegner3();
                    makeAShotGegner(Global.weaponListGegner3,Net.HttpMethods.POST);
                    break;
                case "Shipgegner4":
                    Global.actualiziertweaponListGegner4();
                    makeAShotGegner(Global.weaponListGegner4,Net.HttpMethods.POST);
                    break;
                case "Shipgegner5":
                    Global.actualiziertweaponListGegner5();
                    makeAShotGegner(Global.weaponListGegner5,Net.HttpMethods.POST);
                    break;
                case "Shipgegner6":
                    Global.actualiziertweaponListGegner6();
                    makeAShotGegner(Global.weaponListGegner6,Net.HttpMethods.POST);
                    break;
            }
            canFireGegner=false;
            validationGegner = "";
        }
        //on weapon system Explosion
        if (!sectionsToPlayerResponse.isEmpty() && sectionsToPlayerResponse.get(1).getUsable()==false) {
            stage.getBatch().draw(explosion, 555, 520, 100, 100);
        }else if(!sectionsToPlayerResponse.isEmpty() && sectionsToPlayerResponse.get(0).getUsable()==false){
            stage.getBatch().draw(explosion, 700, 520, 100, 100);
        }else if(!sectionsToPlayerResponse.isEmpty() && sectionsToPlayerResponse.get(2).getUsable()==false){
            stage.getBatch().draw(explosion, 710, 670, 100, 100);
        }
        if(!sectionsToPlayerResponse.isEmpty()){
            Global.sectionsPlayerList= sectionsToPlayerResponse;
            Global.updateVariableSectionShipPlayer();
            Global.currentShipPlayer= sectionsToPlayerResponse.get(0).getShip();
            Global.actualizierungSectionInWeapons();
            List<Section> sizeO=new ArrayList<>();
            sectionsToPlayerResponse =sizeO;
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
        }/*else if (Global.currentShipGegner.getHp()<=0) {
            System.out.println(":::Defeat gegner");
            validation = "";
            mainClient.setScreen(new StationsMap(game));
        }*/
        if (canFire) {
            makeAShot(Global.weaponListPlayer, Net.HttpMethods.POST);
            isFired = true;
            canFire = false;
            validation = "";
        }
        //Update Server Response
        if (!sectionsToGernerResponse.isEmpty()) {
            Section sectionResponse = sectionsToGernerResponse.get(0);
            Ship shiptoUpdate = sectionResponse.getShip();
            Global.currentShipGegner = shiptoUpdate;
            switch (sectionResponse.getShip().getName()) {
                case "Shipgegner1":
                    Global.sectionsgegner1 = sectionsToGernerResponse;
                    Global.updateVariblesSectionsGegner1();
                    Global.shipGegner1 = shiptoUpdate;
                    Global.currentShipGegner = shiptoUpdate;
                    Global.aktualizierenweaponListUniverse2();
                    break;
                case "Shipgegner2":
                    Global.sectionsgegner2 = sectionsToGernerResponse;
                    Global.updateVariblesSectionsGegner2();
                    Global.shipGegner2 = shiptoUpdate;
                    Global.currentShipGegner = shiptoUpdate;
                    Global.aktualizierenweaponListUniverse2();
                    break;
                case "Shipgegner3":
                    Global.sectionsgegner3 = sectionsToGernerResponse;
                    Global.updateVariblesSectionsGegner3();
                    Global.shipGegner3=shiptoUpdate;
                    break;
                case "Shipgegner4":
                    Global.sectionsgegner4 = sectionsToGernerResponse;
                    Global.updateVariblesSectionsGegner4();
                    Global.shipGegner4 = shiptoUpdate;
                    Global.currentShipGegner = shiptoUpdate;
                    Global.aktualizierenweaponListUniverse2();
                    break;
                case "Shipgegner5":
                    Global.sectionsgegner5 = sectionsToGernerResponse;
                    Global.updateVariblesSectionsGegner5();
                    Global.shipGegner5 = shiptoUpdate;
                    Global.currentShipGegner = shiptoUpdate;
                    Global.aktualizierenweaponListUniverse2();
                    break;
                case "Shipgegner6":
                    Global.sectionsgegner6 = sectionsToGernerResponse;
                    Global.updateVariblesSectionsGegner6();
                    Global.shipGegner6 = shiptoUpdate;
                    Global.currentShipGegner = shiptoUpdate;
                    Global.aktualizierenweaponListUniverse2();
                    break;
            }
            Global.updateShipsListgegneru2();
            List<Section> sizeO = new ArrayList<>();
            sectionsToGernerResponse = sizeO;
            //GEGNER FIRE
        }
        //lebengegnerShip.setText(String.valueOf(Global.currentShipGegner.getHp()));
        //lebenplayerShip.setText(String.valueOf(Global.currentShipPlayer.getHp()));
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
        //if (Global.currentShipPlayer.getShield()>0) stage.getBatch().draw(shield, 70, 150, 1100, 1000);
        //shield for enemy
        //if (Global.currentShipGegner.getShield()>0) stage.getBatch().draw(shield, 1120, 150, 900, 1000);


        //explosion on player sections
        //on healthpoint
        /*if (randomNumber == 2) {
            stage.getBatch().draw(explosion, 700, 520, 100, 100);
        }
        //on cockpit
        if (randomNumber == 3) {
            stage.getBatch().draw(explosion, 710, 670, 100, 100);
        }
        //engine
        if (randomNumber == 4) {
            stage.getBatch().draw(explosion, 445, 395, 100, 100);
        }*/



        //explosion on enemy's engine
        if (counterEngine >= 3 && !isEnemyShield) {
            stage.getBatch().draw(explosion, 1515, 422, 100, 100);
        }

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
                sectionsToPlayerResponse = Arrays.asList(sectiongegnerArray);
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
                for (Weapon w :
                        Global.weaponListUniverse2) {
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
                Global.updateweaponVariabelUniverse2();
                Global.actualiziertweaponListGegner1();
                Global.actualiziertweaponListGegner2();
                Global.actualiziertweaponListGegner3();
                Global.actualiziertweaponListGegner4();
                Global.actualiziertweaponListGegner5();
                Global.actualiziertweaponListGegner6();
            }
        }
        shotValidationGegner(weaponList,Net.HttpMethods.POST);
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
