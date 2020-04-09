package com.progmethgame.server.entities;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.progmethgame.common.DisplayType;
import com.progmethgame.network.EntityData;
import com.progmethgame.server.ServerRuntime;
import com.progmethgame.server.blocks.Block;

public abstract class Entity implements Tickable {

	protected Vector2 position;
	protected Vector2 velocity;

	protected DisplayType type;
	protected final UUID gid;
	
	protected final ServerRuntime runtime; 

	public Entity(UUID gid, DisplayType type, ServerRuntime runtime) {
		this.gid = gid;
		this.type = type;
		this.position = new Vector2();
		this.velocity = new Vector2();
		this.runtime = runtime;
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
	
	/** Call when entity touched other.
	 * 
	 * @param other
	 */
	public void onCollide(Entity other) {
		
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

