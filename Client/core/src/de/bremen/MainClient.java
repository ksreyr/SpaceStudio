package de.bremen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.bremen.screens.LogginScreen;
import lombok.Setter;
import lombok.Getter;


public class MainClient extends Game {
	@Setter
	@Getter
	private AssetManager assetManager;



	public LogginScreen logginScreen;
	private SpriteBatch batch;

	@Override
	public void create () {
		assetManager = new AssetManager();

		assetManager.finishLoading();
		logginScreen = new LogginScreen(this);
		batch = new SpriteBatch();
		setScreen(logginScreen);

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
