package com.progmethgame.map.base;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;
import com.progmethgame.EntityProcessorProvider;
import com.progmethgame.GameState;

public abstract class Map implements Disposable {
	
	protected TiledMap tilemap;
	
	public Map() {
		String id = this.getMapId();
		
		//Load the TMX file.
		this.tilemap = new TmxMapLoader().load("map/" + id + ".tmx");
	}
	

	public abstract String getMapId();
	
	@Override
	public void dispose() {
		this.tilemap.dispose();
	}

	public TiledMap getTileMap() {
		return this.tilemap;
	}
	
	public int getTileSize() {
		return this.tilemap.getProperties().get("tilewidth", Integer.class);
	}
	
	public abstract boolean isSolid(int x, int y);

	public abstract void setupGameState(GameState state, EntityProcessorProvider provider);

}
