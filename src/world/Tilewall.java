package world;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tilewall extends Tile{

	public Tilewall(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
//		g.setColor(Color.red);
//		g.fillRect(x - Camera.x+10, y - Camera.y, 20, 20);
	}
}