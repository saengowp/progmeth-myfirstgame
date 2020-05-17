package com.progmethgame.server.entities.effects;

import com.progmethgame.common.DisplayType;
import com.progmethgame.server.entities.Player;

/** Effect that slow down the play's walk speed */
public class Slow extends StatusEffect {
	static final float slowrate = EffectConfig.SLOW_RATE;

	public Slow() {
		super(DisplayType.EFFECT_SLOW);
		this.maxDuration = EffectConfig.SLOW_MAX_DURATION;
		this.duration = maxDuration;
	}


	@Override
	public void getEffect(Player p) {
		p.setSpeed(p.getSpeed()*slowrate);
	}

	@Override
	public void removeEffect(Player p) {
		p.setSpeed(p.getSpeed()/slowrate);
	}

	@Override
	public Slow cpy() {
		return new Slow();
	}

}
