package com.progmethgame.server.entities.guns;

import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.bullets.ConfuseBullet;

/** Gun that spawn confuse bullet */
public class ConfuseGun extends Gun {

	public ConfuseGun(Player owner) {
		super("Confuse Gun", GunConfig.CONFUSE_GUN_MAX_COOLDOWN, owner);
		this.bullet = new ConfuseBullet(owner);
	}

}
