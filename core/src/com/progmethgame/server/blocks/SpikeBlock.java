package com.progmethgame.server.blocks;

import com.progmethgame.server.entities.Player;

/**
 * A block that damage the player when touched
 * @author pigt
 *
 */
public class SpikeBlock extends CooldownBlock {
	
	/** Damage dealt*/
	protected int damage = 7;
	
	public SpikeBlock() {
		super();
	}
	
	@Override
	protected void applyEffect(Player p) {
		p.dealDamge(damage);
	}

	@Override
	public boolean isSolid() {
		return true;
	}
}
