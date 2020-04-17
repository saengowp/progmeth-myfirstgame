package com.progmethgame.server.entities.bullets;

import com.progmethgame.common.DisplayType;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.effects.Confuse;

public class ConfuseBullet extends Bullet {

	public ConfuseBullet(Player owner) {
		super(DisplayType.BULLET_CONFUSE, BulletConfig.CONFUSE_BULLET_SPEED, owner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCollide(Player hitPlayer) {
		// TODO Auto-generated method stub
		if(!hitPlayer.equals(owner)) {
			hitPlayer.setEffect(new Confuse());
			runtime.removeEntity(this);
		}
	}

}
