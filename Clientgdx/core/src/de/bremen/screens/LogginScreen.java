package de.bremen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.bremen.MainClient;
import de.bremen.model.Player;

public class LogginScreen extends BaseScreen {
    // Game Variables
    private Stage stage;
    private Skin skin;

    // Game Variables
    private Label myIP;
    private TextArea ip;
    private TextArea userName;
    private TextArea userPassword;
    private TextButton senden;
    private Label confirmationMesagge;

    private boolean gelogg=false,login=false;

    //Sockets
    private String IPAdresse = "127.0.0.1";

    Net.Protocol protocol = Net.Protocol.TCP;

    //Contructor
    public LogginScreen(final MainClient game) {
        super(game);

        stage = new Stage(new FitViewport(800, 600));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        myIP = new Label(IPAdresse, skin);

        ip = new TextArea("127.0.0.1", skin);
        userName = new TextArea("Name", skin);
        userPassword = new TextArea("Password", skin);

        confirmationMesagge = new Label("Mesagge", skin);

        //BUTTON Local server
        senden = new TextButton("Senden", skin);
        senden.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Event triggered");
                //Client Send
                String userNametoSend = "";
                String userPasswordtoSend = "";
                if (userName.getText().length() == 0 &&
                        userPassword.getText().length() == 0) {
                    return;
                } else {
                    userNametoSend = userName.getText();
                    userPasswordtoSend = userPassword.getText();
                    Player testPlayer = new Player(3, userNametoSend, userPasswordtoSend);
                    sendRequest(testPlayer, Net.HttpMethods.POST);
                    confirmationMesagge.setText(String.valueOf(login));

                }
            }

        });

        //DrawComponents
        confirmationMesagge.setSize(200, 50);
        confirmationMesagge.setPosition(220, 410);

        myIP.setSize(200, 50);
        myIP.setPosition(220, 350);

        ip.setSize(200, 50);
        ip.setPosition(220, 290);

        userName.setSize(200, 50);
        userName.setPosition(220, 220);

        userPassword.setSize(200, 50);
        userPassword.setPosition(220, 150);

        senden.setSize(200, 70);
        senden.setPosition(220, 50);

        stage.addActor(confirmationMesagge);
        stage.addActor(myIP);
        stage.addActor(ip);
        stage.addActor(userName);
        stage.addActor(userPassword);
        stage.addActor(senden);
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
                System.out.println("statusCode: " +statusCode);
                String responseJson = httpResponse.getResultAsString();
                try {
                    System.out.println("Respons: " + responseJson);
                    login=Boolean.parseBoolean(responseJson);

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
        if (login) {
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
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (login) {
            game.setScreen(new LoadingScreen(game));
        }
        stage.act();
        stage.draw();
    }

}
