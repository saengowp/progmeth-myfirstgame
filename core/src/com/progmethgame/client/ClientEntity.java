package com.progmethgame.client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.progmethgame.client.graphic.component.Overlay;
import com.progmethgame.common.DisplayType;
import com.progmethgame.common.EntityData;
import com.progmethgame.common.GameConfig;
import com.progmethgame.common.context.GameContext;

/**
 * Represent client-side's entity data.
 * 
 * This is the only entity available to the client. 
 * It contains just enough data to render whatever it represent.
 */
public class ClientEntity extends Sprite{
	
	/** ID of this entity. This is unique to all entity and is used to communicate with the server */
	private UUID gid;
	
	/** Texture to display */
	private DisplayType type;
	
	/** Most recent position of this entity according to the server (for interpolation) */
	private Vector2[] lastPosBuffer;
	
	/** Most recent time this entity was updated (for interpolation) */
	private long[] lastUpdateTimeBuffer;
	
	/** Overlays attached to this entity */
	private List<Overlay> overlays;
	
	/**
	 * Create new entity from data
	 * @param data entity's data
	 */
	public ClientEntity(EntityData data) {
		this.lastPosBuffer = new Vector2[2];
		this.lastPosBuffer[0] = new Vector2();
		this.lastPosBuffer[1] = new Vector2();
		this.lastUpdateTimeBuffer = new long[2];
		this.lastUpdateTimeBuffer[0] = this.lastUpdateTimeBuffer[1] = System.currentTimeMillis();
		this.overlays = new ArrayList<Overlay>();
		
		setOrigin(0.5f, 0.5f);
		setSize(1, 1);
		
		update(data);
		applyDisplayType();
	}
	
	/**
	 * Update this sprite's texture to match the display type
	 */
	private void applyDisplayType() {
		setRegion(
				GameContext.getClientContext().getAssetManager().get(type.filename(),
						Texture.class));
	}
	
	/**
	 * @return This entity's ID
	 */
	public UUID getGid() {
		return gid;
	}
	
	/**
	 * @return This entity's texture
	 */
	public DisplayType getType() {
		return type;
	}

	/**
	 *  Update this entity's state using EntityData
	 * @param data updated data
	 */
	public void update(EntityData data) {
		this.gid = data.id;
		this.type = data.dispType;
		this.overlays = data.overlays;
		
		//For interpolation
		lastPosBuffer[0].set(lastPosBuffer[1]);
		lastPosBuffer[1].set(data.position);
		lastUpdateTimeBuffer[0] = lastUpdateTimeBuffer[1];
		lastUpdateTimeBuffer[1] = System.currentTimeMillis();
	}
	
	/**
	 * Animate this entity by specific time-step
	 * @param delta time duration
	 */
	public void tick(float delta) {
		// Position interpolation
		Vector2 pos = lastPosBuffer[0].cpy().lerp(
				lastPosBuffer[1],
				Math.max(-1,
						Math.min(1,
								((float) ( System.currentTimeMillis() - GameConfig.CLIENT_ENTITY_INTERPOLATION_TIME_MILLIS - lastUpdateTimeBuffer[0]))/
								(lastUpdateTimeBuffer[1] - lastUpdateTimeBuffer[0]+1)
								)
						));
		setPosition(pos.x,  pos.y);
		
		// Facing detection
		Vector2 facingVec = lastPosBuffer[1].cpy().sub(lastPosBuffer[0]);
		if (!facingVec.isZero()) {
			setRotation(facingVec.angle() - 90);
		}
		
		//Display type update
		applyDisplayType();
	}
	
	/**
	 * Draw this entity's overlays
	 * @param view ScreenViewport
	 * @param batch Drawing Batch
	 * @param rect Rectangle representing this entity in the viewport
	 */
	public void drawOverlay(Viewport view, Batch batch, Rectangle rect) {
		for (Overlay o : overlays)
			o.render(view, batch, rect, this);
	}
}
