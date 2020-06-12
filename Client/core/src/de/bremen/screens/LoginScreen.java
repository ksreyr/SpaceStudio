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
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.bremen.MainClient;
import de.bremen.model.Player;
import de.bremen.service.CommunicationService;
import de.bremen.service.RegistrationService;

public class LoginScreen extends BaseScreen {

    private Stage stage;
    private Skin skin;


    private TextArea userName;



    private TextArea newUserName;
    private TextArea userPassword, newUserPassword;
    private TextButton login;
    private TextButton register;
    private Label confirmationMesagge;
    private Label newUserPasswordCheck;

    private TextArea confirmPassword;

    private static final int BUTTON_POSITION_X = 110;
    private static final int WORLD_WIDTH = 800;
    private static final int WORLD_HEIGHT = 600;
    private static final int TEXTBOX_WIDTH = 200;
    private static final int TEXTBOX_HEIGHT = 35;
    private static final int TEXTBOX_LENGTH = 20;

    public static int PLAYER_ID = 1; //How to be handled?

    private boolean isValid;
    CommunicationService communicationService = new CommunicationService();
    RegistrationService registrationService = new RegistrationService();


    private boolean checkListener;

    private final Texture loginBackground;




    public LoginScreen(final MainClient game, AssetManager assetManager) {
        super(game);

        loginBackground = new Texture("ownAssets\\sgx\\backgrounds\\loginBackground.jpg");

        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));



        userName = new TextArea("username", skin);
        userName.setSize(TEXTBOX_WIDTH, TEXTBOX_HEIGHT);
        userName.setPosition(BUTTON_POSITION_X, 360);
        userName.setMaxLength(TEXTBOX_LENGTH); //max chars for username

        userName.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                userName.setText("");
                //checkListener = true;
            }
        });

        userPassword = new TextArea("password", skin);
        userPassword.setPasswordMode(true);

        userPassword.setMaxLength(TEXTBOX_LENGTH);
        userPassword.setSize(TEXTBOX_WIDTH, TEXTBOX_HEIGHT);
        userPassword.setPosition(BUTTON_POSITION_X, 310);

        //clean placeholder
        this.userPassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                userPassword.setText("");
                userPassword.setPasswordCharacter('*');
               // checkListener = true;

            }
        });


//==============================================================================
        newUserName = new TextArea("new user", skin);
        newUserName.setSize(TEXTBOX_WIDTH, TEXTBOX_HEIGHT);
        newUserName.setPosition(500, 360);
        newUserName.setMaxLength(TEXTBOX_LENGTH); //max chars for username
        newUserName.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
                    // textField.appendText(userName.getText());
                }
                checkListener = true;
            }
        });
        newUserName.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                newUserName.setText("");
                //checkListener = true;
            }
        });


        newUserPassword = new TextArea("password", skin);
        newUserPassword.setPasswordMode(true);
        newUserPassword.setMaxLength(TEXTBOX_LENGTH);
        newUserPassword.setSize(TEXTBOX_WIDTH, TEXTBOX_HEIGHT);
        newUserPassword.setPosition(500, 310);

        //clean placeholder
        this.newUserPassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                newUserPassword.setText("");
                newUserPassword.setPasswordCharacter('*');
               // checkListener = true;

            }
        });

        confirmPassword = new TextArea("retype password", skin);
        confirmPassword.setPasswordMode(true);
        confirmPassword.setMaxLength(TEXTBOX_LENGTH);
        confirmPassword.setSize(TEXTBOX_WIDTH, TEXTBOX_HEIGHT);
        confirmPassword.setPosition(500, 260);
        //clean placeholder
        this.confirmPassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                confirmPassword.setText("");
                confirmPassword.setPasswordCharacter('*');
                //checkListener = true;

            }
        });
//=====================================================================
        userValidity();
        createNewUser();
        confirmationMesagge = new Label("", skin);
        confirmationMesagge.setSize(110, 50);
        confirmationMesagge.setPosition(110, 230);


        newUserPasswordCheck = new Label("", skin);
        newUserPasswordCheck.setSize(110, 50);
        newUserPasswordCheck.setPosition(500, 210);




        login.setSize(TEXTBOX_WIDTH, 70);
        login.setPosition(BUTTON_POSITION_X, 150);
        login.getLabel().setColor(Color.FOREST);


        register.setSize(TEXTBOX_WIDTH, 70);
        register.setPosition(500, 150);
        register.getLabel().setColor(Color.BLACK);

        stage.addActor(confirmationMesagge);
        stage.addActor(userName);
        stage.addActor(newUserName);
        stage.addActor(userPassword);
        stage.addActor(newUserPassword);
        stage.addActor(confirmPassword);
        stage.addActor(newUserPasswordCheck);
        stage.addActor(login);
        stage.addActor(register);
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
    public String getNewUserName() {
        if(checkListener){
        return newUserName.getText();
        }else{
            newUserPasswordCheck.setText("Enter an username!");
            newUserPasswordCheck.setColor(Color.RED);
        }
        return newUserName.getText();
    }

    public String getNewUserPassword() {
        return newUserPassword.getText();
    }
    public String getConfirmPassword(){
        return confirmPassword.getText();
    }

    public void createNewUser(){
        register = new TextButton("Register", skin);
        register.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player testPlayer = new Player(3, getNewUserName(),getNewUserPassword());

                if(getNewUserPassword().contentEquals(getConfirmPassword())){
                    isValid = registrationService.createUser(testPlayer, Net.HttpMethods.POST);
                }else{

                    newUserPasswordCheck.setText("Password does not match!");
                    newUserPasswordCheck.setColor(Color.RED);
                }





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
