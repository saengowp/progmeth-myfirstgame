package com.progmethgame.ai;

import com.progmethgame.entity.base.EntityProcessor;

public class TickProcessor extends EntityProcessor<Tickable>{
	
	public void tick(float delta) {
		for (Tickable e : entities) {
			e.tick(delta);
		}
	}

}
