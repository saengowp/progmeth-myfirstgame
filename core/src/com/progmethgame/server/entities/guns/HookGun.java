package com.progmethgame.server.entities.guns;

import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.bullets.HookBullet;

/** Gun that spawn hook bullet */
public class HookGun extends Gun {

	public HookGun(Player owner) {
		super("Hook Gun", GunConfig.HOOK_GUN_MAX_COOLDOWN, owner);
		this.bullet = new HookBullet(owner);
	}

}
