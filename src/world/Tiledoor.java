package world;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entities.Entity;

public class Tiledoor extends Tile {

	public Tiledoor(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
	}
	
	public static boolean isCollidding(Entity e1, Tile e2) {
		Rectangle e1Mask = new Rectangle(e1.getX(), e1.getY(), e1.getWidth(), e1.getHeight());
		Rectangle e2Mask = new Rectangle(e2.x, e2.y, 32, 32);
		return e1Mask.intersects(e2Mask);
	}
}
