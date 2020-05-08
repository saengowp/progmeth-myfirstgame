package com.progmethgame.server.entities.bullets;

import com.progmethgame.common.DisplayType;
import com.progmethgame.common.context.GameContext;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.effects.Confuse;

public class ConfuseBullet extends StatusBullet {

	public ConfuseBullet(Player owner) {
		super(DisplayType.BULLET_CONFUSE, BulletConfig.CONFUSE_BULLET_SPEED, owner);
		// TODO Auto-generated constructor stub
		this.effect = new Confuse();
	}

	@Override
	public Bullet cpy() {
		// TODO Auto-generated method stub
		return new ConfuseBullet(owner);
	}
}
