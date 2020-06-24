package de.spaceStudio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.spaceStudio.screens.StationsMap;
import lombok.Getter;
import lombok.Setter;

import de.spaceStudio.screens.LoginScreen;

public class MainClient extends Game {
	@Setter
	@Getter
	private AssetManager assetManager;

	public LoginScreen loginScreen;

	public StationsMap stationsMap;
	private SpriteBatch batch;


	@Override
	public void create () {


		assetManager = new AssetManager();
		assetManager.finishLoading();
		//loginScreen = new LoginScreen(this, assetManager);
		stationsMap = new StationsMap(this,assetManager);


		batch = new SpriteBatch();
		//setScreen(loginScreen);
		setScreen(stationsMap);

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
