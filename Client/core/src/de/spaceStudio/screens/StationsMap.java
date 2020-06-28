package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.spaceStudio.MainClient;
import thirdParties.GifDecoder;

import java.util.ArrayList;

public class StationsMap extends BaseScreen {


    private Stage stage;
    private Skin skin;
    private Texture background;
    private Viewport viewport;
    final TextArea textArea;

    private static int POSX = 100;
    private static int POSY = 200;
    int i=0;


    private static String visit = "unvisited planet";
    private static int PLANET_SIZEX = 100;
    private static int PLANET_SIZEY = 100;




    private ImageButton imageButton1, imageButton2, imageButton3, imageButton4, imageButton5;

    ArrayList<Animation<TextureRegion>> animations = new ArrayList<>();
    private float state =0.0f;


    public StationsMap(MainClient game, AssetManager assetManager) {
        super(game);

        viewport = new FitViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        background = new Texture(Gdx.files.internal("Client/core/assets/data/maps/sky-map.jpg"));

        for ( i = 0; i < 20; i++){
            animations.add(GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/start.gif").read())); }


        final Drawable drawable_station_unvisited = new TextureRegionDrawable(new Texture(Gdx.files.internal("Client/core/assets/data/stations/unvisited-removebg-preview.png")));
        final Drawable drawable_station_visited = new TextureRegionDrawable(new Texture(Gdx.files.internal("Client/core/assets/data/stations/visited-removebg-preview.png")));

        textArea = new TextArea(visit,skin);
        planet(drawable_station_unvisited);
        planet2(drawable_station_unvisited);
        planet3(drawable_station_unvisited);
        planet4(drawable_station_unvisited);
        planet5(drawable_station_unvisited);
        stage.addActor(imageButton1);
        stage.addActor(imageButton2);
        stage.addActor(imageButton3);
        stage.addActor(imageButton4);
        stage.addActor(imageButton5);

    }

    private void planet5(Drawable drawable_station_unvisited) {
        imageButton5 = new ImageButton( (drawable_station_unvisited) );
        imageButton5.setPosition(200, 800);  //hikeButton is an ImageButton
        imageButton5.setSize(PLANET_SIZEX,PLANET_SIZEY);
        imageButton5.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final Dialog dialog = new Dialog("Information", skin, "dialog") {
                    public void result(Object obj) {
                        System.out.println("result "+obj);
                    }
                };
                dialog.text("orem Ipsum is simply dummy text of the printing and typesetting industry.\n" +
                        " Lorem Ipsum has been the industry's standard dummy\n" +
                        " text ever since the 1500s, when an unknown printer \n" +
                        "took a galley of type and scrambled it to make a type specimen book." +
                        "moves in to attact\n" +
                        "Are you sure you want to jump there?");
                dialog.button("Yes", true); //sends "true" as the result
                dialog.button("No", false);  //sends "false" as the result
                dialog.key(Input.Keys.ENTER, true); //sends "true" when the ENTER key is pressed
                dialog.show(stage);

            }
        });
        imageButton5.addListener(new HoverListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                imageButton5.addActor(textArea);

            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                textArea.remove();

            }
        });
    }

    private void planet4(Drawable drawable_station_unvisited) {
        imageButton4 = new ImageButton( (drawable_station_unvisited) );
        imageButton4.setPosition(300, 550);
        imageButton4.setSize(PLANET_SIZEX,PLANET_SIZEX);
        imageButton4.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final Dialog dialog = new Dialog("Information", skin, "dialog") {
                    public void result(Object obj) {
                        System.out.println("result "+obj);
                    }
                };
                dialog.text("orem Ipsum is simply dummy text of the printing and typesetting industry.\n" +
                        " Lorem Ipsum has been the industry's standard dummy\n" +
                        " text ever since the 1500s, when an unknown printer took a galley of type\n" +
                        "and scrambled it to make a type specimen book.\n" +
                        "moves in to attact\n" +
                        "Are you sure you want to jump there?");
                dialog.button("Yes", true); //sends "true" as the result
                dialog.button("No", false);  //sends "false" as the result
                dialog.key(Input.Keys.ENTER, true); //sends "true" when the ENTER key is pressed
                dialog.show(stage);

            }
        });
        imageButton4.addListener(new HoverListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                imageButton4.addActor(textArea);

            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                textArea.remove();

            }
        });
    }

    private void planet3(Drawable drawable_station_unvisited) {
        imageButton3 = new ImageButton( (drawable_station_unvisited) );
        imageButton3.setPosition(400, 500);
        imageButton3.setSize(PLANET_SIZEX,PLANET_SIZEX);
        imageButton3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final Dialog dialog = new Dialog("Information", skin, "dialog") {
                    public void result(Object obj) {
                        System.out.println("result "+obj);
                    }
                };
                dialog.text("orem Ipsum is simply dummy text of the printing and typesetting industry.\n" +
                        " Lorem Ipsum has been the industry's standard dummy\n" +
                        " text ever since the 1500s, when an unknown printer took a galley of\n " +
                        "type and scrambled it to make a type specimen book." +
                        "moves in to attact\n" +
                        "Are you sure you want to jump there?");
                dialog.button("Yes", true); //sends "true" as the result
                dialog.button("No", false);  //sends "false" as the result
                dialog.key(Input.Keys.ENTER, true); //sends "true" when the ENTER key is pressed
                dialog.show(stage);

            }
        });
        imageButton3.addListener(new HoverListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                imageButton3.addActor(textArea);

            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                textArea.remove();

            }
        });
    }

    private void planet2(Drawable drawable_station_unvisited) {
        imageButton2 = new ImageButton( (drawable_station_unvisited) );
        imageButton2.setPosition(150, 650);
        imageButton2.setSize(PLANET_SIZEX,PLANET_SIZEX);
        imageButton2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final Dialog dialog = new Dialog("Information", skin, "dialog") {
                    public void result(Object obj) {
                        System.out.println("result "+obj);
                    }
                };
                dialog.text("orem Ipsum is simply dummy text of the printing and typesetting industry.\n" +
                        " Lorem Ipsum has been the industry's standard dummy\n" +
                        " text ever since the 1500s, when an unknown printer took a galley of\n" +
                        " type and scrambled it to make a type specimen book.\n" +
                        "moves in to attact\n" +
                        "Are you sure you want to jump there?");
                dialog.button("Yes", true); //sends "true" as the result
                dialog.button("No", false);  //sends "false" as the result
                dialog.key(Input.Keys.ENTER, true); //sends "true" when the ENTER key is pressed
                dialog.show(stage);

            }
        });
        imageButton2.addListener(new HoverListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                imageButton2.addActor(textArea);

            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                textArea.remove();

            }
        });
    }

    private void planet(Drawable drawable_station_unvisited) {
        imageButton1 = new ImageButton( (drawable_station_unvisited) );
        imageButton1.setPosition(250, 700);
        imageButton1.setSize(PLANET_SIZEX,PLANET_SIZEY);
        imageButton1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final Dialog dialog = new Dialog("Information", skin, "dialog") {
                    public void result(Object obj) {
                        System.out.println("result "+obj);
                    }
                };
                dialog.text("You Jump to into sector of the nebula beset by a plasma storm. An automated Rebel scout stationed at he beacon" +
                        "moves in to attact\n" +
                        "Are you sure you want to jump there?");

                dialog.button("Yes", true); //sends "true" as the result
                dialog.button("No", false);  //sends "false" as the result
                dialog.key(Input.Keys.ENTER, true); //sends "true" when the ENTER key is pressed
                dialog.show(stage);

            }
        });
        imageButton1.addListener(new HoverListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                imageButton1.addActor(textArea);


            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                textArea.remove();

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
        state += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.01f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().begin();
        stage.getBatch().draw(background,0,0,BaseScreen.WIDTH,BaseScreen.HEIGHT);

        Gdx.input.setInputProcessor(stage);
        stage.getBatch().end();
        stage.act();

        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
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
        stage.dispose();
    }


}
