package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Normaldoor extends Tiledoor {

	public String type;

	public Normaldoor(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
//		g.setColor(Color.yellow);
//		g.fillRect(x - Camera.x, y - Camera.y, 32, 32);
	}
}