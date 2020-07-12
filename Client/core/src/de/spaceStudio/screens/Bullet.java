package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.spaceStudio.server.model.Weapon;

public class Bullet extends Weapon {

    public static final int SPEED = 450;
    public static int DEFAULT_X = 200;
    public static int DEFAULT_Y = 200;
    private static Texture bullet;

    public boolean remove = false;

    float x, y;
    public Bullet(float x,float y){
        this.y = y;
        this.x = x;
        if(bullet == null){
            bullet = new Texture("Client/core/assets/combatAssets/bullet.png");
        }
    }

    public void update(float deltaTime){
        x += SPEED * deltaTime;
        if(x > Gdx.graphics.getWidth()-500 ){
            remove = true;
        }
    }


    public void render(SpriteBatch batch){

        batch.draw(bullet,x,y);
    }



}
