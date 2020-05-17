package com.progmethgame.server.entities.effects;

import com.progmethgame.common.DisplayType;
import com.progmethgame.server.entities.Player;

/** An effect that reverse the player's control */
public class Confuse extends StatusEffect {

	public Confuse() {
		super(DisplayType.EFFECT_CONFUSE);
		this.maxDuration = EffectConfig.CONFUSE_MAX_DURATION;
		this.duration = maxDuration;
	}

	@Override
	public void getEffect(Player p) {
		p.setConfuse(true);
	}

	@Override
	public void removeEffect(Player p) {
		p.setConfuse(false);
	}
	
	public Confuse cpy() {
		return new Confuse();
	}

}
