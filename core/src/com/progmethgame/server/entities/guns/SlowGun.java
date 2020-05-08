package com.progmethgame.server.entities.guns;

import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.bullets.SlowBullet;

public class SlowGun extends Gun {

	public SlowGun(Player owner) {
		super("Slow Gun", GunConfig.SLOW_GUN_MAX_COOLDOWN, owner);
		// TODO Auto-generated constructor stub
		this.bullet = new SlowBullet(owner);
	}

}
