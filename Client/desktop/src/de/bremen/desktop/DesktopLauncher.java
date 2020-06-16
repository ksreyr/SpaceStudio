package de.bremen.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.bremen.MainClient;
import de.bremen.screens.BaseScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MainClient(), config);
		config.width = (int) BaseScreen.WIDTH;
		config.height = (int) BaseScreen.HEIGHT;
		config.fullscreen = true;
	}
}
