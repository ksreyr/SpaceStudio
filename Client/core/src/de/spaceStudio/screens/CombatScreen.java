package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.gson.Gson;
import de.spaceStudio.MainClient;
import de.spaceStudio.assets.StyleNames;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.server.model.Planet;
import de.spaceStudio.server.model.Section;
import de.spaceStudio.server.model.SectionTyp;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.model.Weapon;
import de.spaceStudio.service.CombatService;
import de.spaceStudio.util.GdxUtils;
import de.spaceStudio.server.model.Weapon;

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
    private Texture enemyShip;
    private Texture hull;
    private Texture background;
    private TextButton enableShield, enableEnemyShield;

    boolean isFired = false;
    boolean canFire = false;
    private boolean isExploied;
    private boolean sectionw, sectiond, sectionOthers;
    private Texture missilleRight,  explosion, missilleLeft, weaponSystem;
    int fuzeOffsetright,fuzeOffsetLeft;
    private boolean isTargetSelected, isTargetEngine, isTargetCockpit, isTargetWeapon;
    private Skin  skinButton;
    boolean isWin;

    Texture bullet, shield;
    private boolean isShieldEnabled,  isEnemyShield;

    private ImageButton engine, weaponSection,cockpit;
    private int disappearRight = 570;
    private int disappearLeft = 570;
    private int counterEngine = 0;
    private int counterCockpit = 0;
    private int counterWeapon = 0;

    float x=0;

    Sound rocketLaunch;


    ArrayList<Bullet> bullets;
    ArrayList<Bullet> bulletsEnemy;
    ShipSelectScreen shipSelectScreen;
    //
    String validation = "";
    List<Section> sectionsNachFire;
    List<Section> sectionsGernerResponse = new ArrayList<Section>();
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





        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        skinButton = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        background = new Texture("Client/core/assets/combatAssets/CombatBG.jpg");
        playerShip = new Texture("Client/core/assets/combatAssets/blueships_fulled.png");
        enemyShip = new Texture("Client/core/assets/combatAssets/enemy1.png");
        missilleRight =  new Texture("Client/core/assets/combatAssets/missille_out.png");
        missilleLeft = new Texture("Client/core/assets/combatAssets/missille_out.png");
        shield = new Texture("Client/core/assets/combatAssets/shield_2.png");
        explosion = new Texture("Client/core/assets/combatAssets/explosion1_0024.png");
        bullet = new Texture("Client/core/assets/combatAssets/bullet.png");

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
        engine.setPosition(1075,440);
        engine.setSize(1000,100);
        engine.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isTargetEngine = true;
                isTargetSelected=true;
                sectionw = true;
                sectionOthers = false;
                sectiond = false;
                engine.getStyle().imageUp = engine_red;

            }
        });


        weaponSection = new ImageButton(weapon_section);
        weaponSection.setPosition(1450,500);
        weaponSection.setSize(100,100);
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
        cockpit.setPosition(1075,660);
        cockpit.setSize(1000,100);
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
        enableShield = new TextButton("Activate Shield",sgxSkin2,StyleNames.EMPHASISTEXTBUTTON);
        enableShield.setPosition(BaseScreen.WIDTH-1500,200);
        enableShield.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(isShieldEnabled) isShieldEnabled=false;
                else isShieldEnabled = true;
            }
        });


        enableEnemyShield = new TextButton("Enemy Shield",sgxSkin2,StyleNames.EMPHASISTEXTBUTTON);
        enableEnemyShield.setPosition(BaseScreen.WIDTH-400,200);
        enableEnemyShield.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(isEnemyShield) isEnemyShield=false;
                else isEnemyShield = true;

            }
        });



        TextButton escape = new TextButton(" Save Game ", sgxSkin2, StyleNames.EMPHASISTEXTBUTTON);
        escape.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Global.IS_SINGLE_PLAYER = false;
                mainClient.setScreen(new ShipSelectScreen(mainClient));
            }
        });
        escape.setPosition(1000,200);


        stage.addActor(enableEnemyShield);
        stage.addActor(engine);
        stage.addActor(cockpit);
        stage.addActor(enableShield);
        stage.addActor(weaponSection);



        stage.addActor(escape);

        Gdx.input.setInputProcessor(stage);
    }


    private void logicOfFire() {
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
                        if(s.getSectionTyp().equals(SectionTyp.WEAPONS)){
                            //jedes Weapons der User muss dieses section haben
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        };
                    }else if(sectiond){
                        if(s.getSectionTyp().equals(SectionTyp.DRIVE)){
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        };
                    }else{
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
                        if(s.getSectionTyp().equals(SectionTyp.WEAPONS)){
                            //jedes Weapons der User muss dieses section haben
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        };
                    }else if(sectiond){
                        if(s.getSectionTyp().equals(SectionTyp.DRIVE)){
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        };
                    }else{
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
                        if(s.getSectionTyp().equals(SectionTyp.WEAPONS)){
                            //jedes Weapons der User muss dieses section haben
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        };
                    }else if(sectiond){
                        if(s.getSectionTyp().equals(SectionTyp.DRIVE)){
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);
                            }
                        };
                    }else{
                        if(s.getSectionTyp().equals(SectionTyp.NORMAL)){
                            for (Weapon w :
                                    Global.weaponListPlayer) {
                                w.setObjectiv(s);

                            }
                        };
                    }
                }
                break;
        }
        Global.updateweaponPlayerVariabel();
        shotValidation(Global.weaponListPlayer,Net.HttpMethods.POST);
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
                    System.out.println("Request Failed makeAShot");
                }
                System.out.println("statusCode makeAShot: " + statusCode);
                String SectionsGegner = httpResponse.getResultAsString();
                Gson gson = new Gson();
                Section[] aiArray = gson.fromJson(SectionsGegner, Section[].class);
                sectionsGernerResponse = Arrays.asList(aiArray);
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
                System.out.println("statusCode Validation: " + statusCode);
                validation = httpResponse.getResultAsString();

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
        stage.getBatch().draw(playerShip, 300,300,700,700);
        stage.getBatch().draw(enemyShip, 1300,370,550,550);
        stage.getBatch().draw(missilleRight,disappearRight,422,400,50);
        stage.getBatch().draw(missilleLeft,disappearLeft,825,400,50);
        if (!validation.isEmpty() && validation.equals("Fire Accepted")) {
            canFire = true;
        } else if (!validation.isEmpty() && validation.equals("Fire not Accepted")) {
            validation = "";
        }
        if (canFire) {
            makeAShot(Global.weaponListPlayer, Net.HttpMethods.POST);
            isFired = true;
            canFire = false;
            validation = "";
        }
        if (!sectionsGernerResponse.isEmpty()) {
            Section sectionResponse=sectionsGernerResponse.get(0);
            Ship shiptoUpdate =sectionResponse.getShip();
            Global.currentShipGegner=shiptoUpdate;
            switch (sectionResponse.getShip().getName()) {
                case "Shipgegner1":
                    Global.sectionsgegner1 = sectionsGernerResponse;
                    Global.updateVariblesSectionsGegner1();
                    Global.shipGegner1=shiptoUpdate;
                    break;
                case "Shipgegner2":
                    Global.sectionsgegner2 = sectionsGernerResponse;
                    Global.updateVariblesSectionsGegner2();
                    Global.shipGegner2=shiptoUpdate;
                    break;
                case "Shipgegner3":
                    Global.sectionsgegner3 = sectionsGernerResponse;
                    Global.updateVariblesSectionsGegner3();
                    Global.shipGegner3=shiptoUpdate;
                    break;
                case "Shipgegner4":
                    Global.sectionsgegner4 = sectionsGernerResponse;
                    Global.updateVariblesSectionsGegner4();
                    Global.shipGegner4=shiptoUpdate;
                    break;
                case "Shipgegner5":
                    Global.sectionsgegner5 = sectionsGernerResponse;
                    Global.updateVariblesSectionsGegner5();
                    Global.shipGegner5=shiptoUpdate;
                    break;
                case "Shipgegner6":
                    Global.sectionsgegner6 = sectionsGernerResponse;
                    Global.updateVariblesSectionsGegner6();
                    Global.shipGegner6=shiptoUpdate;
                    break;
            }
            Global.updateShipsListgegneru2();
            List<Section> sizeO=new ArrayList<>();
            sectionsGernerResponse=sizeO;
        }

        //Create and launch missiles
        if(Gdx.input.isKeyJustPressed(Input.Keys.A) && isTargetCockpit){
            counterCockpit++;
            if(counterCockpit < 3){
                bullets.add(new Bullet(590,843))  ;
                rocketLaunch.play();
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.D) && isTargetEngine){
            counterEngine++;
            if(counterEngine < 4){
                bullets.add(new Bullet(590,444));
                rocketLaunch.play();
            }

        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.W) && isTargetWeapon){
            counterWeapon++;
            if(counterWeapon < 4){
                bullets.add(new Bullet(590,444));
                //bullets.add(new Bullet(590,843));
                rocketLaunch.play();
            }

        }
        //shield for player
        if(isShieldEnabled) stage.getBatch().draw(shield,70,150,1100,1000);
        //shield for enemy
        if(isEnemyShield) stage.getBatch().draw(shield,1120,150,900,1000);

        //explosion weapon
        //if(counterEngi >= 2)
        //    stage.getBatch().draw(explosion,1450,500,100,100);


        //explosion on player
        if(counterEngine >= 2)
            stage.getBatch().draw(explosion,550,520,100,100);

        //explosion on enemy's engine
        if( counterEngine >= 3 && !isEnemyShield){
            stage.getBatch().draw(explosion,1515,422,100,100);
        }

        if( counterWeapon >= 3 && !isEnemyShield){
            stage.getBatch().draw(explosion,1450,500,100,100);
        }
        //explosion on enemy's cockpit
        if( counterCockpit >= 2 && !isEnemyShield){
            stage.getBatch().draw(explosion,1515,690,100,100);
        }
        //update bullets
        ArrayList<Bullet> bulletToRemove = new ArrayList<>();
        for (Bullet bullet:bullets) {
            bullet.update(delta);
            if(bullet.remove){
                bulletToRemove.add(bullet);

            }
        }

        bulletToRemove.removeAll(bulletToRemove);

        stage.getBatch().end();
        mainClient.getBatch().begin();
        for (Bullet bullet:bullets ) {
            bullet.render(mainClient.getBatch());
        } mainClient.getBatch().end();


        stage.act();
        stage.draw();
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
