package com.progmethgame.server.entities.effects;

import com.progmethgame.common.DisplayType;
import com.progmethgame.server.entities.Player;

/** An effect that caused player to stop moving */
public class Stunt extends StatusEffect {

	public Stunt() {
		super(DisplayType.EFFECT_STUNT);
		this.maxDuration = EffectConfig.STUNT_MAX_DURATION;
		this.duration = maxDuration;
	}

	@Override
	public void getEffect(Player p) {
		p.getVelocity().setZero();
		p.setMovable(false);

	}

	@Override
	public void removeEffect(Player p) {
		p.setMovable(true);
	}
	
	@Override
	public Stunt cpy() {
		return new Stunt();
	}
}
