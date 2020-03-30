package com.progmethgame.ai;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.progmethgame.map.base.Map;
import com.progmethgame.physic.PhysicalComponent;

public class AutoWaypointComponent extends WaypointComponent{

	Vector2 target;
	
	float waitP;

	public AutoWaypointComponent(PhysicalComponent physic, float walkSpeed, Vector2 target) {
		super(physic, walkSpeed);
		this.target = target;
		this.waitP = 0;
	}
	
	@Override
	public void update(float delta, Map map) {
		super.update(delta, map);
		
		
		waitP += delta;
		if (waitP < 1)
			return;
		
		waitP = 0;
		

		PriorityQueue<Pnt> open = new PriorityQueue<Pnt>();
		HashMap<Pnt, Pnt> trace = new HashMap<Pnt, Pnt>();
		HashSet<Pnt> vst = new HashSet<Pnt>();
		HashMap<Pnt, Float> dist = new HashMap<Pnt, Float>();
		
		Pnt tar = new Pnt(0, (int) Math.round(target.x), (int) Math.round(target.y));

		Pnt src = new Pnt(0, (int) Math.round(physic.position.x) , (int) Math.round(physic.position.y));
		src.dist = src.dst(tar);
		open.add(src);
		dist.put(src, 0f);
		

		//Gdx.app.debug("AutoWaypoint", "Start pathfinding to " + tar.x + ", " + tar.y + " from " + src.x + " ," +src.y);
		
		final int mapWidth = map.getTileMap().getProperties().get("width", Integer.class);
		final int mapHeight = map.getTileMap().getProperties().get("height", Integer.class);
		
				
		while (!open.isEmpty()) {
			Pnt u = open.poll();
			if (vst.contains(u))
				continue;
			
			
			if (u.equals(tar)) {
				//Gdx.app.debug("AutoWaypoint", "TargetFound");
				path.clear();

				while (trace.containsKey(u)) {
					path.addFirst(new Vector2(u.x, u.y));
					//Gdx.app.debug("AutoWaypoint", "Appending waypoint " + u.x + ", " + u.y);
					u = trace.get(u);
				}
				
				path.addFirst(new Vector2(u.x, u.y));
				
				return;
			}
			vst.add(u);
			u.dist = dist.get(u);

			//Gdx.app.debug("AutoWaypoint", "F " + u.x + ", " + u.y + " FromSrc " + u.dist);
			
			final int dir[] = { 0, 1, 0, -1, 1, 0, -1, 0};
			for (int i = 0; i < dir.length; i += 2) {
				int nx = u.x + dir[i], ny = u.y + dir[i+1];
				Pnt v = new Pnt(0, nx, ny);
				if (nx < 0 || nx >= mapWidth || ny < 0 || ny >= mapHeight || map.isSolid(nx, ny) || vst.contains(v))
					continue;
				
				if (!dist.containsKey(v) || dist.get(v) > u.dist + 1) {
					dist.put(v, u.dist + 1);
					trace.put(v, u);
					v.dist = u.dist + 1 + v.dst(tar);
					//v.dist = u.dist + 1;

					open.add(v);
				}

			}
		}
		//Gdx.app.debug("AutoWaypoint", "target not found");
	}

}

class Pnt implements Comparable<Pnt> {
	float dist;
	int x, y;
	
	public Pnt(float dist, int x, int y) {
		this.dist = dist;
		this.x = x;
		this.y = y;
	}

	@Override
	public int compareTo(Pnt arg0) {
		return (int) Math.signum(this.dist - arg0.dist);
	}
	
	public float dst(Pnt rhs) {
		//return Math.abs(rhs.x - this.x) + Math.abs(rhs.y - this.y);
		return (float) Math.sqrt((rhs.x - this.x) * (rhs.x - this.x) + (rhs.y - this.y) * (rhs.y - this.y));

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pnt other = (Pnt) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
			
			
}