package world;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import base.Game;
import entities.Entity;


public abstract class Tiledoor extends Tile implements Door {
	
	public static BufferedImage TILE_NORMALDOOR = Game.spritesheet.getSprite(32, 224, 32, 32);
	public static BufferedImage TILE_SPECIALDOOR = Game.spritesheet.getSprite(96, 224, 32, 32);

	public String type;

	public Tiledoor(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
	}
	public static boolean isCollidding(Entity e1, Tiledoor e2) {
		Rectangle e1Mask = new Rectangle(e1.getX(), e1.getY(), e1.getWidth(), e1.getHeight());
		Rectangle e2Mask = new Rectangle(e2.x, e2.y, 32, 32);
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
		
//		g.setColor(Color.cyan);
//		g.fillRect(x - Camera.x, y - Camera.y, 32, 32);
	}
}