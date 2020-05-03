package com.progmethgame.server.entities;

import com.progmethgame.common.DisplayType;
import com.progmethgame.common.GameConfig;

public class TestEntity extends Entity {

	private int cooldown = 0;
	protected DisplayType cool = DisplayType.TEST;
	protected DisplayType hot = DisplayType.RICK;
	
	public TestEntity() {
		super(DisplayType.TEST);
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
			this.type = hot;
		} else {
			this.type = cool;
		}
	}

}
