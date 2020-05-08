package com.progmethgame.server.entities.effects;

import com.progmethgame.server.entities.Player;

public class Confuse extends StatusEffect {


	public Confuse() {
		super(StatusType.CONFUSE);
		// TODO Auto-generated constructor stub
		this.maxDuration = EffectConfig.CONFUSE_MAX_DURATION;
		this.duration = maxDuration;
		this.dispType = dispType.EFFECT_CONFUSE;
	}

	@Override
	public void getEffect(Player p) {
		// TODO Auto-generated method stub
		p.setConfuse(true);
	}

	@Override
	public void removeEffect(Player p) {
		// TODO Auto-generated method stub
		p.setConfuse(false);
	}
	
	public Confuse cpy() {
		return new Confuse();
	}

}
