package com.progmethgame.server.blocks;

import com.progmethgame.server.entities.Player;

/**
 * Block that can interact with a player
 * @author pigt
 *
 */
public interface Interactable {
	
	public void interact(Player p);
}
