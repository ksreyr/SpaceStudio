package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.gson.Gson;
import de.spaceStudio.MainClient;
import de.spaceStudio.assets.StyleNames;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.server.model.Section;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.model.Weapon;
import de.spaceStudio.util.GdxUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static de.spaceStudio.client.util.RequestUtils.setupRequest;


public class CombatScreen extends BaseScreen {

    private MainClient universeMap;
    private final AssetManager assetManager;
    private MainClient mainClient;
    private Viewport viewport;
    private Stage stage;

    private Skin sgxSkin, sgxSkin2;
    private TextureAtlas gamePlayAtlas;


    private Sound click;


    private Skin skin;
    private SpriteBatch batch;

    private Texture playerShip;
    private Texture enemyShip1, enemyShip2, enemyShip3;
    private Texture hull;
    private Texture background;


    boolean isFired = false;
    boolean canFire = false;
    boolean canFireGegner = false;
    private boolean isExploied;
    private boolean isSectionw, isSectiond, isSectionOthers, isSectiono2, isSectionhealth;
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
    List<Section> sectionsToGernerResponse = new ArrayList<Section>();
    List<Section> sectionsToPlayerResponse = new ArrayList<Section>();
    Label lebengegnerShip;
    Label lebenplayerShip;
    //

    public CombatScreen(MainClient mainClient) {
        super(mainClient);
        this.universeMap = mainClient;
        this.mainClient = mainClient;
        assetManager = universeMap.getAssetManager();
    }

