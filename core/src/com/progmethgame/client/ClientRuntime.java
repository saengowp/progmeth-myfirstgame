package com.progmethgame.client;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import com.progmethgame.network.ClientBus;
import com.progmethgame.network.ClientBusListener;
import com.progmethgame.network.EntityData;

public class ClientRuntime implements ClientBusListener, Disposable {
	private GameScreen screen;
	private GameMain gameControl;
	private UUID clientId;
	private ClientBus bus;
	private HashMap<UUID, ClientEntity> entities;
	private AssetManager assetsMan;
	
	public ClientRuntime(GameMain gameControl, String ipaddr) {
		this.gameControl = gameControl;
		this.entities = new HashMap<UUID, ClientEntity>();
		this.assetsMan = new AssetManager();
		initAssets();
		
		gameControl.displayMessage("Waiting...");
		this.assetsMan.finishLoading();
		
		new Thread(() -> {
			Gdx.app.postRunnable(()->gameControl.displayMessage("Connecting... " + ipaddr));
			
			try {
				bus = new ClientBus(ipaddr, this);
			} catch (IOException e) {
				Gdx.app.error("ClientBus", "Error while initializing client bus", e);
				Gdx.app.postRunnable(()->gameControl.displayMessage("Error " + e.getMessage()));
				return;
			}
			
			Gdx.app.postRunnable(()->gameControl.displayMessage("Connected. Awaiting server READY signal"));
		}).start();
	}
	
	private void initAssets() {
		assetsMan.load("player.png", Texture.class);
	}
	
	public AssetManager getAssetManaget() {
		return this.assetsMan;
	}
	
	public ClientEntity getPlayer() {
		return entities.get(clientId);
	}
	
	public Collection<ClientEntity> getEntities() {
		return Collections.unmodifiableCollection(this.entities.values());
	}

	@Override
	public void onEntityAdd(UUID id, EntityData data) {
		entities.put(id, ClientEntity.fromData(data, this));
	}

	@Override
	public void onEntityRemove(UUID id) {
		entities.remove(id);
	}

	@Override
	public void onEntityUpdate(EntityData data) {
		//It's possible that the server send update command before add command. ignore it
		if (entities.containsKey(data.id))
			entities.get(data.id).update(data);
	}

	@Override
	public void onServerReady(UUID assignedId) {
		Gdx.app.log("ClientRuntime", "Assigned player id " + assignedId.toString());
		this.clientId = assignedId;
		this.screen = new GameScreen(this, new GameController(bus));
		gameControl.setScreen(screen);
	}

	@Override
	public void onServerReset() {
		gameControl.displayMessage("Resetting level");
		entities.clear();
	}

	public void tick(float delta) {
		for (ClientEntity e: entities.values())
			e.tick(delta);
	}

	@Override
	public void dispose() {
		assetsMan.dispose();
	}
}
