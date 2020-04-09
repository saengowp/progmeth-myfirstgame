package com.progmethgame.server.entities;

import java.util.UUID;

import com.progmethgame.common.DisplayType;
import com.progmethgame.common.GameConfig;
import com.progmethgame.server.ServerRuntime;

public class TestEntity extends Entity {

	private int cooldown = 0;
	
	public TestEntity(UUID gid, ServerRuntime runtime) {
		super(gid, DisplayType.TEST, runtime);
	}
	
	@Override
	public void onCollide(Entity other) {
		cooldown = (int) (2/GameConfig.SERVER_TICK_RATE);
	}
	
	@Override
	public void tick(float delta) {
		if (cooldown > 0)
			cooldown--;
		
		if (cooldown > 0) {
			this.type = DisplayType.RICK;
		} else {
			this.type = DisplayType.TEST;
		}
	}

}
