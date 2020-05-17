package com.progmethgame.server.blocks;

/**
 * A block that damage the player when walked on
 * @author pigt
 *
 */
public class SpikeFloor extends SpikeBlock {
	
	public SpikeFloor() {
		super();
		this.damage = 5;
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}
}
