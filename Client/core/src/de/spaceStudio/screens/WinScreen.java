package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.spaceStudio.MainClient;
import de.spaceStudio.assets.StyleNames;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.util.GdxUtils;

public class WinScreen extends BaseScreen {

        private MainClient universeMap;
        private final AssetManager assetManager;
        private MainClient mainClient;
        private Viewport viewport;
        private Stage stage;

        private Skin sgxSkin, sgxSkin2;
        private TextureAtlas gamePlayAtlas;

        private Sound click;

        private Skin skin;
        private SpriteBatch batch;

        private Texture playerShip;
        private Texture background;

        private Skin  skinButton;

        float x=0;

       private boolean isWin;

        public WinScreen(MainClient mainClient) {
            super(mainClient);
            this.universeMap = mainClient;
            this.mainClient = mainClient;
            assetManager = universeMap.getAssetManager();
        }

        @Override
        public void show() {

            viewport = new FitViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT);
            stage = new Stage(viewport, universeMap.getBatch());
            click = Gdx.audio.newSound(Gdx.files.internal("Client/core/assets/data/music/mouseclick.wav"));

            sgxSkin2 = new Skin(Gdx.files.internal("Client/core/assets/ownAssets/sgx/skin/sgx-ui.json"));
            skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
            skinButton = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

            background = new Texture("Client/core/assets/combatAssets/CombatBG.jpg");
            playerShip = new Texture("Client/core/assets/combatAssets/blueships_fulled.png");

            final Dialog dialog = new Dialog("Information",skin) {
                public void result(Object obj) {
                    if(obj.toString()=="true") {
                        //hoverListener(planet3ImgBTN,textAreaVIS);

                    }

                }
            };
            if(isWin){
                actionDialog(dialog, "Congratulations! You won the game.\n" +
                        " You might flight to another planet to fight \n" +
                        " You might save the game continue later on\n" +
                        " You might destroy game and quit\n" +
                        "Have a fun!");
            }else {
                actionDialog(dialog, "Unfortunetly you lost the game\n" +
                        " You have lost 20 percent of your resoources \n" +
                        " You might save the game continue later on\n" +
                        " You might destroy game and quit\n" +
                        "Have a fun!");
            }




            Gdx.input.setInputProcessor(stage);
            TextButton moveToMap = new TextButton("Move to Map",sgxSkin2, StyleNames.EMPHASISTEXTBUTTON);
            moveToMap.setPosition(BaseScreen.WIDTH-1500,200);
            moveToMap.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    mainClient.setScreen(new ShipSelectScreen(mainClient));

                }
            });


            TextButton exit = new TextButton("Exit",sgxSkin2,StyleNames.EMPHASISTEXTBUTTON);
            exit.setPosition(BaseScreen.WIDTH-400,200);
            exit.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Gdx.app.exit();
                }
            });

            TextButton saveGame = new TextButton(" Save Game ", sgxSkin2, StyleNames.EMPHASISTEXTBUTTON);
            saveGame.setPosition(1000,200);
            saveGame.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Global.IS_SINGLE_PLAYER = false;
                    mainClient.setScreen(new MenuScreen(mainClient));
                }
            });

            stage.addActor(saveGame);
            stage.addActor(exit);
            stage.addActor(moveToMap);

        }



        private void actionDialog(Dialog dialog, String action) {
            dialog.text(action);
            //dialog.button("", false);
            //dialog.button("Move to Map", false);
            //dialog.button("Save Game", false);
            dialog.key(Input.Keys.ENTER, false);
            dialog.key(Input.Keys.ESCAPE, false);
            dialog.show(stage);
        }


        // Called when the screen should render itself.
        @Override
        public void render(float delta) {
            GdxUtils.clearScreen();
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Gdx.input.setInputProcessor(stage);
            stage.getBatch().begin();
            stage.getBatch().draw(background, 0, 0, BaseScreen.WIDTH, BaseScreen.HEIGHT);
            stage.getBatch().draw(playerShip, BaseScreen.HEIGHT/2,300,700,700);

            stage.getBatch().end();


            stage.act();
            stage.draw();
        }



        // Called when the Application is resized.
        @Override
        public void resize(int width, int height) {
            viewport.update(width, height, true);
        }

        // Called when the Application is paused, usually when it's not active or visible on-screen.
        @Override
        public void pause() {
        }

        // Called when the Application is resumed from a paused state, usually when it regains focus.
        @Override
        public void resume() {
        }

        // Called when this screen is no longer the current screen for a Game.
        @Override
        public void hide() {
            dispose();
        }

        // Called when the Application is destroyed.
        @Override
        public void dispose() {
            super.dispose();
            skin.dispose();
            sgxSkin.dispose();
            sgxSkin2.dispose();
            stage.dispose();
            click.dispose();
            batch.dispose();
            playerShip.dispose();
            background.dispose();
        }
    }









