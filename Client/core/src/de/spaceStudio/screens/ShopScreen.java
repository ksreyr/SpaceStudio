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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.gson.Gson;
import de.spaceStudio.MainClient;
import de.spaceStudio.assets.StyleNames;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.client.util.RequestUtils;
import de.spaceStudio.server.model.*;
import de.spaceStudio.util.GdxUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static de.spaceStudio.client.util.RequestUtils.setupRequest;
import static de.spaceStudio.screens.CombatScreen.*;


public class ShopScreen extends ScreenAdapter {

    private final MainClient universeMap;
    private final AssetManager assetManager;
    private final MainClient mainClient;
    private final Viewport viewport;
    private final Skin spaceSkin;
    private final BitmapFont font;
    private final ShapeRenderer renderer;
    //externes Schiff
    private final SpriteBatch batch;
    //Textures
    private final Texture background;
    private final Texture playerShip;
    private final Texture securityTextureGrey;
    private final Texture driveTextureGrey;
    private final Texture rocket1;
    private final Texture rocket2;
    private final Texture crewMemberMTexture;
    private final Texture crewMemberFTexture;
    private final Texture weaponTexture;
    private final Texture oxygenTexture;
    private final Texture driveTexture;
    private Texture shieldSystem, weaponsSystem, driveSystem, gold, energie;
    private Label goldLabel;
    private final Skin skin;
    public CheckBox checkBoxSection1, checkBoxSection2, checkBoxSection3, checkBoxSection4, checkBoxSection5, checkBoxSection6, checkBoxAllSections;
    //
    List<ShipRessource> shipRessources = new ArrayList<>();
    List<ShopRessource> shopRessources = new ArrayList<>();
    private Stage stage;
    private TextButton next, buy, sell;
    private int itemNumber;

    //crewmember
    private boolean crewMemberS1, crewMemberS2, crewMemberS3, crewMemberS4, crewMemberS5, crewMemberS6;

    private List<CrewMember> myCrew;
    private List<Image> listOfCrewMemberImages;

    public ShopScreen(MainClient mainClient) {
        viewport = new StretchViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT);
        this.universeMap = mainClient;
        this.mainClient = mainClient;
        assetManager = universeMap.getAssetManager();
        this.renderer = new ShapeRenderer();
        this.skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        font = new BitmapFont(Gdx.files.internal("Client/core/assets/skin/default.fnt"));
        spaceSkin = new Skin(Gdx.files.internal("Client/core/assets/ownAssets/sgx/skin/sgx-ui.json"));
        background = new Texture("ownAssets/sgx/backgrounds/galaxyBackground.png");
        playerShip = new Texture("Client/core/assets/data/ships/blueships1_section.png");
        rocket1 = new Texture("data/ships/rocketSmall.png");
        rocket2 = new Texture("data/ships/attack_small.png");
        crewMemberMTexture = new Texture("Client/core/assets/combatAssets/male_human.png");
        crewMemberFTexture = new Texture("Client/core/assets/combatAssets/female_human.png");
        weaponTexture = new Texture("Client/core/assets/combatAssets/missille_out.png");
        oxygenTexture = new Texture("Client/core/assets/OxygenSymbol_large.png");
        driveTexture = new Texture("data/ships/batterie.png");
        //if secure or drive is too low
        securityTextureGrey = new Texture("data/ships/securityGrey.png");
        driveTextureGrey = new Texture("data/ships/batterie.png");
        shieldSystem = new Texture(Gdx.files.internal("Client/core/assets/data/ships/shield.png"));
        driveSystem = new Texture(Gdx.files.internal("Client/core/assets/data/ships/drive.png"));
        weaponsSystem = new Texture(Gdx.files.internal("Client/core/assets/data/ships/weapons.png"));
        //gold = new Texture(Gdx.files.internal("Client/core/assets/data/ships/weapons.png"));
        energie = new Texture(Gdx.files.internal("Client/core/assets/combatAssets/Energy.png"));
        this.goldLabel = new Label("Gold",skin);

        shieldSystem = new Texture(Gdx.files.internal("Client/core/assets/data/ships/shield.png"));
        driveSystem = new Texture(Gdx.files.internal("Client/core/assets/data/ships/drive.png"));
        weaponsSystem = new Texture(Gdx.files.internal("Client/core/assets/data/ships/weapons.png"));

        listOfCrewMemberImages = new ArrayList<>();
        myCrew = Global.combatCrew.get(Global.currentShipPlayer.getId());

