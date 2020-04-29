package com.progmethgame.launcher;

import java.io.IOException;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.progmethgame.client.ClientRuntime;
import com.progmethgame.common.context.GameContext;
import com.progmethgame.launcher.screen.ConnectStage;
import com.progmethgame.launcher.screen.QuitableTextStage;
import com.progmethgame.launcher.screen.StageScreen;
import com.progmethgame.launcher.screen.TextStage;
import com.progmethgame.server.GameError;
import com.progmethgame.server.ServerRuntime;

/**
 * Manage the creation and termination of the game. Also provides primitive menu display
 *
 * Note: There's only *one* launcher.
 */
public class GameLauncher extends Game {

	private StageScreen stageScreen;
	private static GameLauncher launcher;
	
	@Override
	public void create() {
		GameLauncher.launcher = this;
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.log("Launcher", "Initializing");
		this.stageScreen = new StageScreen();
		displayWelcomeScreen();
	}
	
	public static GameLauncher getLauncher() {
		return launcher;
	}

	public void connect(String ipaddr) {
		if (ipaddr == null) {
			displayMessage("Starting server...");
			try {
				ServerRuntime server = new ServerRuntime();
				GameContext.setServerContext(server);
			} catch (IOException | GameError e) {
				displayMessageQuitable("Error while starting server" + e.getMessage());
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
			ClientRuntime client = new ClientRuntime(ipaddr);
			GameContext.setClientContext(client);
		}
	}
	
	public void displayMessage(String message) {
		stageScreen.setStage(new TextStage(message));
		setScreen(stageScreen);
	}
	
	public void displayMessageQuitable(String message) {
		stageScreen.setStage(new QuitableTextStage(message, this));
		setScreen(stageScreen);
		
		cleanUpContext();
	}
	
	public void displayWelcomeScreen() {
		stageScreen.setStage(new ConnectStage(this));
		setScreen(stageScreen);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		cleanUpContext();
	}
	
	private void cleanUpContext() {
		if (GameContext.getClientContext() != null)
			// We need to postRunnable despite being in Rendering Thread because
			// After this frame, There will be many network operation pending on
			// runtime. So we wait until those network operations are ran then
			// dispose the runtime
			Gdx.app.postRunnable(()->GameContext.getClientContext().dispose());
		if (GameContext.getServerContext() != null)
			GameContext.getServerContext().dispose();
	}

}
