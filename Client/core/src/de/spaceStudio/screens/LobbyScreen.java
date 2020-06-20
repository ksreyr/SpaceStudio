package de.spaceStudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.spaceStudio.MainClient;

import static de.spaceStudio.client.util.Global.currentPlayer;
import static de.spaceStudio.client.util.Global.playersOnline;
import static de.spaceStudio.service.LoginService.fetchLoggedUsers;
import static de.spaceStudio.service.LoginService.logout;

/**
 * Lobby screen to show online players
 *
 * @author Miguel Caceres
 * created on 6.17.2020
 */
public class LobbyScreen extends BaseScreen {

    private final MainClient lobby;

    private SpriteBatch batch;


    private BitmapFont font;

    public LobbyScreen(MainClient game) {
        super(game);
        this.lobby = game;
        fetchLoggedUsers();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, "Players online: " + String.valueOf(playersOnline.size()), 25, 300);
        int count = 0;
        for (String playerName : playersOnline) {
            count = count+15;
            font.draw(batch, playerName , 25, (200 + (count)));
        }


        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        logout(currentPlayer);
        super.dispose();
        batch.dispose();
        font.dispose();
    }

    public void hide(){

    }
}
