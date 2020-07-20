package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.gson.Gson;
import de.spaceStudio.MainClient;
import de.spaceStudio.assets.StyleNames;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.model.Playership2;
import de.spaceStudio.server.model.Section;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.model.ShipRessource;
import de.spaceStudio.util.GdxUtils;

import java.util.Arrays;

import static de.spaceStudio.client.util.RequestUtils.setupRequest;


public class ShopScreen2 extends ScreenAdapter {

    private final MainClient universeMap;
    private MainClient mainClient;
    private final AssetManager assetManager;
    private Viewport viewport;
    private Stage stage;
    private Skin spaceSkin;
    private BitmapFont font;
    private ShapeRenderer renderer;

    //externes Schiff
    private SpriteBatch batch;

    private Texture background, playerShip;
    private Playership2 ship;
    private Texture rocket1, rocket2, crewMemberMTexture, crewMemberFTexture, securityTexture, oxygenTexture, driveTexture;
    private TextButton next, buy, sell;
    private int itemNumber;
    public CheckBox checkBoxSection1, checkBoxSection2, checkBoxSection3, checkBoxSection4, checkBoxSection5, checkBoxSection6, checkBoxAllSections;
    //
    ShipRessource shipRessource;

    //

    public ShopScreen2(MainClient mainClient) {
        this.universeMap = mainClient;
        this.mainClient = mainClient;
        assetManager = universeMap.getAssetManager();
        this.renderer = new ShapeRenderer();
        font = new BitmapFont(Gdx.files.internal("Client/core/assets/skin/default.fnt"));

        spaceSkin =  new Skin(Gdx.files.internal("Client/core/assets/ownAssets/sgx/skin/sgx-ui.json"));
        background = new Texture("ownAssets/sgx/backgrounds/galaxyBackground.png");
        playerShip = new Texture("Client/core/assets/data/ships/blueships1_section.png");

        holdShipRessource(Global.currentShipPlayer, Net.HttpMethods.POST);
        rocket1 = new Texture("data/ships/rocketSmall.png");
        rocket2 = new Texture("data/ships/attack.png");
        crewMemberMTexture = new Texture("Client/core/assets/combatAssets/MaleHuman-3.png");
        crewMemberFTexture = new Texture("Client/core/assets/combatAssets/female_human.png");
        securityTexture = new Texture("data/ships/securitySmall.png");
        oxygenTexture = new Texture("Client/core/assets/OxygenSymbol_large.png");
        driveTexture = new Texture("Client/core/assets/fire3.png");
        this.itemNumber = 0;
        batch = new SpriteBatch();
        drawShip();
        nextButton();
        buyItemsButton();
        sellItemsButton();
    }
    public void holdShipRessource(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        final String url = Global.SERVER_URL + Global.SHIP_RESSORUCE_ENDPOINT;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed holdShipRessource");
                }
                System.out.println("statusCode holdShipRessource: " + statusCode);
                String stringshipRessourcen = httpResponse.getResultAsString();
                Gson gson = new Gson();
                ShipRessource[] sectiongegnerArray = gson.fromJson(stringshipRessourcen, ShipRessource[].class);
                shipRessource = Arrays.asList(sectiongegnerArray).get(0);
                System.out.println("statusCode holdShipRessource: " + statusCode);
            }
            public void failed(Throwable t) {
                System.out.println("Request Failed Completely holdShipRessource");
            }

            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
    }
    private void drawShip() {
        // Add new Ship and center it
        ship = new Playership2(0,0);
        ship.x = (Gdx.graphics.getWidth() - ship.width) / 8;
        ship.y = (Gdx.graphics.getHeight() - ship.height) / 3;
    }

    @Override
    public void show(){
        viewport = new FitViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage = new Stage(viewport, universeMap.getBatch());
        stage.addActor(next);
        stage.addActor(buy);
        stage.addActor(sell);


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
        //stage.addActor(checkBoxAllSections);

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

        stage.getBatch().begin();

        batch.begin();

        ship.render(batch);
        font.draw(batch,"Money: " + ship.getMoney() + "$", 0, 1000);
        font.draw(batch,"Secure: " + ship.getSecure()+ "%", 0, 980);
        font.draw(batch,"Oxygen: " + ship.getOxygen() + "%", 0, 960);
        font.draw(batch,"Drive: " + ship.getDrive() + "%", 0, 940);
        //stage.getBatch().draw(background, 0, 0, BaseScreen.WIDTH, BaseScreen.HEIGHT);

        batch.end();
        //stage.getBatch().draw(playerShip, 200,200,700,700);

        batch.begin();
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

        stage.getBatch().end();
        batch.end();
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
                changeItems(true);
                setAllSectionCheckboxesFalse();
            }
        });
    }

    public void sellItemsButton(){
        sell = new TextButton("sell",spaceSkin,StyleNames.EMPHASISTEXTBUTTON);
        sell.setPosition(850,680);

        sell.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeItems(false);
                //setAllSectionCheckboxesFalse();
            }
        });
    }
    public void changeItems(boolean b){

        if(itemNumber > 3){
            setAllSectionCheckboxesFalse();
                if(itemNumber == 4)
                    ship.setSecure(ship.getSecure()+10);
                if(itemNumber == 5)
                    ship.setOxygen(ship.getOxygen()+10);
                if(itemNumber == 6)
                    ship.setDrive(ship.getDrive()+10);
                if(b){
                    ship.setMoney(ship.getMoney()-10);
                }else{
                    ship.setMoney(ship.getMoney()+10);
                }
        }

        if(checkBoxSection1.isChecked()){
            if(itemNumber == 0)
                ship.setRocket1s1(b);
            if(itemNumber == 1)
                ship.setRocket2s2(b);
            if(itemNumber == 2)
                ship.setCrewMemberFs1(b);
            if(itemNumber == 3)
                ship.setCrewMemberMs1(b);
            if(b){
                ship.setMoney(ship.getMoney()-100);
            }else{
                ship.setMoney(ship.getMoney()+100);
            }
        }
        if(checkBoxSection2.isChecked()){
            if(itemNumber == 0)
                ship.setRocket1s2(b);
            if(itemNumber == 1)
                ship.setRocket2s2(b);
            if(itemNumber == 2)
                ship.setCrewMemberFs2(b);
            if(itemNumber == 3)
                ship.setCrewMemberMs2(b);
            if(b){
                ship.setMoney(ship.getMoney()-100);
            }else{
                ship.setMoney(ship.getMoney()+100);
            }
        }
        if(checkBoxSection3.isChecked()){
            if(itemNumber == 0)
                ship.setRocket1s3(b);
            if(itemNumber == 1)
                ship.setRocket2s3(b);
            if(itemNumber == 2)
                ship.setCrewMemberFs3(b);
            if(itemNumber == 3)
                ship.setCrewMemberMs3(b);
            if(b){
                ship.setMoney(ship.getMoney()-100);
            }else{
                ship.setMoney(ship.getMoney()+100);
            }
        }
        if(checkBoxSection4.isChecked()){
            if(itemNumber == 0)
                ship.setRocket1s4(b);
            if(itemNumber == 1)
                ship.setRocket2s4(b);
            if(itemNumber == 2)
                ship.setCrewMemberFs4(b);
            if(itemNumber == 3)
                ship.setCrewMemberMs4(b);
            if(b){
                ship.setMoney(ship.getMoney()-100);
            }else{
                ship.setMoney(ship.getMoney()+100);
            }
        }
        if(checkBoxSection5.isChecked()){
            if(itemNumber == 0)
                ship.setRocket1s5(b);
            if(itemNumber == 1)
                ship.setRocket2s5(b);
            if(itemNumber == 2)
                ship.setCrewMemberFs5(b);
            if(itemNumber == 3)
                ship.setCrewMemberMs5(b);
            if(b){
                ship.setMoney(ship.getMoney()-100);
            }else{
                ship.setMoney(ship.getMoney()+100);
            }
        }
        if(checkBoxSection6.isChecked()){
            if(itemNumber == 0)
                ship.setRocket1s6(b);
            if(itemNumber == 1)
                ship.setRocket2s6(b);
            if(itemNumber == 2)
                ship.setCrewMemberFs6(b);
            if(itemNumber == 3)
                ship.setCrewMemberMs6(b);
            if(b){
                ship.setMoney(ship.getMoney()-100);
            }else{
                ship.setMoney(ship.getMoney()+100);
            }
        }

    }



    public void showTextfield(int itemNumber){
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        if (itemNumber == 0) {

            TextArea textArea = new TextArea("Name: Rocket 1\nHit Probability: 50%\nShots: 2\nDamage: 1/10\nCosts: 100 $", skin);
            textArea.setPosition(1100,380);
            textArea.setWidth(400);
            textArea.setHeight(200);
            stage.addActor(textArea);

        } else if (itemNumber == 1) {

            TextArea textArea = new TextArea("Name: Rocket 2\nHit Probability: 60%\nShots: 3\nDamage: 3/10\nCosts: 100 $", skin);
            textArea.setPosition(1100,380);
            textArea.setWidth(400);
            textArea.setHeight(200);
            stage.addActor(textArea);

        } else if (itemNumber == 2) {

            TextArea textArea = new TextArea("Name: Male CrewMember\nRepairs: 50% per round\nCosts: 100 $", skin);
            textArea.setPosition(1100,380);
            textArea.setWidth(400);
            textArea.setHeight(200);
            stage.addActor(textArea);

        } else if (itemNumber == 3){

            TextArea textArea = new TextArea("Name: Female CrewMember\nRepairs: 60% per round\nCosts: 100 $", skin);
            textArea.setPosition(1100,380);
            textArea.setWidth(400);
            textArea.setHeight(200);
            stage.addActor(textArea);

        } else if (itemNumber == 4) {

            TextArea textArea = new TextArea("Name: Security\nAmount: + 10 %\nCosts: 10 $\nInfo: for the whole ship", skin);
            textArea.setPosition(1100, 380);
            textArea.setWidth(400);
            textArea.setHeight(200);
            stage.addActor(textArea);
        }
        else if (itemNumber == 5) {

            TextArea textArea = new TextArea("Name: Oxygen\nCosts: 10 $\nInfo: for the whole ship", skin);
            textArea.setPosition(1100, 380);
            textArea.setWidth(400);
            textArea.setHeight(200);
            stage.addActor(textArea);

        } else if (itemNumber == 6){

            TextArea textArea = new TextArea("Name: Drive\nCosts: 10 $\nInfo: for the whole ship", skin);
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

    public void setAllSectionCheckboxesFalse(){
        checkBoxSection1.setChecked(false);
        checkBoxSection2.setChecked(false);
        checkBoxSection3.setChecked(false);
        checkBoxSection4.setChecked(false);
        checkBoxSection5.setChecked(false);
        checkBoxSection6.setChecked(false);
    }

}
