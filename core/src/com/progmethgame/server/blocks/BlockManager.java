package com.progmethgame.server.blocks;

/**
 * A Factory to generate a block from an ID
 * @author pigt
 *
 */
public class BlockManager {
	
	/**
	 * Create a block object with that id type
	 * 
	 * @param id id of the block
	 * @return Block with specified id
	 */
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
