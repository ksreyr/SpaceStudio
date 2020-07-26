package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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
import de.spaceStudio.client.util.RequestUtils;
import de.spaceStudio.server.model.Pair;
import de.spaceStudio.server.model.Planet;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.model.StopAbstract;
import de.spaceStudio.service.Jumpservices;
import thirdParties.GifDecoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static de.spaceStudio.client.util.RequestUtils.setupRequest;


public class StationsMap extends BaseScreen {

    private final static Logger LOG = Logger.getLogger(StationsMap.class.getName());
    private static final int POSX = 100;
    private static final int POSY = 200;
    private static final int PLANET_SIZEX = 100;
    private static final int PLANET_SIZEY = 100;
    final TextArea textAreaUN, textAreaVIS, textAreaShop;
    private final Stage stage;
    private final Skin skin;
    private final Texture background;
    private final Viewport viewport;
    private final MainClient mainClient;
    private final String unvisited = "unvisited planet";
    private final String visited = "visited planet";
    private final String shopText = "Shopping mall";
    private final Jumpservices jumpservices = new Jumpservices();
    private final StopAbstract currentStop = Global.planet1;
    //
    private final boolean isGameSaved = false;
    Animation<TextureRegion> start_ship;
    boolean isLast, test;
    List<Pair> coord = new ArrayList<>();
    private ImageButton planet1ImgBTN, planet2ImgBTN, planet3ImgBTN, planet4ImgBTN, planet5ImageBTN;
    private ImageButton startPoint;
    private ImageButton shopImg;
    private boolean isPlanet;
    private int counter = 0;
    private float state = 0.0f;
    private ShipSelectScreen shipSelectScreen;
    //
    private Boolean control = false;
    private TextButton saveGameButton;
    private List<Ship> shipList = new ArrayList<>();
    private Sound click;
    private float xShip = 240;
    private float yShip = 150;


    public StationsMap(final MainClient game) {
        super(game);
        this.mainClient = game;
        viewport = new FitViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        background = new Texture(Gdx.files.internal("Client/core/assets/data/maps/sky-map.jpg"));

        start_ship = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/ZDci.gif").read());
        final Drawable drawable_station_unvisited = new TextureRegionDrawable(new Texture(Gdx.files.internal("Client/core/assets/data/stations/unvisited-removebg-preview.png")));
        final Drawable drawable_station_visited = new TextureRegionDrawable(new Texture(Gdx.files.internal("Client/core/assets/data/stations/visited-removebg-preview.png")));
        final Drawable shopStationIcon = new TextureRegionDrawable(new Texture(Gdx.files.internal("Client/core/assets/data/stations/shopping.png")));


        coord.add(new Pair(160f, 200f));  // Start Point
        coord.add(new Pair(250f, 700f));  // Planet 1
        coord.add(new Pair(500f, 550f));  // Planet 2
        coord.add(new Pair(600f, 800f));  // Planet 3
        coord.add(new Pair(900f, 550f));  // Planet 4
        coord.add(new Pair(1200f, 700f));  // Planet 5


        textAreaUN = new TextArea(unvisited, skin);
        textAreaVIS = new TextArea(visited, skin);
        textAreaShop = new TextArea(shopText, skin);
        planet1(drawable_station_unvisited);
        planet2(drawable_station_unvisited);
        planet3(drawable_station_unvisited);
        planet4(drawable_station_unvisited);
        planet5(drawable_station_unvisited);
        shopStation(shopStationIcon);
        setStartPoint(drawable_station_unvisited);


        RequestUtils.hasLanded(Global.currentPlayer);

        stage.addActor(planet1ImgBTN);
        stage.addActor(planet2ImgBTN);
        stage.addActor(planet3ImgBTN);
        if (!Global.ISEASY) {
            stage.addActor(planet5ImageBTN);
            stage.addActor(planet4ImgBTN);
        }
        stage.addActor(startPoint);
        stage.addActor(shopImg);

    }

