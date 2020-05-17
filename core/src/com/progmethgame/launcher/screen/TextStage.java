package com.progmethgame.launcher.screen;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

/**
 * Display a message
 *
 */
public class TextStage extends BaseStage {
	
	/** Text label displayed */
	protected Label textLabel;
	
	/**
	 * Create new text stage with specified message
	 * @param text a message to display
	 */
	public TextStage(String text) {
		super();
		textLabel = new Label(text, skin);
		textLabel.setFillParent(true);
		textLabel.setWrap(true);
		textLabel.setAlignment(Align.center);
		this.addActor(textLabel);
	}
}
