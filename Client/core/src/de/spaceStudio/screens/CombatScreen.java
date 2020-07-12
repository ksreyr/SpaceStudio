package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.spaceStudio.MainClient;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.server.model.Planet;
import de.spaceStudio.server.model.Section;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.model.Weapon;
import de.spaceStudio.service.CombatService;

import java.util.ArrayList;


public class CombatScreen extends BaseScreen{


    private Stage stage;
    private Skin skin;
    private Viewport viewport;
    private Texture playerShip;
    private Texture enemyShip;
    private Texture hull;
    private Texture background;
    private TextButton enableShield, enableEnemyShield;

    private Texture missilleRight, missilleRightFired, explosion, missilleLeft, missilleLeftFired;
    int fuzeOffsetright,fuzeOffsetLeft;
    boolean isRightPressed, isLeftPressed;
    private boolean isShootEngine, isShootCockpit;
    private boolean isTargetSelected, isTargedEngine, isTargetCockpit, isTargetWeapon;
    private ShapeRenderer shapeRenderer;
    private Skin  skinButton;
    private AssetManager assetManager = null;
    boolean isWin;
    private boolean w, d, o;

    Texture bullet, shield;
    private boolean isShieldEnabled,  isEnemyShield;

    private ImageButton engine, weaponSection,cockpit;
    private int disappearRight = 570;
    private int disappearLeft = 570;
    public static final int SPEED = 450;
    private int counterEngine = 0;
    private int counterCockpit = 0;

    Weapon weapon = Global.weapon;
    float x=0;






    Sound rocketLaunch;
    Ship shipPlayer = Global.currentShip;


    ArrayList<Bullet> bullets;
    ShipSelectScreen shipSelectScreen;

    //
    CombatService cs = new CombatService();
    Planet planet = Global.currentPlanet;
    Ship gegnerShip;
    Section section1Gegner;
    Section section2Gegner;
    Section section3Gegner;
    //

