package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.spaceStudio.MainClient;
import thirdParties.GifDecoder;
public class StationsMap extends BaseScreen {


    private Stage stage;
    private Skin skin;
    private Texture background;
    private Viewport viewport;
    final TextArea textAreaUN, textAreaVIS;
    private ImageButton imageButton1, imageButton2, imageButton3, imageButton4, imageButton5;
    private ImageButton startPoint;
    Animation<TextureRegion> start_ship;

    private static int POSX = 100;
    private static int POSY = 200;

    private  String unvisited = "unvisited planet";
    private  String visited = "visited planet";

    private static int PLANET_SIZEX = 100;
    private static int PLANET_SIZEY = 100;
    private int counter = 0;
    private float state = 0.0f;
    private Sound jumpToPlanet, mouseClick;

    boolean isLast;


    public StationsMap(MainClient game, AssetManager assetManager) {
        super(game);

        viewport = new FitViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        background = new Texture(Gdx.files.internal("Client/core/assets/data/maps/sky-map.jpg"));


        start_ship = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/ZDci.gif").read());
        final Drawable drawable_station_unvisited = new TextureRegionDrawable(new Texture(Gdx.files.internal("Client/core/assets/data/stations/unvisited-removebg-preview.png")));
        final Drawable drawable_station_visited = new TextureRegionDrawable(new Texture(Gdx.files.internal("Client/core/assets/data/stations/visited-removebg-preview.png")));



        textAreaUN = new TextArea(unvisited,skin);
        textAreaVIS = new TextArea(visited,skin);
        planet(drawable_station_unvisited);
        planet2(drawable_station_unvisited);
        planet3(drawable_station_unvisited);
        planet4(drawable_station_unvisited);
        planet5(drawable_station_unvisited);
        setStartPoint(drawable_station_unvisited);
        stage.addActor(imageButton1);
        stage.addActor(imageButton2);
        stage.addActor(imageButton3);
        stage.addActor(imageButton4);
        stage.addActor(imageButton5);
        stage.addActor(startPoint);

    }

