package com.progmethgame.server.entities.bullets;

import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.effects.Burn;
import com.progmethgame.server.entities.effects.Confuse;
import com.progmethgame.server.entities.effects.Slow;
import com.progmethgame.server.entities.effects.StatusEffect;
import com.progmethgame.server.entities.effects.Stunt;

public class StatusBullet extends Bullet {
	
	public StatusBullet(int gid, EntityType type, float speed) {
		super(gid, type, speed);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onHit(Player hitPlayer) {
		// TODO Auto-generated method stub
		StatusEffect effect;
		switch(type) {
		case BULLET_FIRE:
			effect = new Burn();
		case BULLET_CONFUSE:
			effect = new Confuse();
		case BULLET_SLOW:
			effect = new Slow();
		case BULLET_STUNT:
			effect = new Stunt();
			
		}
		
	}

}
