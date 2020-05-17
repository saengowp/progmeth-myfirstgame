package com.progmethgame.server.entities.bullets;

import com.progmethgame.common.DisplayType;
import com.progmethgame.common.context.GameContext;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.effects.Stunt;

/** A bullet that will temporary stop the player */
public class StuntBullet extends StatusBullet {

	public StuntBullet(Player owner) {
		super(DisplayType.BULLET_STUNT, BulletConfig.STUNT_BULLET_SPEED, owner);
		this.effect = new Stunt();
	}

	@Override
	public Bullet cpy() {
		return new StuntBullet(owner);
	}
}
