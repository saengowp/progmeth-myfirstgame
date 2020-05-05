package com.progmethgame.client;

import java.util.ArrayList;
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

public class ClientEntity extends Sprite{
	
	private UUID gid;
	private DisplayType type;
	private ClientRuntime runtime;
	private Vector2[] posBuf;
	private long[] timeBuf;
	private ArrayList<Overlay> overlays;
	
	public ClientEntity(UUID gid, DisplayType type, ClientRuntime runtime) {
		this.gid = gid;
		this.type = type;
		this.runtime = runtime;
		this.posBuf = new Vector2[2];
		this.posBuf[0] = new Vector2();
		this.posBuf[1] = new Vector2();
		this.timeBuf = new long[2];
		this.timeBuf[0] = this.timeBuf[1] = System.currentTimeMillis();
		this.overlays = new ArrayList<Overlay>();
		
		setOrigin(0.5f, 0.5f);
		
		updateTexture();
	}
	
	public UUID getGid() {
		return gid;
	}
	
	public DisplayType getType() {
		return type;
	}
	
	private void updateTexture() {
		setRegion(runtime.getAssetManager().get(type.filename(), Texture.class));
		setSize(1, 1);
	}
	
	public void update(EntityData data) {
		this.gid = data.id;
		this.type = data.dispType;
		posBuf[0].set(posBuf[1]);
		posBuf[1].set(data.position);
		timeBuf[0] = timeBuf[1];
		timeBuf[1] = System.currentTimeMillis();
		
		
		overlays = data.overlays;
	}
	
	public void tick(float delta) {
		Vector2 pos = posBuf[0].cpy().lerp(
				posBuf[1],
				Math.max(-1,
						Math.min(1,
								((float) ( System.currentTimeMillis() - GameConfig.CLIENT_ENTITY_INTERPOLATION_TIME_MILLIS - timeBuf[0]))/
								(timeBuf[1] - timeBuf[0]+1)
								)
						));
		setPosition(pos.x,  pos.y);
		
		Vector2 facingVec = posBuf[1].cpy().sub(posBuf[0]);
		if (!facingVec.isZero()) {
			setRotation(facingVec.angle() - 90);
		}
		
		updateTexture();
		//System.out.println((System.currentTimeMillis() - GameConfig.CLIENT_ENTITY_INTERPOLATION_TIME_MILLIS - timeBuf[0] ) + " " + (timeBuf[1] - timeBuf[0]));
	//	System.out.println("Rendering X Y T X Y Y " + posBuf[0] + " " + timeBuf[0] + " " +posBuf[1] + " " + timeBuf[1] + " T " + System.currentTimeMillis());
	}
	
	public void drawOverlay(Viewport view, Batch batch, Rectangle rect) {
		for (Overlay o : overlays)
			o.render(view, batch, rect, this);
	}
	
	public static ClientEntity fromData(EntityData data, ClientRuntime runtime) {
		ClientEntity e = new ClientEntity(data.id, data.dispType, runtime);
		e.update(data);
		return e;
	}
}
