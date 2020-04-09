package com.progmethgame.client;

import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.progmethgame.client.screen.ConnectStage;
import com.progmethgame.client.screen.StageScreen;
import com.progmethgame.client.screen.TextStage;
import com.progmethgame.network.ClientBus;
import com.progmethgame.network.ServerBus;
import com.progmethgame.server.GameError;
import com.progmethgame.server.ServerRuntime;

public class GameMain extends Game {

	StageScreen stageScreen;
	ClientRuntime runtime;
	ServerRuntime server;
	
	@Override
	public void create() {
		Gdx.app.setLogLevel(Gdx.app.LOG_DEBUG);
		Gdx.app.log("GameMain", "Initializing");
		
		this.stageScreen = new StageScreen();
		stageScreen.setStage(new ConnectStage(this));
		
		setScreen(stageScreen);
	}

	public void connect(String ipaddr) {
		if (ipaddr == null) {
			displayMessage("Starting server...");
			try {
				this.server = new ServerRuntime();
			} catch (IOException | GameError e) {
				displayMessage("Error while starting server" + e.getMessage());
				Gdx.app.error("GameMain", "Error server init", e);
				return;
			}
			
			displayMessage("Server started. delaying 1 sec for complete initialization");
			new Thread(() -> {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				Gdx.app.postRunnable(()->connect(""));
			}).start();
		} else {
			this.runtime = new ClientRuntime(this, ipaddr);
		}
	}
	
	public void displayMessage(String message) {
		stageScreen.setStage(new TextStage(message));
	}
	
	@Override
	public void dispose() {
		super.dispose();
		if (server != null)
			server.dispose();
	}

}
