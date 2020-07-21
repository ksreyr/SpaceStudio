package de.spaceStudio.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import de.spaceStudio.server.model.CrewMember;

public class Playership2 extends Rectangle {

    public float x;
    public float y;

    private Texture texture;
    private Texture rocket1, rocket2, crewMemberMTexture, crewMemberFTexture, oxygenTexture;


    //rocket1
    private boolean rocket1s1,rocket1s2,rocket1s3,rocket1s4,rocket1s5,rocket1s6;
    //rocket2
    private boolean rocket2s1,rocket2s2,rocket2s3,rocket2s4,rocket2s5,rocket2s6;
    //crewmemberF
    private boolean crewMemberFs1;
    private boolean crewMemberFs2;
    private boolean crewMemberFs3;
    private boolean crewMemberFs4;
    private boolean crewMemberFs5;
    private boolean crewMemberFs6;
    //crewmemberM
    private boolean crewMemberMs1;
    private boolean crewMemberMs2;
    private boolean crewMemberMs3;
    private boolean crewMemberMs4;
    private boolean crewMemberMs5;
    private boolean crewMemberMs6;


    private int money;  // FIXME WTF why is this not persisted?????????????
    private int secure;
    private int oxygen;
    private int drive;


    public Playership2(float x, float y) {
        this.x = 0;
        this.y = 0;

        this.money = 100;
        this.secure = 20;
        this.oxygen = 50;
        this.drive = 30;

        texture =  new Texture("Client/core/assets/data/ships/blueships1_section.png");
        rocket1 = new Texture("data/ships/rocketSmall.png");
        rocket2 = new Texture("data/ships/attack_small.png");
        crewMemberMTexture = new Texture("Client/core/assets/combatAssets/MaleHuman-3.png");
        crewMemberFTexture = new Texture("Client/core/assets/combatAssets/female_human.png");
        oxygenTexture = new Texture("Client/core/assets/OxygenSymbol_large.png");

        width = texture.getWidth();
        height = texture.getHeight();

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

    }

    public void render(SpriteBatch batch) {
        // draw texture
        batch.draw(texture, x, y);
        if(rocket1s1){
            batch.draw(rocket1,280,620  );
        }
        if(rocket1s2){
            batch.draw(rocket1,280,160 );
        }
        if(rocket1s3){
            batch.draw(rocket1,500,490 );
        }
        if(rocket1s4){
            batch.draw(rocket1,500,300 );
        }
        if(rocket1s5){
            batch.draw(rocket1,700,490 );
        }
        if(rocket1s6){
            batch.draw(rocket1,700,340 );
        }
        if(rocket2s1){
            batch.draw(rocket2,330,620);
        }
        if(rocket2s2){
            batch.draw(rocket2,330,160);
        }
        if(rocket2s3){
            batch.draw(rocket2,550,490);
        }
        if(rocket2s4){
            batch.draw(rocket2,550,300);
        }
        if(rocket2s5){
            batch.draw(rocket2,750,490);
        }
        if(rocket2s6){
            batch.draw(rocket2,750,340);
        }
        if(crewMemberFs1){
            batch.draw(crewMemberFTexture,300,690);
        }
        if(crewMemberFs2){
            batch.draw(crewMemberFTexture,300,230);
        }
        if(crewMemberFs3){
            batch.draw(crewMemberFTexture,500,560);
        }
        if(crewMemberFs4){
            batch.draw(crewMemberFTexture,500,370);
        }
        if(crewMemberFs5){
            batch.draw(crewMemberFTexture,700,560);
        }
        if(crewMemberFs6){
            batch.draw(crewMemberFTexture,700,410);
        }
        if(crewMemberMs1){
            batch.draw(crewMemberMTexture,350,690);
        }
        if(crewMemberMs2){
            batch.draw(crewMemberMTexture,350,230);
        }
        if(crewMemberMs3){
            batch.draw(crewMemberMTexture,550,560);
        }
        if(crewMemberMs4){
            batch.draw(crewMemberMTexture,550,370);
        }
        if(crewMemberMs5){
            batch.draw(crewMemberMTexture,750,560);
        }
        if(crewMemberMs6){
            batch.draw(crewMemberMTexture,750,410);
        }

    }

    public void dispose() {
        texture.dispose();
        rocket1.dispose();
        rocket2.dispose();
        crewMemberFTexture.dispose();
        crewMemberMTexture.dispose();
        oxygenTexture.dispose();
    }

    public boolean isRocket1s1() {
        return rocket1s1;
    }

    public void setRocket1s1(boolean rocket1s1) {
        this.rocket1s1 = rocket1s1;
    }

    public boolean isRocket1s2() {
        return rocket1s2;
    }

    public void setRocket1s2(boolean rocket1s2) {
        this.rocket1s2 = rocket1s2;
    }

