package com.progmethgame.entity;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.progmethgame.EntityProcessorProvider;
import com.progmethgame.ai.Tickable;
import com.progmethgame.entity.base.Entity;
import com.progmethgame.entity.base.EntityProcessor;
import com.progmethgame.graphic.EntityRenderer;
import com.progmethgame.graphic.RenderComponent;
import com.progmethgame.graphic.Renderable;
import com.progmethgame.physic.PhysicalComponent;
import com.progmethgame.physic.PhysicalEntity;

public class Player extends Entity implements Renderable, PhysicalEntity, Disposable , Tickable{
	Vector2 position;
	RenderComponent render;
	PhysicalComponent physical;
	EntityProcessorProvider provider;
	public Vector2 ctrlDir;
	
	
	public Player(EntityProcessorProvider entityProcessor) {
		position = new Vector2(0, 0);
		render = new RenderComponent(Gdx.files.internal("player.png"), position);
		physical = new PhysicalComponent(position, 1);
		ctrlDir = new Vector2();

		this.provider = entityProcessor;
		
		provider.renderer.register(this);
		provider.physic.register(this);
		provider.ticker.register(this);
	}

	public Vector2 getPosition() {
		return position;
	}

	@Override
	public RenderComponent getRenderComponent() {
		return render;
	}

	@Override
	public void dispose() {
		this.provider.renderer.unregister(this);
		this.provider.physic.unregister(this);
		render.dispose();
	}

	@Override
	public PhysicalComponent getPhysicalComponent() {
		return physical;
	}
	
	public void setFacing(Vector2 target) {
		Vector2 sightVec = target.cpy().sub(this.position);
		this.render.rotation = sightVec.angle() - 90f;
	}

	@Override
	public void tick(float delta) {
		this.physical.velocity.set(ctrlDir.nor().scl(3f));
	}

}
