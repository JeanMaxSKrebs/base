package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Specialdoor extends Tiledoor {

	public Specialdoor(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

	}

	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
//		g.setColor(Color.yellow);
//		g.fillRect(x - Camera.x, y - Camera.y, 32, 32);
	}
}