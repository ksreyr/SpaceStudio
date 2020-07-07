package de.spaceStudio.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PlayerShip extends Rectangle {

    public float x;
    public float y;

    private Texture texture;
    private Texture securityTexture;
    private Texture weaponTexture;

    //Security
    private boolean securityFst;
    private boolean securitySnd;
    private boolean securityTrd;
    private boolean securityFth;

    //Weapon
    private boolean weaponFst;
    private boolean weaponSnd;
    private boolean weaponTrd;
    private boolean weaponFth;


    //Money
    private float money;

    public PlayerShip(float x, float y) {
        this.x = x;
        this.y = y;

        this.securityFst = false;
        this.securitySnd = false;
        this.securityTrd = false;
        this.securityFth = false;

        this.weaponFst = false;
        this.weaponSnd = false;
        this.weaponTrd = false;
        this.weaponFth = false;

        this.money = 100f;

        texture =  new Texture("data/ships/redship2small.png");
        securityTexture = new Texture("data/ships/securitySmall.png");
        weaponTexture = new Texture("data/ships/rocketSmall.png");

        width = texture.getWidth();
        height = texture.getHeight();
    }
    public void render(SpriteBatch batch) {
        // draw texture
        batch.draw(texture, x, y);
        if(securityFst){
            batch.draw(securityTexture,800,530); //todo nicht in pixeln denken
        }
        if(securitySnd){
            batch.draw(securityTexture,980,600);
        }
        if(securityTrd){
            batch.draw(securityTexture,980,470);
        }
        if(securityFth){
            batch.draw(securityTexture,1150,530);
        }

        if(weaponFst){
            batch.draw(weaponTexture,840,520 ); //todo nicht in pixeln denken
        }
        if(weaponSnd){
            batch.draw(weaponTexture,1020,590);
        }
        if(weaponTrd){
            batch.draw(weaponTexture,1020,460);
        }
        if(weaponFth){
            batch.draw(weaponTexture,1190,520);
        }
    }

    public void dispose() {
        texture.dispose();
        securityTexture.dispose();
    }

    public void setSecurityFst(boolean securityFst) {
        this.securityFst = securityFst;
    }

    public void setSecuritySnd(boolean securitySnd) {
        this.securitySnd = securitySnd;
    }

    public void setSecurityTrd(boolean securityTrd) {
        this.securityTrd = securityTrd;
    }

    public void setSecurityFth(boolean securityFth) {
        this.securityFth = securityFth;
    }

    public void setWeaponFst(boolean weaponFst) {
        this.weaponFst = weaponFst;
    }

    public void setWeaponSnd(boolean weaponSnd) {
        this.weaponSnd = weaponSnd;
    }

    public void setWeaponTrd(boolean weaponTrd) {
        this.weaponTrd = weaponTrd;
    }

    public void setWeaponFth(boolean weaponFth) {
        this.weaponFth = weaponFth;
    }

    public void setMoney(float amount){
        this.money = money + amount;
    }
    public float getMoney(){
        return this.money;
    }
}
