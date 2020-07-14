package de.spaceStudio.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Playership2 extends Rectangle {

    public float x;
    public float y;

    private Texture texture;
    private Texture rocket1, rocket2, crewMemberMTexture, crewMemberFTexture, oxygenTexture;


    //rocket1
    private boolean rocket1s1;
    private boolean rocket1s2;
    private boolean rocket1s3;
    private boolean rocket1s4;
    private boolean rocket1s5;
    private boolean rocket1s6;
    //rocket2
    private boolean rocket2s1;
    private boolean rocket2s2;
    private boolean rocket2s3;
    private boolean rocket2s4;
    private boolean rocket2s5;
    private boolean rocket2s6;
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


    private float money;
    private int secure;

    public Playership2(float x, float y) {
        this.x = 0;
        this.y = 0;

        this.money = 100f;
        this.secure = 20;

        texture =  new Texture("Client/core/assets/data/ships/blueships1_section.png");
        rocket1 = new Texture("data/ships/rocketSmall.png");
        rocket2 = new Texture("data/ships/attack.png");
        crewMemberMTexture = new Texture("Client/core/assets/MaleHuman-3.png");
        crewMemberFTexture = new Texture("Client/core/assets/FemaleHuman-2.png");
        oxygenTexture = new Texture("Client/core/assets/OxygenSymbol.png");

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
            batch.draw(rocket1,840,520 );
        }
        if(rocket1s2){
            batch.draw(rocket1,840,520 );
        }
        if(rocket1s3){
            batch.draw(rocket1,840,520 );
        }
        if(rocket1s4){
            batch.draw(rocket1,840,520 );
        }
        if(rocket1s5){
            batch.draw(rocket1,840,520 );
        }
        if(rocket1s6){
            batch.draw(rocket1,840,520 );
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

    public void setMoney(float amount){
        this.money = money + amount;
    }
    public float getMoney(){
        return this.money;
    }
}