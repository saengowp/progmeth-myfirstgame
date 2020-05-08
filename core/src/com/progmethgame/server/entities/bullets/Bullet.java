package com.progmethgame.server.entities.bullets;

import com.progmethgame.common.DisplayType;
import com.progmethgame.common.context.GameContext;
import com.progmethgame.server.blocks.Block;
import com.progmethgame.server.entities.Entity;
import com.progmethgame.server.entities.Player;


public abstract class Bullet extends Entity {
	protected float speed;
	protected Player owner;
	
	public Bullet(DisplayType type, float speed, Player owner) {
		super(type);
		// TODO Auto-generated constructor stub
		this.speed = speed;
		this.owner = owner;
		this.position = owner.getPosition().cpy();
		this.velocity = owner.getFaceDirection().cpy().scl(speed);
	}
	
	abstract public void onCollide(Player hitPlayer);
	
	public void onCollideSolid(Block block) {
		if (block.isSolid()) {
			GameContext.getServerContext().removeEntity(this);
		}
	}
	
	@Override
	public void onCollide(Entity other) {
		if (other instanceof Player && !((Player) other).equals(owner))
			onCollide((Player) other);
	}
	
	@Override
	public float getPhysicalSize() {
		return 0.5f;
	}
	
	public abstract Bullet cpy();
	
	

}
