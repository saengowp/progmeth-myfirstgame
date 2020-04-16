package com.progmethgame.server.entities.effects;

import com.progmethgame.server.entities.Player;

public class Burn extends StatusEffect {
	static final int dps = 5;

	public Burn() {
		// TODO Auto-generated constructor stub
		super(StatusType.BURN);
	}

	@Override
	public void getEffect(Player p) {
		// TODO Auto-generated method stub
		p.setDps(p.getDps()+dps);
	}

	@Override
	public void removeEffect(Player p) {
		// TODO Auto-generated method stub
		p.setDps(p.getDps()-dps);

	}

}
