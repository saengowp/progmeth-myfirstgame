package com.progmethgame.server.entities.guns;

import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.bullets.SlowBullet;

/** Gun that spawn slow bullet */
public class SlowGun extends Gun {

	public SlowGun(Player owner) {
		super("Slow Gun", GunConfig.SLOW_GUN_MAX_COOLDOWN, owner);
		this.bullet = new SlowBullet(owner);
	}

}
