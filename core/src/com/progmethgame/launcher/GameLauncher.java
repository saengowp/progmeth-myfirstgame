package com.progmethgame.launcher;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.progmethgame.client.ClientRuntime;
import com.progmethgame.common.context.GameContext;
import com.progmethgame.launcher.screen.ConnectStage;
import com.progmethgame.launcher.screen.QuitableTextStage;
import com.progmethgame.launcher.screen.StageScreen;
import com.progmethgame.launcher.screen.TextStage;
import com.progmethgame.server.ServerStartupError;
import com.progmethgame.server.ServerRuntime;

/**
 * Provides user interface for creation and termination of the game. 
 *
 * Note: There's only *one* launcher.
 */
public class GameLauncher extends Game {

	/** Current UI stage (if applied) */
	private StageScreen stageScreen;
	
	/** Global game launcher */
	private static GameLauncher launcher;
	
	@Override
	public void create() {
		GameLauncher.launcher = this;
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.log("Launcher", "Initializing");
		this.stageScreen = new StageScreen();
		displayWelcomeScreen();
	}
	
	/** Get the global game launcher */
	public static GameLauncher getLauncher() {
		return launcher;
	}

	/** 
	 * Connect to the server and start the game
	 * @param ipaddr IP address of the server. if null then local server will be started
	 */
	public void connect(String ipaddr) {
		if (ipaddr == null) { // Use local server
			// Start a local server
			displayMessage("Starting server...");
			try {
				ServerRuntime server = new ServerRuntime();
				GameContext.setServerContext(server);
			} catch (ServerStartupError e) {
				displayError(e);
				return;
			}
			
			// Wait for the server to be ready
			displayMessage("Server started. delaying 1 sec for complete initialization");
			new Thread(() -> {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					// Try connect anyway
				}
				
				Gdx.app.postRunnable(()->connect(""));
			}).start();
		} else {
			// Connect
			ClientRuntime client = new ClientRuntime(ipaddr);
			GameContext.setClientContext(client);
		}
	}
	
	/**
	 * Display message
	 * @param message
	 */
	public void displayMessage(String message) {
		stageScreen.setStage(new TextStage(message));
		setScreen(stageScreen);
	}
	
	/**
	 * Display message with with a button to go back to the launcher screen
	 * @param message
	 */
	public void displayMessageQuitable(String message) {
		stageScreen.setStage(new QuitableTextStage(message));
		setScreen(stageScreen);
		
		cleanUpContext();
	}
	
	/**
	 * Display error message
	 * @param error
	 */
	public void displayError(Throwable error) {
		String m = "Error occured\n";
		Throwable e = error;
		while (e != null) {
			m += e.getMessage() + " caused by \n";
			e = e.getCause();
		}
		
		displayMessageQuitable(m);
		Gdx.app.error("Launcher", "displayError", error);
	}
	
	/**
	 * Display connect screen
	 */
	public void displayWelcomeScreen() {
		stageScreen.setStage(new ConnectStage());
		setScreen(stageScreen);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		cleanUpContext();
	}
	
	/**
	 * Cleanup all the context created.
	 */
	private void cleanUpContext() {
		if (GameContext.getClientContext() != null)
			/*
			 * We need to postRunnable despite being in Rendering Thread because After this
			 * frame, There will be many network operation pending on runtime. So we wait
			 * until those network operations are ran then dispose the runtime
			 * 
			 * This may or may not fix the crash.
			 */
			Gdx.app.postRunnable(()->GameContext.getClientContext().dispose());
		if (GameContext.getServerContext() != null)
			GameContext.getServerContext().dispose();
	}

}
