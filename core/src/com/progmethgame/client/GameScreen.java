package com.progmethgame.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.progmethgame.common.GameConfig;
import com.progmethgame.common.context.GameContext;

/**
 * Handle rendering of the game
 */
public class GameScreen implements Screen {
	
	/** Game's map */
	private TiledMap map;
	
	/** Map's renderer */
	private TiledMapRenderer mapRenderer;
	
	/** Game's camera */
	private OrthographicCamera camera;
	
	/** Game's viewport */
	private Viewport viewport;
	
	/** Rendering Batch */
	private Batch batch;
	
	/** Screen's viewport */
	private Viewport hudViewport;
	
	/** Debugger UI */
	private GameDebugger debugger;

	public GameScreen() {
		this.map = new TmxMapLoader().load(GameConfig.MAP_FILEPATH);
		this.mapRenderer = new OrthogonalTiledMapRenderer(map, 1f/map.getProperties().get("tilewidth", Integer.class));
		this.camera = new OrthographicCamera();
		this.viewport = new FillViewport(GameConfig.CLIENT_MAP_VIEWPORT_SIZE, GameConfig.CLIENT_MAP_VIEWPORT_SIZE, this.camera);
		this.batch = new SpriteBatch();
		this.hudViewport = new ScreenViewport();
		this.debugger = new GameDebugger();
	}
	
	
	@Override
	public void show() {
		setupInput();
	}
	
	/**
	 * Setup input controller
	 */
	private void setupInput() {
		GameInputController controller = new GameInputController();
		InputMultiplexer inputMultiplexer = new InputMultiplexer(debugger, controller); 
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	

	@Override
	public void render(float delta) {
		// Apply time-step
		GameContext.getClientContext().tick(delta);

		// Follows the player
		ClientEntity player = GameContext.getClientContext().getEntities().get(GameContext.getClientContext().getClientUUID());
		camera.position.lerp(new Vector3(player.getX(), player.getY(), 0f) , Math.min(1, 5f * delta));
		
		// Clear the screen
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f );
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Render the entity
		camera.update();
		viewport.apply();
		mapRenderer.setView(camera);
		mapRenderer.render();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (ClientEntity e : GameContext.getClientContext().getEntities().values()) {
			e.draw(batch);
		}
		batch.end();
		
		// Render the HUD and Debugger
		Camera hudCam =  hudViewport.getCamera();
		hudCam.update();
		hudViewport.apply();
		batch.setProjectionMatrix(hudCam.combined);
		batch.begin();
		for (ClientEntity e : GameContext.getClientContext().getEntities().values()) {
			//Project the entity to screen coordinate
			Rectangle rect = new Rectangle();
			Vector2 posvec = new Vector2(e.getX(), e.getY());
			viewport.project(posvec);
			Vector2 uvec = new Vector2(e.getX() + 1, e.getY() + 1);
			viewport.project(uvec);
			rect.setPosition(posvec);
			rect.setSize(uvec.x - posvec.x, uvec.y - posvec.y);
			
			//Draw
			e.drawOverlay(hudViewport, batch, rect);
		}
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
	}

}
