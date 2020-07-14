package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.spaceStudio.MainClient;
import de.spaceStudio.assets.StyleNames;
import de.spaceStudio.model.PlayerShip;
import de.spaceStudio.model.Playership2;
import de.spaceStudio.util.GdxUtils;


public class ShopScreen2 extends ScreenAdapter {

    private final MainClient universeMap;
    private MainClient mainClient;
    private final AssetManager assetManager;
    private Viewport viewport;
    private Stage stage;
    private Skin spaceSkin;

    //externes Schiff
    private SpriteBatch batch;

    private Texture background, playerShip;
    private Playership2 ship;
    private Texture rocket1, rocket2, crewMemberMTexture, crewMemberFTexture, securityTexture, oxygenTexture, driveTexture;
    private TextButton next, buy;
    private int itemNumber;
    public CheckBox checkBoxSection1, checkBoxSection2, checkBoxSection3, checkBoxSection4, checkBoxSection5, checkBoxSection6, checkBoxAllSections;




    public ShopScreen2(MainClient mainClient) {
        this.universeMap = mainClient;
        this.mainClient = mainClient;
        assetManager = universeMap.getAssetmanager();

        spaceSkin =  new Skin(Gdx.files.internal("Client/core/assets/ownAssets/sgx/skin/sgx-ui.json"));
        background = new Texture("ownAssets/sgx/backgrounds/galaxyBackground.png");
        playerShip = new Texture("Client/core/assets/data/ships/blueships1_section.png");

        rocket1 = new Texture("data/ships/rocketSmall.png");
        rocket2 = new Texture("data/ships/attack.png");
        crewMemberMTexture = new Texture("Client/core/assets/MaleHuman-3.png");
        crewMemberFTexture = new Texture("Client/core/assets/FemaleHuman-2.png");
        securityTexture = new Texture("data/ships/securitySmall.png");
        oxygenTexture = new Texture("Client/core/assets/OxygenSymbol.png");
        driveTexture = new Texture("Client/core/assets/fire3.png");
        this.itemNumber = 0;
        batch = new SpriteBatch();
        drawShip();
        nextButton();
        buyItemsButton();
    }

    private void drawShip() {
        ship = new Playership2(0,0);
        ship.x = (Gdx.graphics.getWidth() - ship.width) / 2;
        ship.y = (Gdx.graphics.getHeight() - ship.height) / 2;
    }

