package com.progmethgame.server.entities;

import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

	public Vector2 position;
	public Vector2 velocity;
	
	public enum EntityType {
		PLAYER,
		PLAYER_CONTROLABLE
	};
	
	public EntityType type;
	public int gid;
	
	public Entity(int gid, EntityType type) {
		this.gid = gid;
		this.type = type;
		this.position = new Vector2();
		this.velocity = new Vector2();
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return gid;
	}

}
