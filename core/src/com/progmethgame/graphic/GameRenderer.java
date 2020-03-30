package com.progmethgame.graphic;

import java.util.function.Consumer;
import java.util.function.Function;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.progmethgame.GameState;

public class GameRenderer implements Disposable{
	
	OrthographicCamera camera;
	MapRenderer mapRenderer;
	EntityRenderer entityRenderer;
	SpriteBatch batch;
	
	GameState state;
	
	Consumer<Float> updateCallback;
	
	public GameRenderer(GameState state, EntityRenderer entityRenderer, Consumer<Float> updateCallback) {
		this.camera = new OrthographicCamera(); //Viewport will controls the projection.
		this.mapRenderer = new MapRenderer(state.map, camera);
		this.entityRenderer = entityRenderer;
		
		this.batch = new SpriteBatch();
		
		this.camera.position.set(0, 0, 0);
		
		this.state = state;
		this.updateCallback = updateCallback;
	}
	

	public void render(float delta) {
		updateCallback.accept(delta);
		
		//Follows player
		final float followFactor = 0.1f;
		camera.position.lerp(new Vector3(state.player.getPosition(), 0f), followFactor);
		
		this.camera.update();

		this.mapRenderer.render();
		
		this.batch.setProjectionMatrix(this.camera.combined);
		this.batch.begin();
		entityRenderer.render(batch);
		this.batch.end();
	}
	
	public OrthographicCamera getCamera() {
		return this.camera;
	}
	
	public EntityRenderer getEntityRenderer() {
		return this.entityRenderer;
	}

	@Override
	public void dispose() {
		this.mapRenderer.dispose();
	}

}
