package com.progmethgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import com.progmethgame.control.GameController;
import com.progmethgame.entity.Player;
import com.progmethgame.graphic.EntityRenderer;
import com.progmethgame.graphic.GameRenderer;
import com.progmethgame.graphic.GameScreen;
import com.progmethgame.map.TestMap;

public class GameRunner implements Disposable {
	
	final float tickRate = 1f/20; //Hz
	float tickAcc;
	
	GameState state;
	GameRenderer renderer;
	GameController controller;
	GameScreen screen;
	
	EntityRenderer entityRenderer;
	
	public GameRunner() {
		//GameState
		this.state = new GameState();
		tickAcc = 0;

		state.map = new TestMap();
		
		//Graphic
		this.entityRenderer = new EntityRenderer();
		this.renderer = new GameRenderer(state, entityRenderer, (d) -> shortTick(d));
		this.controller = new GameController(state);
		this.screen = new GameScreen(this.renderer, controller);
		
		// Spawn Entity
		state.player = new Player(entityRenderer);
		
		//Init Map
		state.map.setupGameState(this.state);
	}
	
	private void shortTick(float delta) {
		tickAcc += delta;
		if (tickAcc > tickRate) {
			tickAcc = 0;
			tick();
		}
		
		controller.tick(delta);
		
		//Todo: Maybe clean up?
		if (state.gameShouldClose)
			Gdx.app.exit();
	}
	
	private void tick() {
		
	}
	
	public GameRenderer getRenderer() {
		return this.renderer;
	}
	
	public GameScreen getScreen() {
		return this.screen;
	}

	@Override
	public void dispose() {
		this.renderer.dispose();
		this.screen.dispose();
	}

	public EntityRenderer getEntityRenderer() {
		return entityRenderer;
	}
}
