package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.spaceStudio.MainClient;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.server.model.Planet;

import java.util.Random;


public class RandomEventScreen extends BaseScreen {


    private MainClient universeMap;
    private MainClient mainClient;
    private Viewport viewport;
    private Stage stage;
    final TextArea textAreaUN;


    private Skin skin;


    private Sound click;

    Dialog randomDialog;

    AssetManager assetManager;

    Skin skinButton;

    private  String fight = "Fight Enemy";

    private ImageButton TravelImgBTN, FightImgBTN;

    public RandomEventScreen(MainClient game) {
        super(game);
        final Drawable drawable_station_unvisited = new TextureRegionDrawable(new Texture(Gdx.files.internal("Client/core/assets/data/stations/unvisited-removebg-preview.png"))); // FIXME texture
        skinButton = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        this.universeMap = mainClient;
        this.mainClient = mainClient;
        assetManager = new AssetManager();

        textAreaUN = new TextArea(fight, skin);

        eventStop(drawable_station_unvisited);
        stage.addActor(FightImgBTN);

    }

    private void hoverListener(final ImageButton img, final TextArea textArea) {
        img.addListener(new HoverListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                img.addActor(textArea);

            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                textArea.remove();

            }
        });
    }

    private void actionDialog(Dialog dialog, String event) {
        dialog.text(event);
        dialog.button("LAND", true);
        dialog.button("FLEE", false);
        dialog.key(Input.Keys.ENTER, true);
        dialog.key(Input.Keys.ESCAPE, false);
        dialog.show(stage);
    }

    String event_string(int event){
        String result;
        switch (event) {
            case 0:
                result = "You got lucky. You find More life";

                break;
            default:
                result = "";
                break;
        }


        return result;
    }

    private void eventStop(Drawable drawable_station_unvisited) {
        FightImgBTN = new ImageButton( (drawable_station_unvisited) );
        FightImgBTN.setPosition(250, 700);
        FightImgBTN.setSize(20,20);
        hoverListener(FightImgBTN,textAreaUN);
        final Planet planet = Global.planet1;
        final int[] event_number = new int[1];
        FightImgBTN.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final Dialog dialog = new Dialog("Information", skin, "dialog") {
                    public void result(Object obj) {
                        if(obj.toString()=="true") {
                            event_number[0] = getRandomNumberInRange(0,7);

                        }
                    }
                };
                actionDialog( dialog,event_string(event_number[0]));
            }
        });

    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    @Override
    public void show()
    {
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        randomDialog = new Dialog("New Stop for Ship", skin)
        {
            protected void result(Object object)
            {
                System.out.println("Option: " + object);
                if (object.equals(1L))
                {
                    genenerateEvent(getRandomNumberInRange(0, 4));
                    System.out.println("Button Pressed");
                } else {
                    // Goto main menut
                }
                Timer.schedule(new Timer.Task()
                {

                    @Override
                    public void run()
                    {
                        randomDialog.show(stage);
                    }
                }, 1);
            };
        };

        randomDialog.button("Check out the other Ship", 1L);
        randomDialog.button("Check out the Station", 2L);

        TextButton fire = new TextButton("Fire",skinButton,"big");
        TextButton travel = new TextButton("Fire",skinButton,"big");

        Timer.schedule(new Timer.Task()
        {

            @Override
            public void run()
            {
                randomDialog.show(stage);
            }
        }, 1);

    }

    private void genenerateEvent(int number) {
        switch (number) {
            case 0:
                Global.currentShip.setHp(Global.currentShip.getHp() + getRandomNumberInRange(-20, 20));
                Global.currentShip.setShield(Global.currentShip.getShield());
                // TODO Save state of Ship
//                    gameScreen.setIntScore(0);
                break;

            default:
                break;

        }
    }

    @Override
    public void render(float delta)
    {
//        GdxUtils.clearScreen();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

    }

    @Override
    public void dispose()
    {
        stage.dispose();
    }
    // Called when the Application is resized.
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    // Called when the Application is paused, usually when it's not active or visible on-screen.
    @Override
    public void pause() {}

    // Called when the Application is resumed from a paused state, usually when it regains focus.
    @Override
    public void resume() {}

    // Called when this screen is no longer the current screen for a Game.
    @Override
    public void hide() {
        dispose();
    }
}
