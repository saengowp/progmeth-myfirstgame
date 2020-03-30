package com.progmethgame.entity;

import com.badlogic.gdx.math.Vector2;
import com.progmethgame.EntityProcessorProvider;
import com.progmethgame.ai.AutoWaypointComponent;

public class FollowAi extends WalkAi {

	public FollowAi(EntityProcessorProvider entityProcessor, Vector2 target) {
		super(entityProcessor);
		this.waypoint = new AutoWaypointComponent(physical, 1f, target);
	}
	
	

}
