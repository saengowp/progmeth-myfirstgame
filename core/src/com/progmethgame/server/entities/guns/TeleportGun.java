package com.progmethgame.server.entities.guns;

import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.bullets.TeleportBullet;

/** Gun that spawn teleport bullet */
public class TeleportGun extends Gun {

	public TeleportGun(Player owner) {
		super("Teleport Gun", GunConfig.TELEPORT_GUN_MAX_COOLDOWN, owner);
		this.bullet = new TeleportBullet(owner);
	}

}
