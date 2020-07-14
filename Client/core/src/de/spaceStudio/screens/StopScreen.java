package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FillViewport;
import de.spaceStudio.MainClient;

import java.util.Random;


public class StopScreen  extends ScreenAdapter {
    MainClient game;

    private Stage stage;
    private Skin skin;

    public StopScreen(MainClient game) {
        super();
        this.game = game;

    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    String event_description(int event){
        String result;
        switch (event) {
            case 0:
                result = "You got lucky. You find More life";

                break;

            case 1:
                result = "You meet an Enemy Ship, it Starts attackng you";


                // TODO SetScreen Fight

                break;

            case 2:
                result = "Just when you wanted to Dock at the Station you get hit by a Comet";
                // Loose live
                break;

            default:
                result = "Nothing happens";
                break;
        }


        return result;
    }



    String stop_descripton(int event){
        String result;
        switch (event) {
            case 0:
                result = "In front of you there is just baren Landscape. \n Has there been any Life here?" ;
                break;

            case 1:
                result  = "Before you lies a Blueg Laggon, It looks like an oasis";
                break;

            case 2:
                result = "Your emergency Beeper goes of... There is a Ship wreck nearby";
                break;

            default:
                result = "";
                break;
        }


        return result;
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        final int number = getRandomNumberInRange(0,3);


        new Dialog("You have a arrived at a new Stop" , skin) {

            {
                text(stop_descripton(number));
                button("Leave", "false").getButtonTable().row();
                button("Explore", "true");
            }

            @Override
            protected void result(final Object object) {
                if (object.toString() == "true") {
                    final int event_number = getRandomNumberInRange(0, 3);
                    new Dialog("After a while:", skin) {

                        {
                            String outcome = event_description(event_number);
                            text(outcome);
                            button("Flee", 1l);
                            button("Fight", 2l);
                            button("Gather", 3l);
                        }

                        @Override
                        protected void result(Object object) {
                            if (object.equals(2l)) {
                                // TODO Set FightScreen
                                game.setScreen(new CombatScreen(game));
                                // IF there is an enemy

                            } else  if (object.equals(1l)){
                                new Dialog("Go back to Map", skin) {

                                    {
                                        text("You have seen Enough. You go back to your Cockpit");
                                        button("View Map", 1l).getButtonTable().row();
                                    }

                                    @Override
                                    protected void result(Object object) {
                                        game.setScreen(new StationsMap(game));
                                    }
                                }.show(stage);
                            }

                        }
                    }.show(stage);
                } else  if (object.toString() == "false") {
                    game.setScreen(new StationsMap(game));
                }


            }
            }.show(stage);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }
}
