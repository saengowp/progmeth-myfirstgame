package com.progmethgame.server.entities.effects;

import com.progmethgame.server.entities.Player;

public class Slow extends StatusEffect {
	float slowrate;

	public Slow(int cooldown) {
		super(cooldown);
		// TODO Auto-generated constructor stub
		this.slowrate = 0.5f;
	}

	@Override
	public void tick(float delta) {
		// TODO Auto-generated method stub
		

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

}
