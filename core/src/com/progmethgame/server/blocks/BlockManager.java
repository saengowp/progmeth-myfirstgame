package com.progmethgame.server.blocks;

public class BlockManager {
	public final static Block SOLID = new SolidBlock();
	public final static Block WALK = new WalkableBlock();
	
	public static Block fromId(int id) {
		switch(id) {
		case 12:
			return WALK;
		default:
			return SOLID;
		}
	}
}
