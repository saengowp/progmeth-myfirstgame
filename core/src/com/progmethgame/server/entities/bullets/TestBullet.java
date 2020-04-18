package com.progmethgame.server.entities.bullets;

import com.progmethgame.common.DisplayType;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.effects.Burn;

public class TestBullet extends Bullet {

	public TestBullet(Player owner) {
		super(DisplayType.TEST, 9.0f, owner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCollide(Player hitPlayer) {
		// TODO Auto-generated method stub
		if(!hitPlayer.equals(owner)) {
			hitPlayer.setEffect(new Burn());
			runtime.removeEntity(this);
		}
	}

}
