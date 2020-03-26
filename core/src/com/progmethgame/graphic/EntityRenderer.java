package com.progmethgame.graphic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.progmethgame.entity.base.EntityProcessor;
import com.progmethgame.entity.component.Renderable;

public class EntityRenderer extends EntityProcessor<Renderable>{
		
	void render(Batch batch) {
		for (Renderable e : entities) {
			e.getRenderComponent().render(batch);
		}
	}
}
