package com.progmethgame.server.entities.guns;

import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.bullets.TeleportBullet;

public class TeleportGun extends Gun {

	public TeleportGun(Player owner) {
		super("Teleport Gun", GunConfig.TELEPORT_GUN_MAX_COOLDOWN, owner);
		// TODO Auto-generated constructor stub
		this.bullet = new TeleportBullet(owner);
	}

}
