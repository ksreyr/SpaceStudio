package de.bremen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.holidaystudios.tools.GifDecoder;
import de.bremen.MainClient;
import de.bremen.service.CommunicationService;
import de.bremen.service.RegistrationService;
import de.spaceStudio.server.model.Player;
import sun.awt.image.GifImageDecoder;

public class LoginScreen extends BaseScreen {

    private Stage stage;
    private Skin skin;


    private TextArea userName,newUserName;
    private TextArea userPassword, newUserPassword,confirmPassword;
    private TextButton login;
    private TextButton register;
    private Label loginConfirmation;
    private Label registerConfirmation;
    //private final Texture loginBackground;

    private Label okay;
    private Texture registerConfirmBG;

    private static final int BUTTON_POSITION_X = 110;
    private static final int WORLD_WIDTH = 800;
    private static final int WORLD_HEIGHT = 600;
    private static final int TEXTBOX_WIDTH = 200;
    private static final int TEXTBOX_HEIGHT = 35;
    private static final int TEXTBOX_LENGTH = 20;

    private boolean isValid, isCreated;

    CommunicationService communicationService = new CommunicationService();
    RegistrationService registrationService = new RegistrationService();

    private Music music;
    private Sound mouseClick;
    private Sound keyboard;


    Animation<TextureRegion> animation;
    private float state=0.0f;



    private int counter = 0;
    public LoginScreen(final MainClient game, AssetManager assetManager) {
        super(game);


        animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("data/spaceship.gif").read());


        mouseClick = Gdx.audio.newSound(Gdx.files.internal("Client\\core\\assets\\data\\music\\mouseclick.wav"));
        keyboard = Gdx.audio.newSound(Gdx.files.internal("Client\\core\\assets\\data\\music\\keyboard0.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("Client\\core\\assets\\data\\music\\through_space.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();

        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));


        existedUserName();
        existedUserPassword();



        newUserName();
        newUserPassword();
        confirmPassword();

        userValidity();
        createNewUser();
        loginConfirmation = new Label("", skin);
        loginConfirmation.setSize(110, 50);
        loginConfirmation.setPosition(110, 230);


        registerConfirmation = new Label("", skin);
        registerConfirmation.setSize(110, 50);
        registerConfirmation.setPosition(500, 210);


        login.setSize(TEXTBOX_WIDTH, 70);
        login.setPosition(BUTTON_POSITION_X, 150);
        login.getLabel().setColor(Color.FOREST);



        register.setSize(TEXTBOX_WIDTH, 70);
        register.setPosition(500, 150);
        register.getLabel().setColor(Color.BLACK);


        stage.addActor(loginConfirmation);
        stage.addActor(userName);
        stage.addActor(newUserName);
        stage.addActor(userPassword);
        stage.addActor(newUserPassword);
        stage.addActor(confirmPassword);
        stage.addActor(registerConfirmation);
        stage.addActor(login);
        stage.addActor(register);
    }


    private void existedUserName() {
        userName = new TextArea("username", skin);
        userName.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                keyboard.play();
            }
        });
        userName.setSize(TEXTBOX_WIDTH, TEXTBOX_HEIGHT);
        userName.setPosition(BUTTON_POSITION_X, 360);
        userName.setMaxLength(TEXTBOX_LENGTH); //max chars for username

        userName.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                userName.setText("");
                loginConfirmation.setText("");
            }

        });
    }

    private void existedUserPassword() {
        userPassword = new TextArea("password", skin);
        userPassword.setPasswordMode(true);
        userPassword.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                keyboard.play();
            }
        });

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
                loginConfirmation.setText("");

            }
        });
    }

    private void confirmPassword() {
        confirmPassword = new TextArea("retype password", skin);
        textFieldListener(confirmPassword);
        confirmPassword.setPosition(500, 260);
        //clean placeholder
        this.confirmPassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                confirmPassword.setText("");
                registerConfirmation.setText("");
            }
        });
    }

    private void newUserName() {
        newUserName = new TextArea("new user", skin);
        newUserName.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                keyboard.play();
            }
        });
        newUserName.setSize(TEXTBOX_WIDTH, TEXTBOX_HEIGHT);
        newUserName.setPosition(500, 360);
        newUserName.setMaxLength(TEXTBOX_LENGTH); //max chars for username
        newUserName.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                newUserName.setText("");
                registerConfirmation.setText("");
            }
        });
    }

    private void newUserPassword() {
        newUserPassword = new TextArea("password", skin);
        textFieldListener(newUserPassword);
        newUserPassword.setPosition(500, 310);

        //clean placeholder
        this.newUserPassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                newUserPassword.setText("");
                registerConfirmation.setText("");
            }
        });
    }

    private void textFieldListener(final TextArea newUserPassword) {
        newUserPassword.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                keyboard.play();
                newUserPassword.setPasswordCharacter('*');
            }
        });
        newUserPassword.setPasswordMode(true);
        newUserPassword.setMaxLength(TEXTBOX_LENGTH);
        newUserPassword.setSize(TEXTBOX_WIDTH, TEXTBOX_HEIGHT);
    }


    private void userValidity() {
        login = new TextButton("Log in", skin);
        login.addCaptureListener(new ChangeListener() {


            @Override
            public void changed(ChangeEvent event, Actor actor) {

                Player player = Player.builderPlayer()
                        .name(getUserName())
                        .password(getUserPassword())
                        .buildPlayer();
                isValid = communicationService.sendRequest(player, Net.HttpMethods.POST);

                if (!isValid && counter != 0) {
                    loginConfirmation.setText("invalid username or password!");
                    loginConfirmation.setColor(Color.RED);

                }
                mouseClick.play();
            }
        });

        counter++;
    }

    public String getNewUserName() {
        if (!newUserName.getText().contentEquals("new user")) {

            return newUserName.getText();
        }
        return null;
    }

    public String getNewUserPassword() { return newUserPassword.getText(); }

    public String getConfirmPassword() { return confirmPassword.getText();}

    public void createNewUser() {
        register = new TextButton("Register", skin);


        register.addCaptureListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {

                Player p2 = Player.builderPlayer()
                        .name(getNewUserName())
                        .password(getNewUserPassword())
                        .buildPlayer();

                if (p2.getName() == null || p2.getName().contentEquals("")) {
                    registerConfirmation.setText("Enter an username!");
                    registerConfirmation.setColor(Color.RED);
                    mouseClick.play();
                } else {
                    mouseClick.play();

                    if (p2.getPassword().contentEquals(getConfirmPassword())) {
                        isValid = registrationService.createUser(p2, Net.HttpMethods.POST);
                    } else {

                        registerConfirmation.setText("Password does not match!");
                        registerConfirmation.setColor(Color.RED);
                        registerConfirmation.clear();
                    }
                }

            }
        });
        isCreated = true;
    }

    public String getUserName() { return userName.getText(); }

    public String getUserPassword() {
        userPassword.setPasswordMode(true);
        return userPassword.getText();
    }
    @Override
    public void show() { Gdx.input.setInputProcessor(stage); }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        skin.dispose();
        music.dispose();
        stage.dispose();
        mouseClick.dispose();
    }

    @Override
    public void render(float delta) {

        state += Gdx.graphics.getDeltaTime();
        stage.getBatch().begin();
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().draw(animation.getKeyFrame(state),0.0f,0.0f);


        if (isValid) {
            game.setScreen(new LoadingScreen(game));
        }
        stage.getBatch().end();
        stage.act();
        stage.draw();


    }

}
