package com.progmethgame.server.entities.guns;

import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.bullets.StuntBullet;

public class StuntGun extends Gun {

	public StuntGun(Player owner) {
		super("Stunt Gun", GunConfig.STUNT_GUN_MAX_COOLDOWN, owner);
		// TODO Auto-generated constructor stub
		this.bullet = new StuntBullet(owner);
	}

}
