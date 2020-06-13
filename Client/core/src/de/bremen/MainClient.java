package de.bremen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.bremen.screens.LoginScreen;
import lombok.Getter;
<<<<<<< HEAD
import lombok.Setter;
=======


>>>>>>> master
public class MainClient extends Game {
	@Setter
	@Getter
	private AssetManager assetManager;

<<<<<<< HEAD
	public LoginScreen loginScreen;
=======


	public LoginScreen logginScreen;
>>>>>>> master
	private SpriteBatch batch;


	@Override
	public void create () {


		assetManager = new AssetManager();
		assetManager.finishLoading();
		loginScreen = new LoginScreen(this, assetManager);


		batch = new SpriteBatch();
		setScreen(loginScreen);

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
