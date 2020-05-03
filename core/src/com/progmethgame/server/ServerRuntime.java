package com.progmethgame.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Queue;
import com.progmethgame.common.SoundType;
import com.progmethgame.common.context.ServerContext;
import com.progmethgame.network.ServerBus;
import com.progmethgame.network.ServerBusListener;
import com.progmethgame.network.event.server.ServerAddEntityEvent;
import com.progmethgame.network.event.server.ServerPlaySoundEvent;
import com.progmethgame.network.event.server.ServerReadyEvent;
import com.progmethgame.network.event.server.ServerRemoveEntityEvent;
import com.progmethgame.network.event.server.ServerResetEvent;
import com.progmethgame.network.event.server.ServerUpdateEntityEvent;
import com.progmethgame.server.entities.Entity;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.TestEntity;

public class ServerRuntime implements ServerBusListener, Disposable, ServerContext {
	
	private GameMap map;
	private HashMap<UUID, Entity> entities;
	private HashMap<UUID, Player> players;
	
	private final Queue<Entity> entitiesAddQueue, entitiesRemovalQueue;

	ServerBus bus;
	Random rand;
	
	public ServerRuntime() throws IOException, GameError {
		this.map = new GameMap();
		this.entities = new HashMap<UUID, Entity>();
		this.rand = new Random();
		this.players = new HashMap<UUID, Player>();
		this.entitiesAddQueue = new Queue<Entity>();
		this.entitiesRemovalQueue = new Queue<Entity>();
		this.bus = new ServerBus(this);
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
					if (xp < 0 || xp >= map.mapWidth || yp < 0 || yp >= map.mapHeight || !map.getBlock(xp, yp).isSolid()) {
						continue;
					}
					
					float dx = e.getPosition().x - xp, dy = e.getPosition().y - yp;
					if (Math.abs(dx) >= 1 || Math.abs(dy) >= 1) {
						continue;
					}
					
					e.onCollideSolid(map.getBlock(xp, yp));
					
					if (Math.abs(dx) > Math.abs(dy))
						e.getPosition().x +=  (dx > 0 ? 1 - dx : - 1 - dx);
					else
						e.getPosition().y += (dy > 0 ? 1 - dy : - 1 - dy);
								
				}
			}
		}
		
		//Entity-Entity collision
		
		HashSet<Entity> checked = new HashSet<Entity>();
		for (Entity a : entities.values()) {
			checked.add(a);
			for (Entity b : entities.values()) {
				if (checked.contains(b))
					continue;
				if (Math.abs(a.getPosition().x - b.getPosition().x) < 1 && Math.abs(a.getPosition().y - b.getPosition().y) < 1) {
					a.onCollide(b);
					b.onCollide(a);
				}
			}
		}
		
		//Entity - Floor collision
		
		for (Entity e: entities.values()) {
			e.onWalkOn(map.getBlock(Math.round(e.getPosition().x), Math.round(e.getPosition().y)));
		}
	}

	@Override
	public void addEntity(Entity e) {
		entitiesAddQueue.addLast(e);
		ServerAddEntityEvent ev = new ServerAddEntityEvent();
		ev.entityId = e.getGid();
		ev.data = e.getData();
		bus.sendEvent(null, ev);
	}
	
	@Override
	public void removeEntity(Entity e) {
		entitiesRemovalQueue.addLast(e);
		ServerRemoveEntityEvent event = new ServerRemoveEntityEvent();
		event.entityId = e.getGid();
		bus.sendEvent(null, event);
	}

	@Override
	public void onTick(float delta) {
		// Apply entity addition and removal.
		while (!entitiesAddQueue.isEmpty()) {
			entities.put(entitiesAddQueue.first().getGid(), entitiesAddQueue.first());
			entitiesAddQueue.removeFirst();
		}
		while (!entitiesRemovalQueue.isEmpty()) {
			entities.remove(entitiesRemovalQueue.first().getGid());
			players.remove(entitiesRemovalQueue.removeFirst().getGid());
		}
		
		//Tick
		for (Entity e : entities.values()) {
			e.tick(delta);
		}
		
		//Physic
		simulatePhysic(delta);
		
		
		
		//Sync entities data
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
		
		//======= Sync game state ===============================================
		
		bus.sendEvent(id, new ServerResetEvent());
		
		addEntity(player);
		
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

	@Override
	public void onPlayerFire(UUID id) {
		players.get(id).fire();
	}

	@Override
	public void onDebug(UUID id, String debugMsg) {
		switch (debugMsg) {
		case "hello":
			System.out.println("[ServerRuntime] Client " + id.toString() + " send a ping");
			break;
		case "spawntest":
			TestEntity t = new TestEntity();
			t.getPosition().set(players.get(id).getPosition());
			addEntity(t);
			break;
		
		case "hurtme":
			players.get(id).dealDamge(10);
			break;
			
		default:
			break;
		}
		
	}

	@Override
	public void onPlayerSwapGun(UUID id) {
		players.get(id).swapGun();
	}

	@Override
	public void playSound(SoundType s) {
		bus.sendEvent(null, new ServerPlaySoundEvent(s));
	}
}
