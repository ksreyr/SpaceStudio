package de.spaceStudio.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;


public class InputHandler {

    public static boolean UP_TOUCHED;
    public static boolean DOWN_TOUCHED;
    public static boolean LEFT_TOUCHED;
    public static boolean RIGHT_TOUCHED;
    public boolean update(){
        // reset all variables
        UP_TOUCHED = false;
        DOWN_TOUCHED = false;
        LEFT_TOUCHED = false;
        RIGHT_TOUCHED = false;

        // set boolean to true if key is touched
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
           return LEFT_TOUCHED = true;
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            return DOWN_TOUCHED = true;
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            return RIGHT_TOUCHED = true;

        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            return UP_TOUCHED = true;
        }
        return false;
    }
}