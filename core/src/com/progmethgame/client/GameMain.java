package com.progmethgame.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.progmethgame.client.screen.ConnectStage;
import com.progmethgame.client.screen.StageScreen;
import com.progmethgame.client.screen.TextStage;
import com.progmethgame.server.GameServer;

public class GameMain extends Game {

	StageScreen stageScreen;
	GameServer server;
	
	@Override
	public void create() {
		Gdx.app.setLogLevel(Gdx.app.LOG_DEBUG);
		Gdx.app.log("GameMain", "Initializing");
		
		this.stageScreen = new StageScreen();
		stageScreen.setStage(new ConnectStage(this));
		
		setScreen(stageScreen);
	}
	
	public void startServer() {
		this.server = new GameServer();
		server.start();
	}

	public void connect(String ipaddr) {
		stageScreen.setStage(new TextStage("Connecting..."));
		new GameClient(ipaddr, this);
	}
	
	public void displayMessage(String message) {
		stageScreen.setStage(new TextStage(message));
	}
	
	@Override
	public void dispose() {
		super.dispose();
		server.terminate();
	}

}
