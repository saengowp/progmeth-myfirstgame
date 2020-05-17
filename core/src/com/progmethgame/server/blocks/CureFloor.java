package com.progmethgame.server.blocks;

import com.progmethgame.common.GameConfig;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.Tickable;

/**
 * A floor block that remove status effect from the player
 * @author pigt
 *
 */
public class CureFloor extends CooldownBlock {
	
	public CureFloor() {
		super();
	}
	
	@Override
	protected void applyEffect(Player p) {
		p.setEffect(null);
	}

	@Override
	public boolean isSolid() {
		return false;
	}
	
}
