package com.progmethgame.server.entities.effects;

import com.progmethgame.server.entities.Player;

public abstract class StatusEffect {
	protected int duration;
	protected int maxDuration;
	
	public enum StatusType {
		BURN,
		CONFUSE,
		SLOW,
		STUNT
	}
	public StatusType type;
	
	
	public StatusEffect(StatusType type) {
		switch(type) {
		case BURN:
			this.maxDuration = 5;
			break;
		case CONFUSE:
			this.maxDuration = 5;
			break;
		case SLOW:
			this.maxDuration = 5;
			break;
		case STUNT:
			this.maxDuration = 2;
			break;
		}
		this.duration = maxDuration;
	}
	public int getMaxDuration() {
		return maxDuration;
	}
	public abstract void getEffect(Player p);
	public abstract void removeEffect(Player p);
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public boolean decreaseDuration() {
		this.duration -= 1;
		return this.duration <= 0;
				
	}
	
}
