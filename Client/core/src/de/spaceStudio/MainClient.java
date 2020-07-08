package de.spaceStudio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.spaceStudio.screens.*;

import lombok.Getter;
import lombok.Setter;

public class MainClient extends Game {
	@Setter
	@Getter
	private AssetManager assetManager;

	public LoginScreen loginScreen;
	public ShipSelectScreen shipSelectScreen;
	public CombatScreen combatScreen;

	public StationsMap stationsMap;
	private SpriteBatch batch;



	@Override
	public void create () {

		assetManager = new AssetManager();
		assetManager.finishLoading();
		//loginScreen = new LoginScreen(this, assetManager);
		//stationsMap = new StationsMap(this);
		batch = new SpriteBatch();
		//setScreen(stationsMap);
	//	shipSelectScreen= new ShipSelectScreen(this);
		batch = new SpriteBatch();
	    //setScreen(loginScreen);
		//tp = new TP(this);
		//setScreen(tp);

	    combatScreen = new CombatScreen(this);
	    setScreen(combatScreen);




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
