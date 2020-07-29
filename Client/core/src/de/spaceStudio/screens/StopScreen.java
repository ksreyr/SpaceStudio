package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import de.spaceStudio.MainClient;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.client.util.RequestUtils;
import de.spaceStudio.server.model.CrewMember;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.model.ShipRessource;
import de.spaceStudio.server.model.Weapon;

import java.util.List;
import java.util.Objects;
import java.util.Random;


public class StopScreen extends ScreenAdapter {
    private final int dammagePrice = 5;
    private final int coolDownPrice = 4;
    private final int accuracyPrice = 6;
    MainClient game;
    boolean enemyNearBy = Global.currentShipGegner != null;
    private Stage stage;
    private Skin skin;
    private Label statsLabel;

    public StopScreen(MainClient game) {
        super();
        this.game = game;
    }

    /**
     * Generate a random Number
     *
     * @param min lowest
     * @param max highest
     * @return a number inside the bounds
     */
    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    /**
     * Return the Text to the event which is executed prior
     *
     * @param event which is selected
     * @return what happens
     */
    String event_description(int event) {
        String result = "You go Blind";
        switch (event) {
            case 0:
                int life = Global.currentShipPlayer.getHp() + getRandomNumberInRange(1, 50);
                Global.currentShipPlayer.setHp(life);
                RequestUtils.updateShip(Global.currentShipPlayer);
                result = "You got lucky. You find dearly needed Spare Parts. Current HP = " + life;
                break;


            case 1:
                int n = 0;
                int weapon_dammage = Global.currentShipPlayer.getHp() + getRandomNumberInRange(1, 50);
                Weapon weapon = Global.weaponListPlayer.get(n);
                weapon.setDamage(weapon_dammage);
                weapon.setName("Alien Weapon");
                Global.weaponListPlayer.set(n, weapon);
                RequestUtils.upgradeWeapon(List.of(weapon));
                result = "You explore an abandoned Ship, you find a strange Weapon";
                break;

            case 2:
                int damage = Global.currentShipPlayer.getHp() - getRandomNumberInRange(1, 50);
                if (damage > 0) {
                    Global.currentShipPlayer.setHp(damage);
                    result = "Just when you wanted to Dock at the Station you get hit by a Comet";
                } else {
                    Global.currentShipPlayer.setHp(3);
                    result = "A comet hits your ship. You just manage save your Ship";
                }
                RequestUtils.updateShip(Global.currentShipPlayer);
                // Loose live
                break;

            case 3:
                int shield = Global.currentShipPlayer.getShield() + getRandomNumberInRange(1, 50);
                Global.currentShipPlayer.setShield(shield);
                result = "At the abandoned Space Dock there is a Big Box. \n It contains an extra Shield\nShield = " + shield;
                RequestUtils.updateShip(Global.currentShipPlayer);
                break;

            case 4:
                int shieldDammage = Global.currentShipPlayer.getShield() - getRandomNumberInRange(1, 50);
                Global.currentShipPlayer.setShield(shieldDammage);
                result = "You approach the Station. \n A bomb explodes directly next to the Cockpit. \nShield = " + shieldDammage;
                RequestUtils.updateShip(Global.currentShipPlayer);
                break;

            case 5:
                float accuracyLost =  0.3f;
                for (Weapon w :
                        Global.combatWeapons.get(Global.currentShipPlayer.getId())) {
                    w.setHitRate(w.getHitRate() - accuracyLost);
                }
                RequestUtils.upgradeWeapon(Global.combatWeapons.get(Global.currentShipPlayer.getId()));
                result = "A hacker has breached your Systems. All your computers have been fucked. Your aim has gotten a lot worse";
                break;

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
                result = "Before you lies a Blue Laggon, It looks like an oasis";
                break;

            case 2:
                result = "Your emergency Beeper goes of... There is a Ship wreck nearby";
                break;

            default:
                result = "Nothing is here, just the existential void of being";
                break;
        }


        return result;
    }

