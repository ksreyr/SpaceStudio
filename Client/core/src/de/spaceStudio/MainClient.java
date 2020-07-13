package de.spaceStudio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.spaceStudio.screens.*;

import lombok.Getter;
import lombok.Setter;

public class MainClient extends Game {

	private AssetManager assetManager;

	public LoginScreen loginScreen;

	private SpriteBatch batch;

	public CombatScreen combatScreen;
   public MenuScreen menuScreen;



	@Override
	public void create () {

		assetManager = new AssetManager();
		assetManager.finishLoading();
		//menuScreen = new MenuScreen(this);
		//loginScreen = new LoginScreen(this, assetManager);
		batch = new SpriteBatch();


		combatScreen = new CombatScreen(this);
	    setScreen(combatScreen);

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
