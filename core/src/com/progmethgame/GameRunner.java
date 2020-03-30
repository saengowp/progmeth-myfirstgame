package com.progmethgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import com.progmethgame.ai.TickProcessor;
import com.progmethgame.ai.WaypointProcessor;
import com.progmethgame.control.GameController;
import com.progmethgame.entity.Player;
import com.progmethgame.graphic.EntityRenderer;
import com.progmethgame.graphic.GameRenderer;
import com.progmethgame.graphic.GameScreen;
import com.progmethgame.map.TestMap;
import com.progmethgame.physic.PhysicProcessor;

public class GameRunner implements Disposable {
	
	final float tickRate = 1f/20; //Hz
	float tickAcc;
	
	GameState state;
	GameRenderer renderer;
	GameController controller;
	GameScreen screen;
	
	EntityProcessorProvider entityProcessor;
	
	public GameRunner() {
		//GameState
		this.state = new GameState();
		tickAcc = 0;

		state.map = new TestMap();
		
		this.entityProcessor = new EntityProcessorProvider();
		
		//Graphic
		this.entityProcessor.renderer = new EntityRenderer();
		this.renderer = new GameRenderer(state, entityProcessor.renderer, (d) -> shortTick(d));
		this.controller = new GameController(state, renderer);
		this.screen = new GameScreen(this.renderer, controller);
		
		//Physic
		this.entityProcessor.physic = new PhysicProcessor(this.state.map);
		
		//AI
		this.entityProcessor.waypoint = new WaypointProcessor(state.map);
		this.entityProcessor.ticker = new TickProcessor();
		
		//Init Map
		state.map.setupGameState(this.state, this.entityProcessor);
	}
	
	private void shortTick(float delta) {
		tickAcc += delta;
		if (tickAcc > tickRate) {
			tick(tickAcc);

			tickAcc = 0;
		}
		
		//controller.tick(delta);

		entityProcessor.physic.step(tickAcc);
		
		//Todo: Maybe clean up?
		if (state.gameShouldClose)
			Gdx.app.exit();
	}
	
	private void tick(float delta) {
		entityProcessor.waypoint.update(delta);
		entityProcessor.ticker.tick(delta);
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

}
