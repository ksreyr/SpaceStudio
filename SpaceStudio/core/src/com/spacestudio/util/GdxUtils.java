package com.spacestudio.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

public class GdxUtils {

    public static void clearScreen(){
        clearScreen(Color.BLACK);
    }

    public static void clearScreen(Color color) {
        if (color == null) {
            color = Color.BLACK;
        }

        Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}
