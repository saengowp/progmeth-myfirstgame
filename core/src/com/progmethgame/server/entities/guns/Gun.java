package com.progmethgame.server.entities.guns;

import com.progmethgame.common.context.GameContext;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.bullets.Bullet;

public abstract class Gun {
	public String name; 
	protected Bullet bullet;
	protected float cooldown;
	protected float maxCooldown;
	

	protected Player owner;

	public Gun(String name,float maxCooldown, Player owner) {
		this.name = name;
		this.cooldown = 0;
		this.owner = owner;
		this.maxCooldown = maxCooldown;
	}
	
	public void shoot() {
		if(cooldown <= 0) {
			GameContext.getServerContext().addEntity(bullet.cpy());
			this.cooldown = maxCooldown;
		}	
	}
	
	public void recharge(float tickRate) {
		if(cooldown > 0) {
			cooldown -=tickRate;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public Bullet getBullet() {
		return bullet;
	}
	
	public String getStatus() {
		if(cooldown <= 0) {
			return "Ready";
		}else {
			return "Recharged";
		}
	}

}
