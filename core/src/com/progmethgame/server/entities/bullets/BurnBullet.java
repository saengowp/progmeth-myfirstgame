package com.progmethgame.server.entities.bullets;

import com.progmethgame.common.DisplayType;
import com.progmethgame.common.context.GameContext;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.effects.Burn;

public class BurnBullet extends Bullet {

	public BurnBullet(Player owner) {
		super(DisplayType.BULLET_BURN, BulletConfig.BURN_BULLET_SPEED, owner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCollide(Player hitPlayer) {
		// TODO Auto-generated method stub
		if(!hitPlayer.equals(owner)) {
			hitPlayer.setEffect(new Burn());
			GameContext.getServerContext().removeEntity(this);
		}
	}
}
