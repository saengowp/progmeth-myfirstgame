package com.progmethgame.server.entities.bullets;

import java.util.UUID;

import com.progmethgame.common.DisplayType;
import com.progmethgame.server.blocks.Block;
import com.progmethgame.server.entities.Entity;
import com.progmethgame.server.entities.Player;


public abstract class Bullet extends Entity {
	protected float speed;
	protected Player owner;
	
	public Bullet(DisplayType type, float speed, Player owner) {
		super(UUID.randomUUID(),type, owner.runtime);
		// TODO Auto-generated constructor stub
		this.speed = speed;
		this.owner = owner;
		this.position = owner.getPosition().cpy();
		this.velocity = owner.getFaceDirection().cpy().scl(speed);
	}
	
	abstract public void onCollide(Player hitPlayer);
	
	public void onCollideSolid(Block block) {
		if (block.isSolid()) {
			runtime.removeEntity(this);
		}
	}
	
	

}
