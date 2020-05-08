package com.progmethgame.server.entities.bullets;

import com.progmethgame.common.DisplayType;
import com.progmethgame.common.context.GameContext;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.effects.Burn;

public class BurnBullet extends StatusBullet {

	public BurnBullet(Player owner) {
		super(DisplayType.BULLET_BURN, BulletConfig.BURN_BULLET_SPEED, owner);
		// TODO Auto-generated constructor stub
		this.effect = new Burn();
	}

	@Override
	public Bullet cpy() {
		// TODO Auto-generated method stub
		return new BurnBullet(owner);
	}
}
