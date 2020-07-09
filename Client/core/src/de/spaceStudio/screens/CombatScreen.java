package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
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


public class CombatScreen extends BaseScreen{


    private Stage stage;
    private Skin skin;
    private Viewport viewport;
    private Texture playerShip;
    private Texture enemyShip;
    private Texture hull;
    private Texture background;
    private TextButton fire;

    private Texture missilleRight, missilleRightFired, explosion, missilleLeft, missilleLeftFired;
    int fuzeOffsetright,fuzeOffsetLeft;
    boolean isFired;
    private boolean isExploied;
    private boolean isTargetSelected, isTargedEngine, isTargetCockpit, isTargetWeapon;
    private ShapeRenderer shapeRenderer;
    private Skin  skinButton;
    private AssetManager assetManager = null;
    boolean isWin;

    //private TextButton engine, weapon, o2;
    private ImageButton engine, weapon,cockpit;
    int disappearRight = 570;
    int disappearLeft = 570;

    Sound rocketLaunch;


    public CombatScreen(MainClient game) {
        super(game);

        viewport = new FitViewport(BaseScreen.WIDTH,BaseScreen.HEIGHT);
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
        missilleRight =  new Texture(Gdx.files.internal("Client/core/assets/data/missille_out.png"));
        missilleRightFired =  new Texture(Gdx.files.internal("Client/core/assets/data/missille_in.png"));

        missilleLeft =  missilleRight =  new Texture(Gdx.files.internal("Client/core/assets/data/missille_out.png"));
        missilleLeftFired =  new Texture(Gdx.files.internal("Client/core/assets/data/missille_in.png"));

        explosion = new Texture(Gdx.files.internal("Client/core/assets/data/explosion1_0024.png"));
        fuzeOffsetright = 570;
        fuzeOffsetLeft = 570;

        engine = new ImageButton(engine_sym);
        engine.setPosition(1075,440);
        engine.setSize(1000,100);
        engine.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isTargedEngine = true;
                isTargetSelected=true;
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
                cockpit.getStyle().imageUp = cockpit_red;

            }
        });


        Gdx.input.setInputProcessor(stage);
        fire = new TextButton("Fire",skinButton,"small");
        fire.setPosition(800,200);
        fire.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                 //   hoverListener(fire);
                    rocketLaunch.play();
                    isFired = true;

            }
        });


         stage.addActor(fire);
         stage.addActor(engine);
         stage.addActor(cockpit);

    }

    private void hoverListener(final ImageButton imageButton) {
        imageButton.addListener(new HoverListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);

            }
        });
    }


    @Override
    public void show() {
        super.show();
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
        stage.getBatch().draw(hull, 0,1020,500,50);
        stage.getBatch().draw(missilleRight,disappearRight,422,400,50);
        stage.getBatch().draw(missilleLeft,disappearLeft,825,400,50);
        if(isFired &&  isTargedEngine) {
            fuzeOffsetright++;
            disappearRight=BaseScreen.WIDTH;
            stage.getBatch().draw(missilleRightFired,+fuzeOffsetright,422,400,50);
        }
        if(isFired &&  isTargetCockpit) {
            fuzeOffsetLeft++;
            disappearLeft=BaseScreen.WIDTH;
            stage.getBatch().draw(missilleLeftFired,+fuzeOffsetLeft,825,400,50);
        }
        
        if(fuzeOffsetright == 700){
                fuzeOffsetright = BaseScreen.WIDTH;
                isExploied = true;

         }
        if(fuzeOffsetLeft == 700){
            fuzeOffsetLeft = BaseScreen.WIDTH;
            isExploied = true;

        }
        if(isTargedEngine&& isExploied){
            stage.getBatch().draw(explosion,1515,422,100,100);


        }
        if(isTargetCockpit&&isExploied){
            stage.getBatch().draw(explosion,1515, 690,100,100);

        }


        stage.getBatch().end();
/*        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.MAGENTA);
        shapeRenderer.line(0,298, BaseScreen.WIDTH,298);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.line(0,296, BaseScreen.WIDTH,296);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.line(0,294, BaseScreen.WIDTH,294);
        shapeRenderer.setColor(Color.CORAL);
        shapeRenderer.line(0,292, BaseScreen.WIDTH,292);

        // shapeRenderer.
        shapeRenderer.end();*/

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
        stage.dispose();
    }
}
