package com.progmethgame.server.entities.bullets;

import com.progmethgame.common.DisplayType;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.effects.Burn;

/**
 * A demo bullet for testing purpose.
 * @author pigt
 *
 */
public class BulletTest extends Bullet {

	public BulletTest(Player owner) {
		super(DisplayType.TEST, 9.0f, owner);
	}

	@Override
	public void onCollide(Player hitPlayer) {
		hitPlayer.setEffect(new Burn());
	}

	@Override
	public Bullet cpy() {
		return new BulletTest(owner);
	}
	
	

}
