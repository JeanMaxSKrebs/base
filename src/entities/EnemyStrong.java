package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import world.Camera;

public class EnemyStrong extends Enemy {
	
	private static int dano = 25;
	private static int criticalChance = 5;
	private static int criticalDamage = 10;
	
	protected static int reloadingTime = 300, reload = 0;
	protected static boolean preparedAttack = true;
	
	public static BufferedImage[] STRONGENEMY;

	public EnemyStrong(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		STRONGENEMY = new BufferedImage[4];

		for(int i=0; i<qtdDirecoes; i++) {
			STRONGENEMY[i] = Game.spritesheet.getSprite((i*32), 256, 32, 32);			
		}	
	}

	public void tick() {
		moved = false;
		iamAlive();
		
		preparadoAtacar();
		
		movimentar();
		verificaMovimento();
	}
	
	public void preparadoAtacar() {
		if(isPreparedAttack() == false) {
			reloading();
		} else if(isColliddingWithPlayer() == true) {
			reloading();

			Game.player.beingAttacked(this.danoCompleto(dano, criticalChance, criticalDamage));
		}
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
	
	public void render(Graphics g) {
		if(dir == right_dir) {				
			g.drawImage(STRONGENEMY[0], this.getX() - Camera.x, this.getY() - Camera.y, null);			
		} else if(dir == left_dir) {
			g.drawImage(STRONGENEMY[1], this.getX() - Camera.x, this.getY() - Camera.y, null);			
		} else if(dir == up_dir) {
			g.drawImage(STRONGENEMY[2], this.getX() - Camera.x, this.getY() - Camera.y, null);			
		} else if(dir == down_dir) {
			g.drawImage(STRONGENEMY[3], this.getX() - Camera.x, this.getY() - Camera.y, null);			
		}
	}
	
	public static boolean isPreparedAttack() {
		return preparedAttack;
	}
	public static void setPreparedAttack(boolean preparedAttack) {
		EnemyStrong.preparedAttack = preparedAttack;
	}
	
	
}
