package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tileground extends Tile {

	public Tileground(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
//		g.setColor(Color.red);
//		g.fillRect(x - Camera.x, y - Camera.y, 32, 32);
	}
}