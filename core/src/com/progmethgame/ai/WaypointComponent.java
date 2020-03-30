package com.progmethgame.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import com.progmethgame.map.base.Map;
import com.progmethgame.physic.PhysicalComponent;

public class WaypointComponent {
	
	PhysicalComponent physic;
	float walkSpeed;

	public Queue<Vector2> path;

	
	public WaypointComponent(PhysicalComponent physic, float walkSpeed) {
		this.physic = physic;
		this.walkSpeed = walkSpeed;
		this.path = new Queue<Vector2>();
	}
	
	public void update(float delta, Map map) {
		
		while (!path.isEmpty() && path.first().dst2(physic.position) <= 0.01) {
			//Waypoint reached
			//Gdx.app.debug("Waypoint", "Reached " + path.first().x + ", " + path.first().y);
			path.removeFirst();
			physic.velocity.setZero();
		}

		if (!path.isEmpty()) {
			//Try to walk
			Vector2 head = path.first().cpy().sub(physic.position);
			physic.velocity.set(head.nor().scl(walkSpeed));
		}

	}
}