    @Override
    public void show(){
        viewport = new FitViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage = new Stage(viewport, universeMap.getBatch());
        stage.addActor(next);
        stage.addActor(buy);



        //Back to Map Button
        TextButton backToMap = new TextButton(" Back to Map ", spaceSkin , StyleNames.EMPHASISTEXTBUTTON);
        backToMap.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                mainClient.setScreen(new ShipSelectScreen(mainClient));
            }
        });
        backToMap.setPosition(1650,1000);
        stage.addActor(backToMap);

        //add Items Button

        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        checkBoxSection1 = new CheckBox("Section 1", skin);
        checkBoxSection2 = new CheckBox("Section 2", skin);
        checkBoxSection3 = new CheckBox("Section 3", skin);
        checkBoxSection4 = new CheckBox("Section 4", skin);
        checkBoxSection5 = new CheckBox("Section 5", skin);
        checkBoxSection6 = new CheckBox("Section 6", skin);
        checkBoxAllSections = new CheckBox("whole Ship", skin);
        checkBoxSection1.setPosition(800, 900);
        checkBoxSection2.setPosition(800, 870);
        checkBoxSection3.setPosition(800, 840);
        checkBoxSection4.setPosition(800, 810);
        checkBoxSection5.setPosition(800, 780);
        checkBoxSection6.setPosition(800, 750);
        checkBoxAllSections.setPosition(800, 720 );
        checkBoxSection1.setChecked(false);
        checkBoxSection2.setChecked(false);
        checkBoxSection3.setChecked(false);
        checkBoxSection4.setChecked(false);
        checkBoxSection5.setChecked(false);
        checkBoxSection6.setChecked(false);
        checkBoxAllSections.setChecked(false);
        stage.addActor(checkBoxSection1);
        stage.addActor(checkBoxSection2);
        stage.addActor(checkBoxSection3);
        stage.addActor(checkBoxSection4);
        stage.addActor(checkBoxSection5);
        stage.addActor(checkBoxSection6);
        stage.addActor(checkBoxAllSections);

        //Dialog dialog = new Dialog("Store", skin);
        //dialog.setSize(500,250);
        //dialog.setPosition(1300,700);

        //final SelectBox<String> selectBox1 = new SelectBox<String>(skin);
        //selectBox1.setItems("Weapon 1","Weapon 2","Weapon 3","CrewMember");
        //final SelectBox<String> selectBox2 = new SelectBox<String>(skin);
        //selectBox2.setItems("Section 1","Section 2","Section 3","Section 4","Section 5","Section 6");


        //dialog.getContentTable().defaults().pad(5);
        //dialog.getContentTable().add(selectBox2, selectBox1);
        //dialog.button(buyButton);
        //stage.addActor(dialog);


        Gdx.input.setInputProcessor(stage);

    }



    @Override
    public void render(float delta){
        GdxUtils.clearScreen();

        batch.begin();
        ship.render(batch);
        batch.end();

        stage.getBatch().begin();

        stage.getBatch().draw(background, 0, 0, BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage.getBatch().draw(playerShip, 200,200,700,700);



        switch (itemNumber) {
            case 0:
                stage.getBatch().draw(rocket1, 1200, 600);
                break;
            case 1:
                stage.getBatch().draw(rocket2, 1200, 600);
                break;
            case 2:
                stage.getBatch().draw(crewMemberFTexture,1200, 600);
                break;
            case 3:
                stage.getBatch().draw(crewMemberMTexture, 1200, 600);
                break;
            case 4:
                stage.getBatch().draw(securityTexture, 1200, 600);
                break;
            case 5:
                stage.getBatch().draw(oxygenTexture,1200, 600);
                break;
            case 6:
                stage.getBatch().draw(driveTexture, 1200, 600);
                break;
        }
        showTextfield(itemNumber);

        buyItems();

        stage.getBatch().end();
        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void hide() {
        dispose();
    }
    @Override
    public void dispose() {
        stage.dispose();
        ship.dispose();
    }

    public void nextButton() {
        next = new TextButton("next", spaceSkin, StyleNames.EMPHASISTEXTBUTTON);
        next.setPosition(1300,600);

        next.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(itemNumber > 5)
                    itemNumber=0;
                else {
                    itemNumber++;
                }
            }
        });

    }

    public void buyItemsButton(){
        buy = new TextButton("buy",spaceSkin, StyleNames.EMPHASISTEXTBUTTON);
        buy.setPosition(800,680);

        buy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                return;
            }
        });
    }

    public void buyItems(){
        if(checkBoxSection1.isChecked()){
            if(itemNumber == 0)
                stage.getBatch().draw(rocket1,50,50);
            if(itemNumber == 1)
                stage.getBatch().draw(rocket2,50,50);
            if(itemNumber == 2)
                stage.getBatch().draw(crewMemberFTexture,50,50);
            if(itemNumber == 3)
                stage.getBatch().draw(crewMemberMTexture,50,50);
            if(itemNumber == 4)
                stage.getBatch().draw(securityTexture,50,50);
            if(itemNumber == 5)
                stage.getBatch().draw(oxygenTexture,50,50);
            if(itemNumber == 6)
                stage.getBatch().draw(driveTexture,50,50);
        }
    }



    public void showTextfield(int itemNumber){
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        if (itemNumber == 0) {

            TextArea textArea = new TextArea("Name: Rocket 1\nHit Probability: 50%\nShots: 2\nDamage: 1/10\nCosts: 150 $", skin);
            textArea.setPosition(1100,380);
            textArea.setWidth(400);
            textArea.setHeight(200);
            stage.addActor(textArea);

        } else if (itemNumber == 1) {

            TextArea textArea = new TextArea("Name: Rocket 2\nHit Probability: 60%\nShots: 3\nDamage: 3/10\nCosts: 200 $", skin);
            textArea.setPosition(1100,380);
            textArea.setWidth(400);
            textArea.setHeight(200);
            stage.addActor(textArea);

        } else if (itemNumber == 2) {

            TextArea textArea = new TextArea("Name: Male CrewMember\nRepairs: 50% per round\nCosts: 300 $", skin);
            textArea.setPosition(1100,380);
            textArea.setWidth(400);
            textArea.setHeight(200);
            stage.addActor(textArea);

        } else if (itemNumber == 3){

            TextArea textArea = new TextArea("Name: Female CrewMember\nRepairs: 60% per round\nCosts: 400 $", skin);
            textArea.setPosition(1100,380);
            textArea.setWidth(400);
            textArea.setHeight(200);
            stage.addActor(textArea);

        } else if (itemNumber == 4) {

            TextArea textArea = new TextArea("Name: Security\nCosts: 50 $", skin);
            textArea.setPosition(1100, 380);
            textArea.setWidth(400);
            textArea.setHeight(200);
            stage.addActor(textArea);
        }
        else if (itemNumber == 5) {

            TextArea textArea = new TextArea("Name: Oxygen\nCosts: 20 $", skin);
            textArea.setPosition(1100, 380);
            textArea.setWidth(400);
            textArea.setHeight(200);
            stage.addActor(textArea);

        } else if (itemNumber == 6){

            TextArea textArea = new TextArea("Name: Drive\nCosts: 30 $", skin);
            textArea.setPosition(1100, 380);
            textArea.setWidth(400);
            textArea.setHeight(200);
            stage.addActor(textArea);

        } else {
            TextArea textArea = new TextArea("No item chosen", skin);
            textArea.setPosition(1100, 380);
            textArea.setWidth(400);
            textArea.setHeight(200);
            stage.addActor(textArea);
        }

    }

}
