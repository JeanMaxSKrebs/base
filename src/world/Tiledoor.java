package world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import base.Game;
import entities.Entity;

public abstract class Tiledoor extends Tile implements Door {

	public static BufferedImage TILE_NORMALDOOR = Game.spritesheet_Doors.getSprite(0, 0, 112, 112);
	public static BufferedImage TILE_SPECIALDOOR = Game.spritesheet_Doors.getSprite(224, 0, 112, 112);

	public Tiledoor(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

	public static boolean willCollide(Entity e1, Tiledoor e2, int speed) {
		Rectangle e1Mask = new Rectangle(e1.getX(), e1.getY(), e1.getWidth() + speed * 2, e1.getHeight() + speed * 2);
		Rectangle e2Mask = new Rectangle(e2.x, e2.y, 32, 32);
		return e1Mask.intersects(e2Mask);
	}

	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);

		g.setColor(Color.cyan);
		g.fillRect(x - Camera.x, y - Camera.y, 32, 32);
	}

}