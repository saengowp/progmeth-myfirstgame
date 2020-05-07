package com.progmethgame.server.entities;

import java.util.UUID;

import com.progmethgame.client.graphic.component.BannerWinOverlay;
import com.progmethgame.common.DisplayType;

public class WinningBannerEntity extends Entity {

	public WinningBannerEntity(UUID winPlayer) {
		super(DisplayType.TEST);
		getPosition().set(-100, -100); //Very far away off-screen.
		overlays.add(new BannerWinOverlay(winPlayer));
	}

}
