package com.spacestudio.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.spacestudio.UniverseMapBeforeGame;
import com.spacestudio.config.GameConfig;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		// set screen size
		config.width =  (int) GameConfig.WIDTH;
		config.height = (int) GameConfig.HEIGHT;

		new LwjglApplication(new UniverseMapBeforeGame(), config);
	}
}
