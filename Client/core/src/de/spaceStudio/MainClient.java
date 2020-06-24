package de.spaceStudio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.spaceStudio.screens.ShipSelectScreen;
import lombok.Getter;
import lombok.Setter;

import de.spaceStudio.screens.LoginScreen;

public class MainClient extends Game {
	@Setter
	@Getter
	private AssetManager assetManager;

	public LoginScreen loginScreen;
	public ShipSelectScreen shipSelectScreen;

	private SpriteBatch batch;



	@Override
	public void create () {

		assetManager = new AssetManager();
		assetManager.finishLoading();
		loginScreen = new LoginScreen(this);
		shipSelectScreen= new ShipSelectScreen(this);
		batch = new SpriteBatch();
	    setScreen(shipSelectScreen);

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
	public AssetManager getAssetmanager() {
		return assetManager;
	}

}
