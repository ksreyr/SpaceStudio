package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.spaceStudio.MainClient;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.server.model.Player;
import thirdParties.GifDecoder;


import java.util.logging.Logger;

import static de.spaceStudio.client.util.Global.currentPlayer;
import static de.spaceStudio.client.util.RequestUtils.setupRequest;

public class LoginScreen extends BaseScreen {

    private final static Logger LOG = Logger.getLogger(LoginScreen.class.getName());

    private Stage stage;
    private Skin skin;
    private TextField userName, newUserName;
    private TextField userPassword, newUserPassword, confirmPassword;
    private TextButton login;
    private TextButton register;
    private Label loginConfirmation;
    private Label registerConfirmation;
    private TextButton mute, exit;
    private Viewport viewport;

    Animation<TextureRegion> animation;
    private Music music;
    private Sound mouseClick;
    private Sound keyboard;

    private boolean isPressed = false;

    private static final int BUTTON_LOGIN_X = (BaseScreen.WIDTH / 3);
    private static final float BUTTON_REGISTER_X = (BaseScreen.WIDTH / 2) + 100;


    private static final int TEXTBOX_WIDTH = 200;
    private static final int TEXTBOX_HEIGHT = 35;
    private static final int TEXTBOX_LENGTH = 20;


    private boolean isValid = false;
    private float state = 0.0f;

