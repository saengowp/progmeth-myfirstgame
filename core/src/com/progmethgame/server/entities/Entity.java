package com.progmethgame.server.entities;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.progmethgame.common.DisplayType;
import com.progmethgame.network.EntityData;

public abstract class Entity implements Tickable {

	protected Vector2 position;
	protected Vector2 velocity;

	private DisplayType type;
	private UUID gid;

	public Entity(UUID gid, DisplayType type) {
		this.gid = gid;
		this.type = type;
		this.position = new Vector2();
		this.velocity = new Vector2();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Entity && this.gid.equals(((Entity) obj).gid);
	}

	@Override
	public int hashCode() {
		return gid.hashCode();
	}

	@Override
	public void tick(float delta) {

	}

	public Vector2 getVelocity() {
		return this.velocity;
	}

	public Vector2 getPosition() {
		return this.position;
	}

	public DisplayType getType() {
		return type;
	}

	public UUID getGid() {
		return gid;
	}
	
	/** Parse rendering data for GameClient
	 * 
	 * @return EntityData
	 */
	public EntityData getData() {
		EntityData d = new EntityData();
		d.id = this.gid;
		d.dispType = this.type;
		d.position = this.position;
		return d;
	}

}
