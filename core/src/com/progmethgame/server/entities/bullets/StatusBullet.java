package com.progmethgame.server.entities.bullets;

import com.progmethgame.common.DisplayType;
import com.progmethgame.common.context.GameContext;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.effects.Burn;
import com.progmethgame.server.entities.effects.StatusEffect;

public abstract class StatusBullet extends Bullet {
	protected StatusEffect effect;

	public StatusBullet(DisplayType type, float speed, Player owner) {
		super(type, speed, owner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCollide(Player hitPlayer) {
		// TODO Auto-generated method stub
		if(!hitPlayer.equals(owner)) {
			hitPlayer.setEffect(effect.cpy());
			GameContext.getServerContext().removeEntity(this);
		}

	}

}
