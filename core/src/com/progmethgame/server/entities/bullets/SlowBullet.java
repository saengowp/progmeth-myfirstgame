package com.progmethgame.server.entities.bullets;

import com.progmethgame.common.DisplayType;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.effects.Slow;

public class SlowBullet extends Bullet {

	public SlowBullet(Player owner) {
		super(DisplayType.BULLET_SLOW, BulletConfig.SLOW_BULLET_SPEED, owner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCollide(Player hitPlayer) {
		// TODO Auto-generated method stub
		if(!hitPlayer.equals(owner)) {
			hitPlayer.setEffect(new Slow());
			runtime.removeEntity(this);
		}
	}

}
