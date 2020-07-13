package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.net.HttpStatus;
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
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.gson.Gson;
import de.spaceStudio.MainClient;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.server.model.Planet;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.model.StopAbstract;
import thirdParties.GifDecoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static de.spaceStudio.client.util.RequestUtils.setupRequest;

public class StationsMap extends BaseScreen {


    private Stage stage;
    private Skin skin;
    private Texture background;
    private Viewport viewport;
    final TextArea textAreaUN, textAreaVIS;
    private ImageButton planet1ImgBTN, planet2ImgBTN, planet3ImgBTN , planet4ImgBTN, planet5ImageBTN;
    private ImageButton startPoint;
    Animation<TextureRegion> start_ship;

    private static int POSX = 100;
    private static int POSY = 200;
    private MainClient game;

    private  String unvisited = "unvisited planet";
    private  String visited = "visited planet";

    private static int PLANET_SIZEX = 100;
    private static int PLANET_SIZEY = 100;
    private int counter = 0;
    private float state = 0.0f;

    boolean isLast;
    private ShipSelectScreen shipSelectScreen;
    private Boolean control=false;
    //
    private StopAbstract currentStop= Global.planet1;
    private List<Ship> shipList= new ArrayList<Ship>();

    //

    public StationsMap(final MainClient game) {
        super(game);
        this.game = game;
        viewport = new FitViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        background = new Texture(Gdx.files.internal("Client/core/assets/data/maps/sky-map.jpg"));


        start_ship = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/ZDci.gif").read());
        final Drawable drawable_station_unvisited = new TextureRegionDrawable(new Texture(Gdx.files.internal("Client/core/assets/data/stations/unvisited-removebg-preview.png")));
        final Drawable drawable_station_visited = new TextureRegionDrawable(new Texture(Gdx.files.internal("Client/core/assets/data/stations/visited-removebg-preview.png")));


        textAreaUN = new TextArea(unvisited,skin);
        textAreaVIS = new TextArea(visited,skin);
        planet1(drawable_station_unvisited);
        planet2(drawable_station_unvisited);
        planet3(drawable_station_unvisited);
        planet4(drawable_station_unvisited);
        planet5(drawable_station_unvisited);
        setStartPoint(drawable_station_unvisited);
        stage.addActor(planet1ImgBTN);
        stage.addActor(planet2ImgBTN);
        stage.addActor(planet5ImageBTN);
        stage.addActor(planet4ImgBTN);
        stage.addActor(planet3ImgBTN);
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
        planet3ImgBTN = new ImageButton( (drawable_station_unvisited) );
        planet3ImgBTN.setPosition(600, 800);  //hikeButton is an ImageButton
        planet3ImgBTN.setSize(PLANET_SIZEX,PLANET_SIZEY);
        hoverListener(planet3ImgBTN,textAreaUN);
        final Planet planet = Global.planet3;

        planet3ImgBTN.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Global.currentPlanet=Global.planet3;
                final Dialog dialog = new Dialog("Information", skin, "dialog") {
                    public void result(Object obj) {
                        if(obj.toString()=="true") {
                            counter++;
                            hoverListener(planet3ImgBTN,textAreaVIS);
                            jumpService(planet);

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
        planet4ImgBTN = new ImageButton( (drawable_station_unvisited) );
        planet4ImgBTN.setPosition(900, 550);
        planet4ImgBTN.setSize(PLANET_SIZEX,PLANET_SIZEX);
        final Planet planet = Global.planet4;
        hoverListener(planet4ImgBTN,textAreaUN);
        planet4ImgBTN.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final Dialog dialog = new Dialog("Information", skin, "dialog") {
                    public void result(Object obj) {
                        if(obj.toString()=="true") {
                            counter++;
                            hoverListener(planet4ImgBTN,textAreaVIS);
                            jumpService(planet);

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
        planet5ImageBTN = new ImageButton( (drawable_station_unvisited) );
        planet5ImageBTN.setPosition(1200, 700);
        planet5ImageBTN.setSize(PLANET_SIZEX,PLANET_SIZEX);
        hoverListener(planet5ImageBTN,textAreaUN);
        final Planet planet = Global.planet5;
        planet5ImageBTN.addListener(new ChangeListener() {
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
                    hoverListener(planet5ImageBTN,textAreaVIS);
                    jumpService(planet);

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
        planet2ImgBTN = new ImageButton( (drawable_station_unvisited) );
        planet2ImgBTN.setPosition(500, 550);
        planet2ImgBTN.setSize(PLANET_SIZEX,PLANET_SIZEX);
        hoverListener(planet2ImgBTN,textAreaUN);
        final Planet planet = Global.planet2;
        planet2ImgBTN.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final Dialog dialog = new Dialog("Information", skin, "dialog") {
                    public void result(Object obj) {

                            if(obj.toString()=="true") {
                                counter++;
                                hoverListener(planet2ImgBTN,textAreaVIS);
                                jumpService(planet);
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


    private void planet1(Drawable drawable_station_unvisited) {
        planet1ImgBTN = new ImageButton( (drawable_station_unvisited) );
        planet1ImgBTN.setPosition(250, 700);
        planet1ImgBTN.setSize(PLANET_SIZEX,PLANET_SIZEY);
        hoverListener(planet1ImgBTN,textAreaUN);
        final Planet planet = Global.planet1;
        planet1ImgBTN.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final Dialog dialog = new Dialog("Information", skin, "dialog") {
                    public void result(Object obj) {
                        if(obj.toString()=="true") {
                            counter++;
                            hoverListener(planet1ImgBTN,textAreaVIS);
                            jumpService(planet);
                        }
                    }
                };
                actionDialog( dialog," Planet 1 --> You Jump to into sector of the nebula beset by a plasma storm. An automated Rebel scout stationed at he beacon" +
                        "moves in to attact\n" +
                        "Are you sure you want to jump there?");
            }
        });

    }

    private void jumpService(Planet planet) {
        ArrayList<StopAbstract> toChange = new ArrayList<StopAbstract>();
        toChange.add(currentStop);
        toChange.add(planet);
        if (shipList.isEmpty()) {
            makeJumpRequest(toChange, Net.HttpMethods.POST);
        }
    }

    public void makeJumpRequest(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        final String url = Global.SERVER_URL + Global.MAKEJUMP_CREATION_ENDPOINT;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed");
                }
                System.out.println("statusCode of the Jump: " + statusCode);
                String shipsList = httpResponse.getResultAsString();
                Gson gson = new Gson();
                Ship[] aiArray = gson.fromJson(shipsList, Ship[].class);
                shipList = Arrays.asList(aiArray);

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
        if(!shipList.isEmpty()&&control==false){
            Global.currentShip=shipList.get(1);
            Global.currentShipGegner=shipList.get(0);
            game.setScreen(new CombatScreen(game));

            control=true;
        }
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
        skin.dispose();
        stage.dispose();
    }


}
