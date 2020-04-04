package com.progmethgame.server.entities.effects;

import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.Tickable;

public abstract class StatusEffect implements Tickable{
	int cooldown;
	public StatusEffect(int cooldown) {
		this.cooldown = cooldown;
	}
	public abstract void getEffect(Player p);
	public abstract void removeEffect(Player p);
	public void tick(float delta, Player p) {
		//for 1 second 
		if (cooldown > 0) {
			this.cooldown -= 1;
		}else {
			
		}
	};
}
