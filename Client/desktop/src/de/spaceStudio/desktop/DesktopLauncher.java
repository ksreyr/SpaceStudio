package de.spaceStudio.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.spaceStudio.MainClient;
import de.spaceStudio.screens.BaseScreen;
import de.spaceStudio.screens.ShopScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new ShopScreen(), config);
		config.width =  BaseScreen.WIDTH;
		config.height =  BaseScreen.HEIGHT;
        config.title = "SpaceStudio";


	}

/*
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MainClient(), config);

		config.width =  BaseScreen.WIDTH;
		config.height =  BaseScreen.HEIGHT;
		config.resizable = true;


	}*/
}
