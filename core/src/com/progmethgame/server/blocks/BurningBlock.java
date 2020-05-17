package com.progmethgame.server.blocks;

import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.effects.Burn;

/**
 * A block that will set the player on fire if touched
 * @author pigt
 *
 */
public abstract class BurningBlock extends Block implements Interactable {

	@Override
	public void interact(Player p) {
		p.setEffect(new Burn());
	}
	
}
