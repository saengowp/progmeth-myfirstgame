package com.progmethgame.server.entities.effects;

import com.progmethgame.common.DisplayType;
import com.progmethgame.server.entities.Player;

/** An status effect that constantly decrease the player's health */
public class Burn extends StatusEffect {
	
	/** Damage per second after this effect is applied */
	static final int dps = EffectConfig.BURN_DPS;

	public Burn() {
		super(DisplayType.EFFECT_BURN);
		this.maxDuration = EffectConfig.BURN_MAX_DURATION;
		this.duration = maxDuration;
	}

	@Override
	public void getEffect(Player p) {
		p.setDps(p.getDps()+dps);
	}

	@Override
	public void removeEffect(Player p) {
		p.setDps(p.getDps()-dps);

	}
	
	public Burn cpy() {
		return new Burn();
	}
}
