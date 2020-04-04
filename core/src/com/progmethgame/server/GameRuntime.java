package com.progmethgame.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.progmethgame.command.EntityAddCommand;
import com.progmethgame.command.EntityMovementCommand;
import com.progmethgame.command.StartGameCommand;
import com.progmethgame.common.network.ServerPacket;
import com.progmethgame.server.entities.Entity;
import com.progmethgame.server.entities.Player;

public class GameRuntime {
	
	private GameMap map;
	private HashMap<Integer, Entity> entities;
	private HashMap<Integer, Player> players;

	GameServer gameServer;
	Random rand;
	
	public GameRuntime(GameServer gameServer) throws GameError {
		this.map = new GameMap();
		this.entities = new HashMap<Integer, Entity>();
		this.gameServer = gameServer;
		this.rand = new Random();
		this.players = new HashMap<Integer, Player>();
	}

	public void tick(float delta) {
		for (Entity e : entities.values()) {
			
			//Simulate Physic
			e.position.add(e.velocity.cpy().scl(delta));
			
			//Collision
			int pX = (int) e.position.x, pY = (int) e.position.y;

			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					int xp = pX + i;
					int yp = pY + j;
					if (xp < 0 || xp >= map.mapWidth || yp < 0 || yp >= map.mapHeight || !map.isSolid(xp, yp)) {
						continue;
					}
					
					float dx = e.position.x - xp, dy = e.position.y - yp;
					if (Math.abs(dx) >= 1 || Math.abs(dy) >= 1) {
						continue;
					}
					
					if (Math.abs(dx) > Math.abs(dy))
						e.position.x +=  (dx > 0 ? 1 - dx : - 1 - dx);
					else
						e.position.y += (dy > 0 ? 1 - dy : - 1 - dy);
								
				}
			}
			
			//Gdx.app.debug("ServerPhysic", "E POS " + e.position.x + " , " + e.position.y + " V VEC " + e.velocity.x + " , " + e.velocity.y + "DEL " + delta);
			
			EntityMovementCommand moveCom = new EntityMovementCommand();
			moveCom.gid = e.gid;
			moveCom.position = e.position;

			gameServer.notifyClient(-1, moveCom);
		}
	}
	
	public void addEntity(Entity e) {
		entities.put(e.gid, e);
		EntityAddCommand com = new EntityAddCommand();
		com.gid = e.gid;
		com.type = e.type;
		gameServer.notifyClient(-1, com);
	}

	public void registerPlayer(int playerId) {
		Player player = new Player(playerId);
		player.position.set(1, 1);
		
		//Sync game state

		addEntity(player);

		for (Entity e : entities.values()) {
			EntityAddCommand com = new EntityAddCommand();
			com.gid = e.gid;
			com.type = e.type;
			
			gameServer.notifyClient(playerId, com);
		}
		
		{
			StartGameCommand startCom = new StartGameCommand();
			startCom.userid = playerId;
			
			gameServer.notifyClient(playerId, startCom);
		}
		
		players.put(playerId, player);
	}
	
	
	public void onPlayerMove(int playerId, Vector2 movement) {
		players.get(playerId).velocity.set(movement.cpy().nor().scl(5));
	}
}
