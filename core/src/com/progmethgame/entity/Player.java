package com.progmethgame.entity;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.progmethgame.entity.base.Entity;
import com.progmethgame.entity.base.EntityProcessor;
import com.progmethgame.entity.component.PhysicalEntities;
import com.progmethgame.entity.component.RenderComponent;
import com.progmethgame.entity.component.Renderable;
import com.progmethgame.graphic.EntityRenderer;

public class Player extends Entity implements PhysicalEntities, Renderable, Disposable {
	Vector2 position;
	RenderComponent render;
	
	ArrayList<EntityProcessor> processor;
	
	public Player(EntityRenderer renderer) {
		position = new Vector2(0, 0);
		render = new RenderComponent(Gdx.files.internal("player.png"), position);
		
		processor = new ArrayList();
		processor.add(renderer);
		
		
		renderer.register(this);
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public RenderComponent getRenderComponent() {
		return render;
	}

	@Override
	public void dispose() {
		for (EntityProcessor p : processor)
			p.unregister(this);
		
		render.dispose();
	}

}
