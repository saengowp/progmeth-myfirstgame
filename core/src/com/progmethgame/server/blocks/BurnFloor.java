package com.progmethgame.server.blocks;

/** 
 * A floor that burn the player when touched
 * @author pigt
 *
 */
public class BurnFloor extends BurningBlock {

	@Override
	public boolean isSolid() {
		return false;
	}
	
}
