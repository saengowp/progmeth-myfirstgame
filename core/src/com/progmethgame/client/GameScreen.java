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
import com.progmethgame.common.context.ClientContext;
import com.progmethgame.common.context.GameContext;

public class GameScreen implements Screen {
	
	private TiledMap map;
	private TiledMapRenderer mapRenderer;
	private OrthographicCamera camera;
	private Viewport viewport;
	private Batch batch;
	
	private Viewport hudViewport;
	
	private GameDebugger debugger;
	private ClientContext context;
	

	public GameScreen() {
		this.map = new TmxMapLoader().load("map/map.tmx");
		this.mapRenderer = new OrthogonalTiledMapRenderer(map, 1f/map.getProperties().get("tilewidth", Integer.class));
		this.camera = new OrthographicCamera();
		this.viewport = new FillViewport(20, 20, this.camera);
		this.batch = new SpriteBatch();
		this.hudViewport = new ScreenViewport();
		
		this.debugger = new GameDebugger();
		this.context = GameContext.getClientContext();
	}
	
	
	@Override
	public void show() {
		setupInput();
	}
	
	private void setupInput() {
		GameController controller = new GameController();
		InputMultiplexer inputMultiplexer = new InputMultiplexer(debugger, controller); 
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f );
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		context.tick(delta);

		ClientEntity player = context.getEntities().get(context.getClientUUID());
		camera.position.lerp(new Vector3(player.getX(), player.getY(), 0f) , Math.min(1, 5f * delta));
		
		
		camera.update();
		viewport.apply();
		mapRenderer.setView(camera);
		mapRenderer.render();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		for (ClientEntity e : context.getEntities().values()) {
			e.draw(batch);
		}
		
		batch.end();
		
		
		Camera hudCam =  hudViewport.getCamera();
		hudCam.update();
		hudViewport.apply();
		
		batch.setProjectionMatrix(hudCam.combined);
		batch.begin();
		for (ClientEntity e : context.getEntities().values()) {
			Rectangle rect = new Rectangle();
			Vector2 posvec = new Vector2(e.getX(), e.getY());
			viewport.project(posvec);
			Vector2 uvec = new Vector2(e.getX() + 1, e.getY() + 1);
			viewport.project(uvec);
			rect.setPosition(posvec);
			rect.setSize(uvec.x - posvec.x, uvec.y - posvec.y);
			
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
		
		//this.player.dispose();
	}

}
