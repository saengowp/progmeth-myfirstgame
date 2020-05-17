package com.progmethgame.server.entities.guns;

import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.bullets.BurnBullet;

/** Gun that spawn burn bullet */
public class BurnGun extends Gun {

	public BurnGun(Player owner) {
		super("Burn Gun",GunConfig.BURN_GUN_MAX_COOLDOWN,owner);
		this.bullet = new BurnBullet(owner);
	}

}
