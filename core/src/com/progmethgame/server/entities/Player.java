package com.progmethgame.server.entities;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.progmethgame.client.graphic.component.StatusOverlay;
import com.progmethgame.client.graphic.component.HudOverlay;
import com.progmethgame.common.DisplayType;
import com.progmethgame.common.GameConfig;
import com.progmethgame.common.SoundType;
import com.progmethgame.common.context.GameContext;
import com.progmethgame.server.blocks.Block;
import com.progmethgame.server.blocks.Interactable;
import com.progmethgame.server.entities.bullets.Bullet;
import com.progmethgame.server.entities.bullets.TestBullet;
import com.progmethgame.server.entities.bullets.BurnBullet;
import com.progmethgame.server.entities.bullets.ConfuseBullet;
import com.progmethgame.server.entities.bullets.HookBullet;
import com.progmethgame.server.entities.bullets.SlowBullet;
import com.progmethgame.server.entities.bullets.StuntBullet;
import com.progmethgame.server.entities.bullets.TeleportBullet;
import com.progmethgame.server.entities.effects.StatusEffect;
import com.progmethgame.server.entities.guns.BurnGun;
import com.progmethgame.server.entities.guns.ConfuseGun;
import com.progmethgame.server.entities.guns.Gun;
import com.progmethgame.server.entities.guns.HookGun;
import com.progmethgame.server.entities.guns.SlowGun;
import com.progmethgame.server.entities.guns.StuntGun;
import com.progmethgame.server.entities.guns.TeleportGun;

/**Game player*/
public class Player extends Entity{
	
	enum Color {
		BLUE(DisplayType.PLAYER_BLUE_ICON, DisplayType.PLAYER_BLUE),
		RED(DisplayType.PLAYER_RED_ICON, DisplayType.PLAYER_RED),
		GREEN(DisplayType.PLAYER_GREEN_ICON, DisplayType.PLAYER_GREEN),
		ORANGE(DisplayType.PLAYER_ORANGE_ICON, DisplayType.PLAYER_ORANGE),
		PURPLE(DisplayType.PLAYER_PURPLE_ICON, DisplayType.PLAYER_PURPLE),
		YELLOW(DisplayType.PLAYER_YELLOW_ICON, DisplayType.PLAYER_YELLOW);
		
		private DisplayType top;
		private DisplayType front;
		
		private Color(DisplayType front, DisplayType top) {
			this.front = front;
			this.top = top;
		}
		
		public DisplayType getTop() {
			return this.top;
		}
		
		public DisplayType getFront() {
			return this.front;
		}
	};
	/** Status if the player is alive*/
	private boolean alive;
	
	/** Damage dealing to player per second*/
	private int dps;
	
	/**Health point*/
	private int hp;
	
	/** Effect that affect player*/
	private StatusEffect effect;
	
	/**integer for tick method*/
	private int tickCount;
	
	/**Gun Slot for player*/
	private Gun[] gunSlot;
	
	/**The gun that player is holding*/
	private Gun holdedGun;
	
	/**Index of the gun that player is holding*/
	private int gunIndex;
	
	/**Speed of move*/
	private float speed;
	
	/**Walk direction*/
	private Vector2 walkDirection;
	
	/**Status if player can move*/
	private boolean movable;
	
	/**Status if player confuse*/
	private boolean confuse;
	
	/**Status if player can shoot*/
	private boolean shootable;
	
	/**HUD for Player*/
	HudOverlay hud;
	
	/**health bar on player*/
	StatusOverlay healthOv;

	public Player(UUID gid) {
		super(gid, DisplayType.PLAYER);
		this.alive = true;
		this.speed = 4f;
		this.dps = 0;
		this.hp = 100;
		this.tickCount = 0;
		this.walkDirection = new Vector2();
		this.movable = true;
		this.confuse = false;
		this.shootable = true;
		
		this.facingDirection = new Vector2(1,0);
		this.gunSlot = new Gun[] { 
				new BurnGun(this), 
				new ConfuseGun(this), 
				new SlowGun(this), 
				new StuntGun(this),
				new HookGun(this),
				new TeleportGun(this)};
		
		this.gunIndex = 0;
		this.holdedGun = gunSlot[gunIndex];
		
		this.hud = new HudOverlay();
		
		this.healthOv = new StatusOverlay();
		
		this.overlays.add(healthOv);
		this.overlays.add(this.hud);
		
		
		setColor(
				Color.values()[
				               Math.abs((int) gid.getLeastSignificantBits()) 
				               % Color.values().length
				               ]);
	}
	/**
	 * set color of player
	 * @param color
	 */
	public void setColor(Color c) {
		this.hud.setPlayerIcon(c.getFront());
		this.setDisplay(c.getTop());
	}
	
