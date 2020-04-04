package com.progmethgame.server.entities.effects;

import com.progmethgame.server.entities.Player;

public class Burn extends StatusEffect {
	int dps;

	public Burn() {
		// TODO Auto-generated constructor stub
		super(5);
		this.dps = 5;
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

	@Override
	public void tick(float delta) {
		// TODO Auto-generated method stub
		
	}

}
