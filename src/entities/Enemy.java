package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
//import java.awt.Color;
//import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import world.Camera;
//import world.Camera;
import world.World;

public class Enemy extends Entity{
	
	

	private double speed = 1.7;
	private static int dano = 5;
	private int criticalChance = 10;
	private int criticalDamage = 10;
	private static int reloadingTime = 180, reload = 0;
	private static boolean preparedAttack = true;
	
	private int qtdSprites = 4;
	private int frames = 0, maxFrames = 60, index = 0;
//  private int maxIndex = (qtdSprites-1);
	
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = Game.random(qtdSprites);
	
	private int life = 1;
	
	private boolean moved;
	
	private BufferedImage[] sprites;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[qtdSprites];
		sprites[0] = Game.spritesheet.getSprite(0, 160, 32, 32);
		sprites[1] = Game.spritesheet.getSprite(0, 192, 32, 32);
		sprites[2] = Game.spritesheet.getSprite(32, 128, 32, 32);
		sprites[3] = Game.spritesheet.getSprite(0, 128, 32, 32);

		
	}
	
	public void tick() {
		moved = false;

		if(isPreparedAttack() == false) {
			reloading();
		} else if(isColliddingWithPlayer() == true) {
			reloading();
			Game.player.beingAttacked(this.danoCompleto(dano, this.criticalChance, this.criticalDamage));
		}
		
		switch (dir) {
		   case 0:
			   if(World.isFree((int)(x + speed), this.getY())
					   &&!isCollidding((int)(x + speed), this.getY())) {
				   moved = true;
					x+=speed;
					index = 0;
			   } else {
				   dir = Game.random(qtdSprites);
			   }

			   break;

		   case 1:
				if(World.isFree((int)(x - speed), this.getY())
						&&!isCollidding((int)(x - speed), this.getY())) {
					moved = true;
					x-=speed;
					index = 1;
				} else {
					   dir = Game.random(qtdSprites);
				}
				
				break;

		   case 2:
			   if(World.isFree(this.getX(), (int)(y + speed))
					   &&!isCollidding(this.getX(), (int)(y + speed))) {				   
				   moved = true;
				   y+=speed;
					index = 2;
			   } else {
				   dir = Game.random(qtdSprites);
			   }

			   break;
		           
		   case 3:
				if(World.isFree(this.getX(),  (int)(y - speed))
						&&!isCollidding(this.getX(),  (int)(y - speed))) {
					moved = true;
					y-=speed;
					index = 3;
				} else {
					   dir = Game.random(qtdSprites);
				}			
				 
				break;
		  default:
		}
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				dir = Game.random(qtdSprites);
			}
		}
		
		isColliddingPower();
		
		if(life<=0) {
			destroySelf();
			return;
		}

	}
	public void destroySelf() {
		Game.entities.remove(this);
	}

	public int danoCompleto(int dano, int criticalChance, int criticalDamage) {
		if(Game.random(Game.maximumCritic) <= criticalChance) {
			int danoCompleto = dano * 2 * (100 + criticalDamage) / 100;

			return danoCompleto;
		}
		
		return dano;
	}
	
	public void reloading() {

//		System.out.println(reload);
//		System.out.println(reloadingTime);
		if(reload >= reloadingTime) {			
			setPreparedAttack(true);
			reload = 0;
		} else {
			setPreparedAttack(false);
			reload++;
		}
	}
	
	public boolean isColliddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX(), this.getY(), World.TILE_SIZE, World.TILE_SIZE);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), World.TILE_SIZE, World.TILE_SIZE);
		
		return enemyCurrent.intersects(player);
	}
	
	public void isColliddingPower() {
		for(int i = 0; i < Game.powers.size(); i++) {
			Entity e = Game.powers.get(i);
			if(e instanceof Power) {
				if(Entity.isCollidding(this, e)) {
					life--;
					return;
				}
			}
			
//			if(e == this) {
//				continue;
//			}
//			Rectangle targetEnemy = new Rectangle(e.getX(), e.getY(), World.TILE_SIZE, World.TILE_SIZE);
//			if(enemyCurrent.intersects(targetEnemy)) {
//				return true;
//			}
			
		}
	}
	
	public boolean isCollidding(int xNext, int yNext) {
		Rectangle enemyCurrent = new Rectangle(xNext, yNext, World.TILE_SIZE, World.TILE_SIZE);
		for(int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			
			if(e == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX(), e.getY(), World.TILE_SIZE, World.TILE_SIZE);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
			
		}
		
		return false;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
//		g.setColor(Color.BLUE);
//		g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, World.TILE_SIZE, World.TILE_SIZE);
		
		
	}

	public static int getDano() {
		return dano;
	}

	public void setDano(int newDano) {
		Enemy.dano = newDano;
	}

	public static boolean isPreparedAttack() {
		return preparedAttack;
	}

	public void setPreparedAttack(boolean preparedAttack) {
		Enemy.preparedAttack = preparedAttack;
	}

	public static int getReloadingTime() {
		return reloadingTime;
	}

	public static void setReloadingTime(int reloadingTime) {
		Enemy.reloadingTime = reloadingTime;
	}

	public static int getReload() {
		return reload;
	}

	public static void setReload(int reload) {
		Enemy.reload = reload;
	}
	
}

