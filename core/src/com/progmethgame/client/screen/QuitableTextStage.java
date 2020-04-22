package com.progmethgame.client.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.progmethgame.client.GameMain;

public class QuitableTextStage extends TextStage {

	private final GameMain gameMain;
	
	public QuitableTextStage(String text, GameMain gameMain) {
		super(text);
		this.gameMain = gameMain;
		
		Button closeBtn = new TextButton("Back to Main Screen", this.skin);
		closeBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				gameMain.displayWelcomeScreen();
			}
		});
		
		addActor(closeBtn);
	}

}
