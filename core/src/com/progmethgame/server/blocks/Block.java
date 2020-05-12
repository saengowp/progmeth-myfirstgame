package com.progmethgame.server.blocks;

import com.progmethgame.server.entities.Tickable;

public abstract class Block implements Tickable {
	
	public abstract boolean isSolid();

	@Override
	public void tick(float delta) {}
}