    public LoginScreen(final MainClient game, AssetManager assetManager) {

        super(game);

        animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Client/core/assets/data/gifs/space_name.gif").read());

        mouseClick = Gdx.audio.newSound(Gdx.files.internal("Client/core/assets/data/music/mouseclick.wav"));
        keyboard = Gdx.audio.newSound(Gdx.files.internal("Client/core/assets/data/music/keyboard0.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("Client/core/assets/data/music/through_space.mp3"));
        music.setLooping(true);
        music.setVolume(0.09f);
        music.play();

        viewport = new FitViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));


        existedUserName();
        existedUserPassword();

        newUserName();
        newUserPassword();
        confirmPassword();

        login = new TextButton("Log in", skin);
        setTextButton(login, TEXTBOX_WIDTH, 70, BUTTON_LOGIN_X, 300);
        login.getLabel().setColor(Color.FOREST);


        loginConfirmation = new Label("", skin);
        loginConfirmation.setSize(110, 50);
        loginConfirmation.setPosition(BUTTON_LOGIN_X, 230);

        registerConfirmation = new Label("", skin);
        registerConfirmation.setSize(110, 50);
        registerConfirmation.setPosition(BUTTON_REGISTER_X, 350);

        register = new TextButton("Register", skin);
        setTextButton(register, TEXTBOX_WIDTH, 70, (int) BUTTON_REGISTER_X, 300);
        register.getLabel().setColor(Color.BLACK);

        mute = new TextButton("mute", skin);
        mute.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (music.isPlaying()) {
                    music.pause();
                    mute.setText("unmute");
                } else {
                    music.play();
                    mute.setText("mute");
                }
            }
        });
        mute.setSize(75, 30);
        mute.setPosition(80, 100, 100);


        exit = new TextButton("exit", skin);
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        exit.setSize(75, 30);
        exit.setPosition(120, 100);


        stage.addActor(loginConfirmation);
        stage.addActor(userName);
        stage.addActor(newUserName);
        stage.addActor(userPassword);
        stage.addActor(newUserPassword);
        stage.addActor(confirmPassword);
        stage.addActor(registerConfirmation);
        stage.addActor(login);
        stage.addActor(register);
        stage.addActor(mute);
        stage.addActor(exit);

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);

    }

    private void existedUserName() {
        userName = new TextField("username", skin);
        userName.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                keyboard.play();
            }
        });
        userName.setSize(TEXTBOX_WIDTH, TEXTBOX_HEIGHT);
        userName.setPosition(BUTTON_LOGIN_X, 500);
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
        userPassword = new TextField("password", skin);
        userPassword.setPasswordMode(true);
        userPassword.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                keyboard.play();
            }
        });

        userPassword.setMaxLength(TEXTBOX_LENGTH);
        userPassword.setSize(TEXTBOX_WIDTH, TEXTBOX_HEIGHT);
        userPassword.setPosition(BUTTON_LOGIN_X, 450);

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


    private void newUserName() {
        newUserName = new TextField("new user", skin);
        nameTextListener(newUserName);
        newUserName.setSize(TEXTBOX_WIDTH, TEXTBOX_HEIGHT);
        newUserName.setPosition(BUTTON_REGISTER_X, 500);
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

    private void nameTextListener(TextField newUserName) {
        newUserName.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                keyboard.play();
            }
        });
    }

    private void newUserPassword() {
        newUserPassword = new TextField("password", skin);
        textFieldListener(newUserPassword);
        newUserPassword.setPosition(BUTTON_REGISTER_X, 450);

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

    private void confirmPassword() {
        confirmPassword = new TextField("retype password", skin);
        textFieldListener(confirmPassword);
        confirmPassword.setPosition(BUTTON_REGISTER_X, 400);

        this.confirmPassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                confirmPassword.setText("");
                registerConfirmation.setText("");
            }
        });
    }

    private void textFieldListener(final TextField newUserPassword) {
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


    private void setTextButton(TextButton textButton, int width, int height, int but_log_x, int but_log_y) {
        textButton.setSize(width, height);
        textButton.setPosition(but_log_x, but_log_y);

    }

    private void loginUser() {
        login.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                loginConfirmation.setText("login...");
                loginConfirmation.setColor(Color.CYAN);

                mouseClick.play();
                currentPlayer = Player.builderPlayer()
                        .name(getUserName())
                        .password(getUserPassword())
                        .buildPlayer();

                final Json json = new Json();

                json.setOutputType(JsonWriter.OutputType.json);
                LOG.info("JSON to send " + json.toJson(currentPlayer));
                final String requestJson = json.toJson(currentPlayer);

                final String url = Global.SERVER_URL + Global.PLAYER_LOGIN_ENDPOINT;
                Net.HttpRequest request = setupRequest(url, requestJson, Net.HttpMethods.POST);
                Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {

                    public void handleHttpResponse(Net.HttpResponse httpResponse) {
                        int statusCode = httpResponse.getStatus().getStatusCode();
                        LOG.info("statusCode: " + statusCode);
                        String responseJson = httpResponse.getResultAsString();
                        boolean isValid = Boolean.parseBoolean(responseJson);
                        if (statusCode != HttpStatus.SC_OK || !isValid) {
                            loginConfirmation.setText("invalid username or password!");
                            loginConfirmation.setColor(Color.RED);
                            LOG.info("Credentials invalid or server down");
                        } else {
                            LOG.info("Login success");
                            isPressed = true;
                        }
                    }

                    @Override
                    public void failed(Throwable t) {
                    }

                    @Override
                    public void cancelled() {
                        LOG.severe("Request Canceled");
                    }
                });
            }
        });
    }

    public void createNewUser() {
        register.addListener(new ChangeListener() {

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
                        final String createUserURL = Global.SERVER_URL + Global.PLAYER_ENDPOINT;
                        final Json json = new Json();

                        json.setOutputType(JsonWriter.OutputType.json);
                        LOG.info("JSON to send " + json.toJson(p2));
                        final String requestJson = json.toJson(p2);

                        Net.HttpRequest request = setupRequest(createUserURL, requestJson, Net.HttpMethods.POST);

                        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {

                            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                                int statusCode = httpResponse.getStatus().getStatusCode();
                                String responseJson = httpResponse.getResultAsString();
                                LOG.info(responseJson);
                                if (statusCode == HttpStatus.SC_OK && responseJson.equals("201 CREATED")) {
                                    registerConfirmation.setText("Successful!");
                                    registerConfirmation.setColor(Color.GREEN);
                                    LOG.info("Request Success");
                                } else {
                                    LOG.info("statusCode: " + statusCode);
                                    registerConfirmation.setText("Server not available or bad request");
                                    registerConfirmation.setColor(Color.YELLOW);

                                }
                            }

                            public void failed(Throwable t) {
                                registerConfirmation.setText("Server not available or bad request");
                                registerConfirmation.setColor(Color.YELLOW);
                                LOG.severe("Request failed");
                            }

                            @Override
                            public void cancelled() {
                                System.out.println("request cancelled");
                            }
                        });


                    } else {

                        registerConfirmation.setText("Password does not match!");
                        registerConfirmation.setColor(Color.RED);
                        registerConfirmation.clear();
                    }
                }

            }
        });

    }


    public String getNewUserName() {
        if (!newUserName.getText().contentEquals("new user")) {
            return newUserName.getText();
        }
        return null;
    }

    public String getNewUserPassword() {
        return newUserPassword.getText();
    }

    public String getConfirmPassword() {
        return confirmPassword.getText();
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
        loginUser();
        createNewUser();
    }

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
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.01f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (isPressed) {
            game.setScreen(new LoadingScreen(game));
        }
        stage.getBatch().draw(animation.getKeyFrame(state), 0.0f, 0.0f, BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage.getBatch().end();
        stage.act();

        stage.draw();
    }
}
