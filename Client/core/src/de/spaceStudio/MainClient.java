package de.spaceStudio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.spaceStudio.screens.CombatScreen;
import de.spaceStudio.screens.LoginScreen;
import de.spaceStudio.screens.StopScreen;
import de.spaceStudio.screens.WinScreen;

public class MainClient extends Game {

    public LoginScreen loginScreen;
    private AssetManager assetManager;
    private SpriteBatch batch;

    private StopScreen stopScreen;
    private CombatScreen combatScreen;
    private WinScreen winScreen;


    @Override
    public void create() {

        assetManager = new AssetManager();
        assetManager.finishLoading();
        loginScreen = new LoginScreen(this, assetManager);
        batch = new SpriteBatch();
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
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
