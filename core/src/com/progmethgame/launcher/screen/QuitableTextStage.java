package com.progmethgame.launcher.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.progmethgame.launcher.GameLauncher;

/**
 * Display a message with quit button
 * @author pigt
 *
 */
public class QuitableTextStage extends TextStage {
	
	public QuitableTextStage(String text) {
		super(text);
		
		Button closeBtn = new TextButton("Back to Main Screen", this.skin);
		closeBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				GameLauncher.getLauncher().displayWelcomeScreen();
			}
		});
		
		addActor(closeBtn);
	}

}
