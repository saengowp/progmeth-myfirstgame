package com.progmethgame.server.entities;

import com.progmethgame.server.entities.effects.StatusEffect;

public class Player extends Entity implements Tickable{
	private float speed;
	private int dps;
	private int hp;
	StatusEffect effect;

	public Player(int gid) {
		super(gid, EntityType.PLAYER);
		this.speed = 5.0f;
		this.dps = 0;
		this.hp = 100;
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
		this.effect = effect;
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
	public void tick(float delta) {
		//for 1 second
		this.dealDamge(dps);
	}
	

}
