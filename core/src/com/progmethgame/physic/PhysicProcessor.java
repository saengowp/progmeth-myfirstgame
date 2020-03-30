package com.progmethgame.physic;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.progmethgame.entity.base.EntityProcessor;
import com.progmethgame.map.base.Map;

public class PhysicProcessor extends EntityProcessor<PhysicalEntity> {
	
	Map map;
	
	public PhysicProcessor(Map map) {
		this.map = map;
	}
	
	private boolean isCollide(PhysicalComponent a, PhysicalComponent b) {
		//Check collide
		float delX = b.position.x - a.position.x;
		float delY = b.position.y - a.position.y;
		//It suppose to be one but we want tight space
		if (Math.abs(delX) >= 0.8 || Math.abs(delY) >= 0.8)
			return false;
		return true;
	}

	private void resolveCollideRevert(PhysicalComponent a, PhysicalComponent b) {
		if (!isCollide(a, b))
			return;
		a.position.set(a.prvPos);
		b.position.set(b.prvPos);
	}
	
	private void resolveCollideRepell(PhysicalComponent a, PhysicalComponent b) {
		if (!isCollide(a, b))
			return;
		Vector2 repelVec = a.position.cpy().sub(b.position);
		a.velocity.set(repelVec);
		b.velocity.sub(repelVec);
	}
	
	private void applyFriction(PhysicalComponent a, float delta) {
		a.velocity.sub(a.velocity.cpy().nor().scl(delta).clamp(0, a.velocity.len()));
	}
	
	
	
	private void resolveMapCollide(PhysicalComponent e) {
		int posX = (int) e.position.x;
		int posY = (int) e.position.y;
		
		PhysicalComponent temp = new PhysicalComponent(new Vector2(), 1e9f);
		
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int rX = i + posX, rY = j + posY;
				if (map.isSolid(rX, rY)) {
					temp.velocity.setZero();
					temp.position.set(rX, rY);
					resolveCollideRevert(temp, e);
				}
			}
		}
	}

	public void step(float delta) {

		for (PhysicalEntity e : this.entities) {
			e.getPhysicalComponent().prvPos.set(e.getPhysicalComponent().position);
			e.getPhysicalComponent().position.add(e.getPhysicalComponent().velocity.cpy().scl(delta));
			
			applyFriction(e.getPhysicalComponent(), delta);
		}
		for (PhysicalEntity e : this.entities) {
			resolveMapCollide(e.getPhysicalComponent());
			for (PhysicalEntity b : this.entities) {
				if (e == b)
					continue;
				resolveCollideRepell(e.getPhysicalComponent(), b.getPhysicalComponent());
			}
			
		}
		
	}
	
}