    public CombatScreen(MainClient game) {
        super(game);

        viewport = new FitViewport(BaseScreen.WIDTH,BaseScreen.HEIGHT);
        stage = new Stage(viewport);
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

        shapeRenderer = new ShapeRenderer();
        final Drawable engine_sym = new TextureRegionDrawable(new Texture("Client/core/assets/combatAssets/enginesSymbol.png"));
        final Drawable engine_red = new TextureRegionDrawable(new Texture("Client/core/assets/combatAssets/engineRed.png"));
        final Drawable cockpit_nat = new TextureRegionDrawable(new Texture("Client/core/assets/combatAssets/PilotingSymbol.png"));
        final Drawable cockpit_red = new TextureRegionDrawable(new Texture("Client/core/assets/combatAssets/PilotingRed.png"));

        rocketLaunch = Gdx.audio.newSound(Gdx.files.internal("Client/core/assets/data/music/shoot.wav"));
        fuzeOffsetright = 570;
        fuzeOffsetLeft = 570;


        fuzeOffsetright = 570;
        fuzeOffsetLeft = 570;

        bullets = new ArrayList<>();

 /*       if (planet.getName().equals("p1")) {
            gegnerShip = Global.shipGegner1;
        } else if (planet.getName().equals("p2")) {
            gegnerShip = Global.shipGegner1;
        } else if (planet.getName().equals("p3")) {
            gegnerShip = Global.shipGegner2;
        } else if (planet.getName().equals("p4")) {
            gegnerShip = Global.shipGegner2;
        } else if (planet.getName().equals("p5")) {

        }
        ArrayList<Section> sections = Global.sectionofShip(gegnerShip);
        section1Gegner = sections.get(0);
        section2Gegner = sections.get(1);
        section3Gegner = sections.get(2);*/
        engine = new ImageButton(engine_sym);
        engine.setPosition(1075,440);
        engine.setSize(1000,100);
        engine.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isTargedEngine = true;
                isTargetSelected=true;
            w = true;
                o = false;
                d = false;
                engine.getStyle().imageUp = engine_red;

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

                w = false;
                o = true;
                d = false;
                cockpit.getStyle().imageUp = cockpit_red;

            }
        });

        Gdx.input.setInputProcessor(stage);
       enableShield = new TextButton("Activate Shield",skinButton,"small");
        enableShield.setPosition(BaseScreen.WIDTH-1500,200);
        enableShield.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                    if(isShieldEnabled) isShieldEnabled=false;
                    else isShieldEnabled = true;
            }
        });


        enableEnemyShield = new TextButton("Enemy Shield",skinButton,"small");
        enableEnemyShield.setPosition(BaseScreen.WIDTH-400,200);
        enableEnemyShield.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(isEnemyShield) isEnemyShield=false;
                else isEnemyShield = true;

            }
        });

         stage.addActor(enableEnemyShield);
         stage.addActor(engine);
         stage.addActor(cockpit);
        stage.addActor(enableShield);
    }

    private void logicOfFire() {
        if (w) {
            if (gegnerShip.getName().equals("Shipgegner1")) {
                Global.shipGegner1.setHp(gegnerShip.getHp() - Global.weapon.getDamage());
                gegnerShip.setHp(Global.shipGegner1.getHp());
                Global.section3Gegner.setUsable(false);
            } else if (gegnerShip.getName().equals("Shipgegner2")) {
                Global.shipGegner2.setHp(gegnerShip.getHp() - Global.weapon.getDamage());
                gegnerShip.setHp(Global.shipGegner2.getHp());
                Global.section3Gegner2.setUsable(false);
            } else if (gegnerShip.getName().equals("Shipgegner3")) {
                Global.shipGegner3.setHp(gegnerShip.getHp() - Global.weapon.getDamage());
                gegnerShip.setHp(Global.shipGegner3.getHp());
                Global.section3Gegner3.setUsable(false);
            }
            section3Gegner.setUsable(false);
            Global.currentWeapon.setObjectiv(section3Gegner);
        } else if (d) {
            if (gegnerShip.getName().equals("Shipgegner1")) {
                Global.shipGegner1.setHp(gegnerShip.getHp() - Global.weapon.getDamage());
                gegnerShip.setHp(Global.shipGegner1.getHp());
                Global.section1Gegner.setUsable(false);
            } else if (gegnerShip.getName().equals("Shipgegner2")) {
                Global.shipGegner2.setHp(gegnerShip.getHp() - Global.weapon.getDamage());
                gegnerShip.setHp(Global.shipGegner2.getHp());
                Global.section1Gegner2.setUsable(false);
            } else if (gegnerShip.getName().equals("Shipgegner3")) {
                Global.shipGegner3.setHp(gegnerShip.getHp() - Global.weapon.getDamage());
                gegnerShip.setHp(Global.shipGegner3.getHp());
                Global.section1Gegner3.setUsable(false);
            }
            section1Gegner.setUsable(false);
            Global.currentWeapon.setObjectiv(section2Gegner);
        } else if (o) {
            if (gegnerShip.getName().equals("Shipgegner1")) {
                Global.shipGegner1.setHp(gegnerShip.getHp() - Global.weapon.getDamage());
                gegnerShip.setHp(Global.shipGegner1.getHp());
                Global.section2Gegner.setUsable(false);
            } else if (gegnerShip.getName().equals("Shipgegner2")) {
                Global.shipGegner2.setHp(gegnerShip.getHp() - Global.weapon.getDamage());
                gegnerShip.setHp(Global.shipGegner2.getHp());
                Global.section2Gegner2.setUsable(false);
            } else if (gegnerShip.getName().equals("Shipgegner3")) {
                Global.shipGegner3.setHp(gegnerShip.getHp() - Global.weapon.getDamage());
                gegnerShip.setHp(Global.shipGegner3.getHp());
                Global.section2Gegner3.setUsable(false);
            }
            section2Gegner.setUsable(false);
            Global.currentWeapon.setObjectiv(section1Gegner);
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cs.makeAShot(Global.currentWeapon, Net.HttpMethods.POST);
    }

    @Override
    public void render(float delta) {
        super.render(delta);


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().begin();

        stage.getBatch().draw(background, 0, 0, BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage.getBatch().draw(playerShip, 300,300,700,700);
        stage.getBatch().draw(enemyShip, 1300,370,550,550);
        stage.getBatch().draw(missilleRight,disappearRight,422,400,50);
        stage.getBatch().draw(missilleLeft,disappearLeft,825,400,50);


        if(Gdx.input.isKeyJustPressed(Input.Keys.A) && isTargetCockpit){
            counterCockpit++;
            if(counterCockpit < 3){
                bullets.add(new Bullet(590,843))  ;
                rocketLaunch.play();
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.D) && isTargedEngine){
            counterEngine++;
            if(counterEngine < 4){
                bullets.add(new Bullet(590,444));
                rocketLaunch.play();
            }

        }
        if(isShieldEnabled) stage.getBatch().draw(shield,70,150,1100,1000);
        if(isEnemyShield) stage.getBatch().draw(shield,1120,150,900,1000);


        if( counterEngine >= 3 && !isEnemyShield){
            stage.getBatch().draw(explosion,1515,422,100,100);

        }
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
            game.getBatch().begin();
        for (Bullet bullet:bullets ) {
            bullet.render(game.getBatch());
        } game.getBatch().end();


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
        missilleRight.dispose();
        missilleRightFired.dispose();
        bullet.dispose();
        stage.dispose();
    }
}
