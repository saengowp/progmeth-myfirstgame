package com.progmethgame.server;

public class GameError extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1353788442300311372L;
	
	public GameError(String message, Throwable error) {
		super(message, error);
	}

}
