package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.spaceStudio.util.Base;
import de.spaceStudio.util.GdxUtils;


import static com.badlogic.gdx.graphics.Color.RED;

public class TestScreen extends Base {


    private static final float WORLD_HEIGHT = 40f;
    private static final float WORLD_WIDTH = 80f;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private boolean drawGrid = true;
    private boolean drawCircles = true;
    private boolean drawRectangles = true;
    private boolean drawSecurity = false;

    public TestScreen(){
    }

    @Override
    public void create(){
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH,WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width,height);
    }

    @Override
    public void render(){
        GdxUtils.clearScreen();

        renderer.setProjectionMatrix(camera.combined);

        if(drawGrid){
            drawGrid();
        }
        if(drawCircles){
            drawCircles();
        }
        if(drawRectangles){
            drawRectangles();
        }

    }

    public void drawGrid() {
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);

        int worldWidth = (int) WORLD_WIDTH;
        int worldHeight = (int) WORLD_HEIGHT;

        for(int x = -worldWidth; x < worldHeight; x++){
            renderer.line(x, -worldHeight,x,worldHeight);
        }

        for(int y = -worldHeight; y < worldHeight; y++){
            renderer.line(-worldWidth, y, worldWidth, y);
        }

        renderer.setColor(RED);
        renderer.line(-worldWidth, 0.0f, worldWidth, 0.0f);
        renderer.line(0.0f, -worldHeight, 0.0f, worldHeight);

        renderer.end();
    }

    public void drawCircles(){
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.GREEN);
        renderer.circle(0,0,11,40);
        renderer.end();
    }

    public void drawRectangles(){
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        //Testweise Sektionen
        renderer.setColor(Color.FOREST);
        renderer.rect(-8,0,8,8);
        renderer.setColor(Color.BLUE);
        renderer.rect(-8,-8,8,8);
        renderer.setColor(Color.BLUE);
        renderer.rect(0,0,8,8);
        renderer.setColor(Color.FOREST);
        renderer.rect(0,-8,8,8);

        //Testweise Items, die in Sektionen gezogen werden können
        //Box
        renderer.setColor(Color.WHITE);
        renderer.rect(-32,-8,8,16);
        //Items
        renderer.setColor(Color.BLACK);
        renderer.rect(-30,-7,2,2);
        renderer.rect(-30,-3,2,2);
        renderer.rect(-30,1,2,2);
        renderer.rect(-30,5,2,2);

        renderer.end();

    }

    @Override
    public void dispose(){
        renderer.dispose();
    }


}
