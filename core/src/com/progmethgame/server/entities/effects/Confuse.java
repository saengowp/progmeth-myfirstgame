package com.progmethgame.server.entities.effects;

import com.progmethgame.server.entities.Player;

public class Confuse extends StatusEffect {

	public Confuse() {
		super(StatusType.CONFUSE);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void getEffect(Player p) {
		// TODO Auto-generated method stub
		p.setSpeed(-p.getSpeed());
	}

	@Override
	public void removeEffect(Player p) {
		// TODO Auto-generated method stub
		p.setSpeed(-p.getSpeed());
	}

}
