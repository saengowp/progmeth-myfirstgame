package com.progmethgame.common;

/** Sound effect available on the client */
public enum SoundType {
	PEW("pew.wav");
	
	/** File path to the sound */
	private String filename;
	
	/** Create new sound with given file*/
	private SoundType(String filename) {
		this.filename = filename;
	}
	
	/** Get this sound's file path */
	public String getFilepath() {
		return this.filename;
	}
}
