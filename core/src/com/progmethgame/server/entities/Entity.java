package com.progmethgame.server.entities;

import java.util.ArrayList;
import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.progmethgame.client.graphic.component.Overlay;
import com.progmethgame.common.DisplayType;
import com.progmethgame.common.EntityData;
import com.progmethgame.server.blocks.Block;

/**
 * Represent a logical entity on the server
 */
public abstract class Entity implements Tickable {

	/** Position */
	protected Vector2 position;
	
	/** Velocity */
	protected Vector2 velocity;

	/** Displayed type*/
	protected DisplayType type;
	
	/** ID */
	protected final UUID gid;
	
	/** Overlays attached */
	protected ArrayList<Overlay> overlays;

	/**
	 * Create a new entity with specified ID and type
	 * @param gid
	 * @param type
	 */
	public Entity(UUID gid, DisplayType type) {
		this.gid = gid;
		this.type = type;
		this.position = new Vector2();
		this.velocity = new Vector2();
		this.overlays = new ArrayList<Overlay>();
	}
	
	/**
	 * Create a new entity with specified type and a random id
	 * @param type
	 */
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
	
	/**
	 * Change the display type
	 * @param t
	 */
	public void setDisplay(DisplayType t) {
		this.type = t;
	}

	@Override
	public void tick(float delta) {

	}

	/**
	 * Get the velocity
	 * @return
	 */
	public Vector2 getVelocity() {
		return this.velocity;
	}

	/** Get the position */
	public Vector2 getPosition() {
		return this.position;
	}

	/** Get the display type */
	public DisplayType getType() {
		return type;
	}

	/** Get the entity's id */
	public UUID getGid() {
		return gid;
	}
	
	/** 
	 * Parse the graphical rendering data for sending accross the network 
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
	
	/** 
	 * Called when entity touched other.
	 * 
	 * @param other the touched entity
	 */
	public void onCollide(Entity other) {
		
	}

	/**
	 * Called when walked on the block
	 * 
	 * @param block the block that got walked on
	 */
	public void onWalkOn(Block block) {
		
	}
	
	/** 
	 * Call when entity hit solid block
	 * 
	 * @param block the block that this entity collided with
	 */
	public void onCollideSolid(Block block) {
		
	}
	
	
	/**
	 * Return physical size for physic engine's onCollide(Entity) calculation.
	 * 
	 * Size in this case is the side length of the bounding square.
	 * The bounding box has the same center as the default bound.
	 */
	public float getPhysicalSize() {
		return 1;
	}

}

