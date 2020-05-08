package com.progmethgame.server.entities.effects;

import com.progmethgame.common.DisplayType;
import com.progmethgame.server.entities.Player;

public class Stunt extends StatusEffect {

	public Stunt() {
		super(StatusType.STUNT);
		// TODO Auto-generated constructor stub
		this.maxDuration = EffectConfig.STUNT_MAX_DURATION;
		this.duration = maxDuration;
		this.dispType = DisplayType.EFFECT_STUNT;
	}

	@Override
	public void getEffect(Player p) {
		// TODO Auto-generated method stub
		p.setMovable(false);

	}

	@Override
	public void removeEffect(Player p) {
		// TODO Auto-generated method stub
		p.setMovable(true);
	}
	
	public Stunt cpy() {
		return new Stunt();
	}
}
