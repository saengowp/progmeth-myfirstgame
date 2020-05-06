package com.progmethgame.server.blocks;

import com.progmethgame.server.entities.Player;

public class CureFloor extends WalkableBlock implements Interactable {

	@Override
	public void interact(Player p) {
		// TODO Auto-generated method stub
		p.setEffect(null);
	}
	
}
