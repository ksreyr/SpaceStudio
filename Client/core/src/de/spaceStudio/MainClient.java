package de.spaceStudio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.spaceStudio.screens.Ships;
import lombok.Getter;
import lombok.Setter;

import de.spaceStudio.screens.LoginScreen;

public class MainClient extends Game {
	@Setter
	@Getter
	private AssetManager assetManager;

	public LoginScreen loginScreen;
	public Ships ships;

	private SpriteBatch batch;



	@Override
	public void create () {


		assetManager = new AssetManager();
		assetManager.finishLoading();
	//	loginScreen = new LoginScreen(this);
		ships = new Ships(this);

		batch = new SpriteBatch();
	//	setScreen(loginScreen);
		setScreen(ships);

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
