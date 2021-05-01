package entities;

import java.awt.image.BufferedImage;

public class Boss extends Enemy {

	private static int dano = 10;
	private static int criticalChance = 10;
	private static int criticalDamage = 10;
	
	public Boss(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
}
