package com.progmethgame.common.network;

public interface Command<T> {
	public void applyAction(T target);
}
