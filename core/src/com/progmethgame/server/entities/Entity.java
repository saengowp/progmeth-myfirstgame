package com.progmethgame.server.entities;

import com.badlogic.gdx.math.Vector2;

public abstract class Entity implements Tickable{

	public Vector2 position;
	public Vector2 velocity;
	public float speed;
	
	public enum EntityType {
		PLAYER,
		PLAYER_CONTROLABLE,
		BULLET_FIRE,
		BULLET_SLOW,
		BULLET_CONFUSE,
		BULLET_STUNT,
		BULLET_HOOK,
		BULLET_TELEPORT
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
	
	public void tick(float delta) {
		//move
		this.moveWithSpeed(this.speed, delta);
	}
	
	public void moveWithSpeed(float speed,float delta) {
		this.position.add(this.velocity.cpy().nor().scl(delta).scl(speed));
	}

}
