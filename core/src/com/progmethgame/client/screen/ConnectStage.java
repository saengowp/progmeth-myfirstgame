package com.progmethgame.client.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.progmethgame.client.GameMain;

public class ConnectStage extends BaseStage {
	
	public ConnectStage(GameMain game) {
		super();
		
		Table table = new Table();
		table.setFillParent(true);
		
		TextButton createSrvBtn = new TextButton("Create New Server", skin);
		createSrvBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				game.connect(null);
			}
		});
		
		table.add(createSrvBtn);
		table.row();
		table.add(new Label("Join existing server IP:", skin));
		
		TextField ipaddrfield = new TextField("", skin);
		table.add(ipaddrfield);
		
		TextButton joinBtn = new TextButton("Join", skin);
		joinBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				game.connect(ipaddrfield.getText());
			}
		});
		table.add(joinBtn);

		this.addActor(table);
	}
}
