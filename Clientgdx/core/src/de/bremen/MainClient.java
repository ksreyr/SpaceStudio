package de.bremen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Setter;
import lombok.Getter;
public class MainClient extends Game {
	@Setter
	@Getter
	private AssetManager manager;

	public LogginScreen logginScreen;

	@Override
	public void create () {
		manager = new AssetManager();
		manager.finishLoading();
		logginScreen = new LogginScreen(this);
		setScreen(logginScreen);
	}

}