    private void shopStation(Drawable shopStationIcon) {

        shopImg = new ImageButton((shopStationIcon));
        shopImg.setPosition(400, 900);
        shopImg.setSize(PLANET_SIZEX, PLANET_SIZEX);
        hoverListener(shopImg, textAreaShop);
        shopImg.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Global.currentStop = Global.station1;
                isPlanet = false;

                final Dialog dialog = new Dialog("Information", skin, "dialog") {
                    public void result(Object obj) {

                        if (Objects.equals(obj.toString(), "true")) {
                            counter++;
                            hoverListener(shopImg, textAreaShop);
                            jumpService(Global.station1);
                        }
                    }
                };

                actionDialog(dialog, "Shopping Mall --> Lets shop like there's no tomorrow!!\n"
                        + "moves in to the mall\n" + "Are you sure you want to jump there");

            }
        });


    }

    private void setStartPoint(Drawable drawable_station_unvisited) {
        startPoint = new ImageButton((drawable_station_unvisited));
        startPoint.setPosition(coord.get(0).getLeft(), coord.get(0).getRight());  //hikeButton is an ImageButton

        startPoint.setSize(PLANET_SIZEX, PLANET_SIZEY);
        startPoint.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final Dialog dialog = new Dialog("Start Point", skin, "dialog") {
                    public void result(Object obj) {
                        if (Objects.equals(obj.toString(), "true")) ;

                    }
                };
                dialog.text("Here is the start point");

                dialog.key(Input.Keys.ENTER, true);
                dialog.key(Input.Keys.ESCAPE, false);
                dialog.show(stage);

            }
        });

    }


    private void planet1(Drawable drawable_station_unvisited) {
        planet1ImgBTN = new ImageButton((drawable_station_unvisited));
        planet1ImgBTN.setPosition(coord.get(1).getLeft(), coord.get(1).getRight());
        planet1ImgBTN.setSize(PLANET_SIZEX, PLANET_SIZEY);
        hoverListener(planet1ImgBTN, textAreaUN);
        final Planet planet = Global.planet1;
        planet1ImgBTN.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isPlanet = true;
                Global.currentStop = Global.planet1;
                final Dialog dialog = new Dialog("Information", skin, "dialog") {
                    public void result(Object obj) {
                        if (Objects.equals(obj.toString(), "true")) {
                            counter++;
                            hoverListener(planet1ImgBTN, textAreaVIS);
                            Global.currentStopNumber = 1;
                            jumpService(planet);
                        }
                    }
                };
                actionDialog(dialog, " Planet 1 --> You Jump to into sector of the nebula beset by a plasma storm. An automated Rebel scout stationed at he beacon" +
                        "moves in to attact\n" +
                        "Are you sure you want to jump there?");
            }
        });

    }

    private void planet2(Drawable drawable_station_unvisited) {
        planet2ImgBTN = new ImageButton((drawable_station_unvisited));
        planet2ImgBTN.setPosition(coord.get(2).getLeft(), coord.get(2).getRight());
        planet2ImgBTN.setSize(PLANET_SIZEX, PLANET_SIZEX);
        hoverListener(planet2ImgBTN, textAreaUN);
        final Planet planet = Global.planet2;
        planet2ImgBTN.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isPlanet = true;

                Global.currentStop = Global.planet2;
                final Dialog dialog = new Dialog("Information", skin, "dialog") {
                    public void result(Object obj) {

                        if (Objects.equals(obj.toString(), "true")) {
                            counter++;
                            hoverListener(planet2ImgBTN, textAreaVIS);
                            Global.currentStopNumber = 2;
                            jumpService(planet);
                        }
                    }
                };

                actionDialog(dialog, "Planet 2 -->Lorem Ipsum is simply dummy text of the printing and typesetting industry.\n"
                        + " Lorem Ipsum has been the industry's standard dummy\n" +
                        " text ever since the 1500s, when an unknown printer took a galley of\n" +
                        " type and scrambled it to make a type specimen book.\n" +
                        "moves in to attact\n" + "Are you sure you want to jump there");

            }
        });


    }


    private void planet3(Drawable drawable_station_unvisited) {
        planet3ImgBTN = new ImageButton((drawable_station_unvisited));
        planet3ImgBTN.setPosition(coord.get(3).getLeft(), coord.get(3).getRight());  //hikeButton is an ImageButton
        planet3ImgBTN.setSize(PLANET_SIZEX, PLANET_SIZEY);
        hoverListener(planet3ImgBTN, textAreaUN);
        planet3ImgBTN.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isPlanet = true;
                Global.currentStop = Global.planet3;
                final Dialog dialog = new Dialog("Information", skin, "dialog") {
                    public void result(Object obj) {
                        if (Objects.equals(obj.toString(), "true")) {
                            counter++;
                            hoverListener(planet3ImgBTN, textAreaVIS);
                            Global.currentStopNumber = 3;
                            jumpService(Global.planet3);

                        }

                    }
                };
                actionDialog(dialog, "Planet 3 --> Lorem Ipsum is simply dummy text of the printing and typesetting industry.\n" +
                        " Lorem Ipsum has been the industry's standard dummy\n" +
                        " text ever since the 1500s, when an unknown printer \n" +
                        "took a galley of type and scrambled it to make a type specimen book." +
                        "moves in to attact\n" +
                        "Are you sure you want to jump there?");

            }
        });


    }


    private void planet4(Drawable drawable_station_unvisited) {
        isPlanet = true;
        planet4ImgBTN = new ImageButton((drawable_station_unvisited));
        planet4ImgBTN.setPosition(coord.get(4).getLeft(), coord.get(4).getRight());
        planet4ImgBTN.setSize(PLANET_SIZEX, PLANET_SIZEX);
        hoverListener(planet4ImgBTN, textAreaUN);
        planet4ImgBTN.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isPlanet = true;
                Global.currentStop = Global.planet4;
                final Dialog dialog = new Dialog("Information", skin, "dialog") {
                    public void result(Object obj) {
                        if (Objects.equals(obj.toString(), "true")) {
                            counter++;
                            hoverListener(planet4ImgBTN, textAreaVIS);
                            Global.currentStopNumber = 4;
                            jumpService(Global.planet4);

                        }
                    }
                };
                actionDialog(dialog, "Planet 4 --> Lorem Ipsum is simply dummy text of the printing and typesetting industry.\n" +
                        " Lorem Ipsum has been the industry's standard dummy\n" +
                        " text ever since the 1500s, when an unknown printer took a galley of type\n" +
                        "and scrambled it to make a type specimen book.\n" +
                        "moves in to attact\n" +
                        "Are you sure you want to jump there?");

            }
        });
    }

    private void planet5(Drawable drawable_station_unvisited) {
        isPlanet = true;
        planet5ImageBTN = new ImageButton((drawable_station_unvisited));
        planet5ImageBTN.setPosition(coord.get(5).getLeft(), coord.get(5).getRight());
        planet5ImageBTN.setSize(PLANET_SIZEX, PLANET_SIZEX);
        hoverListener(planet5ImageBTN, textAreaUN);
        planet5ImageBTN.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Global.currentStop = Global.planet5;
                final Dialog dialog = new Dialog("Information", skin, "dialog") {
                    public void result(Object obj) {

                        if (Objects.equals(obj.toString(), "true")) {
                            isLast = true;
                        }

                    }
                };
                if (counter > 3) {
                    dialog.text("You are allow to travel last planet");
                    dialog.button("JUMP", true);
                    dialog.key(Input.Keys.ENTER, true);
                    hoverListener(planet5ImageBTN, textAreaVIS);
                    Global.currentStopNumber = 5;
                    jumpService(Global.planet5);

                } else {
                    dialog.text("Before you travel here, you have to visit other planets");
                    dialog.button("BACK", false);
                    dialog.key(Input.Keys.ESCAPE, false);
                }

                //actionDialog(dialog, "?");
                dialog.show(stage);

            }
        });

    }

    /**
     *
     * @param stopAbstract
     */
    private void jumpService(StopAbstract stopAbstract) {
        stopAbstract.setShips(List.of(Global.currentShipPlayer));
        ArrayList<StopAbstract> toChange = new ArrayList<>();
        toChange.add(currentStop);
        toChange.add(stopAbstract);
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
                    LOG.info("Request Failed");
                }
                LOG.info("statusCode of the Jump: " + statusCode);
                String shipsList = httpResponse.getResultAsString();
                Gson gson = new Gson();
                Ship[] aiArray = gson.fromJson(shipsList, Ship[].class);
                shipList = Arrays.asList(aiArray);

            }

            public void failed(Throwable t) {
                LOG.info("Request Failed Completely");
            }

            @Override
            public void cancelled() {
                LOG.info("request cancelled");
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


    private void saveMessageDialog(Dialog dialog, String action) {
        dialog.text(action);
        dialog.button("OK", false);
        dialog.key(Input.Keys.ENTER, true);
        dialog.key(Input.Keys.ESCAPE, false);
        click.play();
        dialog.show(stage);
    }


    private void hoverListener(final ImageButton img, final TextArea textArea) {
        img.addListener(new HoverListener() {
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

    public void renderSaveGameButton() {
        saveGameButton = new TextButton("Save game", skin);
        saveGameButton.setTransform(true);
        saveGameButton.setScaleX(1.8f);
        saveGameButton.setScaleY(1.5f);
        saveGameButton.setPosition(BaseScreen.WIDTH - 250, BaseScreen.HEIGHT - 180);
        saveGameButton.getLabel().setColor(Color.WHITE);
        saveGameButton.getLabel().setFontScale(1.25f, 1.25f);
        saveGameButton.setSize(90, 50);

        click = Gdx.audio.newSound(Gdx.files.internal("Client/core/assets/data/music/mouseclick.wav"));

        saveGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                LOG.info("Button CLicked");
                click.play();
                Gson gson = new Gson();
                Global.singlePlayerGame.setPlayerShip(Global.currentShipPlayer);
                Global.singlePlayerGame.setLastScreen("MAP");
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

                    @Override
                    public void failed(Throwable t) {
                    }

                    @Override
                    public void cancelled() {
                    }
                });


            }
        });
        stage.addActor(saveGameButton);
    }

    @Override
    public void show() {
        super.show();
        if (Global.IS_SINGLE_PLAYER) {
            renderSaveGameButton();
        }

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        state += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.01f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, BaseScreen.WIDTH, BaseScreen.HEIGHT);


        xShip = coord.get(Global.currentStopNumber).getLeft();
        yShip = coord.get(Global.currentStopNumber).getRight();
        stage.getBatch().draw(start_ship.getKeyFrame(state), xShip, yShip, 150, 150);
        Gdx.input.setInputProcessor(stage);
        stage.getBatch().end();
        stage.act();
        stage.draw();
        if (!shipList.isEmpty() && control == false) {
            try {
                Global.currentShipPlayer = shipList.get(1);
                Global.currentShipGegner = shipList.get(0);
                Global.currentGegner = Global.currentShipGegner.getOwner();
            } catch (Exception e) {
                Global.currentShipPlayer = shipList.get(0);
                Global.currentShipGegner = null;
            }
            control = true;

            if (isPlanet) {
                mainClient.setScreen(new TravelScreen(game));
            } else {
                game.setScreen(new ShopScreen(game));
            }
        }

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);

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
        skin.dispose();
        background.dispose();
        click.dispose();
    }


}
