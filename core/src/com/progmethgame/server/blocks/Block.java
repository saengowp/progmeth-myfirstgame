package com.progmethgame.server.blocks;

import com.progmethgame.server.entities.Tickable;

/**
 * Represent a tile on a map
 * @author pigt
 *
 */
public abstract class Block implements Tickable {

	/**
	 * Can entity pass through this block?
	 * @return solidness
	 */
	public abstract boolean isSolid();

	@Override
	public void tick(float delta) {}
}
