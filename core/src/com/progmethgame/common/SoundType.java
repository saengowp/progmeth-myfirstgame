package com.progmethgame.common;

public enum SoundType {
	PEW("pew.wav");
	
	private String filename;
	private SoundType(String filename) {
		this.filename = filename;
	}
	
	public String getFilepath() {
		return this.filename;
	}
}
