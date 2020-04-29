package com.progmethgame.client.graphic.component;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.progmethgame.client.ClientEntity;

/**
 * A graphical widget that render on top of the game in the screen space.
 *
 */
public abstract class Overlay {
	
	public abstract void render(Viewport view, Batch batch, Rectangle rect, ClientEntity target);
	
}
