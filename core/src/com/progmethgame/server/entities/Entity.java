package com.progmethgame.server.entities;

import java.util.ArrayList;
import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.progmethgame.client.graphic.component.Overlay;
import com.progmethgame.common.DisplayType;
import com.progmethgame.common.EntityData;
import com.progmethgame.server.blocks.Block;

public abstract class Entity implements Tickable {

	protected Vector2 position;
	protected Vector2 velocity;

	protected DisplayType type;
	protected final UUID gid;
	
	protected ArrayList<Overlay> overlays;

	public Entity(UUID gid, DisplayType type) {
		this.gid = gid;
		this.type = type;
		this.position = new Vector2();
		this.velocity = new Vector2();
		this.overlays = new ArrayList<Overlay>();
	}
	
	public Entity(DisplayType type) {
		this(UUID.randomUUID(), type);
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
		d.overlays = this.overlays;
		return d;
	}
	
	/** Call when entity touched other.
	 * 
	 * @param other
	 */
	public void onCollide(Entity other) {
		
	}
	
	public void setPosition(Vector2 position) {
		this.position.set(position);
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity.set(velocity);
	}

	/** Call when walked on the block
	 * 
	 * @param block
	 */
	public void onWalkOn(Block block) {
		
	}
	
	/** Call when entity hit solid block
	 * 
	 */
	public void onCollideSolid(Block block) {
		
	}

}

