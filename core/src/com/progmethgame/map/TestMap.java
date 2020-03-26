package com.progmethgame.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.progmethgame.GameState;
import com.progmethgame.map.base.Map;

public class TestMap extends Map {

	@Override
	public String getMapId() {
		return "test";
	}
	
	@Override
	public void setupGameState(GameState state) {
		super.setupGameState(state);
		
		//Special spawn point
		TiledMapTileMapObject spawn = (TiledMapTileMapObject) this.tilemap.getLayers().get("objectLayer").getObjects().get("spawn");
		Gdx.app.debug("Map::TestMap", "found spawn at (" + spawn.getX() + ", " + spawn.getY() + ")");
		state.player.getPosition().set(spawn.getX()/getTileSize(), spawn.getY()/getTileSize());
	}

}
