package de.spaceStudio.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PlayerShip extends Rectangle {

    public float x;
    public float y;

    public float money = 1000.00f;


    private Texture texture;
    private Texture securityTexture;

    private boolean securityFst;
    private boolean securitySnd;
    private boolean securityTrd;
    private boolean securityFth;

    public PlayerShip(){
        this.x = 0;
        this.y = 0;
    }

    public PlayerShip(float x, float y) {
        this.x = x;
        this.y = y;

        this.securityFst = false;
        this.securitySnd = false;
        this.securityTrd = false;
        this.securityFth = false;

        texture =  new Texture("data/ships/redship2small.png");
        securityTexture = new Texture("data/ships/securitySmall.png");

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
            batch.draw(securityTexture,980,600); //todo nicht in pixeln denken
        }
        if(securityTrd){
            batch.draw(securityTexture,980,470); //todo nicht in pixeln denken
        }
        if(securityFth){
            batch.draw(securityTexture,1150,530); //todo nicht in pixeln denken
        }
    }

    public float getMoney(){
        return money;
    }

    public void dispose() {
        texture.dispose();
        securityTexture.dispose();
    }

    public void setSecurityFst(boolean b){
        this.securityFst = b;
    }
    public void setSecuritySnd(boolean b){
        this.securitySnd = b;
    }
    public void setSecurityTrd(boolean b){
        this.securityTrd = b;
    }
    public void setSecurityFth(boolean b){
        this.securityFth = b;
    }
}
