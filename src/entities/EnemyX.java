package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import world.Camera;

public class EnemyX extends Enemy {

	private static int dano = 10;
	private static int criticalChance = 10;
	private static int criticalDamage = 10;
	
	public EnemyX(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

	public void render(Graphics g) {
//		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
//		g.setColor(Color.yellow);
//		g.fillRect(x - Camera.x, y - Camera.y, 32, 32);
	}
	public static int getDano() {
		return dano;
	}

	public void setDano(int dano) {
		EnemyX.dano = dano;
	}

	public static int getCriticalChance() {
		return criticalChance;
	}

	public void setCriticalChance(int criticalChance) {
		EnemyX.criticalChance = criticalChance;
	}

	public static int getCriticalDamage() {
		return criticalDamage;
	}

	public void setCriticalDamage(int criticalDamage) {
		EnemyX.criticalDamage = criticalDamage;
	}
}
