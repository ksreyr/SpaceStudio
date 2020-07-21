package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import de.spaceStudio.util.GdxUtils;


public class ShopScreen extends ScreenAdapter {

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

    //Textures
    private Texture background, playerShip;
    private Texture securityTextureGrey, driveTextureGrey;
    private Texture rocket1, rocket2, crewMemberMTexture, crewMemberFTexture, securityTexture, oxygenTexture, driveTexture;
    private TextButton next, buy, sell;
    private int itemNumber;
    public CheckBox checkBoxSection1, checkBoxSection2, checkBoxSection3, checkBoxSection4, checkBoxSection5, checkBoxSection6, checkBoxAllSections;

    private Skin skin;

    //Ressourcen
    private int money;
    private int secure;
    private int drive;
    private int oxygen;

    //rocket1
    private boolean rocket1s1,rocket1s2,rocket1s3,rocket1s4,rocket1s5,rocket1s6;
    //rocket2
    private boolean rocket2s1,rocket2s2,rocket2s3,rocket2s4,rocket2s5,rocket2s6;
    //crewmemberF
    private boolean crewMemberFs1, crewMemberFs2, crewMemberFs3, crewMemberFs4, crewMemberFs5, crewMemberFs6;
    //crewmemberM
    private boolean crewMemberMs1, crewMemberMs2, crewMemberMs3,crewMemberMs4,crewMemberMs5,crewMemberMs6;
    //secure,drive
    private boolean secureIconS1, driveIconS1,secureIconS2, driveIconS2;

    public ShopScreen(MainClient mainClient) {
        viewport = new FitViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT);
        this.universeMap = mainClient;
        this.mainClient = mainClient;
        assetManager = universeMap.getAssetManager();
        this.renderer = new ShapeRenderer();
        this.skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        font = new BitmapFont(Gdx.files.internal("Client/core/assets/skin/default.fnt"));
        spaceSkin =  new Skin(Gdx.files.internal("Client/core/assets/ownAssets/sgx/skin/sgx-ui.json"));
        background = new Texture("ownAssets/sgx/backgrounds/galaxyBackground.png");
        playerShip = new Texture("Client/core/assets/data/ships/blueships2.png");
        rocket1 = new Texture("data/ships/rocketSmall.png");
        rocket2 = new Texture("data/ships/attack_small.png");
        crewMemberMTexture = new Texture("Client/core/assets/combatAssets/MaleHuman-3.png");
        crewMemberFTexture = new Texture("Client/core/assets/combatAssets/female_human.png");
        securityTexture = new Texture("data/ships/securitySmall.png");
        oxygenTexture = new Texture("Client/core/assets/OxygenSymbol_large.png");
        driveTexture = new Texture("data/ships/batterie.png");
        //if secure or drive is too low
        securityTextureGrey = new Texture("data/ships/securityGrey.png");
        driveTextureGrey = new Texture("data/ships/batterie.png");

        this.itemNumber = 0;
        batch = new SpriteBatch();

        this.money = 100;
        this.secure = 100;
        this.drive = 100;
        this.oxygen = 100;

        //rocket1
        this.rocket1s1 = false;
        this.rocket1s2 = false;
        this.rocket1s3 = false;
        this.rocket1s4 = false;
        this.rocket1s5 = false;
        this.rocket1s6 = false;
        //rocket2
        this.rocket2s1 = false;
        this.rocket2s2 = false;
        this.rocket2s3 = false;
        this.rocket2s4 = false;
        this.rocket2s5 = false;
        this.rocket2s6 = false;
        //crewmemberF
        this.crewMemberFs1 = false;
        this.crewMemberFs2 = false;
        this.crewMemberFs3 = false;
        this.crewMemberFs4 = false;
        this.crewMemberFs5 = false;
        this.crewMemberFs6 = false;
        //crewmemberM
        this.crewMemberMs1 = false;
        this.crewMemberMs2 = false;
        this.crewMemberMs3 = false;
        this.crewMemberMs4 = false;
        this.crewMemberMs5 = false;
        this.crewMemberMs6 = false;

        //must haves
        this.secureIconS1 = false;
        this.driveIconS1 = false;
        this.secureIconS2 = false;
        this.driveIconS2 = false;


