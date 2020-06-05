package de.bremen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.bremen.MainClient;
import de.bremen.model.Player;

public class LoginScreen extends BaseScreen {
    // Game Variables
    private Stage stage;
    private Skin skin;

    private TextArea userName;
    private TextArea userPassword;
    private TextButton confirmUser;
    private Label confirmationMesagge;


    private int buttonPositionX = 310;

    private final int worldWidth = 800;
    private final int worldHeight = 600;

    private boolean isValid = false;



    public LoginScreen(final MainClient game) {
        super(game);

        stage = new Stage(new FitViewport(worldWidth,worldHeight));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        userName = new TextArea("Name", skin);
        userPassword = new TextArea("Password", skin);
        confirmationMesagge = new Label("", skin);


        userValidity();

        //DrawComponents
        confirmationMesagge.setSize(200, 50);
        confirmationMesagge.setPosition(550,70);

        //clean placeholder
        userName.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                userName.setText("");
            }
        });

        userName.setSize(200, 35);
        userName.setPosition(buttonPositionX, 220);

        //clean placeholder
        this.userPassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                userPassword.setText("");
            }
        });


        userPassword.setSize(200, 35);
        userPassword.setPosition(buttonPositionX, 150);

        confirmUser.setSize(200, 70);
        confirmUser.setPosition(buttonPositionX, 50);
        stage.addActor(confirmationMesagge);
        stage.addActor(userName);
        stage.addActor(userPassword);
        stage.addActor(confirmUser);
    }

    /**
     * whether user is valid or not
     */
    private void userValidity(){

        confirmUser = new TextButton("Log in", skin);
        confirmUser.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player testPlayer = new Player(3, getUserName(userName), getUserPassword(userPassword));
                sendRequest(testPlayer, Net.HttpMethods.POST);

                if(!isValid ){ confirmationMesagge.setText("invalid username or password!"); }
            }
        });

    }

    /**
     *
     * @param username provided by player
     * @return
     */
    private String  getUserName(final TextArea username){
        if(username == null || username.getText().length() < 1){
            System.out.println("invalid name or password!");
        }

        return userName.getText();
    }

    /**
     *
     * @param userpassword provided by player
     * @return
     */
    private String  getUserPassword(final TextArea userpassword){
       if(userpassword == null || userpassword.getText().length() < 1){
            System.out.println("invalid name or password!");
        }
        return userpassword.getText();
    }


    public void sendRequest(Object requestObject, String method) {

        final Json json = new Json();

        json.setOutputType(JsonWriter.OutputType.json);
        System.out.println("JSON to send "+json.toJson(requestObject));
        final String requestJson = json.toJson(requestObject); // this is just an example

        Net.HttpRequest request = new Net.HttpRequest(method);
        final String url = "http://127.0.0.1:8080/player/login";
        request.setUrl(url);

        request.setContent(requestJson);

        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {

            public void handleHttpResponse(Net.HttpResponse httpResponse) {

                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed");
                    return;
                }
                System.out.println("statusCode: " + statusCode);
                String responseJson = httpResponse.getResultAsString();
                try {
                    System.out.println("Response: " + responseJson);
                    isValid =Boolean.parseBoolean(responseJson);

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }
            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
        if (isValid ) {
            game.setScreen(new LoadingScreen(game));
        }

    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

       if (isValid ) {  game.setScreen(new LoadingScreen(game)); }
       stage.act();
       stage.draw();


       }

}