        for(int i = 0; i < myCrew.size(); i++){
            listOfCrewMemberImages.add(new Image(new Texture(Gdx.files.internal("Client/core/assets/combatAssets/" +
                    myCrew.get(i).getImg()))));
        }
        for(int i = 0; i < listOfCrewMemberImages.size(); i++){
            listOfCrewMemberImages.get(i).setBounds(30,30,30,30);
            listOfCrewMemberImages.get(i).setPosition(XPlayerShip + myCrew.get(i).getCurrentSection().getxPos(),
                    YPlayerShip + myCrew.get(i).getCurrentSection().getyPos());
        }
        this.itemNumber = 0;
        batch = new SpriteBatch();


        //crewmemberF
        this.crewMemberS1 = false;
        this.crewMemberS2 = false;
        this.crewMemberS3 = false;
        this.crewMemberS4 = false;
        this.crewMemberS5 = false;
        this.crewMemberS6 = false;

        getShipRessourcen(Global.currentShipPlayer, Net.HttpMethods.POST);
        getShopRessourcen(Global.currentStop, Net.HttpMethods.POST);
        //
        nextButton();
        buyItemsButton();
        sellItemsButton();
    }


    @Override
    public void show() {

        //viewport = new FitViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage = new Stage(viewport, universeMap.getBatch());
        stage.addActor(next);
        stage.addActor(buy);
        stage.addActor(sell);

        //Back to Map Button
        TextButton backToMap = new TextButton(" Back to Map ", spaceSkin, StyleNames.EMPHASISTEXTBUTTON);
        backToMap.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                RequestUtils.weaponsByShip(Global.currentShipPlayer);
                RequestUtils.crewMemeberByShip(Global.currentShipPlayer);
                mainClient.setScreen(new StationsMap(mainClient));
            }
        });
        backToMap.setPosition(1650, 1000);
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
        for(int i = 0; i < listOfCrewMemberImages.size(); i++){
            stage.addActor(listOfCrewMemberImages.get(i));
        }

        Gdx.input.setInputProcessor(stage);
    }

    public void getShipRessourcen(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        final String url = Global.SERVER_URL + Global.GET_RESSOURCE_BY_SHIP;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed GegnermakeShot");
                }
                System.out.println("statusCode GegnermakeShot: " + statusCode);
                String shipRessource = httpResponse.getResultAsString();
                Gson gson = new Gson();
                ShipRessource[] sectiongegnerArray = gson.fromJson(shipRessource, ShipRessource[].class);
                shipRessources = Arrays.asList(sectiongegnerArray);
                System.out.println("statusCode makeAShot: " + statusCode);
            }

            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }

            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
    }

    public void getShopRessourcen(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        Gson gson = new Gson();
        gson.toJson(requestObject);
        String requestJson = json.toJson(requestObject);
        requestJson = gson.toJson(requestObject);
        final String url = Global.SERVER_URL + Global.GET_RESSOURCE_BY_STOP;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed getShopRessourcen");
                }
                System.out.println("statusCode getShopRessourcen: " + statusCode);
                String shopRessource = httpResponse.getResultAsString();
                Gson gson = new Gson();
                ShopRessource[] shopRessourceList = gson.fromJson(shopRessource, ShopRessource[].class);
                shopRessources = Arrays.asList(shopRessourceList);
                System.out.println("statusCode getShopRessourcen: " + statusCode);
            }

            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }

            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
    }

    public void buyItem(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        final String url = Global.SERVER_URL + Global.BUY_RESSOURCE;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed buyItem");
                }
                System.out.println("statusCode buyItem: " + statusCode);
                String shipRessource = httpResponse.getResultAsString();
                Gson gson = new Gson();
                ShipRessource[] shipRessourceList = gson.fromJson(shipRessource, ShipRessource[].class);
                shipRessources = Arrays.asList(shipRessourceList);
                getShopRessourcen(Global.currentStop, Net.HttpMethods.POST);
                System.out.println("statusCode makeAShot: " + statusCode);
            }

            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }

            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
    }

    public void buyCrewMember(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        final String url = Global.SERVER_URL + Global.BUY_CREWMEMBER;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed buyCrewMember");
                }
                System.out.println("statusCode buyCrewMember: " + statusCode);
                String shipRessource = httpResponse.getResultAsString();
                Gson gson = new Gson();
                ShipRessource[] shipRessourceList = gson.fromJson(shipRessource, ShipRessource[].class);
                shipRessources = Arrays.asList(shipRessourceList);
                getShopRessourcen(Global.currentStop, Net.HttpMethods.POST);
                System.out.println("statusCode buyCrewMember: " + statusCode);
            }

            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }

            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
    }

    public void buyWeapons(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        final String url = Global.SERVER_URL + Global.BUY_WEAPONS;
        final Net.HttpRequest request = setupRequest(url, requestJson, method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed buyWeapons");
                }
                System.out.println("statusCode buyWeapons: " + statusCode);
                String shipRessource = httpResponse.getResultAsString();
                Gson gson = new Gson();
                ShipRessource[] shipRessourceList = gson.fromJson(shipRessource, ShipRessource[].class);
                shipRessources = Arrays.asList(shipRessourceList);
                getShopRessourcen(Global.currentStop, Net.HttpMethods.POST);
                System.out.println("statusCode buyWeapons: " + statusCode);
            }

            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }

            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
    }

    @Override
    public void render(float delta) {

        GdxUtils.clearScreen();

        stage.getBatch().begin();
        if (!shipRessources.isEmpty()) {
            TextArea textArea = new TextArea(shipRessources.get(0).getName().toString() + shipRessources.get(0).getAmount(), skin);
            textArea.setPosition(BaseScreen.WIDTH / 20, 850);
            textArea.setWidth(200);
            textArea.setHeight(150);
            stage.addActor(textArea);
        }

        TextArea shipInformationArea = new TextArea("Ship Description: \nSection1: \nSection2: Secure \nSection3: \nSection4: Weapon \nSection5: \nSection6: Enegie", skin);
        shipInformationArea.setPosition(900, 50);
        shipInformationArea.setWidth(500);
        shipInformationArea.setHeight(270);
        stage.addActor(shipInformationArea);

        stage.getBatch().draw(background, 0, 0, BaseScreen.WIDTH, BaseScreen.HEIGHT);
        //stage.getBatch().draw(playerShip, BaseScreen.WIDTH / 8, BaseScreen.HEIGHT / 8);

        float positionX = 1450;
        float positionY = 700;

        switch (itemNumber) {
            case 0: //Gold
                //stage.getBatch().draw(gold, positionX, positionY);
                stage.addActor(goldLabel);
                goldLabel.setPosition(positionX, positionY);
                break;
            case 1:
                goldLabel.remove();
                stage.getBatch().draw(energie, positionX, positionY);
                break;
            case 2://TODO Role
                stage.getBatch().draw(crewMemberFTexture, positionX, positionY);
                break;
                /*
            case 3:
                stage.getBatch().draw(crewMemberMTexture, positionX, positionY);
                break;
                */
            case 3:
                stage.getBatch().draw(weaponTexture, positionX, positionY);
                break;
            case 4:
                stage.getBatch().draw(shieldSystem, positionX, positionY);
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
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        spaceSkin.dispose();
        renderer.dispose();
        batch.dispose();
        background.dispose();
        playerShip.dispose();
        securityTextureGrey.dispose();
        driveTextureGrey.dispose();
        rocket1.dispose();
        rocket2.dispose();
        crewMemberMTexture.dispose();
        crewMemberFTexture.dispose();
        weaponTexture.dispose();
        oxygenTexture.dispose();
        driveTexture.dispose();
        skin.dispose();
    }

    public void nextButton() {
        next = new TextButton("next", spaceSkin, StyleNames.EMPHASISTEXTBUTTON);
        next.setPosition(1600, 700);

        next.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (itemNumber > 3)
                    itemNumber = 0;
                else {
                    itemNumber++;
                }
            }
        });
    }

    public void buyItemsButton() {
        buy = new TextButton("buy", spaceSkin, StyleNames.EMPHASISTEXTBUTTON);
        buy.setPosition(900, 780);

        buy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeItems(true);
                if (itemNumber == 0) {
                    buyItem(List.of(shopRessources.get(0)), Net.HttpMethods.POST);
                } else if (itemNumber == 1) {
                    //TODO buy Energie??
                    buyItem(List.of(shopRessources.get(1)), Net.HttpMethods.POST);

                } else if (itemNumber == 2) {
                    if (checkBoxSection1.isChecked()) {
                        buyCrewMember(List.of(CrewMember.crewMemberBuilder().name("Female").role(Role.FIGHTER).currentSection(Global.section1).health(100).buildCrewMember()), Net.HttpMethods.POST);
                    } else if (checkBoxSection2.isChecked()) {
                        buyCrewMember(List.of(CrewMember.crewMemberBuilder().name("Female").role(Role.FIGHTER).currentSection(Global.section2).health(100).buildCrewMember()), Net.HttpMethods.POST);
                    } else if (checkBoxSection3.isChecked()) {
                        buyCrewMember(List.of(CrewMember.crewMemberBuilder().name("Female").role(Role.FIGHTER).currentSection(Global.section3).health(100).buildCrewMember()), Net.HttpMethods.POST);
                    } else if (checkBoxSection4.isChecked()) {
                        buyCrewMember(List.of(CrewMember.crewMemberBuilder().name("Female").role(Role.FIGHTER).currentSection(Global.section4).health(100).buildCrewMember()), Net.HttpMethods.POST);
                    } else if (checkBoxSection5.isChecked()) {
                        buyCrewMember(List.of(CrewMember.crewMemberBuilder().name("Female").role(Role.FIGHTER).currentSection(Global.section5).health(100).buildCrewMember()), Net.HttpMethods.POST);
                    } else if (checkBoxSection6.isChecked()) {
                        buyCrewMember(List.of(CrewMember.crewMemberBuilder().name("Female").role(Role.FIGHTER).currentSection(Global.section6).health(100).buildCrewMember()), Net.HttpMethods.POST);
                    }
                /*
                } else if (itemNumber == 3) {
                    if (checkBoxSection1.isChecked()) {
                        buyCrewMember(List.of(CrewMember.crewMemberBuilder().name("Male").role(Role.FIGHTER).currentSection(Global.section1).health(80).buildCrewMember()), Net.HttpMethods.POST);
                    } else if (checkBoxSection2.isChecked()) {
                        buyCrewMember(List.of(CrewMember.crewMemberBuilder().name("Male").role(Role.FIGHTER).currentSection(Global.section2).health(80).buildCrewMember()), Net.HttpMethods.POST);
                    } else if (checkBoxSection3.isChecked()) {
                        buyCrewMember(List.of(CrewMember.crewMemberBuilder().name("Male").role(Role.FIGHTER).currentSection(Global.section3).health(80).buildCrewMember()), Net.HttpMethods.POST);
                    } else if (checkBoxSection4.isChecked()) {
                        buyCrewMember(List.of(CrewMember.crewMemberBuilder().name("Male").role(Role.FIGHTER).currentSection(Global.section4).health(80).buildCrewMember()), Net.HttpMethods.POST);
                    } else if (checkBoxSection5.isChecked()) {
                        buyCrewMember(List.of(CrewMember.crewMemberBuilder().name("Male").role(Role.FIGHTER).currentSection(Global.section5).health(80).buildCrewMember()), Net.HttpMethods.POST);
                    } else if (checkBoxSection6.isChecked()) {
                        buyCrewMember(List.of(CrewMember.crewMemberBuilder().name("Male").role(Role.FIGHTER).currentSection(Global.section6).health(80).buildCrewMember()), Net.HttpMethods.POST);
                    }
                 */

                } else if (itemNumber == 3) {
                    buyWeapons(List.of(Weapon.WeaponBuilder().damage(50).name("Laser gekauft").hitRate(5).warmUp(4).magazinSize(3).section(Global.section4).img("Lasser").build()), Net.HttpMethods.POST);
                } else if (itemNumber == 4) {
                    //TODO buy shield
                }

                setAllSectionCheckboxesFalse();
            }
        });
    }

    public void sellItemsButton() {
        sell = new TextButton("sell", spaceSkin, StyleNames.EMPHASISTEXTBUTTON);
        sell.setPosition(950, 780);

        sell.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeItems(false);
                //setAllSectionCheckboxesFalse();
            }
        });
        //drawItems();
    }

    public void changeItems(boolean b) {

        if(itemNumber == 2) {
            if (checkBoxSection1.isChecked()) {
                    setCrewMemberS1(b);
            }

            if (checkBoxSection2.isChecked()) {
                    setCrewMemberS2(b);
            }

            if (checkBoxSection3.isChecked()) {
                    setCrewMemberS3(b);
            }

            if (checkBoxSection4.isChecked()) {
                    setCrewMemberS4(b);
            }

            if (checkBoxSection5.isChecked()) {
                    setCrewMemberS5(b);
            }
            if (checkBoxSection6.isChecked()) {
                    setCrewMemberS6(b);
            }
        }

    }


    public void showTextfield(int itemNumber) {
        if (!shopRessources.isEmpty()) {
            if (itemNumber == 0) { //Gold

                TextArea textArea = new TextArea(shopRessources.get(0).getName() + " Amount: " + shopRessources.get(0).getAmount() + " Price: " + shopRessources.get(0).getPrice(), skin);
                textArea.setPosition(1400, 450);
                textArea.setWidth(400);
                textArea.setHeight(200);
                stage.addActor(textArea);

            } else if (itemNumber == 1) { //Energie

                TextArea textArea = new TextArea(shopRessources.get(1).getName() + " Amount: " + shopRessources.get(1).getAmount() + " Price: " + shopRessources.get(1).getPrice(), skin);
                textArea.setPosition(1400, 450);
                textArea.setWidth(400);
                textArea.setHeight(200);
                stage.addActor(textArea);

            } else if (itemNumber == 2) { //CrewMember

                TextArea textArea = new TextArea("Name: Crew Member \nRole: \nCosts: 50 $ \n-> Choose sections to place CrewMember <-", skin);
                textArea.setPosition(1400, 450);
                textArea.setWidth(400);
                textArea.setHeight(200);
                stage.addActor(textArea);

             /*
            } else if (itemNumber == 3) { //CrewMember

                TextArea textArea = new TextArea("Name: CrewMember\nRole: \nCosts: 100 $", skin);
                textArea.setPosition(1400, 450);
                textArea.setWidth(400);
                textArea.setHeight(200);
                stage.addActor(textArea); */

            } else if (itemNumber == 3) { //Weapon

                TextArea textArea = new TextArea("Name: WEAPON LASSER\nAmount: 50  \nCost:30 ", skin);
                textArea.setPosition(1400, 450);
                textArea.setWidth(400);
                textArea.setHeight(200);
                stage.addActor(textArea);

            } else if (itemNumber == 4) { //Secure

                TextArea textArea = new TextArea("Name: Secure \nAmount: 50  \nCost:30 ", skin);
                textArea.setPosition(1400, 450);
                textArea.setWidth(400);
                textArea.setHeight(200);
                stage.addActor(textArea);

            }
        }
    }

    public void setAllSectionCheckboxesFalse() {
        checkBoxSection1.setChecked(false);
        checkBoxSection2.setChecked(false);
        checkBoxSection3.setChecked(false);
        checkBoxSection4.setChecked(false);
        checkBoxSection5.setChecked(false);
        checkBoxSection6.setChecked(false);
    }

    public void drawItems() {

        float playershipX = BaseScreen.WIDTH / 8;
        float playershipY = BaseScreen.HEIGHT / 8;
        float position1 = playerShip.getHeight();
        float position2 = playerShip.getWidth();

        stage.getBatch().draw(playerShip, XPlayerShip, YPlayerShip, WidthPlayerShip, HeightPlayerShip);
        stage.getBatch().draw(shieldSystem, XPlayerShip + 210, YPlayerShip + 290);
        stage.getBatch().draw(driveSystem, XPlayerShip + 110, YPlayerShip + 80);
        stage.getBatch().draw(weaponsSystem, XPlayerShip + 295, YPlayerShip + 180);


        if (crewMemberS1) {
            stage.getBatch().draw(crewMemberFTexture, XPlayerShip + 120, YPlayerShip + 400);
        }
        if (crewMemberS2) {
            stage.getBatch().draw(crewMemberFTexture, XPlayerShip + 200, YPlayerShip + 310);
        }
        if (crewMemberS3) {
            stage.getBatch().draw(crewMemberFTexture,XPlayerShip + 300, YPlayerShip + 300);
        }
        if (crewMemberS4) {
            stage.getBatch().draw(crewMemberFTexture, XPlayerShip + 300, YPlayerShip + 200);
        }
        if (crewMemberS5) {
            stage.getBatch().draw(crewMemberFTexture, XPlayerShip + 200, YPlayerShip + 200);
        }
        if (crewMemberS6) {
            stage.getBatch().draw(crewMemberFTexture, XPlayerShip + 120, YPlayerShip + 100);
        }
    }

    public void setCrewMemberS1(boolean crewMemberS1) {
        this.crewMemberS1 = crewMemberS1;
    }

    public void setCrewMemberS2(boolean crewMemberS2) {
        this.crewMemberS2 = crewMemberS2;
    }

    public void setCrewMemberS3(boolean crewMemberS3) {
        this.crewMemberS3 = crewMemberS3;
    }

    public void setCrewMemberS4(boolean crewMemberS4) {
        this.crewMemberS4 = crewMemberS4;
    }

    public void setCrewMemberS5(boolean crewMemberS5) {
        this.crewMemberS5 = crewMemberS5;
    }

    public void setCrewMemberS6(boolean crewMemberS6) {
        this.crewMemberS6 = crewMemberS6;
    }
}
