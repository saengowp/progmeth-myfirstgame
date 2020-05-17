package com.progmethgame.server.entities.bullets;

import com.progmethgame.common.DisplayType;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.effects.StatusEffect;

/**
 * A bullet that will apply effect to the player
 * @author pigt
 *
 */
public abstract class StatusBullet extends Bullet {
	
	/** Effect to be applied to the player */
	protected StatusEffect effect;

	public StatusBullet(DisplayType type, float speed, Player owner) {
		super(type, speed, owner);
	}

	@Override
	public void onCollide(Player hitPlayer) {
		hitPlayer.setEffect(effect.cpy());
	}

}
