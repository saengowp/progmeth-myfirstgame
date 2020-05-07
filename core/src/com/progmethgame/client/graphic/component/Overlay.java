package com.progmethgame.client.graphic.component;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.progmethgame.client.ClientEntity;

/**
 * A data structure representing a graphical widget attached to an entity. 
 * 
 * It renders on top of the game in the screen space.
 * This is a synchronized data structure. Don't put any context or state data here.
 */
public abstract class Overlay {
	
	/**
	 * Render this overlay
	 * @param view Screen's viewport
	 * @param batch Screen's batch
	 * @param rect The entity's bounding box in screen space.
	 * @param target The owner of this overlay
	 */
	public abstract void render(Viewport view, Batch batch, Rectangle rect, ClientEntity target);
	
}
