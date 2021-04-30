package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import world.Camera;
import world.Normaldoor;
import world.Specialdoor;
import world.Tiledoor;
import world.World;

public class Player extends Entity{

	public boolean left, right, up, down;
	public int right_dir = 1, left_dir = 2, up_dir = 3, down_dir = 4;
	public int dir = 1;
	public double speed = 5;
	public static int keys = 0;
	public static int specialKeys = 0;
	public static int premium = 0;
	
	private static int dodgeChance = 20;
	private static int armor = 0;
	
	private boolean hasBagpack = false;
	
	private int qtdSprites = 4;
	private int frames = 0, maxFrames = 20, index = 0, maxIndex = (qtdSprites-1);
	private boolean moved;
	
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] downPlayer;
	private BufferedImage[] upPlayer;
	
	public double life = 100;
	public static double maxLife = 100;
	public double stamine = 0;
	public static double maxStamine = 3;
	
	public boolean usingPower = false;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
	
		rightPlayer = new BufferedImage[qtdSprites];
		leftPlayer = new BufferedImage[qtdSprites];
		upPlayer = new BufferedImage[qtdSprites];
		downPlayer = new BufferedImage[qtdSprites];
		
		for(int i=0; i<qtdSprites; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite((i*32), 0, 32, 32);			
		}
		
		for(int i=0; i<qtdSprites; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite((i*32), 32, 32, 32);			
		}
		for(int i=0; i<qtdSprites; i++) {
			upPlayer[i] = Game.spritesheet.getSprite((i*32), 64, 32, 32);			
		}
		
		for(int i=0; i<qtdSprites; i++) {
			downPlayer[i] = Game.spritesheet.getSprite((i*32), 96, 32, 32);
		}

	}
	public int danoRecebido(int dano) {
		
		int danoRecebido = dano - armor;
		
		if(Game.random(Game.maximumDodge) <= dodgeChance) {
			return 0;
		}
		
		return danoRecebido;
	}
	
	public boolean beingAttacked(int dano) {
		int danoReal = danoRecebido(dano);
		
		if(danoReal > 0) {
			life = life - danoReal;
		}
		
//		System.out.println(danoReal);
//		System.out.println(life);
		if(iamDead())
			Game.gameState = "GAME_OVER";
		
		return true;
	}
	public boolean iamDead() {
		if(life <= 0) {
			life = 0;
			return true;
		}
		
		return false;
	}
	
	public void checkItems() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(hasBagpack)
				speed = 4;
			
			if(e instanceof BagPack) {
				if(Entity.isCollidding(this, e)) {
					hasBagpack = true;
					armor = armor + 6;
					
					Game.entities.remove(i);

					return;
				}
			}

			if(e instanceof HpBag) {
				if(Entity.isCollidding(this, e)) {
					if(life < maxLife) {
						life += HpBag.life;
						if(life > maxLife)
							life = maxLife;
						Game.entities.remove(i);
					}
					return;
				}
			}
			if(e instanceof StamineBag) {
				if(Entity.isCollidding(this, e)) {
					if(stamine < maxStamine) {
						stamine++;
						Game.entities.remove(i);					
					}
					
					return;
				}
			}

			// itens q são guardados
			if(hasBagpack) {					
				if(e instanceof Key) {
					if(Entity.isCollidding(this, e)) {
						keys++;
						Game.entities.remove(i);
						return;
					}
				}
				if(e instanceof SpecialKey) {
					if(Entity.isCollidding(this, e)) {
						specialKeys++;
						Game.entities.remove(i);
						return;
					}
				}
				if(e instanceof Premium) {
					if(Entity.isCollidding(this, e)) {
						premium++;
						Game.entities.remove(i);
						return;
					}
				}
			}
		}
	}
	
	public void checkDoor(){
		
		
		for (int i = 0; i < Game.tiledoors.size(); i++) {
			Tiledoor t = Game.tiledoors.get(i);

			if(Tiledoor.isCollidding(this, t)) {
				speed = 0;

				if(t instanceof Normaldoor) {
					if(keys > 0) {
						keys--;
						Game.tiledoors.remove(i);
					} else {
						speed = 5;
						if(dir == right_dir) {
							 x =  x - speed;
						 } else if(dir == left_dir){
							 x = x + speed;
						 } else if(dir == down_dir) {
							 y = y - speed;
						 } else if(dir == up_dir) {
							 y = y + speed;
						 }
					}
				} else if(t instanceof Specialdoor) {
					if(specialKeys > 0) {
						specialKeys--;
						Game.tiledoors.remove(i);
					} else {
						speed = 5;
						if(dir == right_dir) {
							 x =  x - speed;
						 } else if(dir == left_dir){
							 x = x + speed;
						 } else if(dir == down_dir) {
							 y = y - speed;
						 } else if(dir == up_dir) {
							 y = y + speed;
						 }
					}
				} 
				return;

			}

		}
	}
	
	public void tick() {
		
		if(usingPower) {
			usingPower = false;
			if(stamine > 0) {
				stamine--;
				 usingPower = false;
				 int dx = 0;
				 int dy = 0;
				 if(dir == right_dir) {
					 dx = 1;
				 } else if(dir == left_dir){
					 dx = -1;
				 } else if(dir == down_dir) {
					 dy = 1;
				 } else if(dir == up_dir) {
					 dy = -1;
				 }
				 
				 Power power = new Power(this.getX(), this.getY(), 32, 32, null, dx, dy);
				 Game.powers.add(power);
			}
		}


		setMoved(false);

		int plusx = (int)(x + speed);
		int minusx = (int)(x - speed);
		int plusy = (int)(y + speed);
		int minusy = (int)(y - speed);
		
		if(right && World.isFree(plusx, this.getY())) {
			setMoved(true);
			dir = right_dir;
			x += speed;
		}
		else if(left && World.isFree(minusx, this.getY())) {
			setMoved(true);
			dir = left_dir;
			x -= speed;
		}
		
		if(down && World.isFree(this.getX(), plusy)) {
			setMoved(true);
			dir = down_dir;		
			y += speed;
		}
		else if(up && World.isFree(this.getX(),  minusy)) {
			setMoved(true);
			dir = up_dir;
			y -= speed;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
		}
		checkItems();
		checkDoor();
			
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH * 32 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT * 32 - Game.HEIGHT);
		
	}

	public void render(Graphics g) {
		if(dir == right_dir) {
			g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			if(hasBagpack) {
				g.drawImage(Entity.BAGPACK_RIGHT, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}
		else if(dir == left_dir) {
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			if(hasBagpack) {
				g.drawImage(Entity.BAGPACK_LEFT, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}			
		}
		else if(dir == up_dir) {
			g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			if(hasBagpack) {
				g.drawImage(Entity.BAGPACK_UP, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}			
		}
		
		else if(dir == down_dir)
			g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		
		g.setColor(Color.black);
		g.fillRect(this.getX() + Game.player.maskx - Camera.x, this.getY() + Game.player.masky - Camera.y, mwidth, mheight);

		
	}
	public double getLife() {
		return life;
	}
	public void setLife(int newLife) {
		this.life = newLife;
	}
	public double getMaxLife() {
		return maxLife;
	}
	public void setMaxLife(int newMaxLife) {
		Player.maxLife = newMaxLife;
	}
	public static int getDodgeChance() {
		return dodgeChance;
	}
	public void setDodgeChance(int newDodgeChance) {
		Player.dodgeChance = newDodgeChance;
	}
	public static int getArmor() {
		return armor;
	}
	public void setArmor(int newArmor) {
		Player.armor = newArmor;
	}
	public static int getKeys() {
		return keys;
	}
	public static void setKeys(int keys) {
		Player.keys = keys;
	}
	public double getStamine() {
		return stamine;
	}
	public void setStamine(double stamine) {
		this.stamine = stamine;
	}
	public static double getMaxStamine() {
		return maxStamine;
	}
	public static void setMaxStamine(double maxStamine) {
		Player.maxStamine = maxStamine;
	}
	public boolean isMoved() {
		return moved;
	}
	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	
}















