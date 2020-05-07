package com.progmethgame.common;

import java.util.ArrayList;
import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.progmethgame.client.graphic.component.Overlay;

/** 
 * Data structure representing networked entity.
 */
public class EntityData {
	public UUID id;
	public DisplayType dispType;
	public Vector2 position;
	public ArrayList<Overlay> overlays;
}
