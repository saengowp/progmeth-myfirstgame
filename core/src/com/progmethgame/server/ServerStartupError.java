package com.progmethgame.server;

/**
 * Fatal error during the startup of the server
 */
public class ServerStartupError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1353788442300311372L;
	
	/**
	 * Create an startup error
	 * @param message Causes
	 * @param error Reason
	 */
	public ServerStartupError(String message, Throwable error) {
		super(message, error);
	}

}
