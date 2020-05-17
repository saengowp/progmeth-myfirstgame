package com.progmethgame.server.entities.guns;

import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.bullets.StuntBullet;

/** Gun that spawn stunt bullet */
public class StuntGun extends Gun {

	public StuntGun(Player owner) {
		super("Stunt Gun", GunConfig.STUNT_GUN_MAX_COOLDOWN, owner);
		this.bullet = new StuntBullet(owner);
	}

}
