package com.progmethgame.entity;

import com.progmethgame.EntityProcessorProvider;
import com.progmethgame.ai.Tickable;
import com.progmethgame.ai.WaypointComponent;
import com.progmethgame.ai.WaypointEntity;

public class WalkAi extends Player implements WaypointEntity , Tickable{

	WaypointComponent waypoint;
	
	public WalkAi(EntityProcessorProvider entityProcessor) {
		super(entityProcessor);
		this.waypoint = new WaypointComponent(physical, 1f);
		entityProcessor.waypoint.register(this);
		entityProcessor.ticker.register(this);
	}

	@Override
	public WaypointComponent getWaypointComponent() {
		return waypoint;
	}

	@Override
	public void tick(float delta) {
		setFacing(position.cpy().add(physical.velocity));
	}

}
