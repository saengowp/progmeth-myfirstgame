package com.progmethgame.ai;

import com.progmethgame.entity.base.EntityProcessor;
import com.progmethgame.map.base.Map;

public class WaypointProcessor extends EntityProcessor<WaypointEntity>{
	
	Map map;
	
	public WaypointProcessor(Map map) {
		this.map = map;
	}

	public void update(float delta) {
		for (WaypointEntity e : entities) {
			e.getWaypointComponent().update(delta, map);
		}
	}
	
}
