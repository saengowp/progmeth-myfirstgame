package com.progmethgame.client.graphic.component;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.progmethgame.client.ClientEntity;
import com.progmethgame.common.context.GameContext;

public class HealthOverlay extends Overlay {

	public float health;
	
	@Override
	public void render(Viewport view, Batch batch, Rectangle rect, ClientEntity target) {
		Healthbar.render(batch, (int) rect.x, (int) rect.y + (int) rect.height + 10, GameContext.getClientContext().getAssetManager(), health);
	}

}
