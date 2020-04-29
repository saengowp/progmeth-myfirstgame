package com.progmethgame.server.entities.bullets;

import com.progmethgame.common.DisplayType;
import com.progmethgame.common.context.GameContext;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.effects.Stunt;

public class StuntBullet extends Bullet {

	public StuntBullet(Player owner) {
		super(DisplayType.BULLET_STUNT, BulletConfig.STUNT_BULLET_SPEED, owner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCollide(Player hitPlayer) {
		// TODO Auto-generated method stub
		if(!hitPlayer.equals(owner)) {
			hitPlayer.setEffect(new Stunt());
			GameContext.getServerContext().removeEntity(this);
		}

	}

}
