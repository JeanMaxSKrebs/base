package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import world.Camera;

public class EnemyY extends Enemy {

	protected static int reloadingTime = 240, reload = 0;
	protected static boolean preparedAttack = true;
	
	public static BufferedImage[] ENEMY_Y;
	
	public EnemyY(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		ENEMY_Y = new BufferedImage[1];

		ENEMY_Y[0] = Game.spritesheet.getSprite(0, 224, 32, 32);
	}
	public void tick() {
		moved = false;
		iamAlive();
		
		preparadoAtacar();
		
		verificaMovimento();
	}
	
	public void preparadoAtacar() {
		if(isPreparedAttack() == false) {
			reloading();
		} else {
			reloading();

			Gosma gosma = new Gosma(this.getX(), this.getY(), 6, 6, null, "y");
			gosma.setMask(13, 13, 6, 6);
			Game.gosmas.add(gosma);		
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
		g.drawImage(ENEMY_Y[0], this.getX() - Camera.x, this.getY() - Camera.y, width, height, null);			
	}
			
	public static boolean isPreparedAttack() {
		return preparedAttack;
	}
	public static void setPreparedAttack(boolean preparedAttack) {
		EnemyY.preparedAttack = preparedAttack;
	}

}
