package com.progmethgame.server.entities.bullets;

import com.progmethgame.common.DisplayType;
import com.progmethgame.common.context.GameContext;
import com.progmethgame.server.blocks.Block;
import com.progmethgame.server.entities.Entity;
import com.progmethgame.server.entities.Player;

/**
 * A bullet entity
 *
 */
public abstract class Bullet extends Entity {
	
	/** Bullet speed*/
	protected float speed;
	
	/** Player who shot the bullet */
	protected Player owner;
	
	public Bullet(DisplayType type, float speed, Player owner) {
		super(type);
		this.speed = speed;
		this.owner = owner;
		this.position = owner.getPosition().cpy();
		this.velocity = owner.getFaceDirection().cpy().scl(speed);
	}
	
	/** Called when the bullet collide a player */
	abstract public void onCollide(Player hitPlayer);
	
	@Override
	public void onCollideSolid(Block block) {
		GameContext.getServerContext().removeEntity(this);
	}
	
	@Override
	public void onCollide(Entity other) {
		if (other instanceof Player && !((Player) other).equals(owner)) {
			onCollide((Player) other);
			GameContext.getServerContext().removeEntity(this);
		}
	}
	
	@Override
	public float getPhysicalSize() {
		return 0.5f;
	}
	
	/** Duplicate this bullet */
	public abstract Bullet cpy();
	
	@Override
	public void tick(float delta) {
		super.tick(delta);
		this.facingDirection.set(velocity);
	}

}
