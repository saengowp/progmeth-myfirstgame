package com.progmethgame.server.blocks;

import com.progmethgame.common.GameConfig;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.Tickable;

public class SpikeFloor extends WalkableBlock implements Interactable, Tickable{
	private static int damage = 5;
	private int cooldown;
	private static int maxCooldown = 5;
	private int tickCount;
	
	public SpikeFloor() {
		super();
		this.cooldown = 0;
		this.tickCount = 0;
	}

	@Override
	public void interact(Player p) {
		// TODO Auto-generated method stub
		if (cooldown <= 0) {
			p.dealDamge(damage);
			this.cooldown = maxCooldown;
		}
	}
	
	public void tick(float delta) {
		
		if (tickCount == 0) {
			if (cooldown > 0) this.cooldown -= 1;
		}
		tickCount++;
		tickCount %= (int) 1/GameConfig.SERVER_TICK_RATE;
	}
}
