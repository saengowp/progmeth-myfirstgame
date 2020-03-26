package com.progmethgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.progmethgame.graphic.GameScreen;

public class ProgMethGame extends Game {

	GameRunner runner;
	GameScreen screen;
	
	@Override
	public void create() {
		Gdx.app.setLogLevel(Gdx.app.LOG_DEBUG);

		this.runner = new GameRunner();
		setScreen(this.runner.getScreen());
	}
	
	@Override
	public void dispose() {
		this.runner.dispose();
	}

}
