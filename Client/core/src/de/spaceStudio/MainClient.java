package de.spaceStudio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.screens.*;
import de.spaceStudio.service.LoginService;

import java.util.Timer;
import java.util.TimerTask;

import static de.spaceStudio.client.util.Global.*;
import static de.spaceStudio.service.LoginService.fetchLoggedUsers;


public class MainClient extends Game {

    public LoginScreen loginScreen;
    private AssetManager assetManager;
    private SpriteBatch batch;

    private StopScreen stopScreen;
    private CombatScreen combatScreen;


    @Override
    public void create() {

        assetManager = new AssetManager();
        assetManager.finishLoading();
        loginScreen = new LoginScreen(this, assetManager);
        batch = new SpriteBatch();

        // 5 Minutes Timeout
        Timer schedule = new Timer();
        schedule.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!IS_SINGLE_PLAYER && multiPlayerGameStarted){
                    System.out.println(playersOnline.size());
                    fetchLoggedUsers();
                if(playersOnline.size() == 0){
                    schedule.cancel();
                    schedule.purge();
                    System.out.println("Player two leave the game");
                    // Current Player won the game
                }
                if(seedTimer > 0){
                    System.out.println("5 minutes.....................................");
                    // DO here win game when other player offline
                }
                seedTimer += 1;
            }
            }
        }, 1000, 300000);

        //300000 = 5 minutes
        //stopScreen = new StopScreen(this);
        //	winScreen = new WinScreen(this);
        setScreen(loginScreen);
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public void dispose() {
        super.dispose();
        assetManager.dispose();
        batch.dispose();

        if (Global.currentPlayer != null) {
            if(!IS_SINGLE_PLAYER){
                LoginService.multiplayerLogout();
                LoginService.unjoinMultiplayer();
            }
            LoginService.logout(Global.currentPlayer);
        }
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
