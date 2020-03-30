package com.progmethgame.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import com.progmethgame.EntityProcessorProvider;
import com.progmethgame.GameState;
import com.progmethgame.entity.WalkAi;
import com.progmethgame.entity.FollowAi;
import com.progmethgame.entity.Player;
import com.progmethgame.map.base.Map;

public class TestMap extends Map {

	@Override
	public String getMapId() {
		return "test";
	}
	
	@Override
	public void setupGameState(GameState state, EntityProcessorProvider provider) {
		//Special spawn point
		TiledMapTileMapObject spawn = (TiledMapTileMapObject) this.tilemap.getLayers().get("objectLayer").getObjects().get("spawn");
		Gdx.app.debug("Map::TestMap", "Spawning player at at (" + spawn.getX() + ", " + spawn.getY() + ")");
		state.player = new Player(provider);
		state.player.getPosition().set(spawn.getX()/getTileSize(), spawn.getY()/getTileSize());
		
		WalkAi fake = new WalkAi(provider);
		fake.getPosition().set(-1, -1);
		fake.getWaypointComponent().path.addLast(new Vector2(-1, 20));
		fake.getWaypointComponent().path.addLast(new Vector2(20, 20));
		fake.getWaypointComponent().path.addLast(new Vector2(20, -1));
		fake.getWaypointComponent().path.addLast(new Vector2(-1, -1));
		
		for (int i = 0; i < 4; i ++) {
			FollowAi foll = new FollowAi(provider, state.player.getPosition());
			foll.getPosition().set(4, 4 + i);
		}

	}

	@Override
	public boolean isSolid(int x, int y) {

		TiledMapTileLayer layer = (TiledMapTileLayer) tilemap.getLayers().get(0);
		if (x < 0 || x >= layer.getWidth() || y < 0 || y >= layer.getHeight())
			return false;
		return layer.getCell(x, y).getTile().getId() != 12;
	}

}
