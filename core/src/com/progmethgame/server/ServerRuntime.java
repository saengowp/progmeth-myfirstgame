package com.progmethgame.server;

import java.io.IOException;
import java.util.ArrayList;
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
import com.progmethgame.server.entities.SmallTestEntity;
import com.progmethgame.server.entities.TestEntity;
import com.progmethgame.server.entities.WinningBannerEntity;

public class ServerRuntime implements ServerBusListener, Disposable, ServerContext {
	
	private GameMap map;
	private HashMap<UUID, Entity> entities;
	private HashMap<UUID, Player> players;
	
	private final Queue<Entity> entitiesAddQueue, entitiesRemovalQueue;

	ServerBus bus;
	Random rand;
	
	public ServerRuntime() throws IOException {
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
					if (xp < 0 || xp >= map.getWidth() || yp < 0 || yp >= map.getHeight() || !map.getBlock(xp, yp).isSolid()) {
						continue;
					}
					
					float dx = e.getPosition().x - xp, dy = e.getPosition().y - yp;
					float collisionRadius = (float) (e.getPhysicalSize()/2 + 0.5);
					if (Math.abs(dx) >= collisionRadius || Math.abs(dy) >= collisionRadius) {
						continue;
					}
					
					e.onCollideSolid(map.getBlock(xp, yp));
					
					if (Math.abs(dx) > Math.abs(dy))
						e.getPosition().x +=  (dx > 0 ? collisionRadius - dx : - collisionRadius - dx);
					else
						e.getPosition().y += (dy > 0 ? collisionRadius - dy : - collisionRadius - dy);
								
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
				float collisionRad = (a.getPhysicalSize() + b.getPhysicalSize())/2 ;
				if (Math.abs(a.getPosition().x - b.getPosition().x) < collisionRad && Math.abs(a.getPosition().y - b.getPosition().y) < collisionRad) {
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
		map.onPlayerEnter(player);
		
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
		case "spawntest": {
			TestEntity t = new TestEntity();
			t.getPosition().set(players.get(id).getPosition());
			addEntity(t);
			break;
		}
		case "spawntestsmall": {
			TestEntity t = new SmallTestEntity();
			t.getPosition().set(players.get(id).getPosition());
			t.getVelocity().set(players.get(id).getFaceDirection());
			addEntity(t);
			break;
		}
		case "hurtme":
			players.get(id).dealDamge(10);
			break;
			
		case "iwin": {
			WinningBannerEntity e = new WinningBannerEntity(id);
			addEntity(e);
			break;
		}
		
		case "reset": {
			reset();
			break;
		}
			
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
	
	@Override
	public void reset() {
		entities.clear();
		entitiesAddQueue.clear();
		entitiesRemovalQueue.clear();
		map = new GameMap();
		players.clear();
		for (UUID id: bus.getConnectionUUIDs()) {
			onClientJoin(id);
		}
	}

}
