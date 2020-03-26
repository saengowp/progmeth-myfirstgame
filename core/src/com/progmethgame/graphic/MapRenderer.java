package com.progmethgame.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.progmethgame.map.base.Map;

public class MapRenderer implements Disposable{
	
	Map map;
	OrthographicCamera camera;
	OrthogonalTiledMapRenderer mapRenderer;
	
	int blockSize;

	public MapRenderer(Map map, OrthographicCamera camera) {
		this.map = map;
		this.camera = camera;
		
		this.blockSize = this.map.getTileSize();
		this.mapRenderer = new OrthogonalTiledMapRenderer(map.getTileMap(), 1f/this.blockSize);
		Gdx.app.debug("MapRenderer", "Rendering with 1/" + this.blockSize + " unit scale");
	}
	
	public void render() {
		this.mapRenderer.setView(camera);
		this.mapRenderer.render();
	}
	
	@Override
	public void dispose() {
		this.mapRenderer.dispose();
	}
	
}