    public boolean isRocket1s3() {
        return rocket1s3;
    }

    public void setRocket1s3(boolean rocket1s3) {
        this.rocket1s3 = rocket1s3;
    }

    public boolean isRocket1s4() {
        return rocket1s4;
    }

    public void setRocket1s4(boolean rocket1s4) {
        this.rocket1s4 = rocket1s4;
    }

    public boolean isRocket1s5() {
        return rocket1s5;
    }

    public void setRocket1s5(boolean rocket1s5) {
        this.rocket1s5 = rocket1s5;
    }

    public boolean isRocket1s6() {
        return rocket1s6;
    }

    public void setRocket1s6(boolean rocket1s6) {
        this.rocket1s6 = rocket1s6;
    }

    public boolean isRocket2s1() {
        return rocket2s1;
    }

    public void setRocket2s1(boolean rocket2s1) {
        this.rocket2s1 = rocket2s1;
    }

    public boolean isRocket2s2() {
        return rocket2s2;
    }

    public void setRocket2s2(boolean rocket2s2) {
        this.rocket2s2 = rocket2s2;
    }

    public boolean isRocket2s3() {
        return rocket2s3;
    }

    public void setRocket2s3(boolean rocket2s3) {
        this.rocket2s3 = rocket2s3;
    }

    public boolean isRocket2s4() {
        return rocket2s4;
    }

    public void setRocket2s4(boolean rocket2s4) {
        this.rocket2s4 = rocket2s4;
    }

    public boolean isRocket2s5() {
        return rocket2s5;
    }

    public void setRocket2s5(boolean rocket2s5) {
        this.rocket2s5 = rocket2s5;
    }

    public boolean isRocket2s6() {
        return rocket2s6;
    }

    public void setRocket2s6(boolean rocket2s6) {
        this.rocket2s6 = rocket2s6;
    }

    public boolean isCrewMemberFs1() {
        return crewMemberFs1;
    }

    public void setCrewMemberFs1(boolean crewMemberFs1) {
        this.crewMemberFs1 = crewMemberFs1;
    }

    public boolean isCrewMemberFs2() {
        return crewMemberFs2;
    }

    public void setCrewMemberFs2(boolean crewMemberFs2) {
        this.crewMemberFs2 = crewMemberFs2;
    }

    public boolean isCrewMemberFs3() {
        return crewMemberFs3;
    }

    public void setCrewMemberFs3(boolean crewMemberFs3) {
        this.crewMemberFs3 = crewMemberFs3;
    }

    public boolean isCrewMemberFs4() {
        return crewMemberFs4;
    }

    public void setCrewMemberFs4(boolean crewMemberFs4) {
        this.crewMemberFs4 = crewMemberFs4;
    }

    public boolean isCrewMemberFs5() {
        return crewMemberFs5;
    }

    public void setCrewMemberFs5(boolean crewMemberFs5) {
        this.crewMemberFs5 = crewMemberFs5;
    }

    public boolean isCrewMemberFs6() {
        return crewMemberFs6;
    }

    public void setCrewMemberFs6(boolean crewMemberFs6) {
        this.crewMemberFs6 = crewMemberFs6;
    }

    public boolean isCrewMemberMs1() {
        return crewMemberMs1;
    }

    public void setCrewMemberMs1(boolean crewMemberMs1) {
        this.crewMemberMs1 = crewMemberMs1;
    }

    public boolean isCrewMemberMs2() {
        return crewMemberMs2;
    }

    public void setCrewMemberMs2(boolean crewMemberMs2) {
        this.crewMemberMs2 = crewMemberMs2;
    }

    public boolean isCrewMemberMs3() {
        return crewMemberMs3;
    }

    public void setCrewMemberMs3(boolean crewMemberMs3) {
        this.crewMemberMs3 = crewMemberMs3;
    }

    public boolean isCrewMemberMs4() {
        return crewMemberMs4;
    }

    public void setCrewMemberMs4(boolean crewMemberMs4) {
        this.crewMemberMs4 = crewMemberMs4;
    }

    public boolean isCrewMemberMs5() {
        return crewMemberMs5;
    }

    public void setCrewMemberMs5(boolean crewMemberMs5) {
        this.crewMemberMs5 = crewMemberMs5;
    }

    public boolean isCrewMemberMs6() {
        return crewMemberMs6;
    }

    public void setCrewMemberMs6(boolean crewMemberMs6) {
        this.crewMemberMs6 = crewMemberMs6;
    }

    public int getSecure() {
        return secure;
    }

    public void setSecure(int secure) {
        this.secure = secure;
    }

    public int getOxygen() {
        return oxygen;
    }

    public void setOxygen(int oxygen) {
        this.oxygen = oxygen;
    }

    public int getDrive() {
        return drive;
    }

    public void setDrive(int drive) {
        this.drive = drive;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}