        nextButton();
        buyItemsButton();
        sellItemsButton();
    }


    @Override
    public void show(){

        //viewport = new FitViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage = new Stage(viewport, universeMap.getBatch());
        stage.addActor(next);
        stage.addActor(buy);
        stage.addActor(sell);

        //Back to Map Button
        TextButton backToMap = new TextButton(" Back to Map ", spaceSkin , StyleNames.EMPHASISTEXTBUTTON);
        backToMap.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainClient.setScreen(new StationsMap(mainClient));
            }
        });
        backToMap.setPosition(1650,1000);
        stage.addActor(backToMap);

        checkBoxSection1 = new CheckBox("Section 1", skin);
        checkBoxSection2 = new CheckBox("Section 2", skin);
        checkBoxSection3 = new CheckBox("Section 3", skin);
        checkBoxSection4 = new CheckBox("Section 4", skin);
        checkBoxSection5 = new CheckBox("Section 5", skin);
        checkBoxSection6 = new CheckBox("Section 6", skin);
        checkBoxSection1.setPosition(900, 1000);
        checkBoxSection2.setPosition(900, 970);
        checkBoxSection3.setPosition(900, 940);
        checkBoxSection4.setPosition(900, 910);
        checkBoxSection5.setPosition(900, 880);
        checkBoxSection6.setPosition(900, 850);
        checkBoxSection1.setChecked(false);
        checkBoxSection2.setChecked(false);
        checkBoxSection3.setChecked(false);
        checkBoxSection4.setChecked(false);
        checkBoxSection5.setChecked(false);
        checkBoxSection6.setChecked(false);
        stage.addActor(checkBoxSection1);
        stage.addActor(checkBoxSection2);
        stage.addActor(checkBoxSection3);
        stage.addActor(checkBoxSection4);
        stage.addActor(checkBoxSection5);
        stage.addActor(checkBoxSection6);

        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(float delta){

        GdxUtils.clearScreen();

        stage.getBatch().begin();

        TextArea textArea = new TextArea("Money: " + money + "\nSecure: 50%\nOxygen: 2\nDrive: ", skin);
        textArea.setPosition(BaseScreen.WIDTH / 20,850);
        textArea.setWidth(200);
        textArea.setHeight(150);
        stage.addActor(textArea);

        TextArea shipInformationArea = new TextArea("Ship Options: \nSection1: Rocket1, Rocket2, CrewMember Female, CrewMember Male, Secure, Drive", skin);
        shipInformationArea.setPosition(900,50);
        shipInformationArea.setWidth(500);
        shipInformationArea.setHeight(250);
        stage.addActor(shipInformationArea);

        stage.getBatch().draw(background, 0, 0, BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage.getBatch().draw(playerShip,BaseScreen.WIDTH / 8, BaseScreen.HEIGHT / 8);

        float positionX = 1450;
        float positionY = 700;

        switch (itemNumber) {
            case 0:
                stage.getBatch().draw(rocket1, positionX, positionY);
                break;
            case 1:
                stage.getBatch().draw(rocket2, positionX, positionY);
                break;
            case 2:
                stage.getBatch().draw(crewMemberFTexture,positionX, positionY);
                break;
            case 3:
                stage.getBatch().draw(crewMemberMTexture, positionX, positionY);
                break;
            case 4:
                stage.getBatch().draw(securityTexture, positionX, positionY);
                break;
            case 5:
                stage.getBatch().draw(oxygenTexture,positionX, positionY);
                break;
            case 6:
                stage.getBatch().draw(driveTexture, positionX, positionY);
                break;
        }
        showTextfield(itemNumber);
        drawItems();

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
    }

    public void nextButton() {
        next = new TextButton("next", spaceSkin, StyleNames.EMPHASISTEXTBUTTON);
        next.setPosition(1600,700);

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
        buy.setPosition(900,780);

        buy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeItems(true);
                //setAllSectionCheckboxesFalse();
            }
        });
    }

    public void sellItemsButton(){
        sell = new TextButton("sell",spaceSkin,StyleNames.EMPHASISTEXTBUTTON);
        sell.setPosition(950,780);

        sell.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeItems(false);
                //setAllSectionCheckboxesFalse();
            }
        });
        //drawItems();
    }

    public void changeItems(boolean b){

        if(itemNumber > 3){
            setAllSectionCheckboxesFalse();
                if(itemNumber == 4)
                    setSecure(getSecure()+10);
                if(itemNumber == 5)
                    setOxygen(getOxygen()+10);
                if(itemNumber == 6)
                    setDrive(getDrive()+10);
                if(b){
                    setMoney(getMoney()-10);
                }else{
                    setMoney(getMoney()+10);
                }
        }

        if(checkBoxSection1.isChecked()){
            if(itemNumber == 0)
                setRocket1s1(b);
            if(itemNumber == 1)
                setRocket2s1(b);
            if(itemNumber == 2)
                setCrewMemberFs1(b);
            if(itemNumber == 3)
                setCrewMemberMs1(b);
            if(b){
                setMoney(getMoney()-100);
            }else{
                setMoney(getMoney()+100);
            }
        }

        if(checkBoxSection2.isChecked()){
            if(itemNumber == 0)
                setRocket1s2(b);
            if(itemNumber == 1)
                setRocket2s2(b);
            if(itemNumber == 2)
                setCrewMemberFs2(b);
            if(itemNumber == 3)
                setCrewMemberMs2(b);
            if(b){
                setMoney(getMoney()-100);
            }else{
                setMoney(getMoney()+100);
            }
        }
        if(checkBoxSection3.isChecked()){
            if(itemNumber == 0)
                setRocket1s3(b);
            if(itemNumber == 1)
                setRocket2s3(b);
            if(itemNumber == 2)
                setCrewMemberFs3(b);
            if(itemNumber == 3)
                setCrewMemberMs3(b);
            if(b){
                setMoney(getMoney()-100);
            }else{
                setMoney(getMoney()+100);
            }
        }
        if(checkBoxSection4.isChecked()){
            if(itemNumber == 0)
                setRocket1s4(b);
            if(itemNumber == 1)
                setRocket2s4(b);
            if(itemNumber == 2)
                setCrewMemberFs4(b);
            if(itemNumber == 3)
                setCrewMemberMs4(b);
            if(b){
                setMoney(getMoney()-100);
            }else{
                setMoney(getMoney()+100);
            }
        }

        if(checkBoxSection5.isChecked()){
            if(itemNumber == 0)
                setRocket1s5(b);
            if(itemNumber == 1)
                setRocket2s5(b);
            if(itemNumber == 2)
                setCrewMemberFs5(b);
            if(itemNumber == 3)
                setCrewMemberMs5(b);
            if(b){
                setMoney(getMoney()-100);
            }else{
                setMoney(getMoney()+100);
            }
        }
        if(checkBoxSection6.isChecked()){
            if(itemNumber == 0)
                setRocket1s6(b);
            if(itemNumber == 1)
                setRocket2s6(b);
            if(itemNumber == 2)
                setCrewMemberFs6(b);
            if(itemNumber == 3)
                setCrewMemberMs6(b);
            if(b){
                setMoney(getMoney()-100);
            }else{
                setMoney(getMoney()+100);
            }
        }

    }


    public void showTextfield(int itemNumber){

        if (itemNumber == 0) {

            TextArea textArea = new TextArea("Name: Rocket 1\nHit Probability: 50%\nShots: 2\nDamage: 1/10\nCosts: 100 $", skin);
            textArea.setPosition(1400,450);
            textArea.setWidth(400);
            textArea.setHeight(200);
            stage.addActor(textArea);

        } else if (itemNumber == 1) {

            TextArea textArea = new TextArea("Name: Rocket 2\nHit Probability: 60%\nShots: 3\nDamage: 3/10\nCosts: 100 $", skin);
            textArea.setPosition(1400,450);
            textArea.setWidth(400);
            textArea.setHeight(200);
            stage.addActor(textArea);

        } else if (itemNumber == 2) {

            TextArea textArea = new TextArea("Name: Male CrewMember\nRepairs: 50% per round\nCosts: 100 $", skin);
            textArea.setPosition(1400,450);
            textArea.setWidth(400);
            textArea.setHeight(200);
            stage.addActor(textArea);

        } else if (itemNumber == 3){

            TextArea textArea = new TextArea("Name: Female CrewMember\nRepairs: 60% per round\nCosts: 100 $", skin);
            textArea.setPosition(1400,450);
            textArea.setWidth(400);
            textArea.setHeight(200);
            stage.addActor(textArea);

        } else if (itemNumber == 4) {

            TextArea textArea = new TextArea("Name: Security\nAmount: + 10 %\nCosts: 10 $\nInfo: for the whole ship", skin);
            textArea.setPosition(1400,450);
            textArea.setWidth(400);
            textArea.setHeight(200);
            stage.addActor(textArea);
        }
        else if (itemNumber == 5) {

            TextArea textArea = new TextArea("Name: Oxygen\nCosts: 10 $\nInfo: for the whole ship", skin);
            textArea.setPosition(1400,450);
            textArea.setWidth(400);
            textArea.setHeight(200);
            stage.addActor(textArea);

        } else if (itemNumber == 6){

            TextArea textArea = new TextArea("Name: Drive\nCosts: 10 $\nInfo: for the whole ship", skin);
            textArea.setPosition(1400,450);
            textArea.setWidth(400);
            textArea.setHeight(200);
            stage.addActor(textArea);

        } else {
            TextArea textArea = new TextArea("No item chosen", skin);
            textArea.setPosition(1400,450);
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

    public void drawItems(){

        float playershipX = BaseScreen.WIDTH / 8;
        float playershipY = BaseScreen.HEIGHT / 8;
        float position1 = playerShip.getHeight();
        float position2 = playerShip.getWidth();

        if(!secureIconS1){
            stage.getBatch().draw(securityTextureGrey,playershipX + 150,playershipY + 520);
        }
        if(secureIconS1){
            stage.getBatch().draw(securityTexture,playershipX + 150,playershipY + 520);
        }
        if(!driveIconS1){
            stage.getBatch().draw(driveTextureGrey,playershipX + 190,playershipY + 520);
        }
        if(driveIconS1){
            stage.getBatch().draw(driveTexture,playershipX + 190,playershipY + 520);
        }
        if(!secureIconS2){
            stage.getBatch().draw(securityTextureGrey,playershipX + 150,playershipY + 140);
        }
        if(secureIconS2){
            stage.getBatch().draw(securityTexture,playershipX + 150,playershipY + 140);
        }
        if(!driveIconS2){
            stage.getBatch().draw(driveTextureGrey,playershipX + 190,playershipY + 140);
        }
        if(driveIconS2){
            stage.getBatch().draw(driveTexture,playershipX + 190,playershipY + 140);
        }
        if(rocket1s1){
            stage.getBatch().draw(rocket1,300,310);
        }
        if(rocket1s2){
            stage.getBatch().draw(rocket1,300,320);
        }
        if(rocket1s3){
            stage.getBatch().draw(rocket1,300,330);
        }
        if(rocket1s4){
            stage.getBatch().draw(rocket1,300,340);
        }
        if(rocket1s5){
            stage.getBatch().draw(rocket1,300,350);
        }
        if(rocket1s6){
            stage.getBatch().draw(rocket1,300,360);
        }
        if(rocket2s1){
            stage.getBatch().draw(rocket2,300,370);
        }
        if(rocket2s2){
            stage.getBatch().draw(rocket2,300,380);
        }
        if(rocket2s3){
            stage.getBatch().draw(rocket2,300,390);
        }
        if(rocket2s4){
            stage.getBatch().draw(rocket2,300,400);
        }
        if(rocket2s5){
            stage.getBatch().draw(rocket2,300,410);
        }
        if(rocket2s6){
            stage.getBatch().draw(rocket2,300,420);
        }
        if(crewMemberFs1){
            stage.getBatch().draw( crewMemberFTexture,300,430);
        }
        if(crewMemberFs2){
            stage.getBatch().draw(crewMemberFTexture,300,440);
        }
        if(crewMemberFs3){
            stage.getBatch().draw(crewMemberFTexture,300,450);
        }
        if(crewMemberFs4){
            stage.getBatch().draw(crewMemberFTexture,300,460);
        }
        if(crewMemberFs5){
            stage.getBatch().draw(crewMemberFTexture,300,470);
        }
        if(crewMemberFs6){
            stage.getBatch().draw(crewMemberFTexture,300,480);
        }
        if(crewMemberMs1){
            stage.getBatch().draw(crewMemberMTexture,300,490);
        }
        if(crewMemberMs2){
            stage.getBatch().draw(crewMemberMTexture,300,500);
        }
        if(crewMemberMs3){
            stage.getBatch().draw(crewMemberMTexture,300,510);
        }
        if(crewMemberMs4){
            stage.getBatch().draw(crewMemberMTexture,300,520);
        }
        if(crewMemberMs5){
            stage.getBatch().draw(crewMemberMTexture,300,530);
        }
        if(crewMemberMs6){
            stage.getBatch().draw(crewMemberMTexture,300,540);
        }
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getSecure() {
        return secure;
    }

    public void setSecure(int secure) {
        this.secure = secure;
    }

    public int getDrive() {
        return drive;
    }

    public void setDrive(int drive) {
        this.drive = drive;
    }

    public int getOxygen() {
        return oxygen;
    }

    public void setOxygen(int oxygen) {
        this.oxygen = oxygen;
    }

    public void setRocket1s1(boolean rocket1s1) {
        this.rocket1s1 = rocket1s1;
    }

    public void setRocket1s2(boolean rocket1s2) {
        this.rocket1s2 = rocket1s2;
    }

    public void setRocket1s3(boolean rocket1s3) {
        this.rocket1s3 = rocket1s3;
    }

    public void setRocket1s4(boolean rocket1s4) {
        this.rocket1s4 = rocket1s4;
    }

    public void setRocket1s5(boolean rocket1s5) {
        this.rocket1s5 = rocket1s5;
    }

    public void setRocket1s6(boolean rocket1s6) {
        this.rocket1s6 = rocket1s6;
    }

    public void setRocket2s1(boolean rocket2s1) {
        this.rocket2s1 = rocket2s1;
    }

    public void setRocket2s2(boolean rocket2s2) {
        this.rocket2s2 = rocket2s2;
    }

    public void setRocket2s3(boolean rocket2s3) {
        this.rocket2s3 = rocket2s3;
    }

    public void setRocket2s4(boolean rocket2s4) {
        this.rocket2s4 = rocket2s4;
    }

    public void setRocket2s5(boolean rocket2s5) {
        this.rocket2s5 = rocket2s5;
    }

    public void setRocket2s6(boolean rocket2s6) {
        this.rocket2s6 = rocket2s6;
    }

    public void setCrewMemberFs1(boolean crewMemberFs1) {
        this.crewMemberFs1 = crewMemberFs1;
    }

    public void setCrewMemberFs2(boolean crewMemberFs2) {
        this.crewMemberFs2 = crewMemberFs2;
    }

    public void setCrewMemberFs3(boolean crewMemberFs3) {
        this.crewMemberFs3 = crewMemberFs3;
    }

    public void setCrewMemberFs4(boolean crewMemberFs4) {
        this.crewMemberFs4 = crewMemberFs4;
    }

    public void setCrewMemberFs5(boolean crewMemberFs5) {
        this.crewMemberFs5 = crewMemberFs5;
    }

    public void setCrewMemberFs6(boolean crewMemberFs6) {
        this.crewMemberFs6 = crewMemberFs6;
    }

    public void setCrewMemberMs1(boolean crewMemberMs1) {
        this.crewMemberMs1 = crewMemberMs1;
    }

    public void setCrewMemberMs2(boolean crewMemberMs2) {
        this.crewMemberMs2 = crewMemberMs2;
    }

    public void setCrewMemberMs3(boolean crewMemberMs3) {
        this.crewMemberMs3 = crewMemberMs3;
    }

    public void setCrewMemberMs4(boolean crewMemberMs4) {
        this.crewMemberMs4 = crewMemberMs4;
    }

    public void setCrewMemberMs5(boolean crewMemberMs5) {
        this.crewMemberMs5 = crewMemberMs5;
    }

    public void setCrewMemberMs6(boolean crewMemberMs6) {
        this.crewMemberMs6 = crewMemberMs6;
    }
}
