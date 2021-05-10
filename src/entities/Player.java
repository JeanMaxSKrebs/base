package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import world.Camera;
import world.World;

public class Player extends Entity{

	public boolean left, right, up, down;
	public int right_dir = 1, left_dir = 2, up_dir = 3, down_dir = 4;
	public int dir = 1;
	public double speed = 5;
	
	private static int dodgeChance = 20;
	private static int armor = 0;
		
	private int qtdSprites = 4;
	private int frames = 0, maxFrames = 20, index = 0, maxIndex = (qtdSprites-1);
	private boolean moved;
	
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] downPlayer;
	private BufferedImage[] upPlayer;
	
	public double life = 100;
	public static double maxLife = 100;
	public double stamine = 100;
	public static double maxStamine = 100;


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
	
	public void tick() {
		setMoved(false);

		int midy = (int)(y + masky + mheight/2);
		int plusy = (int)(y + masky + mheight);
		int minusy = (int)(y + masky);
		
		int midx = (int)(x + maskx + mwidth/2);
		int plusx = (int)(x + maskx + mwidth);
		int minusx = (int)(x + maskx);
		
		
		if(right) {
			setMoved(true);
			dir = right_dir;
			if(World.isFree(plusx, midy, "right"))				
				x += speed;
			
		}
		else if(left) {
			setMoved(true);
			dir = left_dir;
			if(World.isFree(minusx, midy, "left"))
				x -= speed;
		}

		if(down) {
			setMoved(true);
			dir = down_dir;
			if(World.isFree(midx, plusy, "down"))
				y += speed;
		}
		else if(up) {
			setMoved(true);
			dir = up_dir;
			if(World.isFree(midx, minusy, "up"))
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
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH * 32 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT * 32 - Game.HEIGHT);
		
	}

	public void render(Graphics g) {
		if(dir == right_dir) {
			g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(dir == left_dir) {
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(dir == up_dir) {
			g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(dir == down_dir) {			
			g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		
//		g.setColor(Color.red);
//		g.fillRect(this.getX() + maskx - (int)speed - Camera.x, this.getY() + masky - (int)speed - Camera.y, mwidth, mheight);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth + (int)speed, mheight + (int)speed);
//
//		g.setColor(Color.black);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);

		
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
	public double getStamine() {
		return stamine;
	}
	public void setStamine(double stamine) {
		this.stamine = stamine;
	}
	public static double getMaxStamine() {
		return maxStamine;
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
	public boolean isMoved() {
		return moved;
	}
	public void setMoved(boolean moved) {
		this.moved = moved;
	}
}