package com.progmethgame.entity.base;

import java.util.ArrayList;

public abstract class EntityProcessor<T> {
	protected ArrayList<T> entities;
	
	public EntityProcessor() {
		this.entities = new ArrayList<T>();
	}
	
	public void register(T e) {
		entities.add(e);
	}
	
	public void unregister(T e) {
		entities.remove(e);
	}
}
