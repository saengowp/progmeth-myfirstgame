package com.progmethgame.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
	
	TiledMap map;
	TiledMapRenderer mapRenderer;
	OrthographicCamera camera;
	Viewport viewport;
	Batch batch;
	
	GameController controller;
	GameClient client;

	public GameScreen(GameClient client, GameController control) {
		this.map = new TmxMapLoader().load("map/map.tmx");
		this.mapRenderer = new OrthogonalTiledMapRenderer(map, 1/8f);
		this.camera = new OrthographicCamera();
		this.viewport = new FillViewport(20, 20, this.camera);
		this.batch = new SpriteBatch();
		
		this.controller = control;
		
		this.client = client;
		//this.controller = new GameController(player);
	}
	
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(controller);
	}
	
	/*private void simulatePhysic(float delta) {
		player.normalizeMovement();
		
		
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
		final int mapWidht = map.getProperties().get("width", Integer.class);
		final int mapHeight = map.getProperties().get("height", Integer.class);
		//Collision
		
		player.setPosition(player.getX() + delta * player.velocity.x, player.getY() + delta * player.velocity.y);

		int pX = (int) player.getX(), pY = (int) player.getY();

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int xp = pX + i;
				int yp = pY + j;
				if (xp < 0 || xp >= mapWidht || yp < 0 || yp >= mapHeight || layer.getCell(xp, yp).getTile().getId() == 12) {
					continue;
				}
				
				float dx = player.getX() - xp, dy = player.getY() - yp;
				if (Math.abs(dx) >= 1 || Math.abs(dy) >= 1) {
					continue;
				}
				
				if (Math.abs(dx) > Math.abs(dy))
					player.setX(player.getX() + (dx > 0 ? 1 - dx : - 1 - dx));
				else
					player.setY(player.getY() + (dy > 0 ? 1 - dy : - 1 - dy));
							
			}
		}
		
	}
	*/

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f );
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//simulatePhysic(delta);
		client.tick(delta);

		ClientEntity player = client.entities.get(client.getPlayerId());
		camera.position.lerp(new Vector3(player.getX(), player.getY(), 0f) , Math.min(1, 5f * delta));
		
		
		camera.update();
		mapRenderer.setView(camera);
		mapRenderer.render();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		for (ClientEntity e : client.entities.values()) {
			e.draw(batch);
		}
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
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
