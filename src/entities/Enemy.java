package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import base.Game;
import world.Camera;
import world.World;

public abstract class Enemy extends Entity {
	protected double speed = 1.7;
	protected static int dano;
	protected int criticalChance;
	protected int criticalDamage;
	protected static int reloadingTime, reload = 0;
	protected static boolean preparedAttack = true;
	
	protected int frames = 0, maxFrames = 60, index = 0;
	
	protected int qtdDirecoes = 4;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = Game.random(qtdDirecoes);
	
	protected int life = 1;
	
	protected boolean moved;
	
	public static BufferedImage NORMALENEMY = Game.spritesheet.getSprite(0, 224, 32, 32);
	public static BufferedImage STRONGENEMY = Game.spritesheet.getSprite(0, 224, 32, 32);
	public static BufferedImage ENEMY_X = Game.spritesheet.getSprite(0, 224, 32, 32);
	public static BufferedImage ENEMY_Y = Game.spritesheet.getSprite(0, 224, 32, 32);
	public static BufferedImage BOSS = Game.spritesheet.getSprite(0, 224, 32, 32);
	

	//normal
//	private BufferedImage[] rightNormalEnemy;
//	private BufferedImage[] leftNormalEnemy;
//	private BufferedImage[] upNormalEnemy;
//	private BufferedImage[] downNormalEnemy;
		
	//strong
//	public static BufferedImage[] STRONGENEMY;
	
//Game.spritesheet.getSprite(0, 256, 32, 32);
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public void tick() {
		reloadingTime = 30 * Game.enemies.size();
		moved = false;

		if(isPreparedAttack() == false) {
			reloading();
		} else if(isColliddingWithPlayer() == true) {
			reloading();
			if(this instanceof EnemyNormal) {
				dano = EnemyNormal.getDano();
				criticalChance = EnemyNormal.getCriticalChance();
				criticalDamage = EnemyNormal.getCriticalDamage();
			} else if(this instanceof EnemyStrong) {
				dano = EnemyStrong.getDano();
				criticalChance = EnemyStrong.getCriticalChance();
				criticalDamage = EnemyStrong.getCriticalDamage();
			} else if(this instanceof EnemyX) {
				dano = EnemyX.getDano();
				criticalChance = EnemyX.getCriticalChance();
				criticalDamage = EnemyX.getCriticalDamage();
			} else if(this instanceof EnemyY) {
				dano = EnemyY.getDano();
				criticalChance = EnemyY.getCriticalChance();
				criticalDamage = EnemyY.getCriticalDamage();
			} else if(this instanceof Boss) {
				
			}

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
				   dir = Game.random(qtdDirecoes);
			   }

			   break;

		   case 1:
				if(World.isFree((int)(x - speed), this.getY())
						&&!isCollidding((int)(x - speed), this.getY())) {
					moved = true;
					x-=speed;
					index = 1;
				} else {
					   dir = Game.random(qtdDirecoes);
				}
				
				break;

		   case 2:
			   if(World.isFree(this.getX(), (int)(y + speed))
					   &&!isCollidding(this.getX(), (int)(y + speed))) {				   
				   moved = true;
				   y+=speed;
					index = 2;
			   } else {
				   dir = Game.random(qtdDirecoes);
			   }

			   break;
		           
		   case 3:
				if(World.isFree(this.getX(),  (int)(y - speed))
						&&!isCollidding(this.getX(),  (int)(y - speed))) {
					moved = true;
					y-=speed;
					index = 3;
				} else {
					   dir = Game.random(qtdDirecoes);
				}			
				 
				break;
		  default:
		}
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				dir = Game.random(qtdDirecoes);
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
		Game.enemies.remove(this);
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
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
		
//		g.setColor(Color.cyan);
//		g.fillRect(x - Camera.x, y - Camera.y, 32, 32);
	}
	
//	public void render(Graphics g) {
//		if(this instanceof EnemyNormal) {
//			if(dir == right_dir) {
//				g.drawImage(rightNormalEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
//			} else if(dir == left_dir) {
//				g.drawImage(leftNormalEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
//			} else if(dir == up_dir) {
//				g.drawImage(upNormalEnemy[0], this.getX() - Camera.x, this.getY() - Camera.y, null);	
//			} else if(dir == down_dir) {				
//				g.drawImage(downNormalEnemy[0], this.getX() - Camera.x, this.getY() - Camera.y, null);
//			}
//			
//		} else if(this instanceof EnemyStrong) {
//			if(dir == right_dir) {				
//				g.drawImage(Enemy.STRONGENEMY[0], this.getX() - Camera.x, this.getY() - Camera.y, null);			
//			} else if(dir == left_dir) {
//				g.drawImage(Enemy.STRONGENEMY[1], this.getX() - Camera.x, this.getY() - Camera.y, null);			
//			} else if(dir == up_dir) {
//				g.drawImage(Enemy.STRONGENEMY[2], this.getX() - Camera.x, this.getY() - Camera.y, null);			
//			} else if(dir == down_dir) {
//				g.drawImage(Enemy.STRONGENEMY[3], this.getX() - Camera.x, this.getY() - Camera.y, null);			
//			} 
//		} else {
//			g.drawImage(Enemy.ENEMY[0], this.getX() - Camera.x, this.getY() - Camera.y, null);			
//		}
//		
//
//		
//		
////		g.setColor(Color.blue);
////		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
//		
//		
//	}

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

