package com.progmethgame.server.entities.guns;

import com.progmethgame.common.SoundType;
import com.progmethgame.common.context.GameContext;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.bullets.Bullet;

public abstract class Gun {
	
	/** Gun's name */
	public String name;
	
	/** Bullet expelled by this gun */
	protected Bullet bullet;
	
	/** Gun's current cooldown interval */
	protected float cooldown;
	
	/** Gun's cooldown interval */
	protected float maxCooldown;
	
	/** Player who owned the gun */
	protected Player owner;

	public Gun(String name,float maxCooldown, Player owner) {
		this.name = name;
		this.cooldown = 0;
		this.owner = owner;
		this.maxCooldown = maxCooldown;
	}

	/** Spawn bullet from this gun */
	public void shoot() {
		if(cooldown <= 0) {
			GameContext.getServerContext().playSound(SoundType.PEW);
			GameContext.getServerContext().addEntity(bullet.cpy());
			this.cooldown = maxCooldown;
		}	
	}
	
	/** Reduce cooldown count */
	public void recharge(float tickRate) {
		if(cooldown > 0) {
			cooldown -=tickRate;
		}
	}
	
	/** Name of the gun */
	public String getName() {
		return name;
	}
	
	/** Bullet spawned */
	public Bullet getBullet() {
		return bullet;
	}
	
	/** Text displaying status of the gun */
	public String getStatus() {
		if(cooldown <= 0) {
			return "Ready";
		}else {
			return "Recharged";
		}
	}

}
