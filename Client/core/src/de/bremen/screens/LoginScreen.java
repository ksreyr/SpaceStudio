package de.bremen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.bremen.MainClient;
import de.bremen.model.Player;
import de.bremen.service.CommunicationService;

public class LoginScreen extends BaseScreen {

    private Stage stage;
    private Skin skin;
    private TextArea userName, newUser;
    private TextArea userPassword, newUserPassword;
    private TextButton login;
    private TextButton registerUser;
    private Label confirmationMesagge;

    private TextArea confirmPassword;

    private static final int BUTTON_POSITION_X = 110;
    private static final int WORLD_WIDTH = 800;
    private static final int WORLD_HEIGHT = 600;

    private boolean isValid;
    CommunicationService communicationService = new CommunicationService();



    private final Texture loginBackground;




    public LoginScreen(final MainClient game, AssetManager assetManager) {
        super(game);

        loginBackground = new Texture("ownAssets\\sgx\\backgrounds\\loginBackground.jpg");

        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));



        userName = new TextArea("Registered user", skin);
        userName.setSize(200, 35);
        userName.setPosition(BUTTON_POSITION_X, 330);
        userName.setMaxLength(10); //max chars for username
        userName.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
                   // textField.appendText(userName.getText());

                }
            }
        });


        userName.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                userName.setText("");
            }
        });

        userPassword = new TextArea("Password", skin);
        userPassword.setPasswordMode(true);

        userPassword.setMaxLength(20);
        userPassword.setSize(200, 35);
        userPassword.setPosition(BUTTON_POSITION_X, 280);

        //clean placeholder
        this.userPassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                userPassword.setText("");
                userPassword.setPasswordCharacter('*');

            }
        });


//==============================================================================
        newUser = new TextArea("New User", skin);
        newUser.setSize(200, 35);
        newUser.setPosition(500, 330);
        newUser.setMaxLength(10); //max chars for username
        newUser.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
                    // textField.appendText(userName.getText());
                }
            }
        });
        newUser.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                newUser.setText("");
            }
        });


        newUserPassword = new TextArea("Type a password", skin);
        newUserPassword.setPasswordMode(true);
        //newUserPassword.setPasswordCharacter('*');
        newUserPassword.setMaxLength(20);
        newUserPassword.setSize(200, 35);
        newUserPassword.setPosition(500, 280);

        //clean placeholder
        this.newUserPassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                newUserPassword.setText("");
                newUserPassword.setPasswordCharacter('*');

            }
        });

        confirmPassword = new TextArea("confirm the password", skin);
        confirmPassword.setPasswordMode(true);

        confirmPassword.setMaxLength(20);
        confirmPassword.setSize(200, 35);
        confirmPassword.setPosition(500, 230);
        //clean placeholder
        this.confirmPassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                confirmPassword.setText("");
                confirmPassword.setPasswordCharacter('*');

            }
        });
//=====================================================================
        userValidity();
        createNewUser();
        confirmationMesagge = new Label("", skin);
        //DrawComponents
        confirmationMesagge.setSize(110, 50);
        confirmationMesagge.setPosition(110, 230);




        login.setSize(200, 70);
        login.setPosition(BUTTON_POSITION_X, 150);
        login.getLabel().setColor(Color.FOREST);


        registerUser.setSize(200, 70);
        registerUser.setPosition(500, 150);

        stage.addActor(confirmationMesagge);
        stage.addActor(userName);
        stage.addActor(newUser);
        stage.addActor(userPassword);
        stage.addActor(newUserPassword);
        stage.addActor(confirmPassword);
        stage.addActor(login);
        stage.addActor(registerUser);
    }

    /**
     * whether user is valid or not
     */
    private void userValidity() {

        login = new TextButton("Log in", skin);
        login.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player testPlayer = new Player(3, getUserName(), getUserPassword());
                isValid = communicationService.sendRequest(testPlayer, Net.HttpMethods.POST);
                if (!isValid) {

                    confirmationMesagge.setText("invalid username or password!");
                    confirmationMesagge.setColor(Color.RED);

                }

            }
        });

    }

    public void createNewUser(){
        registerUser = new TextButton("Register", skin);
        registerUser.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player testPlayer = new Player(3, getUserName(), getUserPassword());
                isValid = communicationService.sendRequest(testPlayer, Net.HttpMethods.POST);


            }
        });
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
    public void render(float delta) {
        stage.getBatch().begin();
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().draw(loginBackground,0,0,WORLD_WIDTH,WORLD_HEIGHT);
        if (isValid) {
            game.setScreen(new LoadingScreen(game));
        }
        stage.getBatch().end();
        stage.act();
        stage.draw();



    }

}
