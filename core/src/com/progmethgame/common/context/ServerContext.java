package com.progmethgame.common.context;

import com.badlogic.gdx.utils.Disposable;
import com.progmethgame.server.entities.Entity;

public interface ServerContext extends Disposable{
	public void addEntity(Entity e);
	public void removeEntity(Entity e);
}
