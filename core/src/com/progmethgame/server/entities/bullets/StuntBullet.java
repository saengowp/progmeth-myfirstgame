package com.progmethgame.server.entities.bullets;

import com.progmethgame.common.DisplayType;
import com.progmethgame.common.context.GameContext;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.effects.Stunt;

public class StuntBullet extends StatusBullet {

	public StuntBullet(Player owner) {
		super(DisplayType.BULLET_STUNT, BulletConfig.STUNT_BULLET_SPEED, owner);
		// TODO Auto-generated constructor stub
		this.effect = new Stunt();
	}

	@Override
	public Bullet cpy() {
		// TODO Auto-generated method stub
		return new StuntBullet(owner);
	}
}
