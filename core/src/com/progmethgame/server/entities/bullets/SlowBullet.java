package com.progmethgame.server.entities.bullets;

import com.progmethgame.common.DisplayType;
import com.progmethgame.common.context.GameContext;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.effects.Slow;

public class SlowBullet extends StatusBullet {

	public SlowBullet(Player owner) {
		super(DisplayType.BULLET_SLOW, BulletConfig.SLOW_BULLET_SPEED, owner);
		// TODO Auto-generated constructor stub
		this.effect = new Slow();
	}

	@Override
	public Bullet cpy() {
		// TODO Auto-generated method stub
		return new SlowBullet(owner);
	}
}
