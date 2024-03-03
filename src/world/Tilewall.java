package world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;

public class Tilewall extends Tile {

	public static BufferedImage[] TILE_WALL;

	public Tilewall(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

	}

	public void render(Graphics g) {

		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);

//		g.setColor(Color.red);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}

}
