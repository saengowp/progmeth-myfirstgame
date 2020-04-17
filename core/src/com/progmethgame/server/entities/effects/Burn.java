package com.progmethgame.server.entities.effects;

import com.progmethgame.server.entities.Player;

public class Burn extends StatusEffect {
	static final int dps = EffectConfig.BURN_DPS;

	public Burn() {
		// TODO Auto-generated constructor stub
		super(StatusType.BURN);
		this.maxDuration = EffectConfig.BURN_MAX_DURATION;
		this.duration = maxDuration;
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
