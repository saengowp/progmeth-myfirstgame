package com.progmethgame.server.entities;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.progmethgame.common.DisplayType;
import com.progmethgame.common.GameConfig;
import com.progmethgame.server.ServerRuntime;
import com.progmethgame.server.entities.bullets.BulletTest;
import com.progmethgame.server.entities.effects.StatusEffect;

public class Player extends Entity{
	public enum GunType{
		BURN_GUN,
		CONFUSE_GUN,
		SLOW_GUN,
		HOOK_GUN,
		TELEPORT_GUN,
		STUNT_GUN
	}
	
	private int dps;
	private int hp;
	private StatusEffect effect;
	private Vector2 faceDirection;
	private int tickCount;
	private GunType[] gunSlot;  
	
	
	private float speed;
	private Vector2 walkDirection;

	public Player(UUID gid, ServerRuntime runtime) {
		super(gid, DisplayType.PLAYER, runtime);
		this.speed = 5.0f;
		this.dps = 0;
		this.hp = 100;
		this.tickCount = 0;
		this.walkDirection = new Vector2();
		
		this.gunSlot = new GunType[] { 
				GunType.BURN_GUN, 
				GunType.CONFUSE_GUN, 
				GunType.SLOW_GUN, 
				GunType.STUNT_GUN,
				GunType.HOOK_GUN,
				GunType.TELEPORT_GUN};
		}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getDps() {
		return dps;
	}

	public void setDps(int dps) {
		this.dps = dps;
	}

	public StatusEffect getEffect() {
		return effect;
	}

	public void setEffect(StatusEffect effect) {
		//check if no effect
		if (this.effect == null) {
			this.effect = effect;
			this.effect.getEffect(this);
			
		}else if (this.effect.type == effect.type) {
			//expand the duration
			this.effect.setDuration(effect.getMaxDuration());
		}else {
			//remove old effect and set new effect
			this.effect.removeEffect(this);
			this.effect = effect;
			this.effect.getEffect(this);
		}
	}
	
	public int dealDamge(int damage) {
		if (this.hp > damage) {
			this.hp -= damage;
			return damage;
		}else {
			int remainHp = this.hp;
			this.hp = 0;
			return remainHp;
		}
	}
	
	@Override
	public void tick(float delta) {
		//for 1 second
		if(tickCount == 0) {
			this.dealDamge(dps);
			if(this.effect != null && this.effect.decreaseDuration()) {
				this.effect.removeEffect(this);
				this.effect = null;
			}
		}
		this.tickCount++;
		this.tickCount %= (int) 1/GameConfig.SERVER_TICK_RATE;
		
		this.velocity.set(walkDirection.nor().scl(speed));
	}
	
	public void setWalkDirection(Vector2 dir) {
		walkDirection.set(dir);
		if (!dir.isZero()) {
			this.faceDirection = dir.cpy().nor();
		}
	}
	
	public Vector2 getFaceDirection() {
		return faceDirection;
	}

	public void fire() {
		BulletTest e = new BulletTest(this);
		e.getPosition().set(position);
		runtime.addEntity(e);
	}
	

}