    @Override
    public void show() {

        viewport = new FitViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage = new Stage(viewport, universeMap.getBatch());
        click = Gdx.audio.newSound(Gdx.files.internal("Client/core/assets/data/music/mouseclick.wav"));

        sgxSkin2 = new Skin(Gdx.files.internal("Client/core/assets/ownAssets/sgx/skin/sgx-ui.json"));
        System.out.println("Global planet: "+ Global.currentStop.getName());

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        skinButton = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        background = new Texture("Client/core/assets/combatAssets/CombatBG.jpg");
        playerShip = new Texture("Client/core/assets/combatAssets/blueships_fulled.png");
        enemyShip1 = new Texture("Client/core/assets/combatAssets/enemy1.png");
        enemyShip2 = new Texture("Client/core/assets/combatAssets/enemy_2.png");
        enemyShip3 = new Texture("Client/core/assets/combatAssets/enemy_3.png");
        missilleRight = new Texture("Client/core/assets/combatAssets/missille_out.png");
        missilleLeft = new Texture("Client/core/assets/combatAssets/missille_out.png");
        shield = new Texture("Client/core/assets/combatAssets/shield_2.png");
        explosion = new Texture("Client/core/assets/combatAssets/explosion1_0024.png");
        bullet = new Texture("Client/core/assets/combatAssets/bullet.png");

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

                isSectiono2=true;
                isSectionw = false;
                isSectionOthers = false;
                isSectiond = false;
                isSectionhealth =false;


                isTargetO2 = true;
                isTargetSelected = true;

                o2.getStyle().imageUp = oxygen_sym_red;
                engine.getStyle().imageUp = engine_sym;
                weaponSection.getStyle().imageUp = weapon_section;
                cockpit.getStyle().imageUp = cockpit_nat;
                //healthPoint.getStyle().imageUp = medical_sym;
            }
        });
        healthPoint = new ImageButton(medical_sym);
        healthPoint.setPosition(1650, 500);
        healthPoint.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isTargetMedical = true;
                isTargetSelected = true;

                isSectionw = false;
                isSectionOthers = false;
                isSectiond = false;
                isSectiono2=false;
                isSectionhealth =true;

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
                isTargetEngine = true;
                isTargetSelected = true;


                isSectionw = false;
                isSectionOthers = false;
                isSectiond = true;
                isSectiono2=false;
                isSectionhealth =false;

                engine.getStyle().imageUp = engine_red;

            }
        });


        weaponSection = new ImageButton(weapon_section);
        weaponSection.setPosition(1450,500);
        weaponSection.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                isSectiond = false;
                isTargetSelected = true;
                isTargetWeapon = true;

                isSectionw = true;
                isSectionOthers = false;
                isSectiond = false;
                isSectiono2=false;
                isSectionhealth =false;

                weaponSection.getStyle().imageUp = weapon_section_red;

            }
        });

        cockpit = new ImageButton(cockpit_nat);
        cockpit.setPosition(1075,660);
        cockpit.setSize(1000,100);
        cockpit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isTargetSelected = true;
                isTargetCockpit = true;
                isSectionw = false;
                isSectionOthers = true;
                isSectiond = false;
                cockpit.getStyle().imageUp = cockpit_red;

            }
        });

        Gdx.input.setInputProcessor(stage);




        TextButton escape = new TextButton(" Save Game ", sgxSkin2, StyleNames.EMPHASISTEXTBUTTON);
        escape.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Global.IS_SINGLE_PLAYER = false;
                mainClient.setScreen(new ShipSelectScreen(mainClient));
            }
        });
        escape.setPosition(1000,200);


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

        stage.addActor(escape);

        Gdx.input.setInputProcessor(stage);
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
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
                    if (isSectionw) {
                        System.out.println("::: Shipgegner1 WEAPONS GEGNER UNUSABLE::::");
                        if (s.getImg().equals("Section3Gegner1")) {
                            //jedes Weapons der User muss dieses section haben
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        }
                        ;
                        //if section Zeil is drive
                    } else if (isSectiond) {
                        System.out.println(":::Shipgegner1 Drive GEGNER UNUSABLE::::");
                        if (s.getImg().equals("Section2Gegner1")) {
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        }
                        ;
                        //if section Zeil is others
                    } else {
                        System.out.println(":::Shipgegner1 Other GEGNER UNUSABLE::::");
                        if (s.getImg().equals("Section1Gegner1")) {
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
                    if (isSectionw) {
                        System.out.println("::: Shipgegner2 WEAPONS GEGNER UNUSABLE::::");
                        if (s.getImg().equals("Section3Gegner2")) {
                            //jedes Weapons der User muss dieses section haben
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        }

                    } else if (isSectiond) {
                        System.out.println(":::Shipgegner2 Drive GEGNER UNUSABLE::::");
                        if (s.getImg().equals("Section2Gegner2")) {
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        }

                    } else if (isSectiono2) {
                        System.out.println(":::Shipgegner2 O2 GEGNER UNUSABLE::::");
                        if (s.getImg().equals("Section4Gegner2")) {
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        }

                    }else{
                        System.out.println(":::Shipgegner2 Other GEGNER UNUSABLE::::");
                        if (s.getImg().equals("Section1Gegner2")) {
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        }
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
                    if (isSectionw) {
                        System.out.println(":::Shipgegner3 WEAPONS GEGNER UNUSABLE::::");
                        if (s.getImg().equals("Section3Gegner3")) {
                            //jedes Weapons der User muss dieses section haben
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        }
                        ;
                    } else if (isSectiond) {
                        System.out.println(":::Shipgegner3 DRIVE GEGNER UNUSABLE::::");
                        if (s.getImg().equals("Section2Gegner3")) {
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        }
                        ;
                    } else if (isSectiono2) {
                        System.out.println(":::Shipgegner2 O2 GEGNER UNUSABLE::::");
                        if (s.getImg().equals("Section4Gegner3")) {
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        }

                    }else if (isSectionhealth) {
                        System.out.println(":::Shipgegner2 HEALTH GEGNER UNUSABLE::::");
                        if (s.getImg().equals("Section4Gegner3")) {
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        }

                    }else{
                        System.out.println(":::Shipgegner2 Other GEGNER UNUSABLE::::");
                        if (s.getImg().equals("Section1Gegner2")) {
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        }
                    }
                }
                break;

        }

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
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage.getBatch().draw(playerShip, 300, 300, 700, 700);
        if (Global.currentShipGegner != null) {
            stage.getBatch().draw(enemyShip1, 1300, 370, 550, 550);
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
        }else if (Global.currentShipPlayer.getHp()<=0) {
            System.out.println(":::Defeat");
            validationGegner = "";

            mainClient.setScreen(new MenuScreen(game));
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
        }else if (Global.currentShipGegner.getHp()<=0) {
            System.out.println(":::Defeat gegner");
            validation = "";
            mainClient.setScreen(new StationsMap(game));
        }
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
                    Global.shipGegner3 = shiptoUpdate;
                    Global.currentShipGegner = shiptoUpdate;
                    Global.aktualizierenweaponListUniverse2();
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
        if (Global.currentShipPlayer.getShield()>0) stage.getBatch().draw(shield, 70, 150, 1100, 1000);
        //shield for enemy
        if (Global.currentShipGegner.getShield()>0) stage.getBatch().draw(shield, 1120, 150, 900, 1000);


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
}
