package com.progmethgame.network;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.progmethgame.common.DisplayType;

/** Store data needed to render entity on the client-side.
 *
 */
public class EntityData {
	public UUID id;
	public DisplayType dispType;
	public Vector2 position;
}
