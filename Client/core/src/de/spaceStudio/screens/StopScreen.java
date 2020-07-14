package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FillViewport;
import de.spaceStudio.MainClient;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.server.model.CrewMember;
import de.spaceStudio.server.model.Weapon;

import java.util.Random;


public class StopScreen extends ScreenAdapter {
    MainClient game;
    boolean enemyNearBy = true;
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

    String event_description(int event) {
        String result;
        switch (event) {
            case 0:
                int life = Global.currentShipPlayer.getHp() + getRandomNumberInRange(1, 50);
                Global.currentShipPlayer.setHp(life);
                result = "You got lucky. You find dearly needed Spare Parts. Current HP = " + life;
                break;


            case 1:
                int n = 0;
                int weapon_dammage = Global.currentShipPlayer.getHp() + getRandomNumberInRange(1, 50);
                Weapon wepaon = Global.weaponListPlayer.get(n);
                wepaon.setDamage(weapon_dammage);
                Global.weaponListPlayer.set(n, wepaon);
                result = "You meet an abandoned Ship, you find a strange Weapon";
                break;

            case 2:
                int dammage = Global.currentShipPlayer.getHp() + getRandomNumberInRange(1, 50);
                Global.currentShipPlayer.setHp(dammage);
                result = "Just when you wanted to Dock at the Station you get hit by a Comet";
                // Loose live
                break;

            case 3:
                int shield = Global.currentShipPlayer.getShield() + getRandomNumberInRange(1, 50);
                Global.currentShipPlayer.setShield(shield);
                result = "At the abandoned Space Dock there is a Big Box. \n It contains an extra Shield\nShield = " + shield;
                break;

            case 4:
                int shieldDammage = Global.currentShipPlayer.getShield() - getRandomNumberInRange(1, 50);
                Global.currentShipPlayer.setShield(shieldDammage);
                result = "You approach the Station. \n A bomb explodes directly next to the Cockpit. \nShield = " + shieldDammage;
                break;

            case 5:
                int health = getRandomNumberInRange(10, 100);
                CrewMember c = new CrewMember();
                c.setHealth(health);
                //c.getImg() // FIXME which List
                c.setName("Hubert"); // TODO Liam add faker
                c.setCurrentSection(Global.sectionsPlayerList.get(0));
                // FIXME update Backend

            default:
                result = "Nothing happens. You glance at the vast expanse of Space";
                break;
        }


        return result;
    }


    String stop_descripton(int event) {
        String result;
        switch (event) {
            case 0:
                result = "In front of you there is just baren Landscape. \n Has there been any Life here?";
                break;

            case 1:
                result = "Before you lies a Blueg Laggon, It looks like an oasis";
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


    /**
     * Random events decide, what will haben by loading the event Methods  and setting global Values
     * A terminal loads a new Screen
     * Capitals are non Terminals, non Capitals are not
     * Leave = A
     * A =  | flee | fight | shop
     * Flee = stationMap
     * Shop = enter | exit
     * Fight = enemy | noEnemy
     * Stay = stationMap
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        final int number = getRandomNumberInRange(0, 3);


        new Dialog("You have a arrived at a new Stop", skin) {

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
                            button("Shop", 3l);

                        }

                        @Override
                        protected void result(Object object) {

                            if (object.equals(3l)) {
                                new Dialog("Go to Shop", skin) {

                                    {
                                        text("Do you want to go to the Shop");
                                        button("Enter Shop", 1l).getButtonTable().row();
                                        button("NO", 0l);

                                    }

                                    @Override
                                    protected void result(Object object) {
                                        if (object.equals("FIXME")) {
                                            //   game.setScreen(new (...); // FIXME go to Shop
                                        }
                                    }
                                }.show(stage);

                            } else if (object.equals(2l)) {
                                // TODO Set FightScreen
                                if (!enemyNearBy) { // FIXME no enemy
                                    // IF there is an enemy
                                    new Dialog("On the Way Back you Stop...", skin) {

                                        {
                                            text("It has been a long Day You are Tired");
                                            button("View Map", 1l).getButtonTable().row();

                                        }

                                        @Override
                                        protected void result(Object object) {
                                            game.setScreen(new StationsMap(game));
                                        }

                                    }.show(stage);

                                } else {
                                    new Dialog("Enemy Ship approaches", skin) {

                                        {
                                            text("Your radar has picked up an unknown ship on its radar");
                                            button("Fight");
                                        }

                                        @Override
                                        protected void result(Object object) {
                                            game.setScreen(new CombatScreen(game));
                                        }
                                    }.show(stage);

                                }

                            } else if (object.equals(1l)) {
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
                } else if (object.toString() == "false") {
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
