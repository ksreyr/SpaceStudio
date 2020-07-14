package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.spaceStudio.MainClient;
import de.spaceStudio.assets.StyleNames;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.server.model.CrewMember;
import de.spaceStudio.util.GdxUtils;

import java.util.Collection;


public class ShopScreen2 extends ScreenAdapter {

    private final MainClient universeMap;
    private MainClient mainClient;
    private final AssetManager assetManager;
    private Viewport viewport;
    private Stage stage;
    private TextureAtlas gamePlayAtlas;
    private Skin spaceSkin;
    private TextButton buyItem;
    private ShapeRenderer shapeRenderer;
    private boolean weaponFst;

    private Texture background, playerShip;
    private Texture weaponTexture, crewMemberMTexture, crewMemberFTexture, item1, item2, item3;
    private TextButton next;
    private int itemNumber;
    private CheckBox checkBoxSection1, checkBoxSection2, checkBoxSection3, checkBoxSection4, checkBoxSection5, checkBoxSection6;




    public ShopScreen2(MainClient mainClient) {
        this.universeMap = mainClient;
        this.mainClient = mainClient;
        assetManager = universeMap.getAssetmanager();
        this.weaponFst = false;

        spaceSkin =  new Skin(Gdx.files.internal("Client/core/assets/ownAssets/sgx/skin/sgx-ui.json"));
        background = new Texture("ownAssets/sgx/backgrounds/galaxyBackground.png");
        playerShip = new Texture("Client/core/assets/data/ships/blueships1_section.png");

        weaponTexture = new Texture("data/ships/rocketSmall.png");
        crewMemberMTexture = new Texture("Client/core/assets/MaleHuman-3.png");
        crewMemberFTexture = new Texture("Client/core/assets/FemaleHuman-2.png");
        this.itemNumber = 0;
        nextButton();
    }

    @Override
    public void show(){
        viewport = new FitViewport(BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage = new Stage(viewport, universeMap.getBatch());
        stage.addActor(next);



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
        checkBoxSection1.setPosition(800, 900);
        checkBoxSection2.setPosition(800, 870);
        checkBoxSection3.setPosition(800, 840);
        checkBoxSection4.setPosition(800, 810);
        checkBoxSection5.setPosition(800, 780);
        checkBoxSection6.setPosition(800, 750);
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

        //Dialog dialog = new Dialog("Store", skin);
        //dialog.setSize(500,250);
        //dialog.setPosition(1300,700);

        //final SelectBox<String> selectBox1 = new SelectBox<String>(skin);
        //selectBox1.setItems("Weapon 1","Weapon 2","Weapon 3","CrewMember");
        //final SelectBox<String> selectBox2 = new SelectBox<String>(skin);
        //selectBox2.setItems("Section 1","Section 2","Section 3","Section 4","Section 5","Section 6");

        TextButton buyButton = new TextButton("    Buy    ", spaceSkin, StyleNames.EMPHASISTEXTBUTTON);
        buyButton.setPosition(800,710);
        buyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                weaponFst = true;
            }
        });
        stage.addActor(buyButton);

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

        stage.getBatch().draw(background, 0, 0, BaseScreen.WIDTH, BaseScreen.HEIGHT);
        stage.getBatch().draw(playerShip, 200,200,700,700);

        if(weaponFst){
            stage.getBatch().draw(weaponTexture,840,520 );
        }

        switch (itemNumber) {
            case 0:
                stage.getBatch().draw(weaponTexture, 1200, 600);
                break;
            case 1:
                stage.getBatch().draw(crewMemberMTexture, 1200, 600);
                break;
            case 2:
                stage.getBatch().draw(crewMemberFTexture,1200, 600);
                break;
        }
        showTextfield(itemNumber);

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
        next.setPosition(1300,600);
        next.getLabel().setColor(Color.BLACK);

        next.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(itemNumber > 1)
                    itemNumber=0;
                else {
                    itemNumber++;
                }
            }
        });

    }

    public void showTextfield(int itemNumber){
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        if (itemNumber == 0) {
            TextField info = new TextField("informationen", skin);
            info.setPosition(1100,400);
            info.setSize(250,150);
            stage.addActor(info);
        } else {
            TextField info = new TextField("another informationen", skin);
            info.setPosition(1100,400);
            info.setSize(250,150);
            stage.addActor(info);
        }
    }

}
