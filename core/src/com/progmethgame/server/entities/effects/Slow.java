package com.progmethgame.server.entities.effects;

import com.progmethgame.server.entities.Player;

public class Slow extends StatusEffect {
	static final float slowrate = 0.5f;

	public Slow() {
		super(StatusType.SLOW);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void getEffect(Player p) {
		// TODO Auto-generated method stub
		p.setSpeed(p.getSpeed()*slowrate);
	}

	@Override
	public void removeEffect(Player p) {
		// TODO Auto-generated method stub
		p.setSpeed(p.getSpeed()/slowrate);
	}

}
