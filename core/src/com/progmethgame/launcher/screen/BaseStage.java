package com.progmethgame.launcher.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Stage with skin loaded
 */
public abstract class BaseStage extends Stage {
	
	/** Scene2d skin */
	protected Skin skin;

	public BaseStage() {
		super(new ScreenViewport());
		this.skin = new Skin(Gdx.files.internal("plain-james/skin/plain-james-ui.json"));
	}
}