    private void setStartPoint(Drawable drawable_station_unvisited) {
        startPoint = new ImageButton( (drawable_station_unvisited) );
        startPoint.setPosition(160, 200);  //hikeButton is an ImageButton
        startPoint.setSize(PLANET_SIZEX,PLANET_SIZEY);
        startPoint.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final Dialog dialog = new Dialog("Start Point", skin, "dialog") {
                    public void result(Object obj) {
                        if(obj.toString()=="true") ;

                    }
                };
                 dialog.text("Here is the start point");

                dialog.key(Input.Keys.ENTER, true);
                dialog.key(Input.Keys.ESCAPE, false);
                dialog.show(stage);

            }
        });

    }

    private void planet3(Drawable drawable_station_unvisited) {
        imageButton5 = new ImageButton( (drawable_station_unvisited) );
        imageButton5.setPosition(600, 800);  //hikeButton is an ImageButton
        imageButton5.setSize(PLANET_SIZEX,PLANET_SIZEY);
        hoverListener(imageButton5,textAreaUN);
        imageButton5.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final Dialog dialog = new Dialog("Information", skin, "dialog") {
                    public void result(Object obj) {
                        if(obj.toString()=="true") {
                            counter++;
                            hoverListener(imageButton5,textAreaVIS);
                        }

                    }
                };
                actionDialog( dialog,"Planet 3 --> Lorem Ipsum is simply dummy text of the printing and typesetting industry.\n" +
                        " Lorem Ipsum has been the industry's standard dummy\n" +
                        " text ever since the 1500s, when an unknown printer \n" +
                        "took a galley of type and scrambled it to make a type specimen book." +
                        "moves in to attact\n" +
                        "Are you sure you want to jump there?");

            }
        });


    }

    private void planet4(Drawable drawable_station_unvisited) {
        imageButton4 = new ImageButton( (drawable_station_unvisited) );
        imageButton4.setPosition(900, 550);
        imageButton4.setSize(PLANET_SIZEX,PLANET_SIZEX);
        hoverListener(imageButton4,textAreaUN);
        imageButton4.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final Dialog dialog = new Dialog("Information", skin, "dialog") {
                    public void result(Object obj) {
                        if(obj.toString()=="true") {
                            counter++;
                            hoverListener(imageButton4,textAreaVIS);
                        }
                    }
                };
                actionDialog(dialog,"Planet 4 --> Lorem Ipsum is simply dummy text of the printing and typesetting industry.\n" +
                        " Lorem Ipsum has been the industry's standard dummy\n" +
                        " text ever since the 1500s, when an unknown printer took a galley of type\n" +
                        "and scrambled it to make a type specimen book.\n" +
                        "moves in to attact\n" +
                        "Are you sure you want to jump there?");

            }
        });


    }

    private void planet5(Drawable drawable_station_unvisited) {
        imageButton3 = new ImageButton( (drawable_station_unvisited) );
        imageButton3.setPosition(1200, 700);
        imageButton3.setSize(PLANET_SIZEX,PLANET_SIZEX);
        hoverListener(imageButton3,textAreaUN);
        imageButton3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final Dialog dialog = new Dialog("Information", skin, "dialog") {
                    public void result(Object obj) {

                     if(obj.toString() == "true"){
                         isLast = true;
                     }

                    }
                };
                if( counter > 3) {
                    dialog.text("You are allow to travel last planet");
                    dialog.button("JUMP", true);
                    dialog.key(Input.Keys.ENTER, true);
                    hoverListener(imageButton3,textAreaVIS);

                }else {
                    dialog.text("Before you travel here, you have to visit other planets");
                    dialog.button("BACK", false);
                    dialog.key(Input.Keys.ESCAPE, false);
                }

                //actionDialog(dialog, "?");
                dialog.show(stage);

            }
        });

    }

    private void planet2(Drawable drawable_station_unvisited) {
        imageButton2 = new ImageButton( (drawable_station_unvisited) );
        imageButton2.setPosition(500, 550);
        imageButton2.setSize(PLANET_SIZEX,PLANET_SIZEX);
        hoverListener(imageButton2,textAreaUN);
        imageButton2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final Dialog dialog = new Dialog("Information", skin, "dialog") {
                    public void result(Object obj) {

                            if(obj.toString()=="true") {
                                counter++;
                                hoverListener(imageButton2,textAreaVIS);
                            }
                    }
                };

                actionDialog(dialog,"Planet 2 -->Lorem Ipsum is simply dummy text of the printing and typesetting industry.\n"
                                               +" Lorem Ipsum has been the industry's standard dummy\n" +
                                               " text ever since the 1500s, when an unknown printer took a galley of\n" +
                                               " type and scrambled it to make a type specimen book.\n" +
                                               "moves in to attact\n" + "Are you sure you want to jump there");

            }
        });


    }


    private void planet(Drawable drawable_station_unvisited) {
        imageButton1 = new ImageButton( (drawable_station_unvisited) );
        imageButton1.setPosition(250, 700);
        imageButton1.setSize(PLANET_SIZEX,PLANET_SIZEY);
        hoverListener(imageButton1,textAreaUN);
        imageButton1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final Dialog dialog = new Dialog("Information", skin, "dialog") {
                    public void result(Object obj) {
                        if(obj.toString()=="true") {
                            counter++;
                            hoverListener(imageButton1,textAreaVIS);
                        }
                    }
                };
                actionDialog( dialog," Planet 1 --> You Jump to into sector of the nebula beset by a plasma storm. An automated Rebel scout stationed at he beacon" +
                        "moves in to attact\n" +
                        "Are you sure you want to jump there?");
            }
        });

    }

    private void actionDialog(Dialog dialog, String action) {
        dialog.text(action);
        dialog.button("JUMP", true);
        dialog.button("BACK", false);
        dialog.key(Input.Keys.ENTER, true);
        dialog.key(Input.Keys.ESCAPE, false);
        dialog.show(stage);
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
        stage.getBatch().draw(start_ship.getKeyFrame(state), 140, 250, 150,150);
        Gdx.input.setInputProcessor(stage);
        stage.getBatch().end();
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
        stage.dispose();
    }


}