	/**
	 * set player movable status
	 * @param movable
	 */
	public void setMovable(boolean movable) {
		this.movable = movable;
	}
	
	/**
	 * set player confuse status
	 * @param confuse
	 */
	public void setConfuse(boolean confuse) {
		this.confuse = confuse;
	}
	
	/**
	 * @return player's speed
	 */
	public float getSpeed() {
		return speed;
	}
	
	/**
	 * set player speed
	 * @param speed
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	/**
	 * get player damage per second
	 * @return
	 */
	public int getDps() {
		return dps;
	}
	
	/**
	 * set damage per second
	 * @param damage per second
	 */
	public void setDps(int dps) {
		this.dps = dps;
	}
	
	/**
	 * @return player's status effect
	 */
	public StatusEffect getEffect() {
		return effect;
	}

	/**
	 * set player's effect
	 * @param effect
	 */
	public void setEffect(StatusEffect effect) {
		//check if effect is null
		if(effect ==null) {
			if(this.effect != null) {
				//remove remaining effect
				this.effect.removeEffect(this);
			}
			this.effect = null;
			
		}else {
			if(this.effect!=null) {
					//remove remaining effect
					this.effect.removeEffect(this);
			}
			//set new effect
			this.effect = effect;
			//get effect
			this.effect.getEffect(this);
		}
	}
	
	/**
	 * deal damage to player
	 * @param damage
	 * @return amount of damage that deal to player
	 */
	public int dealDamge(int damage) {
		if (this.hp > damage) {
			this.hp -= damage;
			return damage;
		}else {
			int remainHp = this.hp;
			this.hp = 0;
			this.alive = false;
			this.velocity.setZero();
			this.movable = false;
			this.shootable = false;
			return remainHp;
		}
	}
	
	@Override
	public void tick(float delta) {
		//for 1 second
		if(tickCount == 0) {
			this.dealDamge(dps);
			if(this.effect != null && this.effect.decreaseDuration()) {
				this.effect.removeEffect(this);
				this.effect = null;
			}
		}
		this.tickCount++;
		this.tickCount %= (int) 1/GameConfig.SERVER_TICK_RATE;
		if(movable) {
			this.velocity.set(walkDirection.nor().scl(speed));
		}
		this.hud.setHealth(hp/100f);
		
		this.hud.setWeaponName(this.gunSlot[this.gunIndex].getName()+"\nStatus: "+this.gunSlot[this.gunIndex].getStatus());
		this.healthOv.setHealth(hp/100f);
		this.hud.setGunIcon(holdedGun.getBullet().getType());
		this.healthOv.setEffectIcon(effect != null ? effect.getDisplayType() : null);
		
		holdedGun.recharge(GameConfig.SERVER_TICK_RATE);
	}
	
	/**
	 * set player walk direction
	 * @param dir
	 */
	public void setWalkDirection(Vector2 dir) {
		if(confuse) {
			dir.set(dir.scl(-1));
		}
		walkDirection.set(dir);
		if (!dir.isZero()) {
			//set face direction of player
			this.facingDirection = dir.cpy().nor();
		}
	}
	
	/**
	 * @return player's face direction
	 */
	public Vector2 getFaceDirection() {
		return facingDirection;
	}
	
	/**
	 * @return alive
	 */
	public boolean isAlive() {
		return alive;
	}
	
	/**
	 * shoot gun 
	 */
	public void fire() {
		if(shootable) {
			holdedGun.shoot();
		}
	}
	
	/**
	 * swap player's holded gun
	 */
	public void swapGun() {
		gunIndex++;
		gunIndex %= gunSlot.length;
		holdedGun = gunSlot[gunIndex];
	}
	
	@Override
	public void onWalkOn(Block block) {
		if(block instanceof Interactable) {
			((Interactable) block).interact(this);
		}
	}
	
	@Override
	public void onCollideSolid(Block block) {
		if(block instanceof Interactable) {
			((Interactable) block).interact(this);
		}
	}
	

}