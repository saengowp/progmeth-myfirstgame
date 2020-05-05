package com.progmethgame.launcher.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.progmethgame.launcher.GameLauncher;

public class ConnectStage extends BaseStage {
	
	private Texture background;
	private Viewport bgView;
	
	public ConnectStage(GameLauncher game) {
		super();
		
		Table table = new Table();
		table.setFillParent(true);
		table.columnDefaults(0).left();
		table.columnDefaults(1).width(200).padLeft(10);
		
		TextButton createSrvBtn = new TextButton("Create", skin);
		createSrvBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				game.connect(null);
			}
		});
		
		table.add(new Label("Create New Host:", skin));
		table.add(createSrvBtn);
		table.row();
		table.add(new Label("Join Another Host:", skin));
		
		TextField ipaddrfield = new TextField("127.0.0.1", skin);
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
		
		this.background = new Texture("launchbg.png");
		this.bgView = new FillViewport(500, 500);
	}
	
	@Override
	public void draw() {
		this.bgView.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		this.bgView.apply();
		getBatch().setProjectionMatrix(this.bgView.getCamera().combined);
		getBatch().begin();
		getBatch().draw(this.background, 0, 0, 500, 500);
		getBatch().end();
		getViewport().apply();
		super.draw();
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
}
