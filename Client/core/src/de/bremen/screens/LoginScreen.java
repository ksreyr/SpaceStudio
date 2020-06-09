package de.bremen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.bremen.MainClient;
import de.bremen.model.Player;
import de.bremen.service.CommunicationService;

public class LoginScreen extends BaseScreen {
    // Game Variables
    private Stage stage;

    public Skin getSkin() {
        return skin;
    }

    private Skin skin;

    private TextArea userName;
    private TextArea userPassword;
    private TextButton confirmUser;
    private Label confirmationMesagge;

    private boolean isPasswordShown = false;

    private int buttonPositionX = 310;

    private final int worldWidth = 800;
    private final int worldHeight = 600;

    private boolean isValid = false;
    CommunicationService communicationService = new CommunicationService();

    public LoginScreen(final MainClient game) {
        super(game);

        stage = new Stage(new FitViewport(worldWidth, worldHeight));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        userName = new TextArea("Name", skin);


        userPassword = new TextArea("Password", skin);
        userPassword.setPasswordMode(true);
        userPassword.setPasswordCharacter('*');



        userValidity();


        confirmationMesagge = new Label("", skin);

        //clean placeholder
        userName.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                userName.setText("");
            }
        });

        userName.setSize(200, 35);
        userName.setPosition(buttonPositionX, 330);

        //clean placeholder
        this.userPassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                userPassword.setText("");

            }
        });


        userPassword.setSize(200, 35);
        userPassword.setPosition(buttonPositionX, 280);
        userPassword.setPasswordMode(true);

        confirmUser.setSize(200, 70);
        confirmUser.setPosition(buttonPositionX, 150);

        //DrawComponents
        confirmationMesagge.setSize(200, 50);
        confirmationMesagge.setPosition(300, 230);

        stage.addActor(confirmationMesagge);
        stage.addActor(userName);
        stage.addActor(userPassword);
        stage.addActor(confirmUser);
    }

    /**
     * whether user is valid or not
     */
    private void userValidity() {

        confirmUser = new TextButton("Log in", getSkin());
        confirmUser.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player testPlayer = new Player(3, getUserName(), getUserPassword());
                isValid = communicationService.sendRequest(testPlayer, Net.HttpMethods.POST);

            }
        });

    }

    public void setUserName(String username) {
        userName.setText(username);
    }

    public void setPassword(String password) {
        userName.setText(password);
    }


    public String getUserName() {

        return userName.getText();
    }

    public String getUserPassword() {
        userPassword.setPasswordMode(true);
        return userPassword.getText();
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
        if (isValid) {
            game.setScreen(new LoadingScreen(game));
        }

        stage.act();
        stage.draw();


    }

}
