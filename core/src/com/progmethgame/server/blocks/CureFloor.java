package com.progmethgame.server.blocks;

import com.progmethgame.common.GameConfig;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.Tickable;

public class CureFloor extends WalkableBlock implements Interactable,Tickable {
	private int cooldown;
	private int tickCount;
	private static final int maxCooldown = 5;
	
	public CureFloor() {
		super();
		this.cooldown = 0;
		this.tickCount = 0;
	}

	@Override
	public void interact(Player p) {
		// TODO Auto-generated method stub
		if(cooldown <= 0) {
			p.setEffect(null);
			cooldown = maxCooldown;
		}
	}

	@Override
	public void tick(float delta) {
		// TODO Auto-generated method stub
		if (tickCount == 0) {
			if (cooldown > 0) this.cooldown -= 1;
		}
		tickCount++;
		tickCount %= (int) 1/GameConfig.SERVER_TICK_RATE;
	}
	
}
