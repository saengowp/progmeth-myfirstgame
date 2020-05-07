package com.progmethgame.server;

/**
 * the Map is malformatted or not found
 *
 */
public class MapParserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -644306957292892482L;
	
	public MapParserException(String message) {
		super(message);
	}
	
	public MapParserException(String message, Throwable e) {
		super(message, e);
	}
}
