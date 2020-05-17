package com.progmethgame.server.blocks;

/**
 * A solid block that burn the player when touched
 * @author pigt
 *
 */
public class BurnBlock extends BurningBlock {
	
	@Override
	public boolean isSolid() {
		return true;
	}

}
