package com.progmethgame.server.entities.bullets;

import com.progmethgame.common.DisplayType;
import com.progmethgame.common.context.GameContext;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.effects.Burn;

/** Bullet for testing purpose */
public class TestBullet extends Bullet {

	public TestBullet(Player owner) {
		super(DisplayType.TEST, 9.0f, owner);
	}

	@Override
	public void onCollide(Player hitPlayer) {
		hitPlayer.setEffect(new Burn());
	}

	@Override
	public Bullet cpy() {
		return new TestBullet(owner);
	}

}
