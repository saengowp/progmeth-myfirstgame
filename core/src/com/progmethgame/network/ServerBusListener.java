package com.progmethgame.network;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;

public interface ServerBusListener {
	public void onTick(float delta);
	public void onClientJoin(UUID id);
	public void onClientDisconnect(UUID id);
	public void onPlayerMove(UUID id, Vector2 direction);
	public void onPlayerFire(UUID id);
	public void onDebug(UUID id, String debugMsg);
	public void onPlayerSwapGun(UUID id);
}
