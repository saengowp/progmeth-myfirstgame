package com.progmethgame.server.entities.effects;

import com.progmethgame.common.DisplayType;
import com.progmethgame.server.entities.Player;

public class Slow extends StatusEffect {
	static final float slowrate = EffectConfig.SLOW_RATE;

	public Slow() {
		super(StatusType.SLOW);
		// TODO Auto-generated constructor stub
		this.maxDuration = EffectConfig.SLOW_MAX_DURATION;
		this.duration = maxDuration;
		this.dispType = DisplayType.EFFECT_SLOW;
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


	@Override
	public Slow cpy() {
		// TODO Auto-generated method stub
		return new Slow();
	}

}
