package entities;

import java.awt.image.BufferedImage;

public class EnemyNormal extends Enemy {

	private static int dano = 10;
	private static int criticalChance = 10;
	private static int criticalDamage = 10;
	
	public EnemyNormal(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public static int getDano() {
		return dano;
	}

	public void setDano(int dano) {
		EnemyNormal.dano = dano;
	}

	public static int getCriticalChance() {
		return criticalChance;
	}

	public void setCriticalChance(int criticalChance) {
		EnemyNormal.criticalChance = criticalChance;
	}

	public static int getCriticalDamage() {
		return criticalDamage;
	}

	public void setCriticalDamage(int criticalDamage) {
		EnemyNormal.criticalDamage = criticalDamage;
	}

}
