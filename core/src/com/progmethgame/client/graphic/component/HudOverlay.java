package com.progmethgame.client.graphic.component;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.progmethgame.client.ClientEntity;
import com.progmethgame.common.DisplayType;
import com.progmethgame.common.context.GameContext;

/**
 * HUD for displaying player's status
 */
public class HudOverlay extends Overlay {
	
	/** Current health portion*/
	private float health;
	
	/** Weapon's name*/
	private String weaponName;
	
	/** Current gun's icon */
	private DisplayType gunIcon;
	
	/** Current player's icon*/
	private DisplayType playerIcon;
	
	public HudOverlay() {
		this.health = 0;
		this.weaponName = "";
		this.gunIcon = this.playerIcon = DisplayType.TEST;
	}
	
	@Override
	public void render(Viewport view, Batch batch, Rectangle rect, ClientEntity target) {
		//Don't render other player's HUD.
		if (!GameContext.getClientContext().getClientUUID().equals(target.getGid()))
			return;
		
		AssetManager assets = GameContext.getClientContext().getAssetManager();
		
		// Background
		Texture hudBg = assets.get("hud.png", Texture.class);
		batch.draw(hudBg, 0, 0, 960, 540);
		
		// Health bar
		Healthbar.render(batch, 150, 0, 510, 78, health, "HUDhpbarblack.png", "HUDhpbar.png");
		
		// Weapon name
		BitmapFont fnt = assets.get("font.ttf", BitmapFont.class);
		fnt.draw(batch, weaponName, 315, 110);
		
		// Gun icon
		Texture gunBg = assets.get(gunIcon.filename(), Texture.class);
		batch.draw(gunBg, 214, 73, 60, 60);
		
		// Player icon
		Texture playerTex = assets.get(playerIcon.filename(), Texture.class);
		batch.draw(playerTex, 52, 49, 138, 138);
		
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public void setWeaponName(String weaponName) {
		this.weaponName = weaponName;
	}

	public void setGunIcon(DisplayType gunIcon) {
		this.gunIcon = gunIcon;
	}

	public void setPlayerIcon(DisplayType playerIcon) {
		this.playerIcon = playerIcon;
	}

}
