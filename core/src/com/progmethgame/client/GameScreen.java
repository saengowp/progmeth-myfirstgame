package com.progmethgame.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.progmethgame.network.ClientBus;

public class GameScreen implements Screen {
	
	TiledMap map;
	TiledMapRenderer mapRenderer;
	OrthographicCamera camera;
	Viewport viewport;
	Batch batch;
	
	Viewport hudViewport;
	
	GameController controller;
	ClientRuntime runtime;
	GameDebugger debugger;

	public GameScreen(ClientRuntime runtime, GameController control, GameDebugger debugger) {
		this.map = new TmxMapLoader().load("map/map.tmx");
		this.mapRenderer = new OrthogonalTiledMapRenderer(map, 1/8f);
		this.camera = new OrthographicCamera();
		this.viewport = new FillViewport(20, 20, this.camera);
		this.batch = new SpriteBatch();
		this.hudViewport = new ScreenViewport();
		
		this.controller = control;
		this.runtime = runtime;
		this.debugger = debugger;
	}
	
	
	@Override
	public void show() {
		InputMultiplexer inputMultiplexer = new InputMultiplexer(debugger, controller); 
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f );
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//simulatePhysic(delta);
		runtime.tick(delta);

		ClientEntity player = runtime.getPlayer();
		camera.position.lerp(new Vector3(player.getX(), player.getY(), 0f) , Math.min(1, 5f * delta));
		
		
		camera.update();
		viewport.apply();
		mapRenderer.setView(camera);
		mapRenderer.render();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		for (ClientEntity e : runtime.getEntities()) {
			e.draw(batch);
		}
		
		batch.end();
		
		
		Camera hudCam =  hudViewport.getCamera();
		hudCam.update();
		hudViewport.apply();
		
		batch.setProjectionMatrix(hudCam.combined);
		batch.begin();
		
		debugger.render(batch, hudViewport);
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		hudViewport.update(width, height, true);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		this.map.dispose();
		
		//this.player.dispose();
	}

}
