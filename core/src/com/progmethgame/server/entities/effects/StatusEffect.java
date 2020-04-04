package com.progmethgame.server.entities.effects;

import com.progmethgame.server.entities.Player;

public abstract class StatusEffect {
	int cooldown;
	public StatusEffect(int cooldown) {
		this.cooldown = cooldown;
	}
	public abstract void getEffect(Player p);
	public abstract void removeEffect(Player p);
	
}