    private String getStats(Ship s) {

        return "Life :" + s.getHp() + "\n" +
                "Shield :" + s.getShield() + "\n" +
                "Money: " + Global.shipRessource.getAmount() + "\n" +
                "Power: " + s.getPower() + "\n";
    }


    /**
     * Random events decide, what will haben by loading the event Methods  and setting global Values
     * A terminal loads a new Screen
     * Capitals are non Terminals, non Capitals are not
     * Leave = A
     * A =  | flee | fight | shop | upgrade
     * Flee = stationMap
     * Shop = enter | exit
     * Fight = enemy | noEnemy
     * Stay = stationMap
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));


        int row_height = Gdx.graphics.getWidth() / 12;
        int col_width = Gdx.graphics.getWidth() / 12;
        Label.LabelStyle label1Style = new Label.LabelStyle();

        BitmapFont myFont = new BitmapFont(Gdx.files.internal("bitmap/amble.fnt"));
        label1Style.font = myFont;
        label1Style.fontColor = Color.RED;

        statsLabel = new Label(getStats(Global.currentShipPlayer), label1Style);
        statsLabel.setSize(Gdx.graphics.getWidth(), row_height);
        statsLabel.setPosition(50, Gdx.graphics.getHeight() - row_height * 6);
        statsLabel.setAlignment(Align.topLeft);


        final int number = getRandomNumberInRange(0, 3);


        new Dialog("You have a arrived at a new Stop", skin) {

            {
                text(stop_descripton(number));
                button("Leave", "false").getButtonTable().row();
                button("Explore", "true");
            }

            @Override
            protected void result(final Object object) {
                if (Objects.equals(object.toString(), "true")) {
                    final int event_number = getRandomNumberInRange(0, 6);
                    new Dialog("After a while:", skin) {

                        {
                            String outcome = event_description(event_number);
                            text(outcome);
                            button("Flee", 1L);
                            button("Fight", 2L);
                            button("Shop", 3L);
                            button("Upgrade", 4L);

                        }

                        @Override
                        protected void result(Object object) {

                            if (object.equals(3L)) {
                                new Dialog("Go to Shop", skin) {

                                    {
                                        text("Do you want to go to the Shop");
                                        button("Enter Shop", 1L).getButtonTable().row();
                                        button("NO", 0L);

                                    }

                                    @Override
                                    protected void result(Object object) {
                                        game.setScreen(new ShopScreen(game));
                                    }
                                }.show(stage);

                            } else if (object.equals(2L)) {
                                // TODO Set FightScreen
                                if (!enemyNearBy) { // FIXME no enemy
                                    // IF there is an enemy
                                    new Dialog("On the Way Back you Stop...", skin) {

                                        {
                                            text("It has been a long Day You are Tired");
                                            button("View Map", 1L).getButtonTable().row();

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

                            } else if (object.equals(1L)) {
                                new Dialog("Go back to Map", skin) {

                                    {
                                        text("You have seen Enough. You go back to your Cockpit");
                                        button("View Map", 1L).getButtonTable().row();

                                    }

                                    @Override
                                    protected void result(Object object) {
                                        game.setScreen(new StationsMap(game));
                                    }
                                }.show(stage);
                            } else if (object.equals(4L)) {
                                new Dialog("What do you want to Upgrade?", skin) {

                                    {
                                        int price_dammage = Global.weaponListPlayer.size() * dammagePrice;
                                        int price_accuracy = Global.weaponListPlayer.size() * accuracyPrice;
                                        int price_coolDown = Global.weaponListPlayer.size() * coolDownPrice;
                                        int price_life = 20;
                                        int price_shield = 15;

                                        text("You can Upgrade all Weapons or one at a time");
                                        text("You have " + Global.shipRessource.getAmount() + "Money");

                                        if (Global.shipRessource.getAmount() >= price_dammage) {
                                            button("+10% Damage for  all (" + price_dammage + ")", 1L).getButtonTable().row();
                                        }
                                        if (Global.shipRessource.getAmount() >= price_coolDown) {
                                            button("+10% Accuracy for  all (" + price_accuracy + ")", 2L).getButtonTable().row();
                                        }
                                        if (Global.shipRessource.getAmount() >= price_accuracy) {
                                            button("-10% Warmup for  all (" + price_coolDown + ")", 2L).getButtonTable().row();
                                        }
                                        if (Global.shipRessource.getAmount() >= price_life) {
                                            button("+10% Life (" + price_life + ")", 4L).getButtonTable().row();
                                        }

                                        if (Global.shipRessource.getAmount() >= price_shield) {
                                            button("+10 Shield  (" + price_shield + ")", 5L).getButtonTable().row();
                                        }

                                    }

                                    @Override
                                    protected void result(Object object) {
                                        if (object.equals(0L)) {
                                            new Dialog("Go back to Map", skin) {

                                                {
                                                    text("You have seen Enough. You go back to your Cockpit");
                                                    button("View Map", 1L).getButtonTable().row();
                                                }

                                                @Override
                                                protected void result(Object object) {
                                                    game.setScreen(new StationsMap(game));
                                                }
                                            }.show(stage);
                                        } else if (object.equals(1L)) {
                                            for (Weapon w :
                                                    Global.combatWeapons.get(Global.currentShipPlayer.getId())) {
                                                w.setDamage((int) (w.getDamage() + w.getDamage() * 0.1)); // FIXME if dammage is below 10 this will fail
                                            }
                                            RequestUtils.upgradeWeapon(Global.combatWeapons.get(Global.currentShipPlayer.getId()));
                                        } else if (object.equals(2L)) {
                                            for (Weapon w :
                                                    Global.combatWeapons.get(Global.currentShipPlayer.getId())) {
                                                w.setWarmUp((int) (w.getWarmUp() + w.getWarmUp() * 0.1)); // FIXME if dammage is below 10 this will fail
                                            }
                                            RequestUtils.upgradeWeapon(Global.combatWeapons.get(Global.currentShipPlayer.getId()));
                                        } else if (object.equals(3L)) {
                                            for (Weapon w :
                                                    Global.combatWeapons.get(Global.currentShipPlayer.getId())) {
                                                w.setWarmUp((int) (w.getWarmUp() + w.getWarmUp() * 0.1)); // FIXME if dammage is below 10 this will fail
                                            }
                                            RequestUtils.upgradeWeapon(Global.combatWeapons.get(Global.currentShipPlayer.getId()));
                                        } else if (object.equals(4L)) {
                                            Global.currentShipPlayer.setHp(Global.currentShipPlayer.getHp() + (int) (Global.currentShipPlayer.getHp() * 0.1f));
                                            RequestUtils.updateShip(Global.currentShipPlayer);
                                        } else if (object.equals(5L)) {
                                            Global.currentShipPlayer.setShield(Global.currentShipPlayer.getShield() + 10);
                                            RequestUtils.updateShip(Global.currentShipPlayer);
                                        }

                                        // Where does this go
                                        new Dialog("Upgrade Succesfull", skin) {

                                            {
                                                text("You the eager Mechnanic has finished");
                                                button("View Map", 1L).getButtonTable().row();
                                                button("Fight", 2L);

                                            }

                                            @Override
                                            protected void result(Object object) {
                                                if (object.equals(1L)) {
                                                    game.setScreen(new StationsMap(game));
                                                } else if (object.equals(2L)) {
                                                    game.setScreen(new CombatScreen(game));
                                                }
                                            }
                                        }.show(stage);

                                    }
                                }.show(stage);
                            }

                        }
                    }.show(stage);
                } else if (Objects.equals(object.toString(), "false")) {
                    new Dialog("Go back to Map", skin) {

                        {
                            text("You have seen Enough. You go back to your Cockpit");
                            button("View Map", 1L).getButtonTable().row();

                        }

                        @Override
                        protected void result(Object object) {
                            game.setScreen(new StationsMap(game));
                        }
                    }.show(stage);
                }

            }
        }.show(stage);
        stage.addActor(statsLabel);
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0.23f, 0.34f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        statsLabel.setText(getStats(Global.currentShipPlayer));
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
        stage.dispose();
    }
}
