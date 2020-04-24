package com.progmethgame.server.blocks;

import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.effects.Burn;

public interface Burnable extends Interactable{
	
	public default void interact(Player p) {
		p.setEffect(new Burn());
	}
}
