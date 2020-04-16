package com.progmethgame.server.entities.bullets;

import com.progmethgame.server.entities.Entity;
import com.progmethgame.server.entities.Entity.EntityType;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.effects.StatusEffect;

public abstract class Bullet extends Entity {
	public Bullet(int gid, EntityType type, float speed) {
		super(gid, type);
		// TODO Auto-generated constructor stub
		this.speed = speed;
	}
	
	abstract public void onHit(Player hitPlayer);
	
	

}
