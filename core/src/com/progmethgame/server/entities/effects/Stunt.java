package com.progmethgame.server.entities.effects;

import com.progmethgame.server.entities.Player;

public class Stunt extends StatusEffect {

	public Stunt() {
		super(StatusType.STUNT);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void getEffect(Player p) {
		// TODO Auto-generated method stub
		p.setSpeed(0);

	}

	@Override
	public void removeEffect(Player p) {
		// TODO Auto-generated method stub
		p.setSpeed(5.0f);
	}
}
