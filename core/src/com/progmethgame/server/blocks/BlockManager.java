package com.progmethgame.server.blocks;

public class BlockManager {
	public final static Block SOLID = new SolidBlock();
	public final static Block WALK = new WalkableBlock();
	public final static Block BURN_FLOOR = new BurnFloor();
	public final static Block CURE = new CureFloor();
	public final static Block BURN_BLOCK = new BurnBlock();
	
	public static Block fromId(int id) {
		switch(id) {
		case 1:
			return new WalkableBlock();
		case 2:
			return new BurnFloor();
		case 3:
			return new CureFloor();
		case 4:
			return new SpikeFloor();
		case 7:
			return new BurnBlock();
		case 11:
			return new SpikeBlock();
		default:
			return new SolidBlock();
		}
	}
}
