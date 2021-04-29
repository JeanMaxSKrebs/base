package entities;

import java.awt.image.BufferedImage;

public class EnemyStrong extends Enemy {
	
	private static int dano = 25;
	private static int criticalChance = 5;
	private static int criticalDamage = 10;

	public EnemyStrong(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

	public static int getDano() {
		return dano;
	}

	public void setDano(int dano) {
		EnemyStrong.dano = dano;
	}

	public static int getCriticalChance() {
		return criticalChance;
	}

	public void setCriticalChance(int criticalChance) {
		EnemyStrong.criticalChance = criticalChance;
	}

	public static int getCriticalDamage() {
		return criticalDamage;
	}

	public void setCriticalDamage(int criticalDamage) {
		EnemyStrong.criticalDamage = criticalDamage;
	}

	
	
}
