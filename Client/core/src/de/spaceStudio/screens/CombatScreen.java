package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.gson.Gson;
import de.spaceStudio.MainClient;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.server.model.Section;
import de.spaceStudio.server.model.SectionTyp;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.model.Weapon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static de.spaceStudio.client.util.RequestUtils.setupRequest;


public class CombatScreen extends BaseScreen {


    private Stage stage;
    private Skin skin;
    private Viewport viewport;
    private SpriteBatch batch;

    private Texture playerShip;
    private Texture enemyShip;
    private Texture engine1, engine2, oxygen, piloting;
    private Texture engineer, pilot, hull;
    private Texture background;
    private TextButton fire;

    private Texture fuze, fuz2, explosion;
    int fuzeOffset;
    boolean isFired = false;
    boolean canFire = false;
    private boolean isExploied;
    private boolean isTargetSelected;
    private boolean sectionw, sectiond, sectionOthers;
    private ShapeRenderer shapeRenderer;
    private Skin sgxSkin, skinButton;
    private TextureAtlas gamePlayAtlas;
    private AssetManager assetManager = null;
    boolean isWin;

    //private TextButton engine, weapon, o2;
    private ImageButton engine, weapon, cockpit;
    int disappear = 570;

    Sound rocketLaunch;


    //
    String validation = "";
    List<Section> sectionsNachFire;
    List<Section> sectionsGernerResponse = new ArrayList<Section>();
    //

    public CombatScreen(MainClient game) {
        super(game);

        OrthographicCamera camera = new OrthographicCamera(BaseScreen.WIDTH, BaseScreen.HEIGHT);

        viewport = new ExtendViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT, camera);
        stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        skinButton = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        background = new Texture(Gdx.files.internal("Client/core/assets/data/CombatBG.jpg"));
        playerShip = new Texture(Gdx.files.internal("Client/core/assets/blueships_fulled.png"));
        enemyShip = new Texture(Gdx.files.internal("Client/core/assets/data/ships/enemy1.png"));
        hull = new Texture(Gdx.files.internal("Client/core/assets/hull1.png"));
        shapeRenderer = new ShapeRenderer();
        final Drawable engine_sym = new TextureRegionDrawable(new Texture(Gdx.files.internal("Client/core/assets/EnginesSymbol.png")));
        final Drawable engine_red = new TextureRegionDrawable(new Texture(Gdx.files.internal("Client/core/assets/EngineRed.png")));

        final Drawable cockpit_nat = new TextureRegionDrawable(new Texture(Gdx.files.internal("Client/core/assets/PilotingSymbol.png")));
        final Drawable cockpit_red = new TextureRegionDrawable(new Texture(Gdx.files.internal("Client/core/assets/PilotingRed.png")));

        rocketLaunch = Gdx.audio.newSound(Gdx.files.internal("Client/core/assets/data/music/shoot.wav"));
        fuze = new Texture(Gdx.files.internal("Client/core/assets/data/missille_out.png"));
        fuz2 = new Texture(Gdx.files.internal("Client/core/assets/data/missille_in.png"));
        explosion = new Texture(Gdx.files.internal("Client/core/assets/data/explosion1_0024.png"));
        fuzeOffset = 570;
        engine = new ImageButton(engine_sym);
        engine.setPosition(1075, 440);
        engine.setSize(1000, 100);
        cockpit = new ImageButton(cockpit_nat);
        cockpit.setPosition(1075,660);
        cockpit.setSize(1000,100);
        cockpit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isTargetSelected = true;
                sectionw = false;
                sectionOthers = true;
                sectiond = false;
                cockpit.getStyle().imageUp = cockpit_red;

            }
        });

        fire = new TextButton("Fire", skinButton, "small");
        fire.setPosition(800, 200);


        stage.addActor(fire);
        stage.addActor(engine);
        stage.addActor(cockpit);
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

    private void hoverListener(final ImageButton imageButton) {
        imageButton.addListener(new HoverListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                imageButton.setName("selected");

            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);

            }
        });
    }

    int counter=0;

    @Override
    public void show() {
        super.show();
        fire.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //rocketLaunch.play();
                logicOfFire();

            }
        });
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage.getBatch().draw(playerShip, 300, 300, 700, 700);
        stage.getBatch().draw(enemyShip, 1300, 370, 550, 550);
        stage.getBatch().draw(hull, 0, 1020, 500, 50);
        stage.getBatch().draw(fuze, disappear, 422, 400, 50);
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

        if (isFired && isTargetSelected) {
            fuzeOffset++;
            disappear = BaseScreen.WIDTH;
            stage.getBatch().draw(fuz2, +fuzeOffset, 422, 400, 50);

            if (fuzeOffset == 700) {
                fuzeOffset = BaseScreen.WIDTH;
                isExploied = true;
            }
        }

        if(isExploied)
        {
            stage.getBatch().draw(explosion,1515,422,100,100);
        }

        stage.getBatch().end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.MAGENTA);
        shapeRenderer.line(0,298, BaseScreen.WIDTH,298);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.line(0,296, BaseScreen.WIDTH,296);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.line(0,294, BaseScreen.WIDTH,294);
        shapeRenderer.setColor(Color.CORAL);
        shapeRenderer.line(0,292, BaseScreen.WIDTH,292);

        // shapeRenderer.
        shapeRenderer.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width,height);
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
        super.dispose();
        skin.dispose();
        shapeRenderer.dispose();
        rocketLaunch.dispose();
        fuze.dispose();
        fuz2.dispose();
        stage.dispose();
    }
}
