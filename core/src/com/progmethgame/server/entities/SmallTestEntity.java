package com.progmethgame.server.entities;

import com.progmethgame.common.DisplayType;

public class SmallTestEntity extends TestEntity {

	public SmallTestEntity() {
		this.hot = DisplayType.SMALLCUBEEX;
		this.cool= DisplayType.SMALLCUBE;
	}
	
	@Override
	public float getPhysicalSize() {
		return 0.5f;
	}
}
