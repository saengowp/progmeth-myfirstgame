package com.progmethgame.launcher.screen;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

/**
 * Display a message
 *
 */
public class TextStage extends BaseStage {
	
	/**
	 * Create new text stage with specified message
	 * @param text a message to display
	 */
	public TextStage(String text) {
		super();
		Label label = new Label(text, skin);
		label.setFillParent(true);
		label.setWrap(true);
		label.setAlignment(Align.center);
		this.addActor(label);
	}
}
