package com.progmethgame;

import com.progmethgame.ai.TickProcessor;
import com.progmethgame.ai.WaypointProcessor;
import com.progmethgame.graphic.EntityRenderer;
import com.progmethgame.physic.PhysicProcessor;

public class EntityProcessorProvider {

	public EntityRenderer renderer;
	public PhysicProcessor physic;
	public WaypointProcessor waypoint;
	public TickProcessor ticker;
}
