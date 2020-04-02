package com.progmethgame.common.network;

import java.util.ArrayList;

public abstract class Packet<T>{
	public ArrayList<Command<T>> commands;
	
	public Packet() {
		this.commands = new ArrayList<Command<T>>();
	}

	public void apply(T target) {
		for (Command<T> com : commands) {
			com.applyAction(target);
		}
	}
}
