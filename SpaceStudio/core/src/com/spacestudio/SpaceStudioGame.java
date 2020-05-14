package com.spacestudio;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import com.spacestudio.screen.game.GameScreen;

public class SpaceStudioGame extends Game {


	//create() Called when the Application is first created.
	@Override
	public void create() {
		// LOG_DEBUG will let all messages/log output through.
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		setScreen(new GameScreen());
	}
}


