package com.progmethgame.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.progmethgame.network.ServerBus;
import com.progmethgame.network.ServerBusListener;
import com.progmethgame.network.event.server.ServerAddEntityEvent;
import com.progmethgame.network.event.server.ServerReadyEvent;
import com.progmethgame.network.event.server.ServerRemoveEntityEvent;
import com.progmethgame.network.event.server.ServerResetEvent;
import com.progmethgame.network.event.server.ServerUpdateEntityEvent;
import com.progmethgame.server.entities.Entity;
import com.progmethgame.server.entities.Player;

public class ServerRuntime implements ServerBusListener, Disposable {
	
	private GameMap map;
	private HashMap<UUID, Entity> entities;
	private HashMap<UUID, Player> players;

	ServerBus bus;
	Random rand;
	
	public ServerRuntime() throws IOException, GameError {
		this.map = new GameMap();
		this.entities = new HashMap<UUID, Entity>();
		this.bus = new ServerBus(this);
		this.rand = new Random();
		this.players = new HashMap<UUID, Player>();
	}
	
	private void simulatePhysic(float delta) {
		for (Entity e : entities.values()) {
			e.getPosition().add(e.getVelocity().cpy().scl(delta));
			
			
			//Collision
			int pX = (int) e.getPosition().x, pY = (int) e.getPosition().y;

			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					int xp = pX + i;
					int yp = pY + j;
					if (xp < 0 || xp >= map.mapWidth || yp < 0 || yp >= map.mapHeight || !map.isSolid(xp, yp)) {
						continue;
					}
					
					float dx = e.getPosition().x - xp, dy = e.getPosition().y - yp;
					if (Math.abs(dx) >= 1 || Math.abs(dy) >= 1) {
						continue;
					}
					
					if (Math.abs(dx) > Math.abs(dy))
						e.getPosition().x +=  (dx > 0 ? 1 - dx : - 1 - dx);
					else
						e.getPosition().y += (dy > 0 ? 1 - dy : - 1 - dy);
								
				}
			}
		}
	}

	public void addEntity(Entity e) {
		entities.put(e.getGid(), e);
		ServerAddEntityEvent ev = new ServerAddEntityEvent();
		ev.entityId = e.getGid();
		ev.data = e.getData();
		bus.sendEvent(null, ev);
	}
	
	public void removeEntity(Entity e) {
		entities.remove(e.getGid());
		players.remove(e.getGid());
		ServerRemoveEntityEvent event = new ServerRemoveEntityEvent();
		event.entityId = e.getGid();
		bus.sendEvent(null, event);
	}

	@Override
	public void onTick(float delta) {
		for (Entity e : entities.values()) {
			e.tick(delta);
		}
		
		simulatePhysic(delta);
		
		for (Entity e : entities.values()) {
			ServerUpdateEntityEvent event = new ServerUpdateEntityEvent();
			event.data = e.getData();
			bus.sendEvent(null, event);
		}
	}

	@Override
	public void onClientJoin(UUID id) {
		Player player = new Player(id);
		player.getPosition().set(1, 1);
		
		players.put(id, player);
		addEntity(player);
		
		//======= Sync game state ===============================================
		
		bus.sendEvent(id, new ServerResetEvent());
		
		//Sync entities
		for (Entity e : entities.values()) {
			ServerAddEntityEvent addevent = new ServerAddEntityEvent();
			addevent.entityId = e.getGid();
			addevent.data = e.getData();
			bus.sendEvent(id, addevent);
		}
		
		ServerReadyEvent readyevent = new ServerReadyEvent();
		readyevent.assignedId = id;
		bus.sendEvent(id, readyevent);
	}

	@Override
	public void onClientDisconnect(UUID id) {
		removeEntity(players.get(id));
	}

	@Override
	public void onPlayerMove(UUID id, Vector2 direction) {
		players.get(id).setWalkDirection(direction);
	}

	@Override
	public void dispose() {
		bus.terminate();
	}
